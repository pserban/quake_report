package com.example.android.quakereport;

import java.util.Date;

public class Earthquake {
    private String mLocation;
    private Float mMagnitude;
    private Date mDate;

    public Earthquake(String location, Float magnitude, Date date) {
        mLocation = location;
        mMagnitude = magnitude;
        mDate = date;
    }

    public String getLocation() {
        return mLocation;
    }

    public float getMagnitude() {
        return mMagnitude;
    }

    public Date getDate() {
        return mDate;
    }
}
