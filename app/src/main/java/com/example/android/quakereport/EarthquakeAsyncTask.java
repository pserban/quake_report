package com.example.android.quakereport;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {

    private final static String LOG_TAG = EarthquakeAsyncTask.class.getSimpleName();

    private final WeakReference<EarthquakeActivity> activityReference;

    public EarthquakeAsyncTask(EarthquakeActivity context) {
        activityReference = new WeakReference<>(context);
    }

    @Override
    protected ArrayList<Earthquake> doInBackground(String... urls) {
        // Don't perform the request if there are no URL's,
        // or the first URL is null.
        if (urls.length < 1 || urls[0] == null || urls[0].isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);

        return earthquakes;
    }

    @Override
    protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
        super.onPostExecute(earthquakes);

        EarthquakeActivity activity = activityReference.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        if (earthquakes == null) {
            return;
        }

        activity.setupAdapter(earthquakes);
    }
}
