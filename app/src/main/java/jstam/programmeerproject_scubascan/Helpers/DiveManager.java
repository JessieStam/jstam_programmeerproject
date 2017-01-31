package jstam.programmeerproject_scubascan.Helpers;

import android.os.Handler;
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
 * Created by Jessie on 18/01/2017.
 */

public class DiveManager {

    private DatabaseReference my_database;

    int dive_number = 1;
    int finished = 0;
    String dive_name;

    DiveItem dive_item;

    boolean running;

    // define instance
    private static DiveManager ourInstance = null;

    private ArrayList<DiveItem> dive_list = new ArrayList<>();
    private ArrayList<DiveItem> firebase_dive_list = new ArrayList<>();

    // construct the instance
    public static DiveManager getOurInstance(){

        if (ourInstance == null) {
            ourInstance = new DiveManager();
        }

        Log.d("test6", "in the dive manager");
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
                            String safetystop, String notes, String previous_level,
                            String nitrogen_level, String interval_level) {

        my_database = FirebaseDatabase.getInstance().getReference();

        DiveItem new_dive = new DiveItem();

        dive_number = getDiveNumber();

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

        dive_list.add(new_dive);

        dive_name = "Dive " + String.valueOf(dive_number);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(new_dive);

    }

    public DiveItem editDiveGeneral(String dive_name, String user, DiveItem dive, String date,
                                    String country, String dive_spot, String buddy) {

        my_database = FirebaseDatabase.getInstance().getReference();

//        DiveItem edited_dive = new DiveItem();

        dive.setDate(date);
        dive.setCountry(country);
        dive.setDiveSpot(dive_spot);
        dive.setBuddy(buddy);

        my_database.child("users").child(user).child("dive_log").child(dive_name).setValue(dive);

        return dive;
    }

    public void updateLastDive (String user, String date, String time_out,
                                String letter, long totaltime) {

        Log.d("test8", "in update laste dive");

        my_database = FirebaseDatabase.getInstance().getReference();

        LastDive last_dive = new LastDive();

        last_dive.setDate(date);
        last_dive.setTimeOut(time_out);
        last_dive.setLetter(letter);
        last_dive.setTotaltime(totaltime);

        my_database.child("users").child(user).child("last_dive").setValue(last_dive);

    }

//    public void getLastDive() {
//
//        FirebaseAuth mAuth;
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebase_user = mAuth.getCurrentUser();
//        String user_id = firebase_user.getUid();
//
//        my_database = FirebaseDatabase.getInstance().getReference();
//        my_database.child("users").child(user_id).child("last_dive").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                LastDive last_dive = dataSnapshot.getValue(LastDive.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("test4", "in onCancelled");
//            }
//        });
//
//    }

    public int getDiveNumber() {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        Log.d("test4", "in getDiveNumber");
        my_database = FirebaseDatabase.getInstance().getReference();
        my_database.child("users").child(user_id).child("dive_log").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // hier gaat iest asynchroon mis

                Log.d("test4", "in onDataChange");

                Iterable<DataSnapshot> dive_log = dataSnapshot.getChildren();

                // shake hands with each of them.'
                for (DataSnapshot dive : dive_log) {

                    Log.d("test4", "in dive log list");

                    DiveItem dive_item = dive.getValue(DiveItem.class);
                    firebase_dive_list.add(dive_item);
                }

                if (firebase_dive_list.size() == 0) {
                    dive_number = 1;

                    Log.d("test4", "firebase_dive_list is 0");

                } else {
                    Log.d("test4", "firebase_dive_list is NOT 0");

                    int highest = 0;

                    for (DiveItem dive : firebase_dive_list) {
                        Log.d("test4", "dive number: " + dive.getDiveNumber());
                        Log.d("test4", "buddy: " + dive.getBuddy());

                        if (highest < dive.getDiveNumber()) {
                            highest = dive.getDiveNumber();

                            dive_number = highest + 1;

                            Log.d("test4", "dive number = " + dive_number);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test4", "in onCancelled");
            }
        });

        return dive_number;
    }

    public void getDiveInfo(final String dive_name) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        running = true;

        dive_item = new DiveItem();

        Log.d("test6", "in getDiveInfo");
        my_database = FirebaseDatabase.getInstance().getReference();
        my_database.child("users").child(user_id).child("dive_log").child(dive_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("test6", "in onDataChange");

                dive_item = dataSnapshot.getValue(DiveItem.class);

                Log.d("test6", "dive item date is: " + dive_item.getDate());

                //getDiveItem();

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        DiveItem final_dive = new DiveItem();
//        boolean finished;
//
//        Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                Log.d("test6", "handler");
//                final_dive = getDiveItem();
//            }
//        }, 5000);

//        Log.d("test6", "final_dive is returned");
//        return dive_item;
    }

    public DiveItem getDiveItem() {

        Log.d("test6", "getDiveItem");

        Log.d("test6", "dive item date is: " + dive_item.getDate());
        return dive_item;
    }
}
