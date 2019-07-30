package com.example.myapplication0;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    private FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        login = findViewById(R.id.email_sign_in_button);
        signup = findViewById(R.id.email_sign_up_button);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent2 = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent2);
            }
        });

        FirebaseApp.initializeApp(this);
        SharedPreferences sharedLoginType = getApplicationContext().getSharedPreferences("Login_Type", 00);
        //final SharedPreferences.Editor editorSp = sharedLoginType.edit();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        int loginType, check = 0;
        loginType = sharedLoginType.getInt("LoginType", check);
        if (user != null) {
            if (loginType == 11) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            } else if (loginType == 22) {
                Intent intent = new Intent(MainActivity.this, BabysitterMain.class);
                startActivity(intent);
            } else if (loginType == 33) {
                Intent intent = new Intent(MainActivity.this, SellerHome.class);
                startActivity(intent);
            }
        }
        else {
//                Intent intent  = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}

