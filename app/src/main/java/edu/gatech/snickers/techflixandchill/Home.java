package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Snickers on 2/13/16.
 *
 * Home screen of our app itself. Currently allows users to view their profile or logout of the app.
 * Accessing the view profile option will allow users to edit their profile information.
 *
 * @author Snickers
 * @version 1.1
 */
public class Home extends Activity{

    Button logoutButton, viewProfileButton;
    TextView usernameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewProfileButton = (Button) findViewById(R.id.viewProfileButton);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        //get the bundle created in MainActivity
        final Bundle bundle;
        bundle = getIntent().getExtras();
        //Extract the data
        String username = bundle.getString("USERNAME");
        String password = bundle.getString("PASSWORD");

        usernameTextView.setText("Welcome: " + username);

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home.this, UserProfile.class);
                //pass along bundled username and password
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}