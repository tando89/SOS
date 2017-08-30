package com.example.tan089.sos;

import android.app.ProgressDialog;
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

import static android.R.attr.id;

public class GetLiveMessage extends AppCompatActivity {
    private AutoCompleteTextView email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog loginDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_messages);

        email=(AutoCompleteTextView)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        mAuth=FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(GetLiveMessage.this, SosForumChat.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
            }
        };
        //soft keyboard sign in still not working properly
        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void login(View view) {
        //loginDialog.show();
        /*mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    loginDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
                loginDialog.dismiss();
            }
        });*/
        attemptLogin();
    }
    private void attemptLogin() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        //stop the task if the fields are left blank
        if (userEmail.equals("") || userPassword.equals("")) return;
        loginDialog =new ProgressDialog(this);
        loginDialog.setMessage("Login in progress...");
        loginDialog.show();
        //Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Chat: ", "signInWithEmail() onComplete: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("Chat", "Problem signing: " + task.getException());
                    loginDialog.dismiss();
                    showErrorDialog("Error with sign in!");
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
    public void register(View view) {
        startActivity(new Intent(GetLiveMessage.this,RegisterActivity.class));
        finish();
    }
    public void forgotPw (View view) {
        startActivity(new Intent(GetLiveMessage.this, ForgotPassword.class));
        finish();
    }
}
