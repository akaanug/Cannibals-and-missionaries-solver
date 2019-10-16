/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsolver;

/**
 *
 * @author kaan
 */
public class State {

    public int missionaryNum;
    public int cannibalNum;
    public boolean side;
    private static int eachAtStart = 20;
    private String name;
    private State previousState;
    private int stateLevel = 0;

    /**
     * For the root state( no previous state )
     *
     * @param missionaryNum
     * @param cannibalNum
     * @param side
     * @param name
     * @param stateLevel
     */
    public State( int missionaryNum, int cannibalNum, boolean side,
            String name, int stateLevel ) {

        this.missionaryNum = missionaryNum;
        this.cannibalNum = cannibalNum;
        this.name = name;
        this.stateLevel = stateLevel;
        this.side = side;

    }

    /**
     * @param missionaryNum
     * @param cannibalNum
     * @param side
     * @param name
     * @param stateLevel
     * @param previousState
     */
    public State( int missionaryNum, int cannibalNum, boolean side,
            String name, int stateLevel, State previousState ) {

        this.missionaryNum = missionaryNum;
        this.cannibalNum = cannibalNum;
        this.name = name;
        this.stateLevel = stateLevel;
        this.side = side;
        this.previousState = previousState;

    }

    public int getStateLevel() {
        return this.stateLevel;
    }

    public String getStateName() {
        return this.name;
    }

    public void printThisNode() {
        int whichSide;
        if ( side ) {
            whichSide = 1;
        } else {
            whichSide = 0;
        }
        System.out.println( missionaryNum + " " + cannibalNum + " " + whichSide );
    }

    public void print() {
        if ( previousState != null ) {
            previousState.print();
        }

        int whichSide;
        if ( side ) {
            whichSide = 1;
        } else {
            whichSide = 0;
        }

        System.out.println( missionaryNum + "M"
                + cannibalNum + "C"
                + whichSide );
    }

    public boolean equals( State stateToCheck ) {
        return (missionaryNum == stateToCheck.missionaryNum
                && cannibalNum == stateToCheck.cannibalNum
                && side == stateToCheck.side);
    }

    public boolean invalidState() {

        if ( cannibalNum < 0 || missionaryNum < 0 || missionaryNum > eachAtStart
                || cannibalNum > eachAtStart ) {
            return true;
        }

        //if the num of cannibals at west are more than missionaries
        if ( missionaryNum < cannibalNum && missionaryNum > 0 ) {
            return true;
        }

        //if the num of cannibals at east are more than missionaries
        if ( (eachAtStart - missionaryNum < eachAtStart - cannibalNum)
                && (eachAtStart - missionaryNum > 0) ) {
            return true;
        }

        return false;

    }

    public boolean equalsToAnyPrevStates() {
        if ( previousState == null ) {
            return false;
        }
        if ( previousState.cannibalNum == this.cannibalNum
                && previousState.missionaryNum == this.missionaryNum
                && previousState.side == this.side ) {
            System.out.println( "equal" );
            return true;
        } else {
            return previousState.equalsToAnyPrevStates();
        }
    }

    public boolean equalsToState( State s ) {
        return (s.cannibalNum == this.cannibalNum
                && s.missionaryNum == this.missionaryNum
                && s.side == this.side);
    }

}
