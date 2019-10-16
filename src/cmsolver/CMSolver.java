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

        startState = new State( 20, 20, true, "Root", 0 );
        endState = new State( 0, 0, false, "Goal", 999999999 );

        ArrayList solution = sf.getSolutionStates( startState, endState );

        printSolutions( solution );
    }

    private static void printSolutions( ArrayList solution ) {
        //Are there any solutions
        if ( solution.isEmpty() ) {
            System.out.println( "\n\nNO SOLUTIONS HAVE BEEN FOUND\r\n" );
        } else {
            int Solfound = 1;
            //show the Solutions
            for ( int i = 0; i < solution.size(); i++ ) {
                State s = (State) solution.get(i);
                System.out.println( "=====FOUND SOLUTION ["
                        + Solfound++ + "]=====\r\n" );
                System.out.println( "This solution was found at level ["
                        + s.getStateLevel() + "]\r\n" );
                s.print();
                System.out.println( "\r\n" );
            }
        }
    }

}
