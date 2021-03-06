package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Simple extension of an ArrayAdapter to populate the restaurant list view with the criteria
 * mentioned in the email request for this app. Utilizes a restaurant object to find requisite data.
 *
 * @author Scottie
 * @version 1.1
 */
public class TheaterAdapter extends ArrayAdapter<Theater> {
    public TheaterAdapter(Context context, ArrayList<Theater> aTheaters) {
        super(context, 0, aTheaters);
    }

    // Translates a particular `BoxOfficeMovie` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Theater theater = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_theater, parent, false);
        }
        // Lookup views within item layout
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
        TextView tvTelnumber = (TextView) convertView.findViewById(R.id.tvTelnumber);
        TextView tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
        
        // Populate the data into the template view using the data object
        tvName.setText(theater.getName());
        tvAddress.setText(theater.getAddress());
        tvTelnumber.setText(theater.getTelNumber());
        tvDistance.setText(theater.getDistance());

        // Return the completed view to render on screen
        return convertView;
    }
}
