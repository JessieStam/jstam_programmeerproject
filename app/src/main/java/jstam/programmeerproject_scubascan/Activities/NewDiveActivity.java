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
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import jstam.programmeerproject_scubascan.Fragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.NewDiveFragmentPageAdapter;
import jstam.programmeerproject_scubascan.Fragments.SecondNewDiveFragment;
import jstam.programmeerproject_scubascan.Items.DiveItem;
import jstam.programmeerproject_scubascan.Items.UserItem;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class NewDiveActivity extends AppCompatActivity implements FirstNewDiveFragment.FirstNewDiveFragmentListener,
        SecondNewDiveFragment.SecondNewDiveFragmentListener {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    boolean general_data, circumstances_data, gear_data, numbers_data, fish_data, extra_data;
    String date, country, dive_spot, buddy, air_temp, surface_temp, bottom_temp, visibility, water_type, dive_type;
    Button final_save_button;

    private FirebaseAuth mAuth;

    DiveManager dive_manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dive);

        mAuth = FirebaseAuth.getInstance();

        dive_manager = new DiveManager();

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager view_pager = (ViewPager) findViewById(R.id.viewpager);
        view_pager.setAdapter(new NewDiveFragmentPageAdapter(getSupportFragmentManager(),
                NewDiveActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(view_pager);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        final_save_button = (Button) findViewById(R.id.final_save_button);

        // set booleans to false
        general_data = false;
        circumstances_data = false;
        gear_data = false;
        numbers_data = false;
        fish_data = false;
        extra_data = false;

//        // Begin the transaction
//        FragmentTransaction frag_trans = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        frag_trans.replace(R.id.fragment_placeholder, new FirstNewDiveFragment());
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        frag_trans.commit();

//        // Begin the transaction
//        FragmentTransaction frag_trans = getSupportFragmentManager().beginTransaction();
//        // Replace the contents of the container with the new fragment
//        frag_trans.replace(R.id.fragment_placeholder, new SecondNewDiveFragment());
//        // or ft.add(R.id.your_placeholder, new FooFragment());
//        // Complete the changes added above
//        frag_trans.commit();

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
    public void saveEquipmentData(String lead, ArrayList<String> clothes) {
        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

    }

    public void checkIfDataComplete() {

        if (general_data && circumstances_data) {
            final_save_button.setVisibility(View.VISIBLE);

            Log.d("test", "general data and circumsances data are true");
        }
        else {
            if (final_save_button.getVisibility() == View.VISIBLE) {
                final_save_button.setVisibility(View.INVISIBLE);

                Log.d("test", "Not true...");
            }
        }
    }

    public void saveNewDive(View view) {

        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        dive_manager.create_dive(user_id, date, country, dive_spot, buddy, air_temp, surface_temp,
                bottom_temp, visibility, water_type, dive_type);

    }
}
