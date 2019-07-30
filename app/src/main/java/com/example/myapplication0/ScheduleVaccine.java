package com.example.myapplication0;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class ScheduleVaccine extends Fragment{
    FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    ArrayList<VaccineInfo> vaccineList;
    AdapterVaccine adapterVaccine;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
//        setSupportActionBar(toolbar);
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


//        FirebaseRecyclerOptions firebaseRecyclerOptions = new FirebaseRecyclerOptions.Builder<VaccineInfo>().setQuery(queryVaccine, VaccineInfo.class).build();
//
//        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<VaccineInfo, VaccineViewHold> (firebaseRecyclerOptions) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull VaccineViewHold holder, int position, @NonNull VaccineInfo model) {
//                        holder.setVaccineName(model.getVaccinName());
//                        holder.setVaccineDate(model.getVaccineDate());
//                    }
//
//                    @NonNull
//                    @Override
//                    public VaccineViewHold onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vaccine_schedule_view,viewGroup, false);
//                        return new VaccineViewHold(view);
//                    }
//
//                    @Override
//                    public void onError(@NonNull DatabaseError error) {
//                        super.onError(error);
//                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
//                    }
//                };
//        viewVaccineRecycler.setAdapter(firebaseRecyclerAdapter);
//         firebaseRecyclerAdapter.startListening();
////         String time ;


//        Intent intentData = getIntent();
//        time = intentData.getStringExtra("Time");
//        TextView tvTime = findViewById(R.id.tv_time);
//        tvTime.setText(time);
//        Calendar calendar = Calendar.getInstance();
//        String houtMinute [] = time.split(":");

       // Toast.makeText(getApplicationContext(),houtMinute[1].toString(),Toast.LENGTH_LONG).show();
//        String ampm [] = houtMinute[1].split(" ");
//        String hour = houtMinute[0];
//        String minute = ampm[0];
//        int hourSet = Integer.parseInt(hour);
//        int minuteSet = Integer.parseInt(minute);
//        if ((ampm[1]).equals("PM")){
//           hourSet = hourSet + 12;
//        }
//        if (android.os.Build.VERSION.SDK_INT >= 23) {
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                    hourSet, minuteSet, 0);
//        } else {
//            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
//                    hourSet, minuteSet, 0);
//        }
//
//        setAlarm(calendar.getTimeInMillis());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedule_vaccine, container);

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
    //    private void setAlarm(long time){
//       // AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent i  = new Intent(this, MyAlarm.class);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,i,0);
//
//        //alarmManager.setRepeating(AlarmManager.RTC,time,AlarmManager.INTERVAL_DAY,pendingIntent);
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
}
