package jstam.programmeerproject_scubascan.Helpers.Managers;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.Items.LastDive;

/**
 * Scuba Scan - DiveManager
 *
 * Jessie Stam
 * 10560599
 *
 * Singleton class that creates objects and uploads those to Firebase.
 */
public class DiveManager {

    private DatabaseReference my_database;

    int dive_number = 1;
    String dive_name;
    DiveItem dive_item;

    boolean running;

    // define instance
    private static DiveManager ourInstance = null;

    private ArrayList<DiveItem> firebase_dive_list = new ArrayList<>();

    // construct the instance
    public static DiveManager getOurInstance(){

        if (ourInstance == null) {
            ourInstance = new DiveManager();
        }
        return ourInstance;
    }

    /**
     * Create dive and add dive to Firebase.
     */
    public void create_dive(final String user, final String date, final String country,
                            final String dive_spot, final String buddy, final String air_temp,
                            final String surface_temp, final String bottom_temp,
                            final String visibility, final String water_type,
                            final String dive_type, final String lead,
                            final ArrayList<String> clothes_list, final String time_in,
                            final String time_out, final String pressure_in,
                            final String pressure_out, final String depth,
                            final String safetystop, final String notes, final String previous_level,
                            final String nitrogen_level, final String interval_level) {

        final DiveItem new_dive = new DiveItem();

        // get number of dive from Firebase to give new dive the next number
        my_database = FirebaseDatabase.getInstance().getReference();
        my_database.child("users").child(user).child("dive_log").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> dive_log = dataSnapshot.getChildren();

                for (DataSnapshot dive : dive_log) {
                    DiveItem dive_item = dive.getValue(DiveItem.class);
                    firebase_dive_list.add(dive_item);
                }

                // if first dive, number is 1
                if (firebase_dive_list.size() == 0) {
                    dive_number = 1;
                }
                // find highest number and assign next number to new dive
                else {
                    int highest = 0;

                    for (DiveItem dive : firebase_dive_list) {
                        if (highest < dive.getDiveNumber()) {
                            highest = dive.getDiveNumber();
                            dive_number = highest + 1;
                        }
                    }
                }

                // add data to new DiveItem
                new_dive.setDiveNumber(dive_number);
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
                new_dive.setPreviousLevel(previous_level);
                new_dive.setNitrogenLevel(nitrogen_level);
                new_dive.setIntervalLevel(interval_level);

                // set dive name and add to Firebase
                dive_name = "Dive " + String.valueOf(dive_number);
                my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(new_dive);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test4", "in onCancelled");
            }
        });
    }

    /* Get changed general data and update Firebase. Return edited Dive object to activity. */
    public DiveItem editDiveGeneral(String dive_name, String user, DiveItem dive, String date,
                                    String country, String dive_spot, String buddy) {

        my_database = FirebaseDatabase.getInstance().getReference();

        dive.setDate(date);
        dive.setCountry(country);
        dive.setDiveSpot(dive_spot);
        dive.setBuddy(buddy);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(dive);
        return dive;
    }

    /* Get changed circumstances and update Firebase. Return edited Dive object to activity. */
    public DiveItem editDiveCircumstances(String dive_name, String user, DiveItem dive,
                                          String air_temp, String surface_temp, String bottom_temp,
                                          String visibility, String water_type, String dive_type) {

        my_database = FirebaseDatabase.getInstance().getReference();

        dive.setAirTemp(air_temp);
        dive.setSurfaceTemp(surface_temp);
        dive.setBottomTemp(bottom_temp);
        dive.setVisibility(visibility);
        dive.setWaterType(water_type);
        dive.setDiveType(dive_type);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(dive);
        return dive;
    }

    /* Get changed equipment and update Firebase. Return edited Dive object to activity. */
    public DiveItem editDiveEquipment(String dive_name, String user, DiveItem dive, String lead,
                                          ArrayList<String> clothes) {

        my_database = FirebaseDatabase.getInstance().getReference();

        dive.setLead(lead);
        dive.setClothingList(clothes);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(dive);
        return dive;
    }

    /* Get changed technical data and update Firebase. Return edited Dive object to activity. */
    public DiveItem editTechnicalData(String dive_name, String user, DiveItem dive, String time_in,
                                      String time_out, String pressure_in, String pressure_out,
                                      String depth, String safetystop) {

        my_database = FirebaseDatabase.getInstance().getReference();

        dive.setTimeIn(time_in);
        dive.setTimeOut(time_out);
        dive.setPressureIn(pressure_in);
        dive.setPressureOut(pressure_out);
        dive.setDepth(depth);
        dive.setSafetystop(safetystop);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(dive);
        return dive;
    }

    /* Get changed notes and update Firebase. Return edited Dive object to activity. */
    public DiveItem editExtraData(String dive_name, String user, DiveItem dive, String notes) {

        my_database = FirebaseDatabase.getInstance().getReference();

        dive.setNotes(notes);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(dive);
        return dive;
    }

    /* Update LastDive item on Firebase. */
    public void updateLastDive (String user, String date, String time_out,
                                String letter, long totaltime) {

        my_database = FirebaseDatabase.getInstance().getReference();

        LastDive last_dive = new LastDive();

        last_dive.setDate(date);
        last_dive.setTimeOut(time_out);
        last_dive.setLetter(letter);
        last_dive.setTotaltime(totaltime);

        my_database.child("users").child(user).child("last_dive").setValue(last_dive);
    }
}
