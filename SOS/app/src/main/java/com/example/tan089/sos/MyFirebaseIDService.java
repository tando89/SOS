package com.example.tan089.sos;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by tan089 on 7/20/2017.
 */

public class MyFirebaseIDService extends FirebaseInstanceIdService {
    private static String TAG = "InstanceID";

    @Override
    public void onTokenRefresh() {
        //Get updated InstanceID token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token:" + refreshedToken);

        // TODO: Send any registration from any device to the app servers
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {

    }
}
