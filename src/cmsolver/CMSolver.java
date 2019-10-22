/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmsolver;

import java.util.ArrayList;

/**
 *
 * @author kaan
 */
public class CMSolver {

    /**
     * @param args the command line arguments
     */
    public static void main( String[] args ) {

        SolutionFinder sf;
        sf = new SolutionFinder();
        State startState;
        State endState;

        
        //you also need to change eachAtStart from the State class
        //if you want to test with other variables
        startState = new State( 4, 4, true, "Root", 0 );
        endState = new State( 0, 0, false, "Goal", 999999999 );

        ArrayList solution = sf.getSolutions( startState, endState );

        printSolutions( solution );
    }

    private static void printSolutions( ArrayList solution ) {
        //Are there any solutions
        if ( solution.isEmpty() ) {
            System.out.println( "\n\nNO SOLUTIONS!\n\n" );
        } else {
            int solutionCount = 1;
            //show the Solutions
            for ( int i = 0; i < solution.size(); i++ ) {
                State s = (State) solution.get(i);
                System.out.println( "******FOUND SOLUTION !("
                        + solutionCount++ + ")******\n\n" );
                System.out.println( "Solution found at state level: ("
                        + s.getStateLevel() + ")\n\n" );
                s.print();
                System.out.println( "\n\n" );
            }
        }
    }

}
