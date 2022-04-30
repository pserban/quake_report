package com.example.android.quakereport;

import java.util.Date;

public class Earthquake {
    private String mLocation;
    private double mMagnitude;
    private long mTimeInMilliseconds;

    public Earthquake(String location, double magnitude, long timeInMilliseconds) {
        mLocation = location;
        mMagnitude = magnitude;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    public String getLocation() {
        return mLocation;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
}
