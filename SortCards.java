package AlgoHW5;/*
 * AlgoHW5.SortCards.java
 *
 * Version:
 *     $2$
 */

/**
 * CSCI-665
 *
 *  Aim:  Implementations of a dynamic programming algorithm that
 *        determines the minimum number of cards that need to be
 *        moved in order to get the whole set of n cards (1 to n) in
 *        sorted, ascending order.
 *
 *   Complexity of our Implementation: O( n ^ 2 )
 *
 *   @author: Omkar Morogiri,om5692
 *   @author: Vinay Jain,vj9898
 *
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

// main class
public class SortCards {

    // main method
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ));
        int n = Integer.parseInt( br.readLine() ); // read input

        String[] input = br.readLine().strip().split(" ");

        int[] A = new int[n + 1];
        A[0] = 0;
        for( int i = 1; i < n + 1 ; i++ ){
            A[i] = Integer.parseInt( input[i-1] ); // store integer inputs in original array "A"
        }

        // System.out.println(A);


        // below loop implements Longest Increasing Subsequence dp algorithm
        int[] S = new int[ n + 1 ]; // dp array
        S[0] = 0;
        for( int i = 1; i < n + 1; i++ ){
            int max = returnMax( S, A, i ); // find maximum count for valid array entries
//            System.out.println("MAX = " + max);
            S[i] = 1 + max;
//            System.out.print( A[i] + " " );
//            System.out.println( S[i] + " " );
//            System.out.println("--------");
        }
        int max = Arrays.stream( S ).max().getAsInt(); // calculate max count of LIS length

        // output count of elements that needs to be shifted in order to achieve the desired output
        System.out.println( n - max );
    }

    // calculate max value in dp array before the element under consideration
    // ensuring that corresponding entries in array A is ledd than the element under consideration
    private static int returnMax(int[] S, int[] A, int limit) {
        int max = 0;
        for( int i = 1; i < limit ; i++ ){
            if( A[i] < A[limit] ){
                if(max < S[i]){
                    max = S[i];
                }
            }
        }
        return max;
    }

}
