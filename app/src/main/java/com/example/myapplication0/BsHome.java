package com.example.myapplication0;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BsHome extends Fragment {

    Switch aSwitch;
    LinearLayout linearLayout;
    TextView tvOnline,tvOffline;
    String key;

    ArrayAdapter<String> adapterFill;
    ArrayList<Double> reqLatitude =  new ArrayList<Double>();
    ArrayList<Double> reqLongitude = new ArrayList<Double>();
    ArrayList<String> reqContact = new ArrayList<String>();
    ArrayList<String> reqKey = new ArrayList<String>();
    public BsHome() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bs_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aSwitch = view.findViewById(R.id.switch_go);
        tvOffline = view.findViewById(R.id.go_off_tv);
        tvOnline = view.findViewById(R.id.go_on_tv);

//        linearLayout = (LinearLayout) findView
        final ListView listViewReq ;
        listViewReq = (ListView) view.findViewById(R.id.list_availaible);
        linearLayout = view.findViewById(R.id.id_enabled);
        tvOffline.setVisibility(View.INVISIBLE);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tvOffline.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    tvOnline.setVisibility(View.INVISIBLE);
                }else {
                    tvOffline.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.INVISIBLE);
                    tvOnline.setVisibility(View.VISIBLE);

                }
            }
        });

        final ArrayList<String> arrayList = new ArrayList<>();

//        SharedPreferences shareName = getActivity().getSharedPreferences("Babysitter Name",0);

        SharedPreferences shareLongiLatti = getActivity().getSharedPreferences("Babysitter Name",0);
        final SharedPreferences sharedCity = getActivity().getSharedPreferences("CityAdded",00);
        final String givenCity = sharedCity.getString("CityBaby","");
//
//        if (givenCity.equals("")){
//            Toast.makeText(getContext(),"Please Enter your city",Toast.LENGTH_LONG).show();
//            Fragment fragment = new BsProfile();
//            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.babysitter_container, fragment);
//            fragmentTransaction.addToBackStack("BabyHome");
//            fragmentTransaction.commit();
//        }
        final SharedPreferences.Editor editorL = shareLongiLatti.edit();

        final DatabaseReference dbrefReq = FirebaseDatabase.getInstance().getReference("Requests_Babysitter");
        SharedPreferences sharedUserId = getActivity().getSharedPreferences("UserId",0);
        final SharedPreferences.Editor editorId = sharedUserId.edit();
        dbrefReq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    key = dataSnapshot1.getKey();

                    final String city = dataSnapshot1.child("city").getValue().toString();
                    String parentName = dataSnapshot1.child("nameOfParent").getValue().toString();
                    String completeAddress = dataSnapshot1.child("completeAddress").getValue().toString();
                    String locale = dataSnapshot1.child("locale").getValue().toString();
                    String contactClient = dataSnapshot1.child("contactParent").getValue().toString();
                    editorL.putString("ClientContact",contactClient);
                    GeoFire geoFire = new GeoFire(dataSnapshot1.getRef());

                    geoFire.getLocation("LocationReq", new LocationCallback() {
                        @Override
                        public void onLocationResult(String key, GeoLocation location) {
                            double latitude = location.latitude;
                            double longitude = location.longitude;
                            if (city.equals(givenCity)){
                                reqLatitude.add(latitude);
                                reqLongitude.add(longitude);
                            }

                            //Toast.makeText(getContext(),latitude+","+longitude,Toast.LENGTH_LONG).show();


//                            editorL.putLong("Latitude",Double.doubleToRawLongBits(latitude));
//                            editorL.putLong("Longitude", Double.doubleToRawLongBits(longitude));
//                            editorL.commit();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if (city.equals(givenCity)) {
                        String c1 = parentName+"\n"+completeAddress + "\n" + locale;
                        String con = contactClient;
                        arrayList.add(c1);
                        reqContact.add(con);

                        reqKey.add(key);
                    }
                }
                adapterFill = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
                listViewReq.setAdapter(adapterFill);
                listViewReq.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (arrayList.size() > 0 ){

                            Intent toView = new Intent(getContext(),ViewRequest.class);
                            toView.putExtra("UserKey",reqKey.get(position));
//                            Toast.makeText(getContext(),arrayList.get(position),Toast.LENGTH_LONG).show();
                            toView.putExtra("ReqDetails",arrayList.get(position));
                            toView.putExtra("ReqContact",reqContact.get(position));

                            toView.putExtra("ReqLatitude",reqLatitude.get(position));
                            toView.putExtra("ReqLongitude",reqLongitude.get(position));
//                             Toast.makeText(getContext()," "+reqLatitude.get(position)+" "+reqLongitude.get(position),Toast.LENGTH_LONG).show();

                            startActivity(toView);

                        }
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
