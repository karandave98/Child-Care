package com.example.myapplication0;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FillAddress.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FillAddress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillAddress extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editTextaddressLine1, editTextaddressLine2, editTextcity, editTextLocale;
    private OnFragmentInteractionListener mListener;
    Button buttonNext;

    public FillAddress() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FillAddress.
     */
    // TODO: Rename and change types and number of parameters
    public static FillAddress newInstance(String param1, String param2) {
        FillAddress fragment = new FillAddress();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.address_fill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        buttonNext = view.findViewById(R.id.button_next);
        final Context context;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            buttonNext.setEnabled(false);
            Toast.makeText(getContext(), "Please allow permission", Toast.LENGTH_SHORT).show();
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                Toast.makeText(getContext(), "Please allow permission", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 51);
            buttonNext.setEnabled(true);
        }
        else {
            buttonNext.setEnabled(true);
        }
        final SharedPreferences sharedAddress =  getContext().getSharedPreferences("Address_Pass",0) ;
        final SharedPreferences.Editor editor = sharedAddress.edit();
      //  final DatabaseReference dbrefAddressdetail;
//        dbrefAddressdetail = FirebaseDatabase.getInstance().getReference("Address_Info");
//        final String uUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        editTextaddressLine1 = view.findViewById(R.id.addres_line_1);
        editTextaddressLine2 = view.findViewById(R.id.addres_line_2);
        editTextLocale = view.findViewById(R.id.et_locale);
        editTextcity = view.findViewById(R.id.city_details);


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addressLine1 = editTextaddressLine1.getText().toString();
                String addressLine2 = editTextaddressLine2.getText().toString();
                String city = editTextcity.getText().toString();
                String locale = editTextLocale.getText().toString();

                String goAddress = addressLine1+"\n"+addressLine2;

                if (addressLine1.isEmpty()){
                    editTextaddressLine1.setError("Not Valid");
                }
                else if (addressLine2.isEmpty()){
                    editTextaddressLine1.setError("Not valid");
                }
                else if (city.isEmpty()){
                    editTextcity.setError("Not Valid");
                }
                else if (locale.isEmpty()){
                    editTextcity.setError("Not Valid");
                }
                else {
                  // AddresInfo addresInfo = new AddresInfo(addressLine1,addressLine2,city);

                   //dbrefAddressdetail.child(uUid).setValue(addresInfo);
                    editor.putString("Address_Come",goAddress);
                    editor.putString("Locality",locale);
                    editor.putString("City_Address", city);
                    editor.commit();
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

                            Toast.makeText(getContext(), "Please allow permission", Toast.LENGTH_SHORT).show();
                        }
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 51);
                    }

                    Intent tolocator = new Intent(getContext(),BabysitterLocator.class);
                    startActivity(tolocator);
                }

            }
        });


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
