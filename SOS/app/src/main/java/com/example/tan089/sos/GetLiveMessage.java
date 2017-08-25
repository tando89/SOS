package com.example.tan089.sos;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class GetLiveMessage extends AppCompatActivity {
    // TODO: Add member variables here:
    private FirebaseAuth mAuth;
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_messages);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        // TODO: Grab an instance of FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
    }
    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        // TODO: Call attemptLogin() here
        attemptLogin();
    }
    //Open register activity
    public void registerNewUser(View v) {
        Intent registerActivity = new Intent(GetLiveMessage.this, RegisterActivity.class);
        finish();
        startActivity(registerActivity);
    }
    //Open forgot password activity
    public void userPwReset(View v) {
        Intent forgotPasswordActivity = new Intent(GetLiveMessage.this, ForgotPassword.class);
        finish();
        startActivity(forgotPasswordActivity);
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        //stop the task if the fields are left blank
        if (email.equals("") || password.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();
        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("SOS message", "signInWithEmail() onComplete: " + task.isSuccessful());

                if(!task.isSuccessful()) {
                    Log.d("SOS message", "Problem signing: " + task.getException());
                    //display error in dialog box
                    showErrorDialog("Problem with signing in!");
                } else {
                    Intent intent = new Intent(GetLiveMessage.this, SosForumChat.class);
                    finish();
                    startActivity(intent);
                }
            }
        });


    }

    // TODO: Show error on screen with an alert dialog
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
