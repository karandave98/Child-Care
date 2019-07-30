package com.example.myapplication0;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VaccineSchedule extends Fragment {


    ArrayList<VaccineInfo> vaccineList;
    AdapterVaccine adapterVaccine;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule_vaccine,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseReference databaseReferenceVaccine = FirebaseDatabase.getInstance().getReference().child("Vaccine_Info");
//        Query  queryVaccine = databaseReferenceVaccine.orderByKey();

        vaccineList = new ArrayList<VaccineInfo>();

        final RecyclerView viewVaccineRecycler = view.findViewById(R.id.view_recycle);
        viewVaccineRecycler.setHasFixedSize(true);
        viewVaccineRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReferenceVaccine.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
//                    String vname =dataSnapshot1.getValue(VaccineInfo.class).getVaccinName();
//                    String vdate =dataSnapshot1.getValue(VaccineInfo.class).getVaccineDate();
//                    TextView tvn = findViewById(R.id.tv_vaccine_name);
//                    tvn.setText(vname);
//                    TextView tvd = findViewById(R.id.tv_vaccine_date);
//                    tvd.setText(vdate);
                    VaccineInfo vaccineInfo = dataSnapshot1.getValue(VaccineInfo.class);
                    vaccineList.add(vaccineInfo);
//                    Toast.makeText(getApplicationContext(),vname,Toast.LENGTH_SHORT).show();;
                }
                adapterVaccine = new AdapterVaccine(getContext(),vaccineList);
                viewVaccineRecycler.setAdapter(adapterVaccine);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),"Ooops ... Something is wrong", Toast.LENGTH_LONG).show();
            }
        });

    }
}
