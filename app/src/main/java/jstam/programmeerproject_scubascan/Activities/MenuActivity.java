package jstam.programmeerproject_scubascan.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.Helpers.ToolbarHelper;

/**
 * Scuba Scan - MenuActivity
 *
 * Jessie Stam
 * 10560599
 *
 * This is the first activity the user can access only by logging in. From this menu page, the user
 * can choose where to go next.
 */
public class MenuActivity extends LoginActivity implements View.OnClickListener {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // construct toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar_helper = new ToolbarHelper();

        // set listeners for buttons
        findViewById(R.id.new_dive_button).setOnClickListener(this);
        findViewById(R.id.dive_log_button).setOnClickListener(this);
        findViewById(R.id.statistics_button).setOnClickListener(this);
        findViewById(R.id.nitrogen_button).setOnClickListener(this);

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
        } else if (clicked_item.equals("Logged out")) {
            finish();
        }

        return super.onOptionsItemSelected(toolbar);
    }

    /* Listen for button click to determine what to do. */
    @Override
    public void onClick (View v){

        int i = v.getId();

        // upon pressing new dive button, go to NewDiveActivity
        if (i == R.id.new_dive_button) {
            Intent new_dive_activity = new Intent(this, NewDiveActivity.class);
            Toast.makeText(this, R.string.intent_newdive, Toast.LENGTH_SHORT).show();
            startActivity(new_dive_activity);
        }
        // upon pressing dive log button, go to DiveLogActivity
        else if (i == R.id.dive_log_button) {
            Intent dive_log_activity = new Intent(this, DiveLogActivity.class);
            Toast.makeText(this, R.string.intent_divelog, Toast.LENGTH_SHORT).show();
            startActivity(dive_log_activity);
        }
        // upon pressing statistics button, go to StatisticsActivity
        else if (i == R.id.statistics_button) {
            Intent statistics_activity = new Intent(this, StatisticsActivity.class);
            Toast.makeText(this, R.string.intent_statistics, Toast.LENGTH_SHORT).show();
            startActivity(statistics_activity);
        }
        // upon pressing
        else if (i == R.id.nitrogen_button) {
            Intent nitro_activity = new Intent(this, NitrogenActivity.class);
            Toast.makeText(this, R.string.intent_nitrogen, Toast.LENGTH_SHORT).show();
            startActivity(nitro_activity);
        }
    }

    /* When backbutton is pressed, go back to HomeActivity*/
    @Override
    public void onBackPressed() {
        Intent back_to_home = new Intent(this, HomeActivity.class);
        startActivity(back_to_home);
        finish();
    }
}
