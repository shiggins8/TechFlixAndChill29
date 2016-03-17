package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
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
class UserListAdapter extends ArrayAdapter<User> {
    /**
     * Constructor to make the Adapter, given a List of users and an Android app context.
     *
     * @param context Android application context of an activity
     * @param userArrayList List object containing User objects
     */
    public UserListAdapter(Context context, List<User> userArrayList) {
        super(context, R.layout.user_view, userArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Set up a temporary view
        View tempView = convertView;
        // Get the data item for this position
        final User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (tempView == null) {
            final LayoutInflater inflater = LayoutInflater.from(getContext());
            tempView = inflater.inflate(R.layout.user_view, parent, false);
        }
        // Lookup views within item layout
        final TextView name = (TextView) tempView.findViewById(R.id.name);
        final TextView username = (TextView) tempView.findViewById(R.id.username);
        final TextView major = (TextView) tempView.findViewById(R.id.major);
        final ImageView status = (ImageView) tempView.findViewById(R.id.status);
        // Populate the data into the template view using the data object
        name.setText(user.getName());
        username.setText(user.getUsername());
        major.setText(user.getMajor());
        if (user.isLocked()) {
            status.setImageDrawable(getContext().getResources().getDrawable(R.drawable.lock));
        } else if (user.isBlocked()) {
            status.setImageDrawable(getContext().getResources().getDrawable(R.drawable.block));
        } else if (user.isAdmin()) {
            status.setImageDrawable(getContext().getResources().getDrawable(R.drawable.adminimage));
        } else  {
            status.setImageDrawable(getContext().getResources().getDrawable(R.drawable.tick));
        }
        // Return the completed view to render on screen
        return tempView;
    }
}
