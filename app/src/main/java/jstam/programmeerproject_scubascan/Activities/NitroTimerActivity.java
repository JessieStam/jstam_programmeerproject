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
import android.widget.Toast;

import java.util.GregorianCalendar;

import jstam.programmeerproject_scubascan.Helpers.AlertReceiver;
import jstam.programmeerproject_scubascan.R;
import jstam.programmeerproject_scubascan.Helpers.ToolbarHelper;

/**
 * Created by Jessie on 12/01/2017.
 */

public class NitroTimerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ToolbarHelper toolbar_helper;

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

        NotificationManager notify_manager;

        int notific_id = 100;

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            if (bundle.getString("set_timer") != null) {
                setAlarm();
                //showNotification();
            }
        } else {
            Log.d("test7", "bundle is null");
        }

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

    public void setAlarm() {

        Log.d("test7", "in setAlarm");

        Long alert_time = new GregorianCalendar().getTimeInMillis()+5*1000;

        Intent alert_intent = new Intent (this, AlertReceiver.class);

        AlarmManager alarm_manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarm_manager.set(AlarmManager.RTC_WAKEUP, alert_time,
                PendingIntent.getBroadcast(this, 1, alert_intent,
                        PendingIntent.FLAG_UPDATE_CURRENT));

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification() {

        NotificationCompat.Builder notify_builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("ScubaScan").setContentText("Nitrogen-free!")
                .setTicker("Nitrogen-level changed").setSmallIcon(R.drawable.droogpak);

        Intent timer_intent = new Intent(this, NitroTimerActivity.class);

        TaskStackBuilder stackbuilder = TaskStackBuilder.create(this);

        stackbuilder.addParentStack(NitroTimerActivity.class);
        stackbuilder.addNextIntent(timer_intent);

        PendingIntent pending_intent = stackbuilder.getPendingIntent
                (0, PendingIntent.FLAG_UPDATE_CURRENT);

        notify_builder.setContentIntent(pending_intent);

        NotificationManager notify_manager;

        notify_manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notify_manager.notify(100, notify_builder.build());

        //is_notif_active = true;


    }

    @Override
    public void onBackPressed() {

        Intent back_to_menu = new Intent(this, MenuActivity.class);

        startActivity(back_to_menu);

    }


}