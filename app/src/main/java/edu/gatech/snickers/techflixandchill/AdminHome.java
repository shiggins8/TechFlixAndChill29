package edu.gatech.snickers.techflixandchill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Home screen for the admins of the application. Has specific functionality that only admins can
 * access, mainly including viewing all of the users of the app in a list, providing them the
 * ability to select users and make them admins, block or unblock them, or lock/unlock their
 * accounts.
 *
 * Created on 3/12/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class AdminHome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button logoutButton;
        Button viewUsers;
        TextView usernameTextView;

        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewUsers = (Button) findViewById(R.id.viewUsers);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        final Bundle bundle;
        bundle = getIntent().getExtras();
        //Extract the data
        final String message = bundle.getString("NAME");
        usernameTextView.setText(message);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                final Intent i = new Intent(AdminHome.this, MainActivity.class);
                startActivity(i);
            }
        });

        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                final Intent i = new Intent(AdminHome.this, UserList.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
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
