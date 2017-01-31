package jstam.programmeerproject_scubascan.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.FinishedFragments.FirstNewDiveFragmentFinished;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FifthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.FourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.SecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.DisplayFragments.UnfinishedFragments.ThirdNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.DiveLogFragmentPageAdapter;
import jstam.programmeerproject_scubascan.Helpers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.NewDiveFragmentPageAdapter;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.Items.LastDive;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 28/01/2017.
 */

public class DiveLogDetailsActivity extends AppCompatActivity implements FirstNewDiveFragment.FirstNewDiveFragmentListener,
        SecondNewDiveFragment.SecondNewDiveFragmentListener, ThirdNewDiveFragment.ThirdNewDiveFragmentListener,
        FourthNewDiveFragment.FourthNewDiveFragmentListener, FifthNewDiveFragment.FifthNewDiveFragmentListener {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    TextView dive_title;

    String dive_number;

    private FirebaseAuth mAuth;
    FirebaseUser firebase_user;
    String user_id;
    DatabaseReference my_database;

    DiveItem dive_item;
    DiveManager dive_manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dive_log_details);

        mAuth = FirebaseAuth.getInstance();

        firebase_user = mAuth.getCurrentUser();
        user_id = firebase_user.getUid();

        dive_item = new DiveItem();
        dive_manager = DiveManager.getOurInstance();

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        dive_title = (TextView) findViewById(R.id.viewing_dive_title);

        // get extras from BooksFoundActivity
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            dive_number = extras.getString("clicked_dive");
            dive_title.setText(dive_number);
        }


        //get data from firebase
        getDataFromFirebase();

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

    public void getDataFromFirebase() {

        // check previous dive info
        my_database = FirebaseDatabase.getInstance().getReference();
        my_database.child("users").child(user_id).child("dive_log").child(dive_number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("test6", "in onDataChange");

                dive_item = dataSnapshot.getValue(DiveItem.class);

                Log.d("test6", "dive item date is: " + dive_item.getDate());

                //getDiveItem();

                // Get the ViewPager and set it's PagerAdapter so that it can display items
                ViewPager view_pager = (ViewPager) findViewById(R.id.viewpager);
                view_pager.setAdapter(new DiveLogFragmentPageAdapter(getSupportFragmentManager(),
                        DiveLogDetailsActivity.this, dive_number, dive_item));

                view_pager.setOffscreenPageLimit(50);

                // Give the TabLayout the ViewPager
                TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
                tabLayout.setupWithViewPager(view_pager);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void saveGeneralData(String date_input, String country_input, String dive_spot_input,
                                String buddy_input) {

        Toast.makeText(DiveLogDetailsActivity.this, "Edited data!", Toast.LENGTH_SHORT).show();

        DiveItem edited_dive = dive_manager.editDiveGeneral(dive_number, user_id, dive_item, date_input, country_input,
                dive_spot_input, buddy_input);

        dive_item = edited_dive;

    }

    @Override
    public void saveCircumstancesData(String air_temp, String surface_temp, String bottom_temp, String visibility, String water_type, String dive_type) {

    }

    @Override
    public void saveExtraData(String notes) {

    }

    @Override
    public void saveTechnicalData(String time_in, String time_out, String pressure_in, String pressure_out, String depth, String safetystop) {

    }

    @Override
    public void saveEquipmentData(String lead, ArrayList<String> clothes) {

    }
}
