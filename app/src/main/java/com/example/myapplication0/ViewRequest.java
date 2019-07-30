package com.example.myapplication0;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;

public class ViewRequest extends FragmentActivity implements OnMapReadyCallback, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    double latitude , longitude;
    TextView tvDetails, tvContact;
    LatLng givenLatLng;
    Button buttonvie;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LatLng latLng;
    String keyId;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map3);
        mapFragment.getMapAsync(this);

        tvDetails = findViewById(R.id.get_address_show_baby);
        tvContact = findViewById(R.id.contact_tv);

        Intent getDAta =getIntent();
        keyId = getDAta.getStringExtra("UserKey");

        latitude = getDAta.getDoubleExtra("ReqLatitude",0.0);
        longitude = getDAta.getDoubleExtra("ReqLongitude",0.0);
        String detail = getDAta.getStringExtra("ReqDetails");
        final String contact = getDAta.getStringExtra("ReqContact");
        //Toast.makeText(this,latitude+" "+latitude,Toast.LENGTH_LONG).show();

        tvContact.setText("Contact: "+contact);
        tvDetails.setText(detail);
        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callPerson = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+contact));
                startActivity(callPerson);
            }
        });
        buttonvie = findViewById(R.id.button_view_direction);

        //givenLatLng = new LatLng(latitude,longitude);

    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        final Location lastLocation;
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        buildGoogleApiClient();
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else {
                    buildGoogleApiClient();
                    mMap.setMyLocationEnabled(true);
                }

      //  Toast.makeText(getApplicationContext(),latitude + ", "+longitude,Toast.LENGTH_LONG).show();
        LatLng lng = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(lng).title("Parent Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lng, 14F));

    }
    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        //Place current location marker
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Your Location");

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16F));
        buttonvie = findViewById(R.id.button_view_direction);
        final DatabaseReference dbaccept = FirebaseDatabase.getInstance().getReference("Request_Accepted");

        SharedPreferences shareName =  getApplication().getSharedPreferences("Babysitter Name",0);
        final String fullNa = shareName.getString("FullName","");
        final String mail = shareName.getString("MailId","");
        final String contact = shareName.getString("Contact","");

        buttonvie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewRequest.this,latitude+" "+longitude,Toast.LENGTH_LONG).show();
                Log.d("Key",keyId);
                BabysitterPass babysitterPass = new BabysitterPass(fullNa,contact,mail,keyId);
                dbaccept.child(keyId).setValue(babysitterPass);
                String mapUri = "google.navigation:q="+latitude+","+longitude;
                Uri urimap = Uri.parse(mapUri);
                Intent chec = new Intent(Intent.ACTION_VIEW,urimap);
                chec.setPackage("com.google.android.apps.maps");
                startActivity(chec);
            }
        });

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
