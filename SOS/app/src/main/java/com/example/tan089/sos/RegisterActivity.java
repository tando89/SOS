package com.example.tan089.sos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText email,name,password;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    private DatabaseReference userIdRef;
    ProgressDialog registerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText)findViewById(R.id.email);
        name=(EditText)findViewById(R.id.name);
        password=(EditText)findViewById(R.id.password);
        //Create firebase database
        databaseReference= FirebaseDatabase.getInstance().getReference().child("chat_users");
        //Get hold of an instance of FirebaseAuth
        mAuth=FirebaseAuth.getInstance();
        registerDialog=new ProgressDialog(this);
        registerDialog.setMessage("Registering..");

        // TODO: Get hold of an instance of FirebaseAuth
    }
    // Executed when Sign Up button is pressed.
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        email.setError(null);
        password.setError(null);

        // Store values at the time of the login attempt.
        String validEmail = email.getText().toString();
        String validPassword = password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(validPassword)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(validEmail)) {
            email.setError(getString(R.string.error_field_required));
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(validEmail)) {
            email.setError(getString(R.string.error_invalid_email));
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: FirebaseUser() here
            createFirebaseUser();

        }
    }

    private boolean isEmailValid(String email) {
        //More retrictions can be added
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //password must contain more than 5 characters
        return password.length() > 5;
    }

    // TODO: Create a Firebase user
    private void createFirebaseUser() {
        registerDialog.show();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        //create firebase users
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Chat", "createUSer onComplete: " + task.isSuccessful());
                if(!task.isSuccessful()) {
                    Log.d("Chat", "user creation failed");
                    //display error in diaglog box
                    showErrorDialog("Registration failed");
                } else {
                    registerDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Registered successfully",Toast.LENGTH_SHORT).show();
                    userIdRef=databaseReference.child(mAuth.getCurrentUser().getUid());
                    userIdRef.child("name").setValue(name.getText().toString());
                    verifyEmail();
                }
                registerDialog.dismiss();
            }
        });
    }
    // TODO: verify email
    private void verifyEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("Testing: ", "Email sent.");
                }
            }
        });
    }
    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("SOS team")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
}
