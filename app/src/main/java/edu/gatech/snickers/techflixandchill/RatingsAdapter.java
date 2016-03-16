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
    public RatingsAdapter(Context context, List<Rating> aMovies) {
        super(context, 0, aMovies);
    }

    // Translates a particular `BoxOfficeMovie` given a position
    // into a relevant row within an AdapterView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Set up a temporary view
        View tempView = convertView;
        // Get the data item for this position
        final Rating rating = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (tempView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            tempView = inflater.inflate(R.layout.item_rating, parent, false);
        }
        // Lookup views within item layout
        final TextView tvTitle = (TextView) tempView.findViewById(R.id.tvTitle);
        final TextView tvCriticsScore = (TextView) tempView.findViewById(R.id.tvCriticsScore);
        final TextView tvCast = (TextView) tempView.findViewById(R.id.tvCast);
        final ImageView ivPosterImage = (ImageView) tempView.findViewById(R.id.ivPosterImage);

        // Populate the data into the template view using the data object
        tvTitle.setText(rating.getMovie().getTitle());
        tvCriticsScore.setText("Rating: " + rating.getNumericalRating() + " stars");
        tvCast.setText(rating.getCommentRating());
        Picasso.with(getContext()).load(rating.getMovie().getPosterUrl()).into(ivPosterImage);
        // Return the completed view to render on screen
        return tempView;
    }
}