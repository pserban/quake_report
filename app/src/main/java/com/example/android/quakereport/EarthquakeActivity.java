package com.example.android.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    ListView earthquakeListView;
    ArrayAdapter<Earthquake> mEarthquakeArrayAdapter;

    private final AdapterView.OnItemClickListener mOnItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Earthquake currentEarthquake = mEarthquakeArrayAdapter.getItem(i);
                    openWebPage(currentEarthquake.getUrl());
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        setupAdapter();

        EarthquakeAsyncTask task = new EarthquakeAsyncTask(this);
        task.execute(EarthquakeActivity.USGS_REQUEST_URL);
    }

    private void setupAdapter() {
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = findViewById(R.id.list);

        // Set click handler for the items in the list
        earthquakeListView.setOnItemClickListener(mOnItemClickListener);

        // Create a new {@link ArrayAdapter} of earthquakes
        mEarthquakeArrayAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mEarthquakeArrayAdapter);
    }

    public void loadDataIntoAdapter(ArrayList<Earthquake> earthquakes) {
        // Clear the adapter of previous earthquake data
        mEarthquakeArrayAdapter.clear();

        // If there is a valid list of {@link Earthquake}s,
        // then add them to the adapter's data set.
        // This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mEarthquakeArrayAdapter.addAll(earthquakes);
        }
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}