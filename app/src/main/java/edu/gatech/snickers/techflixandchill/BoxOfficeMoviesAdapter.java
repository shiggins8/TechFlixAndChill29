package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

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
    public BoxOfficeMoviesAdapter(Context context, List<BoxOfficeMovie> aMovies) {
        super(context, 0, aMovies);
    }

    // Translates a particular `BoxOfficeMovie` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Set up a temporary view
        View tempView = convertView;
        // Get the data item for this position
        final BoxOfficeMovie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (tempView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            tempView = inflater.inflate(R.layout.item_box_office_movie, parent, false);
        }
        // Lookup views within item layout
        final TextView tvTitle = (TextView) tempView.findViewById(R.id.tvTitle);
        final TextView tvCriticsScore = (TextView) tempView.findViewById(R.id.tvCriticsScore);
        final TextView tvCast = (TextView) tempView.findViewById(R.id.tvCast);
        final ImageView ivPosterImage = (ImageView) tempView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getAudienceScore() + "%");
        tvCast.setText(movie.getCastList());
        Picasso.with(getContext()).load(movie.getPosterUrl()).into(ivPosterImage);
        // Return the completed view to render on screen
        return tempView;
    }
}