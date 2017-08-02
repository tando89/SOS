package com.example.tan089.sos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by tan089 on 7/13/2017.
 */

public class AnonymousEmailReport extends Fragment {
    //Declaring EditText
    private EditText editTextSubject;
    private EditText editTextMessage;

    //Send button
    private Button buttonSend;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View viewEmailReport =  inflater.inflate(R.layout.fragment_email_report, container, false);
        //casting editexts, button to their ids
        editTextSubject = (EditText) viewEmailReport.findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) viewEmailReport.findViewById(R.id.editTextMessage);
        buttonSend = (Button) viewEmailReport.findViewById(R.id.buttonSend);
        //hide keyboard for textedits
        editTextSubject.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        editTextMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        //button click function to send email
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Make sure all the fields are filled
                if(editTextSubject.length() <= 0 || editTextMessage.length() <= 0){
                    Toast.makeText(getActivity().getApplicationContext(), "Please fill out all the required fields*", Toast.LENGTH_LONG).show();
                }
                else {
                    //Confirmation dialog
                    AlertDialog.Builder confirmation_tosend = new AlertDialog.Builder(getActivity());
                    confirmation_tosend.setMessage("Click \"Continue\" to send the report")
                            .setPositiveButton("Continue", alertdialog_oncontinue)
                            .setNegativeButton("Cancel", null)
                            .setCancelable(false).show();
                }
            }
        });
        return  viewEmailReport;
    }
    //Execute sendMail when user click continue. Clear all the fields. Go back to the home page
    protected DialogInterface.OnClickListener alertdialog_oncontinue = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            sendEmail();
            editTextMessage.setText("");
            editTextSubject.setText("");

        }
    };
    //Call the sendMail method
    private void sendEmail() {
        //Getting content for email. Set default recipient @String email
        String email = "tantdo89@gmail.com";
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(getActivity(), email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Anonymous Email Report");
    }
    //hide keyboard after click submit button method
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
