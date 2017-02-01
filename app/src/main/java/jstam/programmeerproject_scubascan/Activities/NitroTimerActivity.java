package jstam.programmeerproject_scubascan.Activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
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

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;

import jstam.programmeerproject_scubascan.Helpers.AlertReceiver;
import jstam.programmeerproject_scubascan.Helpers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.NitrogenCalculator;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.Items.LastDive;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class NitroTimerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    String nitrogen_level, interval_level, current_level;
    long interval;

    TextView nitro_data;
    TextView intro_text;

    NitrogenCalculator calculator;


    DatabaseReference my_database;

    private FirebaseAuth mAuth;
    FirebaseUser firebase_user;
    String user_id;

    String last_date, last_time_out, last_letter;

    long last_totaltime = 0;

    final DiveItem new_dive = new DiveItem();

    LastDive last_dive = new LastDive();

    DiveManager dive_manager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nitrotimer);

        Log.d("test7", "in NitroTimerActivity");

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

        //calculate all the things
        InputStream input_stream_first = getResources().openRawResource(R.raw.nitrogen_first);
        InputStream input_stream_second = getResources().openRawResource(R.raw.nitrogen_second);
        InputStream input_stream_third = getResources().openRawResource(R.raw.nitrogen_third);

        try {
            calculator.readToHashMap("first", input_stream_first);
            calculator.readToHashMap("second", input_stream_second);
            calculator.readToHashMap("third", input_stream_third);
        } catch (IOException e) {
            Log.d("test6", "throws exception");
            e.printStackTrace();
        }

        NotificationManager notify_manager;

        int notific_id = 100;


        // get interval level, zoveel minuten voor de dialog showed

        // wanneer geopend bereken current level

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("set_timer") != null) {

                nitrogen_level = bundle.getString("nitrogen_level");
                interval_level = bundle.getString("interval_level");

                setAlarm(interval_level);
                //showNotification();
            }
        } else {
            Log.d("test7", "bundle is null");
        }

        // set info
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

        // display toast for clicked toolbar item
        String clicked_item = toolbar_helper.getClickedMenuItem(toolbar, this);
        Toast.makeText(this, clicked_item, Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(toolbar);
    }

    public void setAlarm(String current_letter) {

        Log.d("test7", "in setAlarm");

        // calculate minutes
        long minutes = calculator.timeToNitroFree(current_letter);

        long alert_time = new GregorianCalendar().getTimeInMillis() + minutes * 60 * 1000;

        Intent alert_intent = new Intent (this, AlertReceiver.class);

        AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarm_manager.set(AlarmManager.RTC_WAKEUP, alert_time,
                PendingIntent.getBroadcast(this, 1, alert_intent,
                        PendingIntent.FLAG_UPDATE_CURRENT));


        Toast.makeText(this, "Nitrogen-free in " + minutes + " minutes!", Toast.LENGTH_SHORT).show();

    }

    public void setLevel() {

        // haal dingen van firebase
        my_database.child("users").child(user_id).child("last_dive").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Log.d("test8", "datasnapshot is NOT null");

                    LastDive last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {
                        last_letter = last_dive.getLetter();

                        if (last_letter == null) {
                            last_letter = "None";

                        }
                        nitro_data.setText(last_letter);

                    }

                } else {

                    Log.d("test8", "datasnapshot is null");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test4", "in onCancelled");
            }
        });


    }

    public void recalculate(View view){

        last_date = "";
        last_time_out = "";

        // haal dingen van firebase
        my_database.child("users").child(user_id).child("last_dive").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Log.d("test8", "datasnapshot is NOT null");

                    LastDive last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {

                        Log.d("test8", "last_dive NOT null");
                        last_date = last_dive.getDate();
                        last_time_out = last_dive.getTimeOut();
                        //last_letter = last_dive.getLetter();
                        last_totaltime = last_dive.getTotaltime();

                        if (last_letter == null) {
                            Log.d("test8", "last_letter is null");

                            last_letter = "None";
                            Log.d("test8", "changing last_letter to: " + last_letter);
                        }
                    }

                } else {

                    Log.d("test8", "datasnapshot is null");
                }

                interval = calculator.calculateSurfaceInterval(last_time_out, last_date, "", "");

                Log.d("test8", "inteval in change: " + interval);

                current_level = calculator.calculateCurrentLevel(last_letter, interval);

                Log.d("test8", "current_level in change: " + current_level);

                // set current level to text
                if (current_level != null) {
                    nitro_data.setText(current_level);
                    intro_text.setText("Your current nitrogen level is...");
                } else {
                    current_level = "None";
                    nitro_data.setText(current_level);
                    intro_text.setText("Your current nitrogen level is...");
                }

                Log.d("test8", "updateLastDive with user: " + user_id + ", last_date: " + last_date +
                        ", last_time_out: " + last_time_out + ", current_level " + current_level +
                        " and " + last_totaltime);

                if (user_id != null && last_date != null && last_time_out != null
                        && last_totaltime != 0) {

                    dive_manager.updateLastDive(user_id, last_date, last_time_out, current_level,
                            last_totaltime);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test4", "in onCancelled");
            }
        });

        Toast.makeText(this, "Recalculated level!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {

        Intent back_to_menu = new Intent(this, MenuActivity.class);

        startActivity(back_to_menu);

    }


}