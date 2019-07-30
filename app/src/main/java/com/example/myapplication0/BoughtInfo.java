package com.example.myapplication0;

public class BoughtInfo {
    String pname;
    String price;
    String userName;
    String pickOrPlace;

    public BoughtInfo() {
    }

    public BoughtInfo(String pname, String price, String userName, String pickOrPlace) {
        this.pname = pname;
        this.price = price;
        this.userName = userName;
        this.pickOrPlace = pickOrPlace;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPickOrPlace() {
        return pickOrPlace;
    }

    public void setPickOrPlace(String pickOrPlace) {
        this.pickOrPlace = pickOrPlace;
    }
}
