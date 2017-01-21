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

import jstam.programmeerproject_scubascan.Fragments.FifthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.FourthNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.ThirdNewDiveFragment;
import jstam.programmeerproject_scubascan.Helpers.DiveManager;
import jstam.programmeerproject_scubascan.Helpers.NewDiveFragmentPageAdapter;
import jstam.programmeerproject_scubascan.Fragments.SecondNewDiveFragment;
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

    Button final_save_button;

    ArrayList<String> clothing_list;

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

    public void saveNewDive(View view) {

        FirebaseUser firebase_user = mAuth.getCurrentUser();
        String user_id = firebase_user.getUid();

        dive_manager.create_dive(user_id, date, country, dive_spot, buddy, air_temp, surface_temp,
                bottom_temp, visibility, water_type, dive_type, lead, clothing_list, time_in,
                time_out, pressure_in, pressure_out, depth, safetystop, notes);

    }

}
