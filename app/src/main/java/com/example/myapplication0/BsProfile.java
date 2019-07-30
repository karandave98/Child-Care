package com.example.myapplication0;

import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BsProfile extends Fragment {

    public BsProfile() {
        // Required empty public constructor
    }
    EditText etFname,etLname,etmobile,etMail,etCity;
    Button buttonSave;
    String city;
    TextView tvMail;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bs_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etFname = view.findViewById(R.id.bs_first_name);
        etLname = view.findViewById(R.id.bs_last_name);
        tvMail = view.findViewById(R.id.BS_mail);
        etmobile = view.findViewById(R.id.bs_contact_number);
        etCity = view.findViewById(R.id.select_city);
        buttonSave = view.findViewById(R.id.button_save);

        SharedPreferences sharedBAby = getActivity().getSharedPreferences("Babysitter Name",0);
        String fname = sharedBAby.getString("Fname","");
        String lname = sharedBAby.getString("Lname","");
        String mailid = sharedBAby.getString("MailId","");
        String contact = sharedBAby.getString("Contact","");
       // String cityBS = sharedBAby.getString("CityBs","a");
//        Toast.makeText(getContext(),cityBS,Toast.LENGTH_LONG).show();
        final SharedPreferences shareCity = getActivity().getSharedPreferences("CityAdded",00);
        final SharedPreferences.Editor editorCity = shareCity.edit();
        etFname.setText(fname);
        etLname.setText(lname);
        etmobile.setText(contact);
        tvMail.setText(mailid);

        String cheCity = shareCity.getString("CityBaby","");
       // Toast.makeText(getContext(),cheCity,Toast.LENGTH_LONG).show();
        etCity.setText(cheCity);
//        if (!cityBS.equals("")){
//        }else {
//            etCity.setText(cityBS);
//        }
//        if (etCity.getText().toString().equals(null)) {
//            Toast.makeText(getContext(),"Please Provide City Name",Toast.LENGTH_LONG).show();
//        }
//        else {
//            final String city2 = etCity.getText().toString();
//            city = city2;
//        }
//        final SharedPreferences.Editor sharedET = sharedBAby.edit();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityinsert = etCity.getText().toString();
                editorCity.putString("CityBaby",cityinsert);
                editorCity.apply();

                Fragment fragment = new BsHome();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.babysitter_container, fragment);
                fragmentTransaction.addToBackStack("BabyHome");
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
