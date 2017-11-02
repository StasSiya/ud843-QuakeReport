package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Anastasiya on 19.10.2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }



    private static final String LOCATION_SEPARATOR = " of ";
    String primary;
    String offset;
    String urlString;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);

        String currentLocation = currentEarthquake.getLocation();

        if (currentLocation.contains(LOCATION_SEPARATOR)) {
            String[] string = currentLocation.split(LOCATION_SEPARATOR);
            offset = string[0] + LOCATION_SEPARATOR;
            primary = string[1];
        } else {
            offset = getContext().getString(R.string.near_the);
            primary = currentLocation;
        }

        TextView primaryLocation = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocation.setText(primary);

        TextView offsetLocation = (TextView) listItemView.findViewById(R.id.location_offset);
        offsetLocation.setText(offset);


        Date dateObject = new Date(currentEarthquake.getTimeInMilliSeconds());
        TextView date = (TextView) listItemView.findViewById(R.id.date);
        String formattedDate = formatDate(dateObject);
        date.setText(formattedDate);

        TextView time = (TextView) listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        time.setText(formattedTime);

        double currentMagnitude = currentEarthquake.getMagnitude();

        TextView mag = (TextView) listItemView.findViewById(R.id.magnitude);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(currentMagnitude);
        mag.setText(output);
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentMagnitude);

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;
    }


    private int getMagnitudeColor (double currentMagnitude) {
        int magnitudeColorRessourceId;
        int magnitudeFloor = (int) Math.floor(currentMagnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorRessourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorRessourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorRessourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorRessourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorRessourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorRessourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorRessourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorRessourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorRessourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorRessourceId = R.color.magnitude10plus;
                break;
        }

        //Call ContextCompat getColor() to convert the color resource ID into an actual
        // integer color value, and return the result as the return value of the
        // getMagnitudeColor() helper method.
        return ContextCompat.getColor(getContext(),magnitudeColorRessourceId);
    }









    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
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
