/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsolver;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Uses breadth-first search.
 *
 * @author kaan
 */
public class SolutionFinder {

    private int CURRENT_ROOT_STATE = 0;
    private ArrayList searchHistory;
    private static final int boatSize = 2;

    public SolutionFinder() {
        searchHistory = new ArrayList();
    }

    private void addStateToHistory( String stateName, State parent,
            int cannibalNum, int missionaryNum ) {
        int boatDirection;
        if ( parent.side ) {
            boatDirection = -1; // if boat is on the west side
        } else {
            boatDirection = 1; // if boat is on the east side
        }

        String newStateName = parent.getStateName() + stateName;

        State newState = new State( parent.missionaryNum
                + boatDirection * missionaryNum,
                parent.cannibalNum + boatDirection * cannibalNum, !parent.side,
                newStateName, parent.getStateLevel() + 1, parent );

        addStateToHistory( newState );

    }

    private void addStateToHistory( State newState ) {

        if ( newState.invalidState() ) { //&& newState.equalsToAnyPrevStates()
            return;
        }

        searchHistory.add( newState );
    }

    /**
     * Main method that basically does all the work. Compares startState with 
     * the endState and if it is the end state, store it in solutions ArrayList
     * The ones that are not equal to endState gets stored in searchHistory
     * 
     * @param startState
     * @param endState
     * @return an arraylist of all succeeding solutions
     */
    public ArrayList getSolutionStates( State startState, State endState ) {

        int solutionLevel = 0;
        boolean allOptimalSolutionsFound = false;
        boolean foundFirstSolution = false;

        ArrayList solutions;
        //Queue allPaths; might add
        solutions = new ArrayList();

        //Add StartState to the Search History
        addStateToHistory( startState );

        //loop through until all the solutions have been found
        while ( searchHistory.size() > 0 && !allOptimalSolutionsFound ) {

            State current = (State) (searchHistory.get( CURRENT_ROOT_STATE ));
            searchHistory.remove( CURRENT_ROOT_STATE );
            if ( current.equals( endState ) ) {
                if ( foundFirstSolution ) {
                    if ( current.getStateLevel() <= solutionLevel ) {
                        System.out.println( "state: " + current.getStateLevel() );
                        solutions.add( current );
                    } else {
                        allOptimalSolutionsFound = true;
                    }
                } else {
                    foundFirstSolution = true;
                    solutionLevel = current.getStateLevel();
                    solutions.add( current );
                }
            } else if ( current.getStateLevel() == 15 ) { // if no solution is found // max state level in a 4c/m problem is? //what does state level even represents here!?!?!?
//                for ( Object k : searchHistory ) {
//                    if ( ((State) k).equalsToCM( 4, 4, true ) ) {
//                        System.out.println( "*************" );
//                    }
//                    
//                    if ( current.getStateLevel() <= solutionLevel ) {
//                        ((State) k).print();
//                        solutionLevel = ((State)k).getStateLevel();
//                    }
//
//                }
                return solutions;
            } else {
                generateSuccessors( current );
            }

        }
        return solutions;
    }

    private void generateSuccessors( State current ) {

        int cannibalNum;
        int missionaryNum;
        int stateName = 1;

        //loop through all possible combinations
        for ( int m = 0; m <= boatSize; m++ ) {
            for ( int c = 0; c <= boatSize; c++ ) {

                if ( m == 0 && c == 0 ) {
                    continue;
                }
                if ( m + c > boatSize ) {
                    break;
                }

                missionaryNum = m;
                cannibalNum = c;
                addStateToHistory( "_" + stateName++, current,
                        missionaryNum, cannibalNum );
            }
        }
    }

}
