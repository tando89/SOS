package com.example.tan089.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportPage extends AppCompatActivity {
    private String msg;
    private List<String> devices;
    private ProgressDialog progressDialog;
    private Button cancelButton, getMessage;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);
        castButtons();
        devices = new ArrayList<>();
        loadRegisteredDevices();
        cancelButton = (Button) findViewById(R.id.cancelBnt);
        getMessage = (Button) findViewById(R.id.getMessage);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportPage.this, GetLiveMessage.class);
                startActivity(intent);
            }
        });
    }
    public void castButtons() {
        findViewById(R.id.gunBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.fireBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.bombBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.fightingBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.StalkBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.bioHazardBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.TornadoBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.IntruderBnt).setOnClickListener(buttonsReport);
        findViewById(R.id.terroristBnt).setOnClickListener(buttonsReport);
    }
    public View.OnClickListener buttonsReport = new View.OnClickListener() {
        @Override
        public void onClick(View reportsBnt) {
            //intent to map activity?
            //Intent intent = null;
            switch (reportsBnt.getId()){
                case R.id.gunBnt:
                    msg = "There is a shooting in campus!";
                    //moveToMap();
                    break;
                case R.id.fireBnt:
                    msg = "There is a fire in campus!";
                    //moveToMap();
                    break;
                case R.id.bombBnt:
                    msg = "There is a bomb threat in campus!";
                   // moveToMap();
                    break;
                case R.id.fightingBnt:
                    msg = "There is a fight in campus";
                    //moveToMap();
                    break;
                case R.id.StalkBnt:
                    msg = "There is a stalker in campus";
                    //moveToMap();
                    break;
                case R.id.bioHazardBnt:
                    msg = "There is a biohazard in campus";
                    //moveToMap();
                    break;
                case R.id.TornadoBnt:
                    msg = "There is a tornado in campus";
                    //moveToMap();
                    break;
                case R.id.IntruderBnt:
                    msg = "There is an intruder in campus";
                    //moveToMap();
                    break;
                case R.id.terroristBnt:
                    msg = "There are terrorists in campus";
                    //moveToMap();
                    break;
            }
            sendMultiplePush(msg);
        }

    };
    //method to load all the devices from database
    private void loadRegisteredDevices() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Devices...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_FETCH_DEVICES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                JSONArray jsonDevices = obj.getJSONArray("devices");

                                for (int i = 0; i < jsonDevices.length(); i++) {
                                    JSONObject d = jsonDevices.getJSONObject(i);
                                    devices.add(d.getString("email"));
                                }

                                /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                        ActivitySendPushNotification.this,
                                        android.R.layout.simple_spinner_dropdown_item,
                                        devices);

                                spinner.setAdapter(arrayAdapter);*/
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

        };
        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }
    private void sendMultiplePush(String message) {
        /*final String title = editTextTitle.getText().toString();
        final String message = editTextMessage.getText().toString();
        final String image = editTextImage.getText().toString();*/
        final String title = "SOS";
        //final String message = "Good morning, this is a test from Tan";
        final String alarmMsg = message.toString(); //testing
        progressDialog.setMessage("Sending Push");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_SEND_MULTIPLE_PUSH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        //Toast.makeText(ActivitySendPushNotification.this, response, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                //params.put("message", message);
                params.put("message", alarmMsg); //testing
                /*if (!TextUtils.isEmpty(image))
                    params.put("image", image);*/
                return params;
            }
        };

        MyVolley.getInstance(this).addToRequestQueue(stringRequest);
    }
    /* will be using when Map is connecting to the Map server
    private void moveToMap() {
        Intent confirm = new Intent(ReportPage.this, MapActivity.class);
        startActivity(confirm);
    }*/
}
