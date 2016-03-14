package edu.gatech.snickers.techflixandchill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {

    Button logoutButton;
    Button viewProfileButton;
    Button viewUsers;
    TextView usernameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        logoutButton = (Button) findViewById(R.id.logoutButton);
        viewProfileButton = (Button) findViewById(R.id.viewProfileButton);
        viewUsers = (Button) findViewById(R.id.viewUsers);
        usernameTextView = (TextView) findViewById(R.id.usernameTextView);

        final Bundle bundle;
        bundle = getIntent().getExtras();
        //Extract the data
        String username = bundle.getString("USERNAME");
        String password = bundle.getString("PASSWORD");
        String theName = bundle.getString("NAME");
        String message = theName;
        usernameTextView.setText(message);

        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminHome.this, UserProfile.class);
                //pass along bundled username and password
                i.putExtras(bundle);
                startActivity(i);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(AdminHome.this, MainActivity.class);
                startActivity(i);
            }
        });

        viewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(AdminHome.this, UserList.class);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
