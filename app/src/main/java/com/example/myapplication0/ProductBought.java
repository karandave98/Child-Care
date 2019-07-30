package com.example.myapplication0;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductBought extends AppCompatActivity {
    ImageView imageP;
    TextView tvPName,tvPPrice,tvPDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_bought);

        imageP = findViewById(R.id.product_image);
        tvPName = findViewById(R.id.product_name_bought);
        tvPDesc = findViewById(R.id.product_desrciption);
        tvPPrice = findViewById(R.id.product_price_bought);
        Intent intentGet =getIntent();
        String imageUrl = intentGet.getStringExtra("productUrl");
        final String pname = intentGet.getStringExtra("productName");
        final String pPrice = intentGet.getStringExtra("productPrice");
        String pDesc = intentGet.getStringExtra("productDesc");
        final String sellerId = intentGet.getStringExtra("productId");

        Picasso.get().load(imageUrl).into(imageP);
        tvPName.setText(pname);
        tvPPrice.setText(pPrice);
        tvPDesc.setText(pDesc);

        Button buttonPick = findViewById(R.id.button_pick);
        Button buttonPlace = findViewById(R.id.button_place);
        SharedPreferences sharedName = getApplicationContext().getSharedPreferences("Client Name",0);
        final String userName= sharedName.getString("NameOfClient","");
        final DatabaseReference dbrefBought = FirebaseDatabase.getInstance().getReference("Product_Bought");

        buttonPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoughtInfo boughtInfo = new BoughtInfo(pname,pPrice,userName,"PICK");
                dbrefBought.child(sellerId).setValue(boughtInfo);
                Intent intent = new Intent(ProductBought.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        buttonPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoughtInfo boughtInfo = new BoughtInfo(pname,pPrice,userName,"PLACE");
                dbrefBought.child(sellerId).setValue(boughtInfo);
                Intent intent = new Intent(ProductBought.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
