package jstam.programmeerproject_scubascan.Helpers.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import jstam.programmeerproject_scubascan.Activities.DiveLogActivity;
import jstam.programmeerproject_scubascan.Activities.HomeActivity;
import jstam.programmeerproject_scubascan.Activities.NitrogenActivity;
import jstam.programmeerproject_scubascan.Activities.NewDiveActivity;
import jstam.programmeerproject_scubascan.Activities.StatisticsActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - ToolbarHelper
 *
 * Jessie Stam
 * 10560599
 *
 * Helper class that handles toolbar actions.
 */
public class ToolbarHelper {

    Context context;
    private FirebaseAuth mAuth;

    /**
     * Gets the clicked toolbar item and handles accordingly.
     */
    public String getClickedMenuItem(MenuItem item, Context context) {

        this.context = context;
        int id = item.getItemId();
        String toast = "";

        switch (id) {
            // when account button is clicked, log user out
            case R.id.action_account:

                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                Intent logOut = new Intent(context, HomeActivity.class);
                logOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                logOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                logOut.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                context.startActivity(logOut);
                break;

            case R.id.new_dive_toolbutton:

                Intent new_dive_activity = new Intent(context, NewDiveActivity.class);
                toast = context.getString(R.string.intent_newdive);
                context.startActivity(new_dive_activity);
                break;

            case R.id.dive_log_toolbutton:

                Intent dive_log_activity = new Intent(context, DiveLogActivity.class);
                toast = context.getString(R.string.intent_divelog);
                context.startActivity(dive_log_activity);
                break;

            case R.id.statistics_toolbutton:

                Intent statistics_activity = new Intent(context, StatisticsActivity.class);
                toast = context.getString(R.string.intent_statistics);
                context.startActivity(statistics_activity);
                break;

            case R.id.nitrogen_toolbutton:

                Intent nitrogen_activity = new Intent (context, NitrogenActivity.class);
                toast = context.getString(R.string.intent_nitrogen);
                context.startActivity(nitrogen_activity);
                break;
        }
        return toast;
    }
}