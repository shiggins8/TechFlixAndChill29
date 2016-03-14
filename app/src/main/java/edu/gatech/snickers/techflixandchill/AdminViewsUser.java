package edu.gatech.snickers.techflixandchill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AdminViewsUser extends AppCompatActivity {

    Firebase ref;
    TextView userProfileUsernameTV, userProfilePasswordTV, userProfileEmailTV, userProfileSecuHintTV,
            userProfileNameTV, userProfileMajorTV;
    Switch lock, block;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_views_user);

        userProfileUsernameTV = (TextView) findViewById(R.id.userProfileUsernameTV);
        userProfilePasswordTV = (TextView) findViewById(R.id.userProfilePasswordTV);
        userProfileEmailTV = (TextView) findViewById(R.id.userProfileEmailTV);
        userProfileSecuHintTV = (TextView) findViewById(R.id.userProfileSecuHintTV);
        userProfileMajorTV = (TextView) findViewById(R.id.userProfileMajorTV);
        userProfileNameTV = (TextView) findViewById(R.id.userProfileNameTV);
        lock = (Switch) findViewById(R.id.lock);
        block = (Switch) findViewById(R.id.block);

        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");
        Firebase.setAndroidContext(this);
        ref =  new Firebase("https://techflixandchill.firebaseio.com/users/"+ username);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userProfileNameTV.setText("Name: " + user.getName());
                userProfileUsernameTV.setText("Username: " + user.getUsername());
                userProfilePasswordTV.setText("Password: " + user.getPassword());
                userProfileEmailTV.setText("Email: " + user.getEmail());
                userProfileSecuHintTV.setText("Security Hint: " + user.getSecurityHint());
                userProfileMajorTV.setText("Major: " + user.getMajor());
                if (user.isLocked() ) {
                    lock.setChecked(true);
                } else {
                    lock.setChecked(false);
                }
                if (user.isBlocked() ) {
                    block.setChecked(true);
                } else {
                    block.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });

        lock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ref.child("locked").setValue(true);
                } else {
                    ref.child("locked").setValue(false);
                }
            }
        });

        block.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ref.child("blocked").setValue(true);
                } else {
                    ref.child("blocked").setValue(false);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_views_user, menu);
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
