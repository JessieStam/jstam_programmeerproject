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
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
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

        findViewById(R.id.new_dive_button).setOnClickListener(this);
        findViewById(R.id.dive_log_button).setOnClickListener(this);
        findViewById(R.id.statistics_button).setOnClickListener(this);
        findViewById(R.id.fish_log_button).setOnClickListener(this);

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
    public void onClick (View v){
        int i = v.getId();
        String new_instr = "Logged in succesfully!";

        if (i == R.id.new_dive_button) {

            Intent new_dive_activity = new Intent(this, NewDiveActivity.class);
            Toast.makeText(this, "Logging a new dive...", Toast.LENGTH_SHORT).show();

            startActivity(new_dive_activity);
            finish();

        } else if (i == R.id.dive_log_button) {

            Intent dive_log_activity = new Intent(this, DiveLogActivity.class);
            Toast.makeText(this, "Opening dive log...", Toast.LENGTH_SHORT).show();

            startActivity(dive_log_activity);
            finish();

        } else if (i == R.id.statistics_button) {

            Intent statistics_activity = new Intent(this, StatisticsActivity.class);
            Toast.makeText(this, "Showing statistics...", Toast.LENGTH_SHORT).show();

            startActivity(statistics_activity);
            finish();

        } else if (i == R.id.fish_log_button) {

            Intent nitro_activity = new Intent(this, NitroTimerActivity.class);
            Toast.makeText(this, "Showing nitrogren levels...", Toast.LENGTH_SHORT).show();

            startActivity(nitro_activity);
            finish();
        }
    }
}
