package com.example.myapplication0;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BuyProduct extends Fragment {

    public BuyProduct() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    ArrayList<ProductInfo> arrayProduct;
    AdapterProduct adapterProduct;
    ArrayList<String> arrayKey;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_product, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycle_product);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayProduct = new ArrayList<ProductInfo>();
        arrayKey = new ArrayList<String>();
//        arrayKey.add("AB");
        DatabaseReference dbrefProduct = FirebaseDatabase.getInstance().getReference("Baby_Products");//.child("7ajLKgYom2fx0BFMevo0gT1cmJ22");
        dbrefProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                   // Toast.makeText(getActivity(),ds1.getKey(),Toast.LENGTH_SHORT).show();
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        arrayKey.add(ds1.getKey());
                        ProductInfo productInfo = ds2.getValue(ProductInfo.class);
                        arrayProduct.add(productInfo);
                    }

                }

                adapterProduct = new AdapterProduct(getContext(), arrayProduct, arrayKey);
                recyclerView.setAdapter(adapterProduct);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        class Myadapter extends ArrayAdapter<BoughtInfo>{
//            Context context;
//            public Myadapter(Context context, int resource) {
//                super(context, resource);
//            }
//
//            public Myadapter(Context context, BoughtInfo boughtInfo1) {
//                super(context,ArrayList<ProductInfo> arPr, ArrayList<String> arKey );
//                this.context = context1;
////                objects;
//
//            }
//
//            @NonNull
//            @Override
//            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//                return super.getView(position, convertView, parent);
////                LayoutInflater linearLayout1 = (LayoutInflater)context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
//                View roe = linearLayout1.inflate(R.layout.product_view,parent,false);
//
//
//            }
//        }


    }
}
