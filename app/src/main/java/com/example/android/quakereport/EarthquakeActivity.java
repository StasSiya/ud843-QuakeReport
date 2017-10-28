/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private EarthquakeAdapter adapter;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    //Specify ID for my earthquake Loader in case I will use multiple loaders.

    private static final int EARTHQUAKE_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Test: Earthquake Activity on Create() called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        Log.i(LOG_TAG, "Calling initLoader...()");
        getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Earthquake currentEarthquake = adapter.getItem(position);
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, earthquakeUri);
                startActivity(intent);
            }
        });

    }


    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle bundle) {
        Log.v (LOG_TAG, "TEST OnLoadCreate");
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // Clear the adapter of previous earthquake data
        Log.i (LOG_TAG, "TEST: onLoadFInished () called");
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {

            adapter.addAll(earthquakes);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Clear the adapter of previous earthquake data
        adapter.clear();
    }

}




