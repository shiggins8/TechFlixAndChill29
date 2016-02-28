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
        Picasso.with(getContext()).load("https://s-media-cache-ak0.pinimg.com/236x/01/a6/7e/01a67e0bc3ef04860142e8c54920d3e7.jpg").into(ivPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }
}