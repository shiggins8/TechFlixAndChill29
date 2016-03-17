package edu.gatech.snickers.techflixandchill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AdminViewsUser extends AppCompatActivity {

    /**
     * A reference to the Firebase database, used for querying the user data within the class.
     */
    private Firebase ref;
    /**
     * TextViews that will be populated with the information from the User the Admin wishes to
     * view.
     */
    private TextView userProfileUsernameTV, userProfilePasswordTV, userProfileEmailTV, userProfileSecuHintTV, userProfileNameTV, userProfileMajorTV;
    /**
     * Switches that allow an admin to block, unblock, lock, or unlock a user.
     */
    private Switch lock, block;
    //TODO add a save button so that the admin has to click it, it will close the activity and prevent weird back arrow functionality

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_views_user);

        Button returnFromViewsUserBtn;

        userProfileUsernameTV = (TextView) findViewById(R.id.userProfileUsernameTV);
        userProfilePasswordTV = (TextView) findViewById(R.id.userProfilePasswordTV);
        userProfileEmailTV = (TextView) findViewById(R.id.userProfileEmailTV);
        userProfileSecuHintTV = (TextView) findViewById(R.id.userProfileSecuHintTV);
        userProfileMajorTV = (TextView) findViewById(R.id.userProfileMajorTV);
        userProfileNameTV = (TextView) findViewById(R.id.userProfileNameTV);
        lock = (Switch) findViewById(R.id.lock);
        block = (Switch) findViewById(R.id.block);
        final Switch admin = (Switch) findViewById(R.id.admin);
        returnFromViewsUserBtn = (Button) findViewById(R.id.returnFromViewUserBtn);

        final Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("username");
        Firebase.setAndroidContext(this);
        ref =  new Firebase("https://techflixandchill.firebaseio.com/users/"+ username);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final User user = snapshot.getValue(User.class);
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

        admin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ref.child("admin").setValue(true);
                } else {
                    ref.child("admin").setValue(false);
                }
            }
        });

        returnFromViewsUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(AdminViewsUser.this, UserList.class);
                //finish();
                startActivity(i);
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
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
