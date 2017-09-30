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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText email,name,password;
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
        sendTokenToServer();
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
    // TODO: send token and email to server
    private void sendTokenToServer() {
    /*registerDialog = new ProgressDialog(this);
    registerDialog.setMessage("Registering Device...");
    registerDialog.show();*/

        final String token = MySharedPref.getInstance(this).getDeviceToken();
        final String registerEmail = email.getText().toString();

        if (token == null) {
            //registerDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //registerDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            //Toast.makeText(RegisterActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //registerDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", registerEmail);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
