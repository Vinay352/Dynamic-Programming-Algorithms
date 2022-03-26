package AlgoHW5;/*
 * AlgoHW5.Babysit.java
 *
 * Version:
 *     $2$
 */

/**
 * CSCI-665
 *
 *  Aim:  Implementations of a dynamic programming algorithm that
 *        determines the maximum income that brother and sister can generate
 *        by working together, given some constraints.
 *
 *   Complexity of our Implementation: O( n ^ 2 )
 *
 *   A significant extension of weighted interval scheduling.
 *
 *   @author: Omkar Morogiri,om5692
 *   @author: Vinay Jain,vj9898
 *
 *
 */


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// mian class
public class Babysit {
    // main method
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().strip()); // take input for number of jobs

        String[] lines = new String[n];

        for( int i = 0; i < n ; i++ ){
            lines[i] = br.readLine();
            // System.out.println("vinay");
        }

        JobDetails[] jobs = new JobDetails[n+1]; // array to store all the jobs

        JobDetails zero = new JobDetails(0,0,0,0,0);
        jobs[0] = zero;

        // fill the jobs array with all the details of the jobs entered
        for( int i = 1; i <= n ; i++ ){
            String[] line = lines[i-1].split(" ");
//            System.out.println();
            JobDetails j = new JobDetails( Integer.parseInt(line[0].strip()), Integer.parseInt(line[1].strip()), Integer.parseInt(line[2].strip()), Integer.parseInt(line[3].strip()), Integer.parseInt(line[4].strip()) );
            jobs[i] = j;


//            System.out.println();
//            System.out.println(j.day + " " + j.start + " " + j.end + " " + j.childrenCount + " " + j.pay + " " + j.valid);
//            System.out.println("vinay");
        }



        // sorting the jobs array, based on first the day and then the finish time
        Arrays.sort( jobs, Comparator.comparing( JobDetails::getDay ).thenComparing( JobDetails::getEnd ) );



        //jobs = sortJobsOnFinsihTime(jobs);
//        System.out.println("------");
//
//        for( int i = 0; i <= n ; i++ ){
//            System.out.println(jobs[i].day + " " + jobs[i].start + " " + jobs[i].end + " " + jobs[i].childrenCount + " " + jobs[i].pay + " " + jobs[i].valid);
//        }
//
//        System.out.println("------");



        int ans = 0;
        int last = jobs[1].day;

        // jobs on a particular day only
        int jobsForTheDayCount = 0;
        List<JobDetails> jobsForTheDay = new ArrayList<JobDetails>(); // holds information regarding only the jobs for one particular day

        // this loop is to traverse through the sorted list of jobs and compute the
        // maximum income generated per day and then add them all up to get our
        // final answer.
        for(int i = 0; i <= n; i++){

            if(jobs[i].day != last){

                JobDetails[] dayJobs = new JobDetails[ jobsForTheDayCount ];
                dayJobs = jobsForTheDay.toArray(dayJobs);

                ans += dpExecution( dayJobs, jobsForTheDayCount );
                jobsForTheDayCount = 0;

                jobsForTheDay = new ArrayList<JobDetails>();
                jobsForTheDay.add( jobs[i] );

                last = jobs[i].day;
            }
            else{
                jobsForTheDay.add( jobs[i] );
                jobsForTheDayCount++;
            }
        }

        JobDetails[] dayJobs = new JobDetails[ jobsForTheDayCount ];
        dayJobs = jobsForTheDay.toArray(dayJobs);

        ans += dpExecution( dayJobs, jobsForTheDayCount );



//        System.out.println("--------------");
//        System.out.println("--------------");
//        System.out.println("--------------");



        System.out.println(ans); // our final answer

    }

    // method to execute the dynamic programming algorithm for jobs related to a particular day
    private static int dpExecution(JobDetails[] jobs, int n) {

        // where index indicate which job it is and value indicates which job can it (index value) fall back to
        int[] rho = createRhoFunction(jobs, n);


//        for(int i = 0; i < n + 1; i++){
//            System.out.println(i + " -> " + rho[i]);
//        }


        int[][] dp = new int[n+1][n+1];

        dp[0][0] = 0;

        for(int i = 1; i <= n; i++){
            if(jobs[i].valid == 1){
                // System.out.print("??? " + i + " -> " + rho[i] + " " + jobs[i].pay + "      ");
                dp[0][i] = Math.max(dp[0][i-1], jobs[i].pay + dp[0][ rho[i] ]);
            }
            else{
                dp[0][i] = dp[0][i-1];
            }
            dp[i][0] = dp[0][i];
//            System.out.print(dp[0][i] + " ");
        }

//        System.out.println();

//        for(int i = 1; i <= n; i++){
////            if(jobs[i].valid == 1){
////                dp[i][0] = Math.max(dp[i-1][0], jobs[i].pay + dp[ rho[i] ][0]);
////            }
////            else{
////                dp[i][0] = dp[i-1][0];
////            }
//            System.out.print(dp[i][0] + " ");
//        }

//        System.out.println("-----------");

        for(int i = 1; i <= n; i++){
            for(int j = i; j <= n; j++){
                if(i == j){
                    if(jobs[i].valid == 0){
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                    else{
                        if(jobs[i].childrenCount >= 4){
                            dp[i][j] = Math.max(jobs[i].pay + dp[ rho[i] ][ rho[j] ], dp[i - 1][j - 1]);
                        }
                        else{
                            dp[i][j] = Math.max(jobs[i].pay + dp[ rho[i] ][ j - 1 ], dp[i - 1][j - 1]);
                        }
                    }
                }
                else if(j > i){
                    if(jobs[j].valid == 0){
                        dp[i][j] = dp[i][j - 1];
                    }
                    else{
                        if(jobs[j].childrenCount >= 4){
                            dp[i][j] = dp[i][j - 1];
                        }
                        else{
                            dp[i][j] = Math.max(jobs[j].pay + dp[i][ rho[j] ], dp[i][j - 1]);
                        }
                    }
                }
                dp[j][i] = dp[i][j];
            }
        }

//        System.out.println("-------------");

//        for(int i = 0; i <= n; i++){
//            for(int j = 0; j <= n; j++){
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }

//        System.out.println(dp[n][n]);

        return dp[n][n];

    }

    // method to check to which job index can brother or sister safely fall back
    private static int fallBackJob(JobDetails jobs[], int i)
    {
        for (int j = i - 1; j >= 0; j--) {
            // finish before next is started
            if (jobs[j].end <= jobs[i].start)
                return j;
        }
        return 0;
    }

    // method to compute list of fall back options for all the job array elements
    private static int[] createRhoFunction(JobDetails[] jobs, int n) {
        int[] rho = new int[n+1];
        rho[0] = 0;
        for( int j = 1; j < n + 1 ;j++ ){
            rho[j] = fallBackJob(jobs, j);
        }
        return rho;
    }


}
