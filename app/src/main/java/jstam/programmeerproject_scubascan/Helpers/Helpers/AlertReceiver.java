package jstam.programmeerproject_scubascan.Helpers.Helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import jstam.programmeerproject_scubascan.Activities.NitrogenActivity;
import jstam.programmeerproject_scubascan.R;

/**
 * Scuba Scan - AlertReceiver
 *
 * Jessie Stam
 * 10560599
 *
 * BroadcastReceiver class that sets a timer for when nitrogen residue is gone.
 */
public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotification(context, context.getString(R.string.app_title), context.getString
                (R.string.nitrogen_free_notification), context.getString
                (R.string.nitrogen_update_notification));
    }

    /**
     * This function creates the notification.
     */
    private void createNotification(Context context, String message, String text, String alert) {

        // when notification clicked, go to this activity
        PendingIntent pending_intent = PendingIntent.getActivity(context, 0, new Intent(context,
                NitrogenActivity.class), 0);

        // build notification
        NotificationCompat.Builder notify_builder = (NotificationCompat.Builder)
                new NotificationCompat.Builder(context)
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
