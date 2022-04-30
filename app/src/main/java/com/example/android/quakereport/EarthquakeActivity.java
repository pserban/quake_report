package com.example.android.quakereport;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        // DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);

        try {
            earthquakes.add(new Earthquake("San Francisco", 7.2f, df.parse("02-02-2016")));
            earthquakes.add(new Earthquake("London", 6.1f, df.parse("07-20-2015")));
            earthquakes.add(new Earthquake("Tokyo", 3.9f, df.parse("11-10-2014")));
            earthquakes.add(new Earthquake("Mexico City", 5.4f, df.parse("05-03-2014")));
            earthquakes.add(new Earthquake("Moscow", 2.8f, df.parse("01-31-2013")));
            earthquakes.add(new Earthquake("Rio de Janeiro", 4.9f, df.parse("08-19-2012")));
            earthquakes.add(new Earthquake("Paris", 1.6f, df.parse("10-30-2011")));
        }
        catch (java.text.ParseException pex) {
            Log.v(LOG_TAG, pex.toString());
        }

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}