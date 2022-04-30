package com.example.android.quakereport;

import java.util.Date;

public class Earthquake {
    private String mLocation;
    private double mMagnitude;
    private Date mDate;

    public Earthquake(String location, double magnitude, Date date) {
        mLocation = location;
        mMagnitude = magnitude;
        mDate = date;
    }

    public String getLocation() {
        return mLocation;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public Date getDate() {
        return mDate;
    }
}
