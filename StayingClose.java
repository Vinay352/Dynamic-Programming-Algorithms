package AlgoHW5;/*
 * AlgoHW5.StayingClose.java
 *
 * Version:
 *     $2$
 */

/**
 * CSCI-665
 *
 *  Aim:  Implementations of a dynamic programming algorithm that
 *        determines the common subsequence between 2 different arrays
 *        with the largest sum such that their partial sum never differs
 *        by more than | 1 | (mod of 1) and their final sum should be equal.
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

public class StayingClose {
    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        int n = Integer.parseInt( br.readLine().strip() );

        String[] input = br.readLine().strip().split(" "); // input as string

        int i = 0;
        int[] A = new int[ n ]; // original A array
        while( i < n ){
            A[i] = Integer.parseInt( input[ i ].strip() ); // storing int values in original array "A"
            i++;
        }

        input = br.readLine().strip().split(" ");

        i = 0;
        int[] B = new int[ n ]; // original A array
        while( i < n ){
            B[i] = Integer.parseInt( input[ i ].strip() ); // storing int values in original array "B"
            i++;
        }

//        print1d(A , n);
//        print1d(B , n);

        int[][][] dp = new int[ n + 1 ][ n + 1 ][3];
        dp = initialize3D(dp, n+1);

        // partial sum 0 -> index 0
        // partial sum 1 -> index 1
        // partial sum 2 -> index -1

        for(i = 1; i < n + 1; i++){
            for(int j = 1; j < n + 1; j++){
                if ( A[ i - 1 ] == B[ j - 1 ] ){
                    dp[i][j][0] = dp[i-1][j-1][0] + A[i - 1]; // 0
                    dp[i][j][1] = dp[i-1][j-1][1] + ( ( dp[i-1][j-1][1] != 0 ? 1 : 0) * A[i - 1] ); // 1
                    dp[i][j][2] = dp[i-1][j-1][2] + ( ( dp[i-1][j-1][2] != 0 ? 1 : 0) * A[i - 1] ); // -1
                }
                else if( A[ i - 1 ] - B[ j - 1 ] == 1 ){
                    dp[i][j][0] = Math.max( Math.max( dp[i-1][j][0], dp[i][j-1][0] ), dp[i-1][j-1][2] + ( ( dp[i-1][j-1][2] != 0 ? 1 : 0) * A[i - 1] ) ); // 0
                    dp[i][j][1] = Math.max( Math.max( dp[i-1][j][1], dp[i][j-1][1] ), dp[i-1][j-1][0] + A[i - 1]); // 1
                    dp[i][j][2] = Math.max( dp[i-1][j][2], dp[i][j-1][2] ); // -1
                }
                else if( A[ i - 1 ] - B[ j - 1 ] == -1 ){
                    dp[i][j][0] = Math.max( Math.max( dp[i-1][j][0], dp[i][j-1][0] ), dp[i-1][j-1][1] + ( ( dp[i-1][j-1][1] != 0 ? 1 : 0) * A[i - 1] )); // 0
                    dp[i][j][1] = Math.max( dp[i-1][j][1], dp[i][j-1][1] ); // 1
                    dp[i][j][2] = Math.max( Math.max( dp[i-1][j][2], dp[i][j-1][2] ), dp[i-1][j-1][0] + A[i - 1]); // -1
                }
                else if( A[ i - 1 ] - B[ j - 1 ] == 2 ){
                    dp[i][j][0] = Math.max( dp[i-1][j][0], dp[i][j-1][0] ); // 0
                    dp[i][j][1] = Math.max( Math.max( dp[i-1][j][1], dp[i][j-1][1] ), dp[i-1][j-1][2] + ( ( dp[i-1][j-1][2] != 0 ? 1 : 0) * A[i - 1] )); // 1
                    dp[i][j][2] = Math.max( dp[i-1][j][2], dp[i][j-1][2] ); // -1
                }
                else if( A[ i - 1 ] - B[ j - 1 ] == -2 ){
                    dp[i][j][0] = Math.max( dp[i-1][j][0], dp[i][j-1][0] ); // 0
                    dp[i][j][1] = Math.max( dp[i-1][j][1], dp[i][j-1][1] ); // 1
                    dp[i][j][2] = Math.max( Math.max( dp[i-1][j][2], dp[i][j-1][2] ), dp[i-1][j-1][1] + ( ( dp[i-1][j-1][1] != 0 ? 1 : 0) * A[i - 1] )); // -1
                }
                else{
                    dp[i][j][0] = Math.max( dp[i-1][j][0], dp[i][j-1][0]); // 0
                    dp[i][j][1] = Math.max( dp[i-1][j][1], dp[i][j-1][1]); // 1
                    dp[i][j][2] = Math.max( dp[i-1][j][2], dp[i][j-1][2]); // 2
                }
            }
        }

        System.out.println(dp[n][n][0]);

//        print3d(dp, n +1);

    }

    private static void print1d(int[] dp, int n) {
        for(int j = 0; j < n; j++){
            System.out.print(dp[j] + " ");
        }
        System.out.println();
    }

    private static void print3d(int[][][] dp, int n) {
        for(int k = 0; k < 3; k++){
            for(int j = 0; j < n; j++){
                for(int i = 0; i < n; i++){
                    System.out.print(dp[j][i][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static int[][][] initialize3D(int[][][] dp, int n) {

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < 3; k++){
                    dp[i][j][k] = 0;
                }
            }
        }
        return dp;
    }

}
