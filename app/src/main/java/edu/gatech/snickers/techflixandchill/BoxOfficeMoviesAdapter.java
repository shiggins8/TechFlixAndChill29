package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Essential adapter class that extends the ArrayAdapter class. Utilized to set up a listView row
 * by adapting an array of movie objects and then translating a specific movie into the list given
 * a position into a relevant row within the list view.
 *
 * Created by Scottie on 2/26/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class BoxOfficeMoviesAdapter extends ArrayAdapter<BoxOfficeMovie> {
    public BoxOfficeMoviesAdapter(Context context, ArrayList<BoxOfficeMovie> aMovies) {
        super(context, 0, aMovies);
    }

    // Translates a particular `BoxOfficeMovie` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BoxOfficeMovie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_box_office_movie, parent, false);
        }
        // Lookup views within item layout
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getAudienceScore() + "%");
        tvCast.setText(movie.getCastList());
        Picasso.with(getContext()).load(movie.getPosterUrl()).into(ivPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }
}