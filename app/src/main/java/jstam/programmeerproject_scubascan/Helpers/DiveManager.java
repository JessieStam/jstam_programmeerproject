package jstam.programmeerproject_scubascan.Helpers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Items.DiveItem;

/**
 * Created by Jessie on 18/01/2017.
 */

public class DiveManager {

    private DatabaseReference my_database;


    int dive_number = 0;
    String dive_name;

    // define instance
    private static DiveManager ourInstance = null;

    private ArrayList<DiveItem> dive_list = new ArrayList<>();

    // construct the instance
    public static DiveManager getOurInstance(){

        if (ourInstance == null) {
            ourInstance = new DiveManager();
        }
        return ourInstance;
    }

    /**
     * Create dive and add dive to dive list.
     */
    public void create_dive(String user, String date, String country, String dive_spot,
                            String buddy, String air_temp, String surface_temp, String bottom_temp,
                            String visibility, String water_type, String dive_type, String lead,
                            ArrayList<String> clothes_list, String time_in, String time_out,
                            String pressure_in, String pressure_out, String depth,
                            String safetystop, String notes) {

        my_database = FirebaseDatabase.getInstance().getReference();

        dive_number++;

        DiveItem new_dive = new DiveItem();

        new_dive.setNumber(dive_number);
        new_dive.setDate(date);
        new_dive.setCountry(country);
        new_dive.setDiveSpot(dive_spot);
        new_dive.setBuddy(buddy);
        new_dive.setAirTemp(air_temp);
        new_dive.setSurfaceTemp(surface_temp);
        new_dive.setBottomTemp(bottom_temp);
        new_dive.setVisibility(visibility);
        new_dive.setWaterType(water_type);
        new_dive.setDiveType(dive_type);
        new_dive.setLead(lead);
        new_dive.setClothingList(clothes_list);
        new_dive.setTimeIn(time_in);
        new_dive.setTimeOut(time_out);
        new_dive.setPressureIn(pressure_in);
        new_dive.setPressureOut(pressure_out);
        new_dive.setDepth(depth);
        new_dive.setSafetystop(safetystop);
        new_dive.setNotes(notes);

        dive_list.add(new_dive);

        dive_name = "Dive " + String.valueOf(dive_number);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(new_dive);

    }

}
