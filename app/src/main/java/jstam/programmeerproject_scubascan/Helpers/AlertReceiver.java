package jstam.programmeerproject_scubascan.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import jstam.programmeerproject_scubascan.Activities.NitroTimerActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Created by Jessie on 29/01/2017.
 */

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("test7", "in AlertReceiver");

        createNotification(context, "Scuba Scan", "Nitrogen level is...", "Nitrogen update!");

    }

    private void createNotification(Context context, String message, String text, String alert) {

        Log.d("test7", "in Create notification");

        PendingIntent pending_intent = PendingIntent.getActivity(context, 0, new Intent(context,
                NitroTimerActivity.class), 0);

        NotificationCompat.Builder notify_builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setContentTitle(message).setContentText(text)
                .setTicker(alert).setSmallIcon(R.drawable.droogpak);

        notify_builder.setContentIntent(pending_intent);

        notify_builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);

        notify_builder.setAutoCancel(true);

        NotificationManager notify_manager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        notify_manager.notify(1, notify_builder.build());

    }
}
