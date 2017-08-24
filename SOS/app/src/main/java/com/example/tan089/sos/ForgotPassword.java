package com.example.tan089.sos;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    EditText Email;
    Button resetBnt;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Email = (EditText) findViewById(R.id.forgot_pw_email);
        resetBnt = (Button) findViewById(R.id.forgotPwBnt);
        mAuth = FirebaseAuth.getInstance();

    }

    public void resetPw (View v) {
        attemptResetPw();
        Email.setText("");
    }
    private void attemptResetPw() {
        String UserEmail = Email.getText().toString();
        mAuth.sendPasswordResetEmail(UserEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("SOS", "forgot password: " + task.isSuccessful());
                if(!task.isSuccessful()) {
                    Log.d("SOS", "Problem signing: " + task.getException());
                } else {
                    Toast.makeText(ForgotPassword.this, "Reset password link has sent to your email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
