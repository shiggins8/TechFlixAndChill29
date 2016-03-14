package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by nirajsuresh on 3/13/16.
 */
class UserListAdapter extends ArrayAdapter<User> {
    public UserListAdapter(Context context, ArrayList<User> userArrayList) {
        super(context, R.layout.user_view, userArrayList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.user_view, parent, false);
        }
        // Lookup views within item layout
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView username = (TextView) convertView.findViewById(R.id.username);
        TextView major = (TextView) convertView.findViewById(R.id.major);
        ImageView status = (ImageView) convertView.findViewById(R.id.status);
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
        return convertView;
    }
}
