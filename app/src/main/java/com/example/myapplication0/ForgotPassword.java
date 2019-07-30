package com.example.myapplication0;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {
EditText input;
ProgressBar progressBar;
Button buttonSubmit;
TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        input = findViewById(R.id.forgot_email);
        buttonSubmit = findViewById(R.id.email_forgot);
        progressBar = findViewById(R.id.login_progress);
        tv = findViewById(R.id.message_forgot);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputAddress = input.getText().toString().trim();
                if (!isValidMail(inputAddress)) {

                    input.requestFocus();
                }
                else {
                    input.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    FirebaseAuth.getInstance().sendPasswordResetEmail(inputAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    if (task.isSuccessful()) {
                                       progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Email Sent.", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ForgotPassword.this, LoginActivity.class);

                                        startActivity(intent);
                                    } else {
                                       progressBar.setVisibility(View.GONE);
                                       tv.setText(task.getException().getMessage());
//                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG)
//                                                .show();

                                    }
                                }
                            });
                }
            }
        });
    }


    private boolean isValidMail(String email) {
        boolean check;
        Pattern p;
        Matcher m;

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        p = Pattern.compile(EMAIL_STRING);

        m = p.matcher(email);
        check = m.matches();

        if(!check) {
            input.setError("Not Valid Email");
        }
        return check;
    }
}