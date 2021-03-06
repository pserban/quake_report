package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.graphics.drawable.GradientDrawable;

import androidx.core.content.ContextCompat;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    private static final String LOCATION_SEPARATOR = " of ";
    
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
        String formattedMagnitude = formatMagnitude(currentEarthquake.getMagnitude());
        magnitudeView.setText(formattedMagnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String originalLocation = currentEarthquake.getLocation();

        String locationOffset = null;
        String primaryLocation = null;

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }
        else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        // Find location views
        TextView locationOffsetView = listItemView.findViewById(R.id.location_offset);
        TextView primaryLocationView = listItemView.findViewById(R.id.primary_location);
        // Get the city and set the text on the TextView
        locationOffsetView.setText(locationOffset);
        primaryLocationView.setText(primaryLocation);

        // Create a new Date object from the time in milliseconds of the earthquake.
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());

        // Find date_text_view
        TextView dateView = listItemView.findViewById(R.id.date);
        // Get the date and set the text on the TextView
        String formattedDate = formatDate(dateObject);
        dateView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = listItemView.findViewById(R.id.time);
        // Format the time string (i.e. "4:30PM")
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

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    /**
     * Return the color value associated with a magnitude
     */
    private int getMagnitudeColor(double magnitude) {
        int colorResourceID;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1: colorResourceID = R.color.magnitude1; break;
            case 2: colorResourceID = R.color.magnitude2; break;
            case 3: colorResourceID = R.color.magnitude3; break;
            case 4: colorResourceID = R.color.magnitude4; break;
            case 5: colorResourceID = R.color.magnitude5; break;
            case 6: colorResourceID = R.color.magnitude6; break;
            case 7: colorResourceID = R.color.magnitude7; break;
            case 8: colorResourceID = R.color.magnitude8; break;
            case 9: colorResourceID = R.color.magnitude9; break;
            default: colorResourceID = R.color.magnitude10plus;
        };
        return ContextCompat.getColor(getContext(), colorResourceID);
    }
}
