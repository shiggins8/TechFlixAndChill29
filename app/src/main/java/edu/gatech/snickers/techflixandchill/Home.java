package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Scottie on 2/13/16.
 *
 * Home screen of our app itself
 */
public class Home extends Activity{

    Button logoutButton, viewProfileButton;
    TextView usernameTextView;
    EditText usernameEntered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewProfileButton = (Button) findViewById(R.id.viewProfileButton);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        //get the bundle created in MainActivity
        Bundle bundle = getIntent().getExtras();
        //Extract the data
        String username = bundle.getString("USERNAME");
        String password = bundle.getString("PASSWORD");

        usernameTextView.setText("Welcome: " + username);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}