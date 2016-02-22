package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewInTheaters extends Activity {

    Button newInTheatersReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_in_theaters);

        final Bundle bundle;
        bundle = getIntent().getExtras();

        newInTheatersReturnHome = (Button) findViewById(R.id.newInTheatersReturnToHome);

        newInTheatersReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(NewInTheaters.this, Home.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

}
