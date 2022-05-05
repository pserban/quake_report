package com.example.android.quakereport;

import android.content.Context;

import androidx.annotation.Nullable;
//import androidx.loader.content.AsyncTaskLoader;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private String mURL;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mURL = url;
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        // Don't perform the request if the URL null or empty.
        if (mURL == null || mURL.isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mURL);

        return earthquakes;
    }
}
