package jstam.programmeerproject_scubascan.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import jstam.programmeerproject_scubascan.Fragments.FirstNewDiveFragment;
import jstam.programmeerproject_scubascan.Fragments.SecondNewDiveFragment;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class NewDiveActivity extends AppCompatActivity implements SecondNewDiveFragment.SecondNewDiveFragmentListener {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    boolean general_data;
    boolean circumstances_data;
    boolean gear_data;
    boolean numbers_data;
    boolean fish_data;
    boolean extra_data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dive);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

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

        // Begin the transaction
        FragmentTransaction frag_trans = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        frag_trans.replace(R.id.fragment_placeholder, new SecondNewDiveFragment());
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        frag_trans.commit();

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

//    @Override
//    public void saveGeneralData(String date, String country, String dive_spot, String buddy) {
//        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();
//
//        general_data = true;
//    }

    @Override
    public void saveCircumstancesData(String air_temp, String surface_temp, String bottom_temp,
                                      String visibility, String water_type, String dive_type) {
        Toast.makeText(NewDiveActivity.this, "Saved data!", Toast.LENGTH_SHORT).show();

        circumstances_data = true;
    }

}
