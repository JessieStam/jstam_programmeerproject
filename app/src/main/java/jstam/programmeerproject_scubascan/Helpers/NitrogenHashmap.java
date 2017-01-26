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
import java.util.HashMap;

import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 24/01/2017.
 */

public class NitrogenHashmap implements Serializable {

    InputStream input_stream;

    public void readToHashMap(InputStream input_stream) throws IOException {

        Log.d("test6", "ReadToHashMap started");

        HashMap<String, HashMap<String, String>> nitrogen_table = new HashMap<>();

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

        for (HashMap.Entry<String, HashMap<String, String>> entry : nitrogen_table.entrySet()) {
            String key = entry.getKey();
            HashMap<String, String> value = entry.getValue();

            for (HashMap.Entry<String, String> depth : value.entrySet()) {
                String time_key = depth.getKey();
                String time_value = depth.getValue();

                Log.d("test6", "Depth is: " + key + ". Time is: " + time_key + ". Letter is: " + time_value);
            }
        }
    }

    public void calculateNitrogen(String depth, String bottomtime) {

        // do dingen

    }
}
