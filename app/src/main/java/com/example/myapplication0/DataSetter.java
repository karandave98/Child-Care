package com.example.myapplication0;

public class DataSetter {
    private String date;
    private String time;

    public DataSetter() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DataSetter(String date, String time) {
        this.date = date;
        this.time = time;
    }
}
