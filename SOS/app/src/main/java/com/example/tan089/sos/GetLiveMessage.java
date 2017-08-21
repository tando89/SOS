package com.example.tan089.sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GetLiveMessage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_messages);
    }

    //Open register activity
    public void registerNewUser(View v) {
        Intent registerActivity = new Intent(GetLiveMessage.this, RegisterActivity.class);
        finish();
        startActivity(registerActivity);
    }
}
