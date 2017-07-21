package com.example.tan089.sos;


import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
    }
}
