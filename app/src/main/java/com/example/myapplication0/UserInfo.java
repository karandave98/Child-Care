package com.example.myapplication0;

public class UserInfo {
    private  String userId;
    private  String fName;
    private  String lName;
    private  String email;
    private String mobile;

    public UserInfo(String userId, String fName, String lName, String email, String mobile) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.mobile = mobile;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public UserInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}
