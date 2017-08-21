package com.example.tan089.sos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

public class ReportPage extends AppCompatActivity {
    private String msg;
    private String phoneNo = "+19093798800";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);
        castButtons();
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
    }
    public View.OnClickListener buttonsReport = new View.OnClickListener() {
        @Override
        public void onClick(View reportsBnt) {
            //intent to map activity?
            Intent intent = null;
            switch (reportsBnt.getId()){
                case R.id.gunBnt:
                    msg = "There is a shooting in campus!";
                    break;
                case R.id.fireBnt:
                    msg = "There is a fire in campus!";
                    break;
                case R.id.bombBnt:
                    msg = "There is a bomb threat in campus!";
                    break;
                case R.id.fightingBnt:
                    msg = "There is a fight in campus";
                    break;
                case R.id.StalkBnt:
                    msg = "There is a stalker in campus";
                    break;
                case R.id.bioHazardBnt:
                    msg = "There is a biohazard in campus";
                    break;
                case R.id.TornadoBnt:
                    msg = "There is a tornado in campus";
                    break;
                case R.id.IntruderBnt:
                    msg = "There is an intruder in campus";
                    break;
            }
            sendSMS(msg);
        }
    };
    private void sendSMS(String message) {

        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(getApplicationContext(), "SOS sent", Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this, MapActivity.class));

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "SOS message Failed to send", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
