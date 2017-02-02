package jstam.programmeerproject_scubascan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.Items.LastDive;
import jstam.programmeerproject_scubascan.Items.UserItem;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class StatisticsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    DatabaseReference my_database;

    private FirebaseAuth mAuth;
    FirebaseUser firebase_user;
    String user_id;

    String username;

    TextView username_view, num_dives_view, days_view, hours_view, minutes_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        username_view = (TextView) findViewById(R.id.username_text);
        num_dives_view = (TextView) findViewById(R.id.number_dives);
        days_view = (TextView) findViewById(R.id.days_input_text);
        hours_view = (TextView) findViewById(R.id.hours_input_text);
        minutes_view = (TextView) findViewById(R.id.minutes_input_text);

        mAuth = FirebaseAuth.getInstance();

        my_database = FirebaseDatabase.getInstance().getReference();
        firebase_user = mAuth.getCurrentUser();
        user_id = firebase_user.getUid();

        fillInUsername();
        fillInNumDives();
        fillInBottomtime();

    }

    public void fillInUsername() {

        // haal dingen van firebase
        my_database.child("users").child(user_id).child("user_info").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {
                    Log.d("test8", "datasnapshot is NOT null");

                    UserItem user_item = dataSnapshot.getValue(UserItem.class);

                    if (user_item != null) {

                        username = user_item.getUsername();

                        if (username.length() > 8) {
                            username_view.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                        }

                        username_view.setText(username);
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

    public void fillInNumDives() {

        my_database.child("users").child(user_id).child("dive_log").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int num_dives = 0;

                Log.d("test4", "in onDataChange");

                Iterable<DataSnapshot> dive_log = dataSnapshot.getChildren();

                // shake hands with each of them.'
                for (DataSnapshot dive : dive_log) {

                    Log.d("test9", "num_dives = " + num_dives);
                    num_dives += 1;
                }


                num_dives_view.setText(String.valueOf(num_dives));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("test4", "in onCancelled");
            }
        });

    }

    public void fillInBottomtime() {

        // haal dingen van firebase
        my_database.child("users").child(user_id).child("last_dive").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long bottom_time = 0;

                if (dataSnapshot != null) {
                    Log.d("test8", "datasnapshot is NOT null");

                    LastDive last_dive = dataSnapshot.getValue(LastDive.class);

                    if (last_dive != null) {

                        bottom_time = last_dive.getTotaltime();

                        long days = bottom_time / 1440;
                        long hours = (bottom_time % 1440) / 60;
                        long minutes = bottom_time % 60;

                        days_view.setText(String.valueOf(days));
                        hours_view.setText(String.valueOf(hours));
                        minutes_view.setText(String.valueOf(minutes));
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

        if (!clicked_item.equals("")) {
            Toast.makeText(this, clicked_item, Toast.LENGTH_SHORT).show();
        }

        finish();

        return super.onOptionsItemSelected(toolbar);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
