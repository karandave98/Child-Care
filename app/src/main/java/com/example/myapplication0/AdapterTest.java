package com.example.myapplication0;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterTest extends RecyclerView.Adapter<AdapterTest.MyVh> {
Context context;
ArrayList<VaccineInfo> arrayList;
DatabaseReference databaseReference;


    public AdapterTest(Context context, ArrayList<VaccineInfo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyVh(LayoutInflater.from(context).inflate(R.layout.view_test,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyVh myVh, final int i) {
        myVh.tvVname.setText(arrayList.get(i).getTest_name());
        final String dateUp = arrayList.get(i).getDays_after();
        myVh.tvVDate.setText(dateUp);


//        int monthAftet = Integer.parseInt(dateUp);
//        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("Data_date",0);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();
//
//        DataSetter dataSetter;
//        databaseReference = FirebaseDatabase.getInstance().getReference("Date_Info");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            FirebaseAuth auth = FirebaseAuth.getInstance();
//            String uid = auth.getCurrentUser().getUid();
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               if (dataSnapshot.hasChild(uid)){
//                   String time = dataSnapshot.child(uid).getValue(DataSetter.class).getDate();
//                   editor.putString("dateDB",time);
//                   editor.commit();
//               }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
//
//        String a = "";
//        String dateds = sharedPreferences.getString("dateDB",a);
//        String inDate[] = dateds.split("/");
//        int month ; int year; int day;
//        day= Integer.parseInt(inDate[0]);
//        month= Integer.parseInt(inDate[1]);
//        year= Integer.parseInt(inDate[2]);
////        Calendar cal = Calendar.getInstance();
////        cal.set(year,month,day);
////        cal.add(month,(month+monthAftet));
////                //month = month+monthAftet;
////        year = cal.get(Calendar.YEAR);
////        month = cal.get(Calendar.MONTH);
////        day = cal.get(Calendar.DATE);
//        month += monthAftet;
//        while (month > 12){
//            month = month -12;
//            year = year +1;
//        }
//        //to check fabruary
//        if (month == 2 && day >28){
//            if (year % 4 == 0 ){
//                if (day > 29){
//                    month = 3;
//                    day =  (day-29);
//                }
//            }
//            else {
//                month = 3;
//                day = day - 28;
//            }
//        }
//         String finalDate = day + "/" + month + "/" + year;

//        myVh.tvVDate.setText(finalDate);


    }

//    private String getDated(int ida) {
//        final int par = ida;
//        databaseReference = FirebaseDatabase.getInstance().getReference("Date_Info");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            FirebaseAuth auth = FirebaseAuth.getInstance();
//            String uid = auth.getCurrentUser().getUid();
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String time = dataSnapshot.child(uid).getValue(DataSetter.class).getDate();
//
//                final String inDate[] = time.split("/");
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
////        int month ; int year;
////        month= Integer.parseInt(inDate[1]);
////        year= Integer.parseInt(inDate[2]);
//////                int idateup = Integer.parseInt(dateUp);
////
////        month = month+par;
////        while (month > 12){
////            month = month -12;
////            year = year +1;
////        }
////         String finalDate = inDate[0] + "/" + month + "/" + year;
////        return finaldate;
//    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyVh extends RecyclerView.ViewHolder{
        TextView tvVname , tvVDate;

        public MyVh(View item){
        super(item);
        tvVname = item.findViewById(R.id.tv_test_name);
        tvVDate = item.findViewById(R.id.month_after);
        }
    }

}
