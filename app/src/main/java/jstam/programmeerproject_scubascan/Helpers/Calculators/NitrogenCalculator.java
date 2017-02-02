package jstam.programmeerproject_scubascan.Helpers.Calculators;

import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - NitrogenCalculator
 *
 * Jessie Stam
 * 10560599
 *
 * Calculator for nitrogen levels, surface intervals and the time is takes to be nitrogen-free.
 */
public class NitrogenCalculator implements Serializable {

    HashMap<String, HashMap<String, String>> nitrogen_first = new HashMap<>();
    HashMap<String, HashMap<String, String>> nitrogen_second = new HashMap<>();
    HashMap<String, HashMap<String, String>> nitrogen_third = new HashMap<>();

    private HashMap<String, HashMap<String, String>> nitrogen_table = new HashMap<>();

    /**
     * This function read input streams to nested HashMaps. Depending on the given name, the
     * function saves the output to a certain HashMap.
     */
    public void readToHashMap(String table_name, InputStream input_stream) throws IOException {

        nitrogen_table = new HashMap<>();
        String line;

        // read input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(input_stream));
        while ((line = reader.readLine()) != null) {
            HashMap<String, String> depth_table = new HashMap<>();

            String[] depth_key = line.split(":");

            String[] depth_values = depth_key[1].split(",");
            for (String depth_value : depth_values) {
                String time_value[] = depth_value.split("-");
                depth_table.put(time_value[0], time_value[1]);
            }
            nitrogen_table.put(depth_key[0], depth_table);
        }

        switch (table_name) {
            case "first":
                nitrogen_first = nitrogen_table;
                break;
            case "second":
                nitrogen_second = nitrogen_table;
                break;
            case "third":
                nitrogen_third = nitrogen_table;
                break;
        }
    }

    /**
     * This function calculates nitrogen levels.
     */
    public String calculateNitrogen(String depth, String bottomtime, long added_time) {

        String letter = "";
        bottomtime += String.valueOf(added_time);

        // check if HashMap exists
        if (nitrogen_first != null) {

            // minimum depth is 10 meter
            if (Integer.parseInt(depth) < 10) {
                depth = String.valueOf(10);
            }

            // if given depth is not in HashMap, add a meter
            boolean depth_not_found = true;

            while (depth_not_found) {
                if (nitrogen_first.get(depth) != null) {
                    depth_not_found = false;
                } else {
                    int depth_int = Integer.parseInt(depth) + 1;
                    depth = String.valueOf(depth_int);
                }
            }

            // if given time is not found, add a minute
            boolean time_not_found = true;
            int counter = 0;

            while (time_not_found) {

                letter = nitrogen_first.get(depth).get(bottomtime);

                if (letter != null) {
                    time_not_found = false;
                } else {
                    counter += 1;

                    int bottomtime_int = Integer.parseInt(bottomtime) + 1;
                    bottomtime = String.valueOf(bottomtime_int);

                    // if given time is more than maximum time in table, level is Z
                    if (counter < 39) {
                        letter = "Z";
                        time_not_found = false;
                    }
                }
            }
        }
        return letter;
    }

    /**
     * This function calculates nitrogen levels after a certain period of time.
     */
    public String calculateCurrentLevel (String letter, long interval) {

        String current_letter = "";

        // check if HashMap exists
        if (nitrogen_second != null) {

            // if interval is not in HashMap, add a minute
            boolean interval_not_found = true;
            while (interval_not_found) {
                
                // check if level is not already none
                if (!letter.equals("None")) {

                    current_letter = nitrogen_second.get(letter).get(String.valueOf(interval));

                    if (current_letter != null) {
                        interval_not_found = false;
                    } else {
                        interval += 1;

                        // after 360 minutes, nitrogon level is always none
                        if (interval > 360) {
                            interval_not_found = false;
                            current_letter = "None";
                        }
                    }
                } else {
                    interval_not_found = false;
                    current_letter = "None";
                }
            }
        }
        return current_letter;
    }

    /**
     * This function calculates how long it takes before nitrogen level is none.
     */
    public long timeToNitroFree(String current_letter) {

        String minutes = "0";

        // check if HashMap exists and if level is not already none
        if (nitrogen_second != null) {
            if (!current_letter.equals("None")) {
                minutes = nitrogen_second.get(current_letter).get("none");
            } else {
                minutes = "0";
            }
        }
        return Integer.valueOf(minutes);
    }

    /**
     * This function calculates time that has to be added to the bottom time because the diver
     * entered the water with a nitrogen residue.
     */
    public long calculateAddedTime (String letter, String depth) {

        long added_time = 0;
        String time = "0";

        // check if HashMap exists
        if (nitrogen_third != null) {

            // if depth is not in HashMap, add a meter
            boolean depth_not_found = true;
            while (depth_not_found) {

                // check if level is not none
                if (!letter.equals("None")) {
                    time = nitrogen_third.get(letter).get(depth);

                    if (time != null) {
                        depth_not_found = false;
                    } else {
                        int depth_int = Integer.parseInt(depth) + 1;
                        depth = String.valueOf(depth_int);
                    }
                }
                else {
                    depth_not_found = false;
                }
            }

            added_time = Integer.valueOf(time);
        }
        return added_time;
    }

    /**
     * This function calculates the bottom time in minutes.
     */
    public long calculateBottomTime(String time_in, String time_out) {

        long bottomtime = 0;

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = format.parse(time_in);
            date2 = format.parse(time_out);

            // calculate difference between times to minutes
            long difference = date2.getTime() - date1.getTime();;
            long diff_minutes = difference / (60 * 1000) % 60;
            long diff_hours = difference / (60 * 60 * 1000) % 24;

            bottomtime = (diff_hours * 60) + diff_minutes;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bottomtime;
    }

    /* This function calculates the total bottom time. */
    public long calculateTotalTime(long total_time, long bottom_time) {
        return total_time + bottom_time;
    }

    /**
     * This function calculates the time above water between dives. If last_time_out and last_date
     * Strings are empty, it takes the current time instead and calculates time between last dive
     * and now.
     */
    public long calculateSurfaceInterval(String time_out, String date, String last_time_out,
                                         String last_date) {

        long surface_interval = 0;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        String date_start;
        String date_stop;

        // calculate time between dive and current time
        if (last_time_out.equals("") && last_date.equals("")){
            date_start = date + " " + time_out;
            date_stop = format.format(new Date());
        }
        // calculate time between end of last dive and start of new dive
        else {
            date_start = last_date + " " + last_time_out;
            date_stop = time_out + " " + date;
        }

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = format.parse(date_start);
            date2 = format.parse(date_stop);

            // calculate time to minutes
            long difference = date2.getTime() - date1.getTime();

            long diff_minutes = difference / (60 * 1000) % 60;
            long diff_hours = difference / (60 * 60 * 1000) % 24;
            long diff_days = difference / (24 * 60 * 60 * 1000);

            surface_interval = (diff_days * 1440) + (diff_hours * 60) + (diff_minutes);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return surface_interval;
    }
}
