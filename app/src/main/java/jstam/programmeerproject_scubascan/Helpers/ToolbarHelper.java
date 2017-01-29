package jstam.programmeerproject_scubascan.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import jstam.programmeerproject_scubascan.Activities.DiveLogActivity;
import jstam.programmeerproject_scubascan.Activities.NitroTimerActivity;
import jstam.programmeerproject_scubascan.Activities.LoginActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Activities.StatisticsActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * TBR Jar - MenuHelper
 *
 * Jessie Stam
 * 10560599
 *
 * Helper class that handles toolbar actions.
 */
public class ToolbarHelper {

    Context context;

    /**
     * Gets the clicked toolbar item and handles accordingly.
     */
    public String getClickedMenuItem(MenuItem item, Context context) {

        this.context = context;
        //manager = BookManager.getOurInstance();

        int id = item.getItemId();
        String toast = "";

        // chick which item is clicked
        switch (id) {
            // if settings button is clicked, inform user that it doesn't work
            case R.id.action_settings:
                toast = "This button is for decorative purposes only";
                break;
            // if account button is clicked, take user to MainActivity
            case R.id.action_account:
                Intent openAccountpage = new Intent(context, LoginActivity.class);
                context.startActivity(openAccountpage);
                break;

            case R.id.new_dive_toolbutton:
                if (toast.equals("")) {
                    Intent new_dive_activity = new Intent(context, NewDiveActivity.class);
                    toast = "Logging a new dive...";

                    context.startActivity(new_dive_activity);
                }
                break;

            // if favorite list is clicked and user in logged in, take user to favorite
            case R.id.dive_log_toolbutton:
                if (toast.equals("")) {
                    Intent dive_log_activity = new Intent(context, DiveLogActivity.class);
                    toast = "Opening dive log...";

                    context.startActivity(dive_log_activity);
                }
                break;

            // if finished list is clicked and user is logged in, take user to finished;
            case R.id.statistics_toolbutton:
                if (toast.equals("")) {
                    Intent statistics_activity = new Intent(context, StatisticsActivity.class);
                    toast = "Showing statistics...";

                    context.startActivity(statistics_activity);
                }
                break;

            // if current list is clicked and user is logged in, take user to current list;
            case R.id.fish_log_toolbutton:
                if (toast.equals("")) {
                    Intent fish_log_activity = new Intent (context, NitroTimerActivity.class);
                    toast = "Opening aquarium...";

                    context.startActivity(fish_log_activity);
                }
                break;
        }
        return toast;
    }
}