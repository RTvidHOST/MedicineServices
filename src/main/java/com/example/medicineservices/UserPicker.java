package com.example.medicineservices;

import java.sql.Date;

public class UserPicker {
    String user;
    Date date;

    public UserPicker(String user, java.util.Date date) {
        this.user = user;
        this.date = (Date) date;
    }

    public UserPicker(Date date) {

    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = (Date) date;
    }
}
