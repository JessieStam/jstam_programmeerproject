package jstam.programmeerproject_scubascan.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import java.util.GregorianCalendar;
import jstam.programmeerproject_scubascan.Helpers.Helpers.AlertReceiver;
import jstam.programmeerproject_scubascan.Helpers.Managers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.Calculators.NitrogenCalculator;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.Items.LastDive;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.Helpers.ToolbarHelper;

/**
 * Scuba Scan - NitrogenActivity
 *
 * Jessie Stam
 * 10560599
 *
 * This activity shows the user's last calculated nitrogen level. Upon pressing the button, this
 * activity recalculates that level according to the current time. When accessed from
 * NewDiveActivity, this activity sets a timer for when the user is nitrogen-free.
 */
public class NitrogenActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    String nitrogen_level, interval_level, current_level;
    long interval;
    long last_totaltime = 0;

    TextView nitro_data;
    TextView intro_text;

    NitrogenCalculator calculator;

    DatabaseReference my_database;
    private FirebaseAuth mAuth;
    FirebaseUser firebase_user;

    String last_date, last_time_out, last_letter, user_id;

    LastDive last_dive = new LastDive();

    DiveManager dive_manager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrogen);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        intro_text = (TextView) findViewById(R.id.nitrogen_text);
        nitro_data = (TextView) findViewById(R.id.nitrogen_letter);

        mAuth = FirebaseAuth.getInstance();
        my_database = FirebaseDatabase.getInstance().getReference();
        firebase_user = mAuth.getCurrentUser();
        user_id = firebase_user.getUid();

        calculator = new NitrogenCalculator();

        dive_manager = DiveManager.getOurInstance();

        // get files for calculating nitrogen and read to HashMap
        InputStream input_stream_first = getResources().openRawResource(R.raw.nitrogen_first);
        InputStream input_stream_second = getResources().openRawResource(R.raw.nitrogen_second);
        InputStream input_stream_third = getResources().openRawResource(R.raw.nitrogen_third);

        try {
            calculator.readToHashMap(getString(R.string.first), input_stream_first);
            calculator.readToHashMap(getString(R.string.second), input_stream_second);
            calculator.readToHashMap(getString(R.string.third), input_stream_third);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // if extras from NewDiveActivity, set alarm
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("set_timer") != null) {
                nitrogen_level = bundle.getString("nitrogen_level");
                interval_level = bundle.getString("interval_level");

                setAlarm(interval_level);
            }
        }
        setLevel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu toolbar) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.toolbar_main, toolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem toolbar) {

        String clicked_item = toolbar_helper.getClickedMenuItem(toolbar, this);
        // display toast for clicked toolbar item
        if (!clicked_item.equals("")) {
            Toast.makeText(this, clicked_item, Toast.LENGTH_SHORT).show();
        }
        finish();

        return super.onOptionsItemSelected(toolbar);
    }

    /* This activity sets an alarm for when there is no more nitrogen residue. */
    public void setAlarm(String current_letter) {

        // calculate time to nitrogen-free and set to milliseconds
        long minutes = calculator.timeToNitroFree(current_letter);
        long alert_time = new GregorianCalendar().getTimeInMillis() + minutes * 60 * 1000;

        // set alarm
        Intent alert_intent = new Intent (this, AlertReceiver.class);
        AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarm_manager.set(AlarmManager.RTC_WAKEUP, alert_time,
                PendingIntent.getBroadcast(this, 1, alert_intent,
                        PendingIntent.FLAG_UPDATE_CURRENT));

        // let user know how long it will take
        Toast.makeText(this, getString(R.string.nitrogen_toast1) + minutes +
                getString(R.string.nitrogen_toast2), Toast.LENGTH_SHORT).show();
    }

    /* This function displays the user's last calculated nitrogen level */
    public void setLevel() {

        my_database.child("users").child(user_id).child("last_dive")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {
                        last_letter = last_dive.getLetter();

                        if (last_letter == null) {
                            last_letter = "None";
                        }
                        nitro_data.setText(last_letter);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * When this button is clicked, this function recalculates the user's nitrogen level and updates
     * it on Firebase.
     */
    public void recalculate(View view){

        last_date = "";
        last_time_out = "";

        my_database.child("users").child(user_id).child("last_dive")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    LastDive last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {
                        last_date = last_dive.getDate();
                        last_time_out = last_dive.getTimeOut();
                        last_totaltime = last_dive.getTotaltime();
                    }

                    if (last_letter == null) {
                        last_letter = "None";
                    }

                    // calculate time between dive and now and calculate current nitrogen level
                    interval = calculator.calculateSurfaceInterval(last_time_out, last_date, "", "");
                    current_level = calculator.calculateCurrentLevel(last_letter, interval);

                    // set current level to TextView
                    if (current_level != null) {
                        nitro_data.setText(current_level);
                        intro_text.setText(R.string.current_nitrogen_text);
                    } else {
                        current_level = "None";
                        nitro_data.setText(current_level);
                        intro_text.setText(R.string.current_nitrogen_text);
                    }

                    // update last dive on Firebase
                    if (user_id != null && last_date != null && last_time_out != null
                            && last_totaltime != 0) {

                        dive_manager.updateLastDive(user_id, last_date, last_time_out, current_level,
                                last_totaltime);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        Toast.makeText(this, R.string.recal_level, Toast.LENGTH_SHORT).show();
    }

    /* When back navigation is pressed, go back to MenuActivity. */
    @Override
    public void onBackPressed() { finish(); }
}