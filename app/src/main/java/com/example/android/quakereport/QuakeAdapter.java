package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class QuakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = QuakeAdapter.class.getSimpleName();

    public QuakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
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
        // with the ID magnitude_text_view
        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitude_text_view);
        // Get the magnitude and set the text on the TextView
        magnitudeTextView.setText(String.format("%.1f", currentEarthquake.getMagnitude()));

        // Find city_text_view
        TextView cityTextView = listItemView.findViewById(R.id.city_text_view);
        // Get the city and set the text on the TextView
        cityTextView.setText(currentEarthquake.getCity());

        // Find date_text_view
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        // Get the date and set the text on the TextView
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        dateTextView.setText(sdf.format(currentEarthquake.getTime()));

        // Return the whole list item layout (containing 3 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
