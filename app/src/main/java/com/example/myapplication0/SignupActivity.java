package com.example.myapplication0;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private EditText editTextFirstName, editTextLastName,editTextEmail, editTextMobile,
            editTextPassword, etConfirmPassword;
    public Button buttonSignup;
    private Pattern pattern;
    private Matcher matcher;
    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private RadioButton radioParents, radioBabysitter, radioSeller;

    private DatabaseReference mDatabaseUserDetail;
    private HashMap<String,Object> mapUser = new HashMap<>();
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //FirebaseApp.initializeApp(this);
        //View backgroundImage = (View) findViewById(R.id.background);
        //backgroundImage.setAlpha((float) 0.5);
        editTextFirstName = (EditText) findViewById(R.id.first_name);
        editTextLastName = (EditText) findViewById(R.id.last_name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextMobile = (EditText) findViewById(R.id.contact_number);
        etConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        buttonSignup = (Button) findViewById(R.id.email_sign_up_button);
        radioParents = findViewById(R.id.parent_radio);
        radioBabysitter = findViewById(R.id.babysitter_radio);
        radioSeller = findViewById(R.id.seller_radio);

        radioGroup = findViewById(R.id.radio_select);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();

        //pattern = Pattern.compile(EMAIL_PATTERN);


        final String password = editTextPassword.getText().toString().trim();
        final String confirmPassword = etConfirmPassword.getText().toString().trim();
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextFirstName.getText().toString().trim().isEmpty()){
                    editTextFirstName.setError("Please enter your first name");
                    editTextFirstName.requestFocus();
                }
                else if (editTextLastName.getText().toString().trim().isEmpty()){
                    editTextLastName.setError("Please enter your last name");
                    editTextLastName.requestFocus();
                }
                else if (!isValidPhone(editTextMobile.getText().toString()) ){
                    editTextMobile.setError("Not a valid Number");
                    editTextMobile.requestFocus();
                }
                else if (!isValidMail(editTextEmail.getText().toString()))
                {
                    editTextEmail.setError("Not a valid Email Id");
                    editTextEmail.requestFocus();
                }
                else if( password.length()<7 && !isvalidPassword(editTextPassword.getText().toString())) {
                    editTextPassword.setError("Not valid password");
                    Toast.makeText(getApplicationContext(), "Password must contain one special character, one digit and length must be atleast 7.", Toast.LENGTH_LONG).show();
                    editTextPassword.requestFocus();
                }
                else if(!isConfirmed()) {
                    etConfirmPassword.setError("Password does not match");
                    etConfirmPassword.requestFocus();
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Registered Successfully\n Please Log In to continue.", Toast.LENGTH_LONG).show();
//                                Intent itent = new Intent(SignupActivity.this, LoginActivity.class);
//                                startActivity(itent);
//                                finish();
                                if (radioParents.isChecked())
                                {
                                    addUserInfo();
//                                    Intent itent = new Intent(SignupActivity.this, LoginActivity.class);
//                                    getIntent().putExtra("Flag",1);
//                                    startActivity(itent);
//                                    finish();
                                }
                                else if (radioBabysitter.isChecked())
                                {
                                    addBabysitterInfo();
//                                    Intent itent = new Intent(SignupActivity.this, LoginActivity.class);
//                                    getIntent().putExtra("Flag",2);
//                                    startActivity(itent);
//                                    finish();
                                }
                                else if (radioSeller.isChecked())
                                {
                                    addSellerInfo();
//                                    Intent itent = new Intent(SignupActivity.this, LoginActivity.class);
//                                    getIntent().putExtra("Flag",3);
//                                    startActivity(itent);
//                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Select One of the radio button.",Toast.LENGTH_LONG).show();
                                }
                                Intent itent = new Intent(SignupActivity.this, LoginActivity.class);

                                startActivity(itent);
                                finish();
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });


                }
            }
        });
    }
    private void addUserInfo(){
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();
        mDatabaseUserDetail = FirebaseDatabase.getInstance().getReference("User_Info");

//        String userId = mDatabaseUserDetail.push().getKey();
        String uUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        mapUser.put("First Name", fName);
//        mapUser.put("Last Name", lName);
//        mapUser.put("Email", email);
//        mapUser.put("Mobile", mobile);
//        mapUser.put("User Id", userId);
        UserInfo userInfo = new UserInfo(uUid,fName,lName,email,mobile);

        mDatabaseUserDetail.child(uUid).setValue(userInfo);


    }

    private void addBabysitterInfo(){
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();

        mDatabaseUserDetail = FirebaseDatabase.getInstance().getReference("Babysitter_Info");
        String userId = mDatabaseUserDetail.push().getKey();
        String uUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserInfo userInfo = new UserInfo(uUid,fName,lName,email,mobile);

        mDatabaseUserDetail.child(uUid).setValue(userInfo);


    }

    private void addSellerInfo(){
        String fName = editTextFirstName.getText().toString().trim();
        String lName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();

        mDatabaseUserDetail = FirebaseDatabase.getInstance().getReference("Seller_Info");
        String userId = mDatabaseUserDetail.push().getKey();
        String uUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        UserInfo userInfo = new UserInfo(uUid,fName,lName,email,mobile);

        mDatabaseUserDetail.child(uUid).setValue(userInfo);


    }

    public boolean isValidPhone(CharSequence phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        } else {
            boolean check=false;
            if(!Pattern.matches("[a-zA-Z]+", phone)) {
                if(phone.length() != 10) {
                    check = false;
                } else {
                    check = true;
                }
            } else {
                check=false;
            }
            return check;
        }
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
    public boolean isConfirmed(){
        if (etConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())){
            return true;
        }
        else {
            return false;
        }
    }
}
