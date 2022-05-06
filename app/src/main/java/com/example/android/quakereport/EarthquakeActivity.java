package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
// import androidx.loader.app.LoaderManager;

import android.app.LoaderManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getSimpleName();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query";

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

        // Get a reference to the ConnectivityManager to check the
        // state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            setupListView();
            getLoaderManager().initLoader(1, null, this);
        } else {
            setTextViewContent(R.string.no_network_connection);
            hideProgressBar();
        }
    }

    private void setupListView() {
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = findViewById(R.id.list);

        // Set click handler for the items in the list
        earthquakeListView.setOnItemClickListener(mOnItemClickListener);

        TextView textView = findViewById(R.id.empty_list_view);
        earthquakeListView.setEmptyView(textView);

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

    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(EarthquakeActivity.this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        if (data == null) {
            return;
        }

        loadDataIntoAdapter(data);

        setTextViewContent(R.string.no_earthquakes_msg);

        hideProgressBar();
    }

    private void hideProgressBar() {
        ProgressBar progressBar = findViewById(R.id.download_progress_indicator);
        progressBar.setVisibility(View.GONE);
    }

    private void setTextViewContent(int resourceID) {
        TextView textView = findViewById(R.id.empty_list_view);
        textView.setText(resourceID);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquake>> loader) {
        loadDataIntoAdapter(new ArrayList<Earthquake>());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}