package com.example.myapplication0;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BabysitterBooked extends Fragment {

    TextView tvBaName,tvbamail,tvBacontact;
    Button buttondone;
    public BabysitterBooked() {
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
        return inflater.inflate(R.layout.fragment_babysitter_booked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvBaName = view.findViewById(R.id.babysitter_name);
        tvbamail = view.findViewById(R.id.babysitter_mail);
        tvBacontact = view.findViewById(R.id.babysitter_contact);

        buttondone = view.findViewById(R.id.button_done);

        DatabaseReference dbAccep = FirebaseDatabase.getInstance().getReference("Request_Accepted");
        dbAccep.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String bName = dataSnapshot.child(userId).getValue(BabysitterPass.class).getFullNAme();
                String bMail = dataSnapshot.child(userId).getValue(BabysitterPass.class).getmailId();
                String bContact = dataSnapshot.child(userId).getValue(BabysitterPass.class).getContact();

                tvBaName.setText(bName);
                tvbamail.setText(bMail);
                tvBacontact.setText(bContact);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final RatingBar ratingBar = view.findViewById(R.id.ratingbar_babysitter);
        buttondone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float rate = ratingBar.getRating();

                Toast.makeText(getActivity(),rate+" ",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

}
