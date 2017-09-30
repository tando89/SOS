package com.example.tan089.sos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tan089 on 9/30/2017.
 */

public class MySharedPref {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";

    private static MySharedPref mInstance;
    private static Context mCtx;

    private MySharedPref(Context context) {
        mCtx = context;
    }
    public static synchronized MySharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySharedPref(context);
        }
        return mInstance;
    }
    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }
    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }
}
