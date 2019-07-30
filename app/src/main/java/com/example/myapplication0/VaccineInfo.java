package com.example.myapplication0;

import java.util.Calendar;

public class VaccineInfo {
    private String vaccine_id;
    String vaccine_name;
    String  days_after;
    String test_name;

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

    public VaccineInfo() {
    }

    public String changeDate(String vaccineDate) {
        String temp [] = vaccineDate.split("/");
        String date = temp[0];
        String month = temp[1];
        String year = temp[2];
        int months = Integer.parseInt(month);
        int years = Integer.parseInt(year);
        if (months > 12){
            months = months - 12;
            years = years + 1;
        }
        return (date + "/" + months + "/" + years);
    }

    public VaccineInfo(String vaccine_id, String vaccine_name, String days_after, String test_name) {
        this.vaccine_id = vaccine_id;
        this.vaccine_name = vaccine_name;
        this.days_after = days_after;
        this.test_name = test_name;
    }

    public String getVaccine_id() {
        return vaccine_id;
    }

    public void setVaccine_id(String vaccine_id) {
        this.vaccine_id = vaccine_id;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getDays_after() {
        return days_after;
    }

    public void setDays_after(String days_after) {
        this.days_after = days_after;
    }
}
