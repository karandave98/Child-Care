package com.example.myapplication0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText contact,firstName,lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

         contact = findViewById(R.id.update_contact_number);
         firstName = findViewById(R.id.update_first_name);
         lastName = findViewById(R.id.update_last_name);
        TextView email = findViewById(R.id.show_email);
        Button update_details = findViewById(R.id.update_details_button);

        Toolbar toolbar = findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedName = getApplicationContext().getSharedPreferences("Client Name",0);
        final String email_update = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        final String first_Name = sharedName.getString("FirstNameOfUser","");
        final String last_Name = sharedName.getString("LastNameofUser","");
        final String contact_number = sharedName.getString("ContactClient","");

        databaseReference = FirebaseDatabase.getInstance().getReference("User_Info");
//        Toast.makeText(this,first_Name,Toast.LENGTH_LONG).show();
        contact.setText(contact_number);
        firstName.setText(first_Name);
        lastName.setText(last_Name);
        email.setText(email_update);
        final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = databaseReference.child(UID);

                if (firstName.getText().toString().isEmpty()){
                    firstName.setError("Not Valid");
                }
                else if(lastName.getText().toString().isEmpty()){
                    lastName.setError("Not valid");
                }
                else if (contact.getText().toString().isEmpty()){
                    contact.setError("Not Valid");
                }
                else {
                    UserInfo userInfo = new UserInfo(UID,firstName.getText().toString(),last_Name,email_update,contact_number);

                    Toast.makeText(UserProfile.this,"Information Updated Successfully.",Toast.LENGTH_LONG).show();
                    databaseReference.setValue(userInfo);
                    Intent home = new Intent(UserProfile.this,HomeActivity.class);
                    startActivity(home);
                    finish();
                }


            }
        });



    }
}
