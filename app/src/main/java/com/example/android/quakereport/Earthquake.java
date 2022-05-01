package com.example.android.quakereport;

import java.util.Date;

public class Earthquake {
    private String mLocation;
    private String mUrl;
    private double mMagnitude;
    private long mTimeInMilliseconds;

    public Earthquake(String location, String url, double magnitude, long timeInMilliseconds) {
        mLocation = location;
        mUrl = url;
        mMagnitude = magnitude;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getUrl() { return mUrl; }

    public double getMagnitude() {
        return mMagnitude;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
}
