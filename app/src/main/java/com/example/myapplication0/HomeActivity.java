package com.example.myapplication0;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference,dbDateCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressBar = findViewById(R.id.signout_progress);
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getActionBar().setTitle("Child Care");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        View view = navigationView.getHeaderView(0);
        final TextView nav_user_name = view.findViewById(R.id.nav_user_name);
        final TextView nav_user_mail = view.findViewById(R.id.nav_user_mail);

        TextView tv_user_profile = view.findViewById(R.id.update_profile);
        tv_user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user_profile = new Intent(HomeActivity.this,UserProfile.class);
                startActivity(user_profile);
            }
        });
        String userMail = mAuth.getCurrentUser().getEmail().toString();
        nav_user_mail.setText(userMail);
        databaseReference = FirebaseDatabase.getInstance().getReference("User_Info");

        SharedPreferences sharedName = getApplicationContext().getSharedPreferences("Client Name",0);
        final SharedPreferences.Editor editName = sharedName.edit();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userName ;
                if (mAuth.getCurrentUser()== null){}
                else {
                    String userId =mAuth.getCurrentUser().getUid();
                    if (dataSnapshot.hasChild(userId)){
                        String fname = dataSnapshot.child(userId).getValue(UserInfo.class).getfName();
                        String laname = dataSnapshot.child(userId).getValue(UserInfo.class).getlName();
                        String contact = dataSnapshot.child(userId).getValue(UserInfo.class).getMobile();
                        userName = fname + " " +laname;
                        editName.putString("NameOfClient",userName);
                        editName.putString("FirstNameOfUser",fname);
                        editName.putString("LastNameofUser",laname);
                        editName.putString("ContactClient", contact);
                        editName.commit();
                        nav_user_name.setText(userName.toUpperCase());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent get = getIntent();
        int che = get.getIntExtra("ActBook",0);
        if (che==25){
            navigationView.setCheckedItem(R.id.nav_babysitter);

            checkAddress();
        }
        else {
            navigationView.setCheckedItem(R.id.nav_vaccine_notification);

            checkData();
        }
//        final Fragment frags;
//        SharedPreferences dateSP = getApplicationContext().getSharedPreferences("Date", 00);
//        final SharedPreferences.Editor editorDate = dateSP.edit();
//        String a ="";
//        String inputDate = dateSP.getString("Date", a);
        navigationView.setCheckedItem(R.id.nav_vaccine_notification);

        checkData();//starting of vaccine schedule fragment

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.home, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment frags = null;
        int id = item.getItemId();

        if (id == R.id.nav_vaccine_notification) {
                checkData();
//            Called method checkData for this operation
//            dbDateCheck = FirebaseDatabase.getInstance().getReference("Date_Info");
//            dbDateCheck.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (mAuth.getCurrentUser() == null){
//
//                    }else {
//                        String userId = mAuth.getCurrentUser().getUid();
//                        String date = dataSnapshot.child(userId).getValue(DataSetter.class).getDate();
//                        String time = dataSnapshot.child(userId).getValue(DataSetter.class).getTime();
//                        Fragment frags;
//                        if (date.equals("") || time.equals("")){
//                            frags = new ChildVaccine();
//                        }
//                        else {
//                            frags = new VaccineSchedule();
//                        }
//                        if (frags != null){
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            FragmentTransaction ft = fragmentManager.beginTransaction();
//
//                            ft.replace(R.id.frame_content,frags);
//                            ft.commit();
//                        }
//                    }
//                }
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        } else if (id == R.id.nav_pregnant_woman) {
                frags = new SchedulePregnant();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.frame_content,frags);
            ft.addToBackStack("Back");
            ft.commit();
        } else if (id == R.id.nav_babysitter) {
              //frags = new BabysitterLocator();
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 51);
                }
            }
            checkAddress();

        } else if (id == R.id.nav_babyproducts) {

            frags = new BuyProduct();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();

            ft.replace(R.id.frame_content,frags);
            ft.addToBackStack("Buy_Product");
            ft.commit();

        } else if (id == R.id.nav_share) {
            try {
                Intent shared = new Intent(Intent.ACTION_SEND);
                shared.setType("text/plain");
                shared.putExtra(Intent.EXTRA_SUBJECT,"Child Care");
                String sharedMsg = "\nYou should try this application.\nI am using it.It is nice.\n\n";
                sharedMsg = sharedMsg + "LINK" + "\n\n";
                shared.putExtra(Intent.EXTRA_TEXT, sharedMsg);
                startActivity(Intent.createChooser(shared,"Share With"));
            }catch (Exception e){

            }

        } else if (id == R.id.nav_log_out) {
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to Sign Out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            FirebaseAuth.getInstance().signOut();
                            Intent i2 = new Intent(HomeActivity.this,MainActivity.class);
                              startActivity(i2);
                              finish();
                         }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void checkAddress(){
        dbDateCheck = FirebaseDatabase.getInstance().getReference("Address_info");
        dbDateCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mAuth.getCurrentUser() == null){

                }else {
                    Fragment frags ;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    String userId = mAuth.getCurrentUser().getUid();
                    if (dataSnapshot.hasChild(userId)) {
                        frags = new BookBabysitter();
                        ft.replace(R.id.frame_content, frags);
                        ft.addToBackStack("BabysitterBook");
                        if(!getSupportFragmentManager().isStateSaved()) {
                            ft.commit();
                        }

//                        }
                    }
                    else {
                        frags = new FillAddress();
                        ft.addToBackStack("AddressFill");
                        ft.replace(R.id.frame_content, frags);
                        if(!getSupportFragmentManager().isStateSaved()) {
                            ft.commit();
                        }


                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Fragment frags = new FillAddress();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.frame_content,frags);
                ft.commit();
            }
        });
    }
    public void checkData(){
        dbDateCheck = FirebaseDatabase.getInstance().getReference("Date_Info");
        dbDateCheck.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (mAuth.getCurrentUser() == null){

                }else {
                    String userId = mAuth.getCurrentUser().getUid();
                    Fragment frags;
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    if (dataSnapshot.hasChild(userId)) {
                        //String date = dataSnapshot.child(userId).getValue(DataSetter.class).getDate();
                        //String time = dataSnapshot.child(userId).getValue(DataSetter.class).getTime();
//                        if (date.equals("") || time.equals("")) {
//                            frags = new ChildVaccine();
//                        } else {
                            frags = new VaccineSchedule();
//                        }
                        //if (frags != null) {
                            ft.replace(R.id.frame_content, frags);
                            ft.addToBackStack("VaccineGo");
//                        }
                    }
                    else {
                        frags = new ChildVaccine();
                        ft.addToBackStack("Back3");
                        ft.replace(R.id.frame_content, frags);
                    }
                    if(!getSupportFragmentManager().isStateSaved()) {
                        ft.commit();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
