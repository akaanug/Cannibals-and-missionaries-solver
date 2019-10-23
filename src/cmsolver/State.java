/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsolver;

/**
 * @author kaan
 */
public class State {

    public int missionaryNum;
    public int cannibalNum;
    public boolean side; //west if true
    public static int eachAtStart = 4;
    private String name;
    public State previousState;
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

    public void printThisState() {
        int whichSide;
        if ( side ) {
            whichSide = 1;
        } else {
            whichSide = 0;
        }
        System.out.println( missionaryNum + "M"
                + cannibalNum + "C" + whichSide + " -> " + name );
    }

    /**
     * Prints all states recursively
     */
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

    public void setEach( int amount ) {
        eachAtStart = amount;
    }

    /**
     * Compare with another state's m, c and side(boat location).
     *
     * @param stateToCheck
     * @return
     */
    public boolean equalsToState( State stateToCheck ) {
        return (missionaryNum == stateToCheck.missionaryNum
                && cannibalNum == stateToCheck.cannibalNum
                && side == stateToCheck.side);
    }

    /**
     * Checks if the state is appropriate.
     *
     * @return
     */
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

}
