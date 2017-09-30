package com.example.tan089.sos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.example.tan089.sos.R.layout.fragment_home;

/**
 * Created by tan089 on 7/13/2017.
 */

public class home extends Fragment {
   /* private Button buttonDisplayToken;
    private TextView textViewToken;*/
    Button getMessage;
    //TextView Message;
    ImageButton alarmButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file at the bottom
        //change R.layout.yourlayoutfilename for each of your fragments

        View viewHome =  inflater.inflate(fragment_home, container, false);
        //Casting variables
        //Message = (TextView) viewHome.findViewById(R.id.msgText);
        /*buttonDisplayToken = (Button) viewHome.findViewById(R.id.buttonDisplayToken);
        buttonDisplayToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting token from shared preferences
                String token = MySharedPref.getInstance(getActivity()).getDeviceToken();

                //if token is not null
                if (token != null) {
                    //displaying the token
                    textViewToken.setText(token);
                } else {
                    //if token is null that means something wrong
                    textViewToken.setText("Token not generated");
                }
            }
        });
        textViewToken = (TextView) viewHome.findViewById(R.id.textViewToken);*/

        /*//Will be
        LocalBroadcastManager.getInstance(this.getActivity()).registerReceiver(mHandler, new IntentFilter("com.example.tan089.sos_Message"));

        if (getActivity().getIntent().getExtras() != null) {
            for(String key : getActivity().getIntent().getExtras().keySet())
            {
                if(key.equals("message"))
                    Message.setText("Message from server:" + " " + getActivity().getIntent().getExtras().getString(key));
            }
        } //remove*/

        getMessage = (Button) viewHome.findViewById(R.id.getMessage);
        getMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetLiveMessage.class);
                startActivity(intent);
            }
        });

        alarmButton = (ImageButton) viewHome.findViewById(R.id.alarmBnt);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), confirmEmergencyPage.class);
                startActivity(intent);
            }
        });
        return viewHome;

    }
    /*//Declare the handler
    private BroadcastReceiver mHandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String MessageBody = intent.getStringExtra("message");
            Message.setText(MessageBody);

        }
    };*/

    /*@Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this.getActivity()).unregisterReceiver(mHandler);
    }*/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("S.O.S");

    }
}
