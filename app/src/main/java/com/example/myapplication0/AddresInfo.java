package com.example.myapplication0;

import com.firebase.geofire.GeoFire;

public class AddresInfo {
    String completeAddress;
    String locale;
    String city;



    public AddresInfo() {
    }

    public AddresInfo(String completeAddress, String locale,String city) {
        this.completeAddress = completeAddress;
        this.city = city;
        this.locale = locale;

    }

    public String getCompleteAddress() {
        return completeAddress;
    }

    public void setCompleteAddress(String completeAddress) {
        this.completeAddress = completeAddress;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


}
