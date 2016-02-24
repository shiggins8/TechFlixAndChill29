package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Snickers on 2/13/16.
 *
 * Last modified on 2/21/16.
 *
 * Home screen of our app itself. Currently allows users to view their profile or logout of the app.
 * Accessing the view profile option will allow users to edit their profile information. As of the
 * last update, allows users to view the newly released movies on DVD, in theaters, and additionally
 * search for movies by title or keywords. Utilizes the Rotten Tomatoes API and Databases for that
 * functionality.
 *
 * @author Snickers
 * @version 1.2
 */
public class Home extends Activity{

    Button logoutButton, viewProfileButton, searchMoviesBtn, newReleasesBtn, newOnDVDBtn;
    TextView usernameTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewProfileButton = (Button) findViewById(R.id.viewProfileButton);
        searchMoviesBtn = (Button) findViewById(R.id.searchMoviesBtn);
        newReleasesBtn = (Button) findViewById(R.id.newReleasesBtn);
        newOnDVDBtn = (Button) findViewById(R.id.newOnDVDBtn);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        //get the bundle created in MainActivity
        final Bundle bundle;
        bundle = getIntent().getExtras();
        //Extract the data
        String username = bundle.getString("USERNAME");
        String password = bundle.getString("PASSWORD");
        String theName = bundle.getString("NAME");

        String message = "Welcome: " + theName;

        usernameTextView.setText(message);

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

        searchMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to the page to search for movies and display results
                Intent i = new Intent(Home.this, SearchMovies.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        newReleasesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to page to view and display movies newly released to theaters
                Intent i = new Intent(Home.this, NewInTheaters.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        newOnDVDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to page to view and display movies newly released to DVD
                Intent i = new Intent(Home.this, NewOnDvd.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }
}