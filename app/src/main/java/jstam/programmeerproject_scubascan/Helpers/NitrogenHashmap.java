package jstam.programmeerproject_scubascan.Helpers;

import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 24/01/2017.
 */

public class NitrogenHashmap {

    public void readToHashMap() throws Exception {

        String file_path = "src/main/res/dictionaries/dictionary_10m.txt";
        HashMap<String, String> map = new HashMap<String, String>();

        String line;

        BufferedReader reader = new BufferedReader(new FileReader(file_path));
        while ((line = reader.readLine()) != null)
        {
            String[] parts = line.split(":", 2);
            if (parts.length >= 2)
            {
                String key = parts[0];
                String value = parts[1];
                map.put(key, value);
            } else {
                System.out.println("ignoring line: " + line);
            }
        }

        for (String key : map.keySet())
        {
            System.out.println(key + ":" + map.get(key));
        }
        reader.close();
    }
}
