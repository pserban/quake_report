package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    public EarthquakeAdapter(Activity context, List<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_element, parent, false);
        }

        // The the {@link Earthquake} object located at this position in the list
        Earthquake currentEarthquake = getItem(position);

        // Find the TextView in earthquake_list_element.xml layout
        // with the ID magnitude
        TextView magnitudeView = listItemView.findViewById(R.id.magnitude);
        // Get the magnitude and set the text on the TextView
        magnitudeView.setText(String.format("%.1f", currentEarthquake.getMagnitude()));

        // Find location
        TextView cityView = listItemView.findViewById(R.id.location);
        // Get the city and set the text on the TextView
        cityView.setText(currentEarthquake.getLocation());

        // Find date_text_view
        TextView dateView = listItemView.findViewById(R.id.date);
        // Get the date and set the text on the TextView
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        dateView.setText(sdf.format(currentEarthquake.getDate()));

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
