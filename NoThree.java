package AlgoHW5;/*
 * AlgoHW5.NoThree.java
 *
 * Version:
 *     $2$
 */

/**
 * CSCI-665
 *
 *  Aim:  Implementations of a dynamic programming algorithm that
 *        determines maximum possible sum of subsequence in a given array
 *        such that there are no 3 consecutive elements from the
 *        original sequence.
 *
 *   Complexity of our Implementation: O(n)
 *
 *   @author: Omkar Morogiri,om5692
 *   @author: Vinay Jain,vj9898
 *
 *
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;

// Main Class where program execution begins
public class NoThree {

    // main method
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        int n = Integer.parseInt( br.readLine() ); // no. of inputs

        String[] input = br.readLine().strip().split(" "); // input as string

        int i = 1;
        int[] A = new int[ n + 1 ]; // original array
        A[0] = 0;
        while( i <= n ){
            A[i] = Integer.parseInt( input[ i - 1 ].strip() ); // storing int values in original array "A"
            i++;
        }

        int[] S = new int[ n + 1 ]; // dp array
        S[0] = 0;  // base case
        for( i = 1; i <= n; i++ ){
            if( i == 1 ){ // base case
                S[i] = A[i];
            }
            else if( i == 2 ){ // base case
                S[i] = A[ i - 1 ] + A[i];
            }
            else if( i >= 3 ){
                S[i] = Math.max( Math.max( A[ i - 1 ] + S[ i - 3 ], S[ i - 2 ] ) + A[i], S[ i - 1 ] );
            }
        }

        // print result stored at last index
        System.out.println( S[n] );

    }

}
