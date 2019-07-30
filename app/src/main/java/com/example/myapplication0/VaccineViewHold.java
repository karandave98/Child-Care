package com.example.myapplication0;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class VaccineViewHold extends RecyclerView.ViewHolder {
    View mView ;

    public VaccineViewHold(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }
    public void setVaccineName(String vaccineName){
        TextView tvVaccineName = mView.findViewById(R.id.tv_vaccine_name);
        tvVaccineName.setText(vaccineName);
    }
    public void setVaccineDate(String date){
        TextView tvVaccineDate = mView.findViewById(R.id.vaccine_date);
        tvVaccineDate.setText(date);
    }
}
