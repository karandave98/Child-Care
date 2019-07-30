package com.example.myapplication0;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerHome extends AppCompatActivity {
    String pname,userName,pPrice,pickOrPlace;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapterFill;
    ListView listOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        Toolbar toolbar1 = findViewById(R.id.toolbar_babysitter);
        setSupportActionBar(toolbar1);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference dbbought =  FirebaseDatabase.getInstance().getReference("Product_Bought").child(userId);

        arrayList = new ArrayList<String>();
        listOrder = findViewById(R.id.list_orders);

        dbbought.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
//                    int maxresult = ds1
                    pname = dataSnapshot.getValue(BoughtInfo.class).getPname();
                    pPrice = dataSnapshot.getValue(BoughtInfo.class).getPrice();
                    userName = dataSnapshot.getValue(BoughtInfo.class).getUserName();
                    pickOrPlace = dataSnapshot.getValue(BoughtInfo.class).getPickOrPlace();

                    BoughtInfo boughtInfo = new BoughtInfo(pname,pPrice,userName,pickOrPlace);
                    arrayList.add("Name: "+userName+"\n\n"+pname + "\n"+pPrice+"\n\n"+pickOrPlace);
                }
                adapterFill = new ArrayAdapter<String>(SellerHome.this,android.R.layout.simple_list_item_1,arrayList);
                listOrder.setAdapter(adapterFill);


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
            Intent intyo = new Intent(SellerHome.this,MainActivity.class);
            startActivity(intyo);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
