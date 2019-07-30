package com.example.myapplication0;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    ProgressBar progressBar;
    private EditText editTextEmail,editTextPassword;
    public Button buttonSignin;
    private FirebaseAuth mAuth;
    SharedPreferences sharedLoginType;
    int loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonSignin = findViewById(R.id.email_sign_in_button);
        progressBar = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();
        sharedLoginType = getApplicationContext().getSharedPreferences("Login_Type", 00);
        final SharedPreferences.Editor editorLogin = sharedLoginType.edit();

       // loginType = sharedLoginType.getInt("Login_Data", 0);

//        Intent intent = getIntent();
//        final String flag = intent.getStringExtra("Flag");


        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidMail(editTextEmail.getText().toString()))
                {
                    editTextEmail.setError("Not a valid Email Id");
                    editTextEmail.requestFocus();
                }
                else if( editTextPassword.length()<7 && !isvalidPassword(editTextPassword.getText().toString())) {
                    editTextPassword.setError("Not valid password");
                    editTextPassword.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                DatabaseReference dbref, babyref, sellerref;
                                dbref = FirebaseDatabase.getInstance().getReference("User_Info");
                                babyref = FirebaseDatabase.getInstance().getReference("Babysitter_Info");
                                sellerref = FirebaseDatabase.getInstance().getReference("Seller_Info");

                                final String mailCheck = editTextEmail.getText().toString().trim();

                                int count = 0;
                                while(count<3){
                                    mAuth = FirebaseAuth.getInstance();
                                    if(count == 0){
                                        dbref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (mAuth.getCurrentUser()== null){}
                                                else {
                                                    String userId =mAuth.getCurrentUser().getUid();
                                                   if (dataSnapshot.hasChild(userId)){
                                                       String email = dataSnapshot.child(userId).getValue(UserInfo.class).getEmail();
                                                       if(mailCheck.equals(email)){
                                                           editorLogin.putInt("LoginType",11);
                                                           editorLogin.commit();
                                                           Intent toact = new Intent(LoginActivity.this,HomeActivity.class);
                                                           startActivity(toact);
                                                           finish();
                                                       }
                                                       else {
                                                       }
                                                   }

                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else if(count == 1){
                                        babyref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (mAuth.getCurrentUser()== null){}
                                                else {
                                                    String userId = mAuth.getCurrentUser().getUid();

                                                    if (dataSnapshot.hasChild(userId)) {
                                                        String email = dataSnapshot.child(userId).getValue(UserInfo.class).getEmail();
                                                        if (mailCheck.equals(email)) {
                                                            editorLogin.putInt("LoginType",22);
                                                            editorLogin.commit();
                                                            Intent toact = new Intent(LoginActivity.this, BabysitterMain.class);
                                                            startActivity(toact);
                                                            finish();
                                                        } else {

                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    else {
                                        sellerref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               if (mAuth.getCurrentUser()== null){

                                               }
                                               else {
                                                   String userId = mAuth.getCurrentUser().getUid();
                                                   if(dataSnapshot.hasChild(userId)) {
                                                       editorLogin.putInt("LoginType",33);
                                                       editorLogin.commit();
                                                       String email = dataSnapshot.child(userId).getValue(UserInfo.class).getEmail();
                                                       if (mailCheck.equals(email)) {
                                                           Intent toact = new Intent(LoginActivity.this, SellerHome.class);
                                                           startActivity(toact);
                                                           finish();
                                                       } else {

                                                       }
                                                   }
                                               }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                    count++;
                                }progressBar.setVisibility(View.GONE);
//                                switch (flag){
//                                    case "1":
//                                        Intent toHome =  new Intent(LoginActivity.this, HomeActivity.class);
//                                        startActivity(toHome);
//                                        break;
//                                    case "2":
//                                        Intent toBabysit =  new Intent(LoginActivity.this, BabysitterMain.class);
//                                        startActivity(toBabysit);
//                                        break;
//                                    case "3":
//                                        Intent toSeller =  new Intent(LoginActivity.this, SellerHome.class);
//                                        startActivity(toSeller);
//                                        break;
//
//                                }

                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void ResetPassword (View view){
        Intent intent = new Intent(LoginActivity.this,ForgotPassword.class);
        startActivity(intent);
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
            editTextEmail.setError("Not Valid Email");
        }
        return check;
    }
    public boolean isvalidPassword(String pwd ) {
        boolean check;
        Pattern p;
        Matcher m;
        String PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        p= Pattern.compile(PATTERN);
        m = p.matcher(pwd);
        check = m.matches();
        if(!check) {
            editTextPassword.setError("Not Valid Password");
        }
        return check;
    }
}

