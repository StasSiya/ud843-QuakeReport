package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

import static com.example.android.quakereport.EarthquakeActivity.LOG_TAG;

/**
 * Created by Anastasiya on 23.10.2017.
 */




public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String mUrl;


    public EarthquakeLoader (Context context, String url) {
        super(context);
        mUrl = url;


        }
        @Override
        protected void onStartLoading () {
            Log.i (LOG_TAG, "TEST: onStartLoading() called");
            forceLoad();
        }
        @Override
        public List<Earthquake> loadInBackground () {
            Log.i (LOG_TAG, "TEST: loadInBackground () called");

            if (mUrl ==null){
                return null;
            }
            List<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
            return earthquakes;
        }

    }