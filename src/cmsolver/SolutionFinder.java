/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsolver;

import java.util.ArrayList;

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

    /**
     * Main method that basically does all the work. Compares startState with
     * the endState and if it is the end state, store it in solutions ArrayList
     *
     * @param startState
     * @param endState
     * @return an arraylist of all succeeding solutions
     */
    public ArrayList getSolutions( State startState, State endState ) {

        int solutionLevel = 0;
        boolean allOptimalSolutionsFound = false;
        boolean foundFirstSolution = false;

        ArrayList solutions;
        solutions = new ArrayList();

        //Add StartState to the Search History
        addStateToHistory( startState );

        startState.printThisState();
        System.out.println( "---------\nNext State Level: ( Remove the first state, "
                + "print the remaining state(s)(if there is any), then search and print removed state's child(s)(if there is any). )\n-----------" );

        //loop through until all the solutions have been found
        while ( searchHistory.size() > 0 && !allOptimalSolutionsFound ) {

            State current = (State) (searchHistory.get( CURRENT_ROOT_STATE ));
            searchHistory.remove( CURRENT_ROOT_STATE );

            if ( current.equalsToState( endState ) ) {
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
            } else {
                generateNewStates( current );
            }

        }
        return solutions;
    }

    /**
     * checks if the current state is equal to any other states in the path
     * allows to search through states without loops
     *
     * @return true if it is equal to any other parent state
     * @param current
     */
    private boolean equalToAnyParent( State current ) {

        if ( current.getStateLevel() == 0 ) { //if current is the starting state
            return false;
        }

        State prevStates = current.previousState;

        while ( prevStates.getStateLevel() != 0 ) {

            if ( current.equalsToState( prevStates ) ) {
                return true;
            }

            prevStates = prevStates.previousState;

            if ( current.equalsToState( prevStates ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * adds appropriate states into stateHistory
     *
     * @return true if added successfully
     * @param stateName
     * @param parent
     * @param cannibalNum
     * @param missionaryNum
     */
    private boolean addStateToHistory( String stateName, State parent,
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

        if ( equalToAnyParent( newState ) || newState.invalidState() ) {
            return false;
        }
        searchHistory.add( newState );

        return true;

    }

    //to add the first state
    private void addStateToHistory( State newState ) {

        if ( newState.invalidState() ) {
            return;
        }

        searchHistory.add( newState );
    }

    /**
     * Generate all children of the current state.
     *
     * @param current
     */
    private void generateNewStates( State current ) {

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

                // to label stateNames accordingly
                if ( !addStateToHistory( "_" + stateName++, current,
                        missionaryNum, cannibalNum ) ) {
                    stateName--;
                }

            }
        }

        //prints the searchHistory for tracing purpose
        searchHistory.forEach( ( k ) -> {
            ((State) k).printThisState();
        } );
        System.out.println( "---------\nNext State Level:\n-----------" );
    }

}
