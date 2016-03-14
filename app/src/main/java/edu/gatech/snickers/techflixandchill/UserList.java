package edu.gatech.snickers.techflixandchill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class UserList extends AppCompatActivity {

    private ListView userlist;
    private UserListAdapter userListAdapter;
    private Firebase ref;
    public ArrayList<User> users = new ArrayList<User>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userlist = (ListView) findViewById(R.id.lvuserlist);
        userListAdapter= new UserListAdapter(this, users);
        userlist.setAdapter(userListAdapter);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://techflixandchill.firebaseio.com/users/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.getChildren()) {
                    User toAdd = user.getValue(User.class);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
