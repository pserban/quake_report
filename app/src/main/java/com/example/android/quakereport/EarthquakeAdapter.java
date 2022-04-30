package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        // Create a new Date object from the time in milliseconds of the earthquake.
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find date_text_view
        TextView dateView = listItemView.findViewById(R.id.date);
        // Get the date and set the text on the TextView
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = listItemView.findViewById(R.id.time);
        // Format the time string(i.e. "4:30PM")
        String formattedTime = formatTime(dateObject);
        timeView.setText(formattedTime);

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
