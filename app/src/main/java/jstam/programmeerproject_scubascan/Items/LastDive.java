package jstam.programmeerproject_scubascan.Items;

/**
 * Created by Jessie on 30/01/2017.
 */

public class LastDive {

    private String date;
    private String time_out;
    private String letter;
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

    // methods for username
    public String getDate() { return date; }
    public void setDate(String new_date) { date = new_date; }

    // methods for time out
    public String getTimeOut() { return time_out; }
    public void setTimeOut(String new_time_out) { time_out = new_time_out; }

    public String getLetter() { return letter; }
    public void setLetter(String new_letter) { letter = new_letter; }

    public long getTotaltime() { return totaltime; }
    public void setTotaltime(long new_totaltime) { totaltime = new_totaltime; }
}
