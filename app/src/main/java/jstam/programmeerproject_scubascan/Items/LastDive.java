package jstam.programmeerproject_scubascan.Items;

/**
 * Scuba Scan - LastDive
 *
 * Jessie Stam
 * 10560599
 *
 * Object that stores all the data of the last dive that is made.
 */
public class LastDive {

    private String date, time_out, letter;
    private long totaltime;

    public LastDive() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public LastDive(String date, String time_out, String letter, long totaltime) {
        this.date = date;
        this.time_out = time_out;
        this.letter = letter;
        this.totaltime = totaltime;
    }

    // methods for date
    public String getDate() { return date; }
    public void setDate(String new_date) { date = new_date; }

    // methods for time out
    public String getTimeOut() { return time_out; }
    public void setTimeOut(String new_time_out) { time_out = new_time_out; }

    // methods for nitrogen level
    public String getLetter() { return letter; }
    public void setLetter(String new_letter) { letter = new_letter; }

    // methods for total bottom time
    public long getTotaltime() { return totaltime; }
    public void setTotaltime(long new_totaltime) { totaltime = new_totaltime; }
}
