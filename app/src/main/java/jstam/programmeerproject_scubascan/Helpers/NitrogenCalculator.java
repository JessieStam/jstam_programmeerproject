package jstam.programmeerproject_scubascan.Helpers;

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
 * Created by Jessie on 24/01/2017.
 */

public class NitrogenCalculator implements Serializable {

    HashMap<String, HashMap<String, String>> nitrogen_first = new HashMap<>();
    HashMap<String, HashMap<String, String>> nitrogen_second = new HashMap<>();
    HashMap<String, HashMap<String, String>> nitrogen_third = new HashMap<>();

    private HashMap<String, HashMap<String, String>> nitrogen_table = new HashMap<>();

    public void readToHashMap(String table_name, InputStream input_stream) throws IOException {

        Log.d("test6", "ReadToHashMap started");

        nitrogen_table = new HashMap<>();

        String line;

        BufferedReader reader = new BufferedReader(new InputStreamReader(input_stream));
        while ((line = reader.readLine()) != null) {
            HashMap<String, String> depth_table = new HashMap<>();

            String[] depth_key = line.split(":");

            Log.d("test6", "String depth_key[0]: " + depth_key[0]);
            Log.d("test6", "String depth_key[1]: " + depth_key[1]);

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

    public String calculateNitrogen(String depth, String bottomtime, long added_time) {

        String letter = "";

        // calculate highest key
        if (nitrogen_first != null) {

            if (Integer.parseInt(depth) < 10) {
                depth = String.valueOf(10);
            }

            boolean depth_not_found = true;

            while (depth_not_found) {

                if (nitrogen_first.get(depth) != null) {
                    depth_not_found = false;

                    Log.d("test7", "depth is: " + depth);

                } else {
                    int depth_int = Integer.parseInt(depth) + 1;
                    depth = String.valueOf(depth_int);

                    Log.d("test7", "depth is empty, new depth is " + depth);
                }
            }

            boolean time_not_found = true;

            while (time_not_found) {

                // hier zit nog een bug
                letter = nitrogen_first.get(depth).get(bottomtime);

                if (letter != null) {
                    time_not_found = false;

                    Log.d("test7", "letter is: " + letter);

                } else {
                    int bottomtime_int = Integer.parseInt(bottomtime) + 1;
                    bottomtime = String.valueOf(bottomtime_int);

                    Log.d("test7", "letter is empty, new bottomtime is " + bottomtime);


                    // if bottomtime_int is increased more than 5 times or so, warn diver, because probably Z
                }
            }

        } else {
            Log.d("test7", "nitrogentable is null");
        }

        return letter;

    }

    public String calculateCurrentLevel (String letter, long interval) {

        String current_letter = "";

        Log.d("test8", "in calculateCurrentLevel");

        // calculate highest key
        if (nitrogen_second != null) {

            boolean interval_not_found = true;

            while (interval_not_found) {
                
                if (!letter.equals("None")) {
                    
                    current_letter = nitrogen_second.get(letter).get(String.valueOf(interval));

                    if (current_letter != null) {

                        Log.d("test8", "letter is: " + letter);
                        interval_not_found = false;

                    } else {
                        interval += 1;

                        Log.d("test8", "current_letter, new interval is " + interval);

                        if (interval > 360) {

                            //time is over, nitrogen free

                            interval_not_found = false;

                            current_letter = "None";

                        }
                    }
                } else {

                    interval_not_found = false;

                    Log.d("test8", "current_letter is " + current_letter);
                    current_letter = "None";
                }
                

                    // if bottomtime_int is increased more than 5 times or so, warn diver, because probably Z
            }

        } else {
            Log.d("test7", "nitrogentable is null");
        }

        return current_letter;

    }

    public long timeToNitroFree(String current_letter) {

        String minutes = "0";

        // calculate highest key
        if (nitrogen_second != null) {

            if (!current_letter.equals("None")) {
                minutes = nitrogen_second.get(current_letter).get("none");
            } else {
                minutes = "0";
            }

            Log.d("test7", "minutes is " + minutes);


        } else {
            Log.d("test7", "nitrogentable is null");
        }

        return Integer.valueOf(minutes);

    }

    public long calculateAddedTime (String letter, String depth) {

        long added_time = 0;
        String time = "0";

        // calculate highest key
        if (nitrogen_third != null) {

            // ding voor depth

            Log.d("test8", "nitrogen_thid != null");

            boolean depth_not_found = true;

            while (depth_not_found) {

                if (!letter.equals("None")) {
                    time = nitrogen_third.get(letter).get(depth);

                    if (time != null) {
                        depth_not_found = false;

                        Log.d("test8", "depth is: " + depth);

                    } else {
                        int depth_int = Integer.parseInt(depth) + 1;
                        depth = String.valueOf(depth_int);

                        Log.d("test7", "depth is empty, new depth is " + depth);
                    }
                }
                else {
                    Log.d("test8", "letter is none");

                    depth_not_found = false;
                }
            }

            added_time = Integer.valueOf(time);

        } else {
            Log.d("test7", "nitrogentable is null");
        }

        Log.d("test8", "added time = " + added_time);

        return added_time;
    }

    public long calculateBottomTime(String time_in, String time_out) {

        long bottomtime = 0;

//        String time1 = "16:25";
//        String time2 = "18:12";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        Date date2 = null;

        try {
            date1 = format.parse(time_in);
            date2 = format.parse(time_out);

            long difference = date2.getTime() - date1.getTime();

            Log.d("test7", "difference is: " + difference);

            long diff_minutes = difference / (60 * 1000) % 60;
            long diff_hours = difference / (60 * 60 * 1000) % 24;

            Log.d("test7", "hours: " + diff_hours);
            Log.d("test7", "minutes: " + diff_minutes);

            bottomtime = (diff_hours * 60) + diff_minutes;

            Log.d("test7", "bottomtime: " + bottomtime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return bottomtime;
    }

    public long calculateTotalTime(long total_time, long bottom_time) {
        return total_time + bottom_time;
    }

    public long calculateSurfaceInterval(String time_out, String date, String last_time_out, String last_date) {

        long surface_interval = 0;

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

//        String dateStart = "14-01-2012 09:29:58";
//        String dateStop = "15-01-2012 10:31:48";

        String date_start;
        String date_stop;

        if (last_time_out.equals("") && last_date.equals("")){
            date_start = date + " " + time_out;
            date_stop = format.format(new Date());
        } else {

            date_start = last_date + " " + last_time_out;
            date_stop = time_out + " " + date;

        }

//        String date_start = date + " " + time_out;
//        String date_stop = format.format(new Date());

        Log.d("test7", "date_start: " + date_start);
        Log.d("test7", "date_stop: " + date_stop);

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = format.parse(date_start);
            date2 = format.parse(date_stop);

            //in milliseconds
            long difference = date2.getTime() - date1.getTime();

            long diff_minutes = difference / (60 * 1000) % 60;
            long diff_hours = difference / (60 * 60 * 1000) % 24;
            long diff_days = difference / (24 * 60 * 60 * 1000);

            Log.d("test7", "days: " + diff_days);
            Log.d("test7", "hours: " + diff_hours);
            Log.d("test7", "minutes: " + diff_minutes);

            surface_interval = (diff_days * 1440) + (diff_hours * 60) + (diff_minutes);

            Log.d("test7", "surfacetime: " + surface_interval);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return surface_interval;
    }
}
