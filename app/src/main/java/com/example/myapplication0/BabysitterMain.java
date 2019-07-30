package com.example.myapplication0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BabysitterMain extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Fragment fragment = new BsHome();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.babysitter_container,fragment);
                    fragmentTransaction.addToBackStack("BabyHome");
                    fragmentTransaction.commit();
                    return true;


                case R.id.navigation_notifications:
                    Fragment fraProf = new BsProfile();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.babysitter_container,fraProf);
                    ft.addToBackStack("BabyHome");
                    ft.commit();
                   // mTextMessage.setText(R.string.title_notifications)
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_main);

        Toolbar toolbar1 = findViewById(R.id.toolbar_babysitter);
        setSupportActionBar(toolbar1);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences sharedCity1 = this.getSharedPreferences("CityAdded",00);
        final String givenCity = sharedCity1.getString("CityBaby","none");
        if (givenCity.equals("none")){
            Fragment fragment = new BsProfile();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.babysitter_container, fragment);
            fragmentTransaction.addToBackStack("BabyHome");
            fragmentTransaction.commit();

        }
        else {
            Fragment fragment1 = new BsHome();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.babysitter_container, fragment1);
            fragmentTransaction.addToBackStack("BabyHome");
            fragmentTransaction.commit();
        }


        SharedPreferences shareName =  getApplication().getSharedPreferences("Babysitter Name",0);
//        String chechCity = shareName.getString("CityBS","Ahmedabad");
//        if (chechCity.equals("")){
//            Fragment fragment = new BsProfile();
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.babysitter_container,fragment);
//            fragmentTransaction.addToBackStack("BabyProfile");
//            fragmentTransaction.commit();
//        }
//        else {
//
//        }
        final SharedPreferences.Editor editorNa = shareName.edit();

        DatabaseReference dbrefinfo = FirebaseDatabase.getInstance().getReference("Babysitter_Info");
        dbrefinfo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (user!=null){
                    String fname = dataSnapshot.child(user).getValue(UserInfo.class).getfName();
                    String lname = dataSnapshot.child(user).getValue(UserInfo.class).getlName();
                    String contact = dataSnapshot.child(user).getValue(UserInfo.class).getMobile();
                    String mail = dataSnapshot.child(user).getValue(UserInfo.class).getEmail();
                    editorNa.putString("FullName",fname + " " +lname);
                    editorNa.putString("Fname",fname);
                    editorNa.putString("Lname",lname);
                    editorNa.putString("Contact",contact);
                    editorNa.putString("MailId",mail);
                    editorNa.commit();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.babysitter_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {
            FirebaseAuth fire = FirebaseAuth.getInstance();
            fire.signOut();
            Intent intyo = new Intent(BabysitterMain.this,MainActivity.class);
            startActivity(intyo);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }
}
