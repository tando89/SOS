package com.example.tan089.sos;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

/**
 * Created by tan089 on 7/20/2017.
 */

public class MyFirebaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "FCM";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //TODO : Handle FCM messages here
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message:" + remoteMessage.getNotification().getBody());

        if (remoteMessage.getData().size() > 0) {
            //set the key from FCM console to "message"
            String message = remoteMessage.getData().get("message");
            String messageNotification = remoteMessage.getNotification().getBody();
            //Use Intent to send the message from FCM notification to Home page as a textView
            Intent intent = new Intent ("com.example.tan089.sos_Message");
            intent.putExtra("message", message);
            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
            localBroadcastManager.sendBroadcast(intent);
            createNotification(messageNotification);
        }
    }
    private void createNotification( String messageBody) {
        /* Trying to intent the notification to another activity
        Intent intent = new Intent(this, Updates.class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);*/

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setVibrate(new long[]{3000, 3000, 2000, 2000, 1000})
                .setSmallIcon(R.drawable.sos_launch_icon)
                .setContentText(messageBody)
                .setAutoCancel( true )
                .setSound(notificationSoundURI);
                //.setContentIntent(resultIntent);
                mNotificationBuilder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}
