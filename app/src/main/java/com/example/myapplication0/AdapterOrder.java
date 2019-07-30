package com.example.myapplication0;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class AdapterOrder extends ArrayAdapter<BoughtInfo>  {

    BoughtInfo boughtInfo;
    public AdapterOrder(Context context, ArrayList<BoughtInfo> arrayBought) {
        super(context, 0,  arrayBought);
    }
//
//    @Override
//    public View getView(int position,  View convertView,  ViewGroup parent) {
//        return super.getView(position, convertView, parent);
////        BoughtInfo boughtInfo = getItem(position);
//        if (convertView == null){
////            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_view,parent,false);
//        }
//
//
//    }
}
