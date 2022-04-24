package com.example.android.quakereport;

import java.util.Date;

public class Earthquake {
    private String mCity;
    private Float mMagnitude;
    private Date mTime;

    public Earthquake(String city, Float magnitude, Date time) {
        mCity = city;
        mMagnitude = magnitude;
        mTime = time;
    }

    public String getCity() {
        return mCity;
    }

    public float getMagnitude() {
        return mMagnitude;
    }

    public Date getTime() {
        return mTime;
    }
}
