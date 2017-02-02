package jstam.programmeerproject_scubascan.Activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FifthFragUnfinished;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstFragUnfinished;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FourthFragUnfinised;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.ThirdFragUnfinished;
import jstam.programmeerproject_scubascan.Helpers.Managers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.Adapters.NewFragPageAdapter;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.SecondFragUnfinished;
import jstam.programmeerproject_scubascan.Helpers.Calculators.NitrogenCalculator;
import jstam.programmeerproject_scubascan.Items.LastDive;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.Helpers.ToolbarHelper;

/**
 * Scuba Scan - NewDiveActivity
 *
 * Jessie Stam
 * 10560599
 *
 * This activity enables the user to add a new dive to the dive log, using five root fragments,
 * containing two fragments each (one for getting user input and one for displaying saved data).
 * The root fragments are displayed using a tab layout. First they are filled with the five
 * unfinished fragments. When the user submits the data by pressing the confirm button in the
 * fragment, the data is parsed to this activity and the unfinished fragment is replaced by the
 * finished fragment that displays the data. When all the data is complete, a final save button
 * appears that saves the data to Firebase. Using the NitrogenCalculator, the user's nitrogen levels
 * before entering the water, just after the dive and when saving this dive are calculated. These
 * are then displayed in a dialog that also allows to set a timer for when the user is nitrogen free
 * again.
 */
public class NewDiveActivity extends AppCompatActivity implements FirstFragUnfinished.FirstNewDiveFragmentListener,
        SecondFragUnfinished.SecondNewDiveFragmentListener, ThirdFragUnfinished.ThirdNewDiveFragmentListener,
        FourthFragUnfinised.FourthNewDiveFragmentListener, FifthFragUnfinished.FifthNewDiveFragmentListener {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    boolean general_data, circumstances_data, equipment_data, technical_data, fish_data, extra_data;
    String date, country, dive_spot, buddy, air_temp, surface_temp, bottom_temp, visibility,
            water_type, dive_type, lead, time_in, time_out, pressure_in, pressure_out, depth,
            safetystop, notes, last_date, last_time_out, last_letter, previous_level, nitrogen_level,
            interval_level, user_id;
    long interval, last_totaltime, bottomtime, totalbottomtime, interval_last_now, added_time;
    ArrayList<String> clothing_list;

    Button final_save_button;

    DiveManager dive_manager;
    NitrogenCalculator nitrogen;

    private FirebaseAuth mAuth;
    DatabaseReference my_database;
    FirebaseUser firebase_user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dive);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        mAuth = FirebaseAuth.getInstance();
        dive_manager = DiveManager.getOurInstance();

        // get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager view_pager = (ViewPager) findViewById(R.id.viewpager);
        view_pager.setAdapter(new NewFragPageAdapter(getSupportFragmentManager(),
                NewDiveActivity.this));
        view_pager.setOffscreenPageLimit(50);

        // set ViewPager to the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(view_pager);

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

        final String clicked_item = toolbar_helper.getClickedMenuItem(toolbar, this);

        // display toast for clicked toolbar item
        if (!clicked_item.equals("")) {
            Toast.makeText(this, clicked_item, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(toolbar);
    }

    /* Save general data from the first fragment and check if all data is complete yet. */
    @Override
    public void saveGeneralData(String date_input, String country_input, String dive_spot_input,
                                String buddy_input) {

        Toast.makeText(NewDiveActivity.this, R.string.saved_data, Toast.LENGTH_SHORT).show();

        date = date_input;
        country = country_input;
        dive_spot = dive_spot_input;
        buddy = buddy_input;

        general_data = true;

        checkIfDataComplete();
    }

    /* Save circumstances from the second fragment and check if all data is complete yet. */
    @Override
    public void saveCircumstancesData(String air_temp_input, String surface_temp_input,
                                      String bottom_temp_input, String visibility_input,
                                      String water_type_input, String dive_type_input) {

        Toast.makeText(NewDiveActivity.this, R.string.saved_data, Toast.LENGTH_SHORT).show();

        air_temp = air_temp_input;
        surface_temp = surface_temp_input;
        bottom_temp = bottom_temp_input;
        visibility = visibility_input;
        water_type = water_type_input;
        dive_type = dive_type_input;

        circumstances_data = true;

        checkIfDataComplete();
    }

    /* Save equipment from the third fragment and check if all data is complete yet. */
    @Override
    public void saveEquipmentData(String lead_input, ArrayList<String> clothes_input) {
        Toast.makeText(NewDiveActivity.this, R.string.saved_data, Toast.LENGTH_SHORT).show();

        lead = lead_input;
        clothing_list = clothes_input;

        equipment_data = true;

        checkIfDataComplete();

    }

    /* Save technical data from the fourth fragment and check if all data is complete yet. */
    @Override
    public void saveTechnicalData(String time_in_input, String time_out_input,
                                  String pressure_in_input, String pressure_out_input,
                                  String depth_input, String safetystop_input) {

        Toast.makeText(NewDiveActivity.this, R.string.saved_data, Toast.LENGTH_SHORT).show();

        time_in = time_in_input;
        time_out = time_out_input;
        pressure_in = pressure_in_input;
        pressure_out = pressure_out_input;
        depth = depth_input;
        safetystop = safetystop_input;

        technical_data = true;

        checkIfDataComplete();
    }

    /* Save notes from the fifth fragment and check if all data is complete yet. */
    @Override
    public void saveExtraData(String notes_input) {

        Toast.makeText(NewDiveActivity.this, R.string.saved_data, Toast.LENGTH_SHORT).show();

        notes = notes_input;

        extra_data = true;

        checkIfDataComplete();
    }


    /* If fragment data is not valid, a toast is send and displayed here. */
    @Override
    public void showFragmentToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    /**
     * This function checks if all fragment data is acquired. If so, a final save button is
     * displayed, so the user can save the data to Firebase.
     */
    public void checkIfDataComplete() {

        if (general_data && circumstances_data && equipment_data && technical_data && extra_data) {
            final_save_button.setVisibility(View.VISIBLE);
        }
        else {
            if (final_save_button.getVisibility() == View.VISIBLE) {
                final_save_button.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * When button is clicked, HashMaps for calculating nitrogen levels are read. Firebase is
     * checked for a previous dive, which is used to calculate a possible leftover nitrogen level.
     */
    public void importData(View view) {

        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        nitrogen = new NitrogenCalculator();

        // import the files for calculating nitrogen and read to HashMaps
        InputStream input_stream_first = getResources().openRawResource(R.raw.nitrogen_first);
        InputStream input_stream_second = getResources().openRawResource(R.raw.nitrogen_second);
        InputStream input_stream_third = getResources().openRawResource(R.raw.nitrogen_third);

        try {
            nitrogen.readToHashMap(getString(R.string.first), input_stream_first);
            nitrogen.readToHashMap(getString(R.string.second), input_stream_second);
            nitrogen.readToHashMap(getString(R.string.third), input_stream_third);
        } catch (IOException e) {
            e.printStackTrace();
        }

        last_letter = "";
        last_totaltime = 0;
        added_time = 0;

        // check for previous dive
        my_database = FirebaseDatabase.getInstance().getReference();
        my_database.child("users").child(user_id).child("last_dive").addListenerForSingleValueEvent
                (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    LastDive last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {
                        last_date = last_dive.getDate();
                        last_time_out = last_dive.getTimeOut();
                        last_letter = last_dive.getLetter();
                        last_totaltime = last_dive.getTotaltime();

                        Log.d("test8", "last total time is: " + last_totaltime);
                    }
                }

                // use acquired variables to calculate nitrogen levels
                calculateValues();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * This function uses the acquired information to calculate the bottom time, total bottom time,
     * surface inteval between the end of the last dive and the start of this dive, the previous
     * nitrogen level, the bottom time that has to be added to this dive, the nitrogen level after
     * this dive and the nitrogen level when logging this dive.
     */
    public void calculateValues() {

        bottomtime = nitrogen.calculateBottomTime(time_in, time_out);
        totalbottomtime = nitrogen.calculateTotalTime(last_totaltime, bottomtime);

        // if there's a previous dive, calculate extra data
        if (last_time_out != null && last_date != null) {

            // calculate interval between last dive and this one
            interval_last_now = nitrogen.calculateSurfaceInterval(time_in, date, last_time_out,
                    last_date);

            // if there was a nitrogen residue after last dive, calculate level before this dive
            if (last_letter != null) {
                if (!last_letter.equals("")){

                    previous_level = nitrogen.calculateCurrentLevel(last_letter, interval_last_now);

                    // if diver entered the water with a nitrogen residue, calculate extra time
                    if (!previous_level.equals("")) {
                        added_time = nitrogen.calculateAddedTime(previous_level, depth);
                    }
                }
            }
        }

        // calculate nitrogen level upon resurfacing and upon logging
        nitrogen_level = nitrogen.calculateNitrogen(depth, String.valueOf(bottomtime), added_time);
        interval = nitrogen.calculateSurfaceInterval(time_out, date, "", "");
        interval_level = nitrogen.calculateCurrentLevel(nitrogen_level, interval);

        saveNewDive();
    }

    /**
     * New dive, including calculated levels, is added to Firebase. Calculated levels are shown to
     * the user in dialog, which also gives the user the option to set a timer for when there's no
     * more nitrogen residue.
     */
    public void saveNewDive() {

        // save new dive and update last dive information
        dive_manager.create_dive(user_id, date, country, dive_spot, buddy, air_temp, surface_temp,
                bottom_temp, visibility, water_type, dive_type, lead, clothing_list, time_in,
                time_out, pressure_in, pressure_out, depth, safetystop, notes, previous_level,
                nitrogen_level, interval_level);

        dive_manager.updateLastDive(user_id, date, time_out, interval_level, totalbottomtime);

        // show custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_divesaved);
        dialog.setTitle("Dive logged!");

        // change the dialog according to the levels
        TextView info = (TextView) dialog.findViewById(R.id.dialog_newdive_info);

        if (previous_level == null || previous_level.equals("None")) {

            if (nitrogen_level.equals("Z")) {
                info.setText(getString(R.string.dialog_1) + bottomtime + getString(R.string.dialog_2)
                        + getString(R.string.dialog_3) + nitrogen_level + "." +
                        getString(R.string.dialog_4) + interval_level + getString(R.string.dialog_6));
            } else {
                info.setText(getString(R.string.dialog_1) + bottomtime + getString(R.string.dialog_2)
                        + getString(R.string.dialog_3) + nitrogen_level + "." +
                        getString(R.string.dialog_4) + interval_level + ".");
            }
        }  else {
            if (nitrogen_level.equals("Z")) {
                info.setText(getString(R.string.dialog_1) + bottomtime + getString(R.string.dialog_2)
                        + getString(R.string.dialog_5)
                        + previous_level + ". " + getString(R.string.dialog_3) + nitrogen_level + "." +
                        getString(R.string.dialog_4) + interval_level + getString(R.string.dialog_6));
            } else {
                info.setText(getString(R.string.dialog_1) + bottomtime + getString(R.string.dialog_2)
                        + getString(R.string.dialog_5)
                        + previous_level + ". " + getString(R.string.dialog_3) + nitrogen_level + "." +
                        getString(R.string.dialog_4) + interval_level + ".");
            }
        }

        Button yesbutton = (Button) dialog.findViewById(R.id.dialog_newdive_yesbutton);
        // if button is clicked, close the custom dialog and set timer
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                showToast(getString(R.string.timer_set));
                timerActivity("set_timer");
                dialog.dismiss();
            }
        });

        Button nobutton = (Button) dialog.findViewById(R.id.dialog_newdive_nobutton);
        // if button is clicked, close the custom dialog and go back to menu
        nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(getString(R.string.no_timer_set));
                backToMenu();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /* Show a toast. */
    public void showToast(String toast){
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    /* Go back to MenuActivity. */
    public void backToMenu() {
        Intent menu_activity = new Intent(this, MenuActivity.class);
        startActivity(menu_activity);
        finish();
    }

    /* Move to NitrogenActivity and tell it to set a timer. */
    public void timerActivity(String set_timer) {

        Intent timer_activity = new Intent(this, NitrogenActivity.class);

        timer_activity.putExtra(set_timer, set_timer);
        timer_activity.putExtra("nitrogen_level", nitrogen_level);
        timer_activity.putExtra("interval_level", interval_level);

        startActivity(timer_activity);

        finish();
    }

    /* When back navigation is pressed, warn user that data will be lost. */
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
