package edu.gatech.snickers.techflixandchill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides the functionality for admins to be able to view a list of all of the users of the app.
 * Utilizes a call to the Firebase database to obtain information for all of the users in the
 * system and populates a list view accordingly.
 *
 * Created on 3/10/16.
 *
 * @author Snickers
 * @version 1.1
 */
public class UserList extends AppCompatActivity {
    /**
     * A list view that will display the users in a vertical list, creating one row for each
     * user that needs to be placed in the list.
     */
    private ListView userlist;
    /**
     * Extension of an ArrayAdapter that populates the list view with users.
     */
    private UserListAdapter userListAdapter;
    /**
     * An ArrayList of users that will be filled based on the query to the Firebase database.
     */
    private List<User> users = new ArrayList<User>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userlist = (ListView) findViewById(R.id.lvuserlist);
        userListAdapter= new UserListAdapter(this, users);
        userlist.setAdapter(userListAdapter);
        setupUserSelectedListener();
        Firebase.setAndroidContext(this);
        final Firebase ref = new Firebase("https://techflixandchill.firebaseio.com/users/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (final DataSnapshot user : snapshot.getChildren()) {
                    final User toAdd = user.getValue(User.class);
                    userListAdapter.add(toAdd);
                    userListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Enables the user to select an individual row in the list view, and then an activity will
     * be started to view that movie, passing in information specific to the selected movie.
     */
    public void setupUserSelectedListener() {
        userlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                final Intent i = new Intent(UserList.this, AdminViewsUser.class);
                //Bundle bundle2 = UserList.this.getIntent().getExtras();
                final String username = userListAdapter.getItem(position).getUsername();
                i.putExtra("username", username);
                //i.putExtras(bundle2);
                startActivity(i);
                finish();
            }
        });
    }
}
