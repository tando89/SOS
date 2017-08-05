package com.example.tan089.sos;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by tan089 on 8/4/2017.
 */

public class MySingleton {
    private static  MySingleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;

    private MySingleton (Context context)
    {
        // Specify the application context
        mCtx =context;
        // Get the request queue
        requestQueue = getRequestQueue();
    }
    //synchronized method
    public static synchronized MySingleton getInstance(Context context) {
        // If Instance is null then initialize new Instance
        if (mInstance==null){
            mInstance = new MySingleton(context);
        }
        // Return MySingleton new Instance
        return mInstance;
    }
    //method for request queue
    public RequestQueue getRequestQueue() {
        //check to see the requestQueue is initialized or not
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        //return request queue
        return requestQueue;
    }

    public <T>void addTorequestqueue(Request<T> request){
        // Add the specified request to the request queue
        requestQueue.add(request);
    }
}
