package com.example.myapplication0;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookBabysitter extends Fragment {


    // TODO: Rename and change types of parameters
    TextView tvaddress,tvChangeAddress , tvlocale;
    Button buttnBook;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbrefAddress, dbrefReq;

    public BookBabysitter() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_babysitter,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvaddress = view.findViewById(R.id.tv_address_get);
        tvChangeAddress = view.findViewById(R.id.change_tv);
        tvlocale = view.findViewById(R.id.tv_locale_book);
        buttnBook = view.findViewById(R.id.button_book);

        firebaseAuth = FirebaseAuth.getInstance();

        dbrefAddress = FirebaseDatabase.getInstance().getReference("Address_info");
        SharedPreferences sharedLongi = getActivity().getSharedPreferences("Longi_Latti",0);
        final SharedPreferences.Editor editLon = sharedLongi.edit();

        SharedPreferences sharedLongi1 = getActivity().getSharedPreferences("Customer_details",0);
        final SharedPreferences.Editor editLon1 = sharedLongi1.edit();


        dbrefAddress.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (firebaseAuth.getCurrentUser()!= null){
                    String userID = firebaseAuth.getCurrentUser().getUid();
                    if (dataSnapshot.hasChild(userID)){
                        String adddress = dataSnapshot.child(userID).getValue(AddresInfo.class).getCompleteAddress();
                        String locale = dataSnapshot.child(userID).getValue(AddresInfo.class).getLocale();
                        String city = dataSnapshot.child(userID).getValue(AddresInfo.class).getCity();
                        GeoFire geoFire = new GeoFire(dbrefAddress.child(userID));

                        editLon1.putString("AddressInbook",adddress);
                        editLon1.putString("Localitys",locale);
                        editLon1.putString("CityBooks",city);
                        editLon1.commit();
                        geoFire.getLocation("Location", new LocationCallback() {
                                    @Override
                                    public void onLocationResult(String key, GeoLocation location) {
                                        double latitude = location.latitude;
                                        double longitude = location.longitude;
                                        editLon.putLong("Latitude",Double.doubleToRawLongBits(latitude));
                                        editLon.putLong("Longitude", Double.doubleToRawLongBits(longitude));
                                        editLon.commit();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });


                        tvaddress.setText(adddress);
                        String add = locale + ", "+ city;
                        tvlocale.setText(add);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        tvChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fillAddres = new FillAddress();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_content,fillAddres);
                transaction.addToBackStack("Change_address");
                transaction.commit();

            }
        });
        dbrefReq = FirebaseDatabase.getInstance().getReference("Requests_Babysitter");
        SharedPreferences sharedRequest = getActivity().getSharedPreferences("Longi_Latti",0);
        SharedPreferences sharedRequest1 = getActivity().getSharedPreferences("Customer_details",0);
        String a = "",b = "",c ="";

        final String addres = sharedRequest1.getString("AddressInbook",a);
        final String locale = sharedRequest1.getString("Localitys",b);
        final String city1 = sharedRequest1.getString("CityBooks",c);

        final long latLong = sharedRequest.getLong("Latitude",0);
        final long longLong = sharedRequest.getLong("Longitude",0);

        SharedPreferences sharedname = getActivity().getSharedPreferences("Client Name" , 0);
        final String name = sharedname.getString("NameOfClient","");
        final String contact = sharedname.getString("ContactClient","");

        buttnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestInfo requestInfo = new RequestInfo(addres,locale,city1,name,contact);
                if (firebaseAuth.getCurrentUser() != null){
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    final double latitude = Double.longBitsToDouble(latLong);
                    final double longitude = Double.longBitsToDouble(longLong);

                    Toast.makeText(getContext(),city1+","+longitude,Toast.LENGTH_LONG).show();

                    dbrefReq.child(userId).setValue(requestInfo);
                    dbrefReq = dbrefReq.child(userId);
                    GeoFire geoFire = new GeoFire(dbrefReq);
                    geoFire.setLocation("LocationReq", new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            Log.d("Ok","Generated Successfully");
                        }
                    });

                }
                Fragment frags = new BabysitterBooked();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_content,frags);
                transaction.addToBackStack("BookedBabysitter");
                transaction.commit();
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
