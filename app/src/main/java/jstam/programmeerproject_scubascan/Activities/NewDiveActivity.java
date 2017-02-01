package jstam.programmeerproject_scubascan.Activities;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FifthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.ThirdNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.AlertReceiver;
import jstam.programmeerproject_scubascan.Helpers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.NewDiveFragmentPageAdapter;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.SecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.NitrogenCalculator;
import jstam.programmeerproject_scubascan.Items.LastDive;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class NewDiveActivity extends AppCompatActivity implements FirstNewDiveFragment.FirstNewDiveFragmentListener,
        SecondNewDiveFragment.SecondNewDiveFragmentListener, ThirdNewDiveFragment.ThirdNewDiveFragmentListener,
        FourthNewDiveFragment.FourthNewDiveFragmentListener, FifthNewDiveFragment.FifthNewDiveFragmentListener {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    boolean general_data, circumstances_data, equipment_data, technical_data, fish_data, extra_data;

    String date, country, dive_spot, buddy, air_temp, surface_temp, bottom_temp, visibility,
            water_type, dive_type, lead, time_in, time_out, pressure_in, pressure_out, depth,
            safetystop, notes;

    String last_date, last_time_out, last_letter;

    String nitrogen_level, interval_level;
    long interval;

    String previous_level;
    long last_totaltime;

    long bottomtime, totalbottomtime;

    long interval_last_now, added_time;

    Button final_save_button;

    ArrayList<String> clothing_list;

    private FirebaseAuth mAuth;

    DiveManager dive_manager;

    NitrogenCalculator nitrogen;

    DatabaseReference my_database;

    NotificationManager notify_manager;

    int notific_id = 100;

    boolean is_notif_active;

    FirebaseUser firebase_user;
    String user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dive);

        mAuth = FirebaseAuth.getInstance();

        dive_manager = DiveManager.getOurInstance();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager view_pager = (ViewPager) findViewById(R.id.viewpager);
        view_pager.setAdapter(new NewDiveFragmentPageAdapter(getSupportFragmentManager(),
                NewDiveActivity.this));

        view_pager.setOffscreenPageLimit(50);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(view_pager);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        final_save_button = (Button) findViewById(R.id.final_save_button);

        clothing_list = new ArrayList<>();

        firebase_user = mAuth.getCurrentUser();
        user_id = firebase_user.getUid();

        // set booleans to false
        general_data = false;
        circumstances_data = false;
        equipment_data = false;
        technical_data = false;
        fish_data = false;
        extra_data = false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu toolbar) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.toolbar_main, toolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem toolbar) {

        // display toast for clicked toolbar item
        final String clicked_item = toolbar_helper.getClickedMenuItem(toolbar, this);

        if (!clicked_item.equals("")) {
            Toast.makeText(this, clicked_item, Toast.LENGTH_SHORT).show();
        }

//        finish();

        return super.onOptionsItemSelected(toolbar);
    }

    @Override
    public void saveGeneralData(String date_input, String country_input, String dive_spot_input, String buddy_input) {

        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

        date = date_input;
        country = country_input;
        dive_spot = dive_spot_input;
        buddy = buddy_input;

        general_data = true;

        checkIfDataComplete();
    }

    @Override
    public void saveCircumstancesData(String air_temp_input, String surface_temp_input, String bottom_temp_input,
                                      String visibility_input, String water_type_input, String dive_type_input) {

        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

        air_temp = air_temp_input;
        surface_temp = surface_temp_input;
        bottom_temp = bottom_temp_input;
        visibility = visibility_input;
        water_type = water_type_input;
        dive_type = dive_type_input;

        circumstances_data = true;

        checkIfDataComplete();
    }

    @Override
    public void saveEquipmentData(String lead_input, ArrayList<String> clothes_input) {
        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

        lead = lead_input;
        clothing_list = clothes_input;

        equipment_data = true;

        checkIfDataComplete();

    }

    @Override
    public void saveTechnicalData(String time_in_input, String time_out_input,
                                  String pressure_in_input, String pressure_out_input,
                                  String depth_input, String safetystop_input) {

        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

        time_in = time_in_input;
        time_out = time_out_input;
        pressure_in = pressure_in_input;
        pressure_out = pressure_out_input;
        depth = depth_input;
        safetystop = safetystop_input;

        technical_data = true;

        checkIfDataComplete();
    }

    @Override
    public void saveExtraData(String notes_input) {

        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

        notes = notes_input;

        extra_data = true;

        checkIfDataComplete();
    }

    @Override
    public void showFragmentToast(String toast) {

        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();

    }

    public void checkIfDataComplete() {

        Log.d("test", "checkIfDataComplete is running");

        if (general_data && circumstances_data && equipment_data && technical_data && extra_data) {
            final_save_button.setVisibility(View.VISIBLE);

            Log.d("test", "data are true");
        }
        else {
            Log.d("test", "Not true...");

            if (final_save_button.getVisibility() == View.VISIBLE) {
                final_save_button.setVisibility(View.INVISIBLE);

            }
        }
    }

    public void importData(View view) {

        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        //calculate all the things
        InputStream input_stream_first = getResources().openRawResource(R.raw.nitrogen_first);
        InputStream input_stream_second = getResources().openRawResource(R.raw.nitrogen_second);
        InputStream input_stream_third = getResources().openRawResource(R.raw.nitrogen_third);

        nitrogen = new NitrogenCalculator();

        try {
            nitrogen.readToHashMap("first", input_stream_first);
            nitrogen.readToHashMap("second", input_stream_second);
            nitrogen.readToHashMap("third", input_stream_third);
        } catch (IOException e) {
            Log.d("test6", "throws exception");

            e.printStackTrace();
        }

//        bottomtime = nitrogen.calculateBottomTime(time_in, time_out);
//        totalbottomtime = nitrogen.calculateTotalTime(total_time, bottomtime);

        // check of al een letter
        last_letter = "";
        last_totaltime = 0;
        added_time = 0;

        // check previous dive info
        my_database = FirebaseDatabase.getInstance().getReference();
        my_database.child("users").child(user_id).child("last_dive").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("test8", "in on data change");

                if (dataSnapshot != null) {
                    Log.d("test8", "datasnapshot is NOT null");

                    LastDive last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {
                        last_date = last_dive.getDate();
                        last_time_out = last_dive.getTimeOut();
                        last_letter = last_dive.getLetter();
                        last_totaltime = last_dive.getTotaltime();

                        Log.d("test8", "last total time is: " + last_totaltime);
                    }

                } else {

                    Log.d("test8", "datasnapshot is null");
                }

                Log.d("test8", "going to save new dive");
                calculateValues();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test8", "in onCancelled");
            }
        });
    }

    public void calculateValues() {

        Log.d("test8", "in calculate values");

        bottomtime = nitrogen.calculateBottomTime(time_in, time_out);
        totalbottomtime = nitrogen.calculateTotalTime(last_totaltime, bottomtime);

        // if last dive excists
        if (last_time_out != null && last_date != null) {

            Log.d("test8", "last time out and date are not null: " + last_time_out + " " + last_date);

            interval_last_now = nitrogen.calculateSurfaceInterval(time_in, date, last_time_out, last_date);

            Log.d("test8", "interval between last and now: " + interval_last_now);

            // if last letter is not "" (no nitrogen residue) calculate level after interval
            if (last_letter != null) {

                if (!last_letter.equals("")){
                    Log.d("test8", "last letter is not empty, but: " + last_letter);

                    previous_level = nitrogen.calculateCurrentLevel(last_letter, interval_last_now);

                    Log.d("test8", "previous level is: " + previous_level);

                    if (!previous_level.equals("")) {

                        added_time = nitrogen.calculateAddedTime(previous_level, depth);

                    }
                }

            }
            else {
                Log.d("test8", "last letter is null");
            }
        }


        // calculate letter --> haal letter van firebase --> calculate interval --> calculate interval level
        // user-info last dive date, last dive letter, last dive time out,

        nitrogen_level = nitrogen.calculateNitrogen(depth, String.valueOf(bottomtime), added_time);
        interval = nitrogen.calculateSurfaceInterval(time_out, date, "", "");
        interval_level = nitrogen.calculateCurrentLevel(nitrogen_level, interval);

        Log.d("test8", "bottomtime: " + bottomtime);
        Log.d("test8", "totalbottomtime: " + totalbottomtime);
        Log.d("test8", "previouslevel: " + previous_level);
        Log.d("test8", "added_time: " + added_time);
        Log.d("test8", "nitrogen: " + nitrogen_level);
        Log.d("test8", "interval is: " + interval);
        Log.d("test8", "interval_level: " + interval_level);

        saveNewDive();
    }

    public void saveNewDive() {

        Log.d("test8", "back in save new dive");

        dive_manager.create_dive(user_id, date, country, dive_spot, buddy, air_temp, surface_temp,
                bottom_temp, visibility, water_type, dive_type, lead, clothing_list, time_in,
                time_out, pressure_in, pressure_out, depth, safetystop, notes, previous_level,
                nitrogen_level, interval_level);

        // finally update last dive
        dive_manager.updateLastDive(user_id, date, time_out, interval_level, totalbottomtime);

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_divesaved);
        dialog.setTitle("Dive logged!");

        // set the custom dialog components - text, image and button
        TextView info = (TextView) dialog.findViewById(R.id.dialog_newdive_info);

        if (previous_level == null || previous_level.equals("None")) {
            info.setText("Dived for " + bottomtime + " minutes. Nitrogen level upon resurfacing was " + nitrogen_level + "." +
                    " Nitrogen level now is " + interval_level + ".");
        } else {
            info.setText("Dived for " + bottomtime + " minutes. Nitrogen level upon entering the water was "
                    + previous_level + ". Nitrogen level upon resurfacing was " + nitrogen_level + "." +
                    " Nitrogen level now is " + interval_level + ".");
        }

        Button yesbutton = (Button) dialog.findViewById(R.id.dialog_newdive_yesbutton);
        // if button is clicked, close the custom dialog
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                showToast("Timer was set!");
//                showNotification();

                //setAlarm();

                timerActivity("set_timer");

                dialog.dismiss();
            }
        });

        Button nobutton = (Button) dialog.findViewById(R.id.dialog_newdive_nobutton);
        // if button is clicked, close the custom dialog
        nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("No timers set");
                backToMenu();

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    public void backToMenu() {

        Intent menu_activity = new Intent(this, MenuActivity.class);
        startActivity(menu_activity);

        finish();
    }

    public void timerActivity(String set_timer) {

        Intent timer_activity = new Intent(this, NitroTimerActivity.class);

        timer_activity.putExtra(set_timer, set_timer);
        timer_activity.putExtra("nitrogen_level", nitrogen_level);
        timer_activity.putExtra("interval_level", interval_level);

        startActivity(timer_activity);

        finish();
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(NewDiveActivity.this);
        builder.setMessage("Are you sure you want to leave? Progress will be lost.");
        builder.setCancelable(true);
        builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog_interface, int which) {
                finish();
            }
        });
        builder.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog_interface, int which) {
                dialog_interface.cancel();
            }
        });
        AlertDialog alert_dialog = builder.create();
        alert_dialog.show();
    }
}
