package com.example.myapplication0;

public class BabysitterPass {
    String fullNAme;
    String contact;
    String mailId;
    String userKey;

    public BabysitterPass() {
    }

    public BabysitterPass(String fullNAme, String contact, String mailId, String userKey) {
        this.fullNAme = fullNAme;
        this.contact = contact;
        this.mailId = mailId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getFullNAme() {
        return fullNAme;
    }

    public void setFullNAme(String fullNAme) {
        this.fullNAme = fullNAme;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getmailId() {
        return mailId;
    }

    public void setmailId(String mailId) {
        this.mailId = mailId;
    }
}
