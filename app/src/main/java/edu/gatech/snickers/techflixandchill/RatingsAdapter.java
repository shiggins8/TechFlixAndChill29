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
 * ArrayAdapter extension class that sets up the dynamic list view for the Ratings objects. As it
 * is currently coded, it will load one standard image into the ImageView. Will be updated in the
 * future to get the corresponding URL for thumbnail image based on movie that is being rated.
 *
 * Created 2/27/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class RatingsAdapter extends ArrayAdapter<Rating> {
    public RatingsAdapter(Context context, ArrayList<Rating> aMovies) {
        super(context, 0, aMovies);
    }

    // Translates a particular `BoxOfficeMovie` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Rating rating = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_rating, parent, false);
        }
        // Lookup views within item layout
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticsScore);
        TextView tvCast = (TextView) convertView.findViewById(R.id.tvCast);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(rating.getTitleOfMovie());
        tvCriticsScore.setText("Rating: " + rating.getNumericalRating() + " stars");
        tvCast.setText(rating.getCommentRating());
        //TODO remove Schmidt picture url, even if it is hilarious
        Picasso.with(getContext()).load("http://www.i2clipart.com/cliparts/3/1/2/c/clipart-movie-popcorn-bag-312c.png").into(ivPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }
}