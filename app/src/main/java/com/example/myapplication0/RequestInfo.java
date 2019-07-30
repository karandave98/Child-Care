package com.example.myapplication0;

public class RequestInfo {
    String completeAddress;
    String locale;
    String city;
    String nameOfParent;
    String contactParent;

    public RequestInfo() {
    }

    public String getContactParent() {
        return contactParent;
    }

    public void setContactParent(String contactParent) {
        this.contactParent = contactParent;
    }

    public RequestInfo(String completeAddress, String locale, String city, String nameOfParent, String contactParent) {
        this.completeAddress = completeAddress;
        this.city = city;
        this.locale = locale;
        this.nameOfParent = nameOfParent;
        this.contactParent = contactParent;

    }

    public String getNameOfParent() {
        return nameOfParent;
    }

    public void setNameOfParent(String nameOfParent) {
        this.nameOfParent = nameOfParent;
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
