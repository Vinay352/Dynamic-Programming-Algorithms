package AlgoHW5;/*
 * AlgoHW5.JobDetails.java
 *
 * Version:
 *     $2$
 */

/**
 * CSCI-665
 *
 *  This class stores information about the various job details.
 *
 *  Helper class for AlgoHW5.Babysit.java
 *
 *
 */


public class JobDetails {
    int day; // which day the job is
    int start; // start time of the job
    int end; // end time of the job
    int childrenCount; // number of children to babysit for the job
    int pay; // total pay for the hours the job is
    int valid; // is the job valid w.r.t the time constraints of working

    public JobDetails(int day, int start, int end, int childrenCount, int pay){
        this.day = day;
        this.start = start;
        this.end = end;
        this.childrenCount = childrenCount;
        this.pay = pay * (end - start) / 100;

        if(this.start < 600 || this.start > 2300){
            this.valid = 0;
        }
        else if(this.end < 600 || this.end > 2300){
            this.valid = 0;
        }
        else{
            valid = 1;
        }
    }

    // helper function to get end time of the job
    public int getEnd(){
        return this.end;
    }

    // helper function to get the day of the job
    public int getDay(){
        return this.day;
    }
}
