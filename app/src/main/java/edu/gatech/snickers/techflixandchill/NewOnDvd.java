package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewOnDvd extends Activity {

    Button newDVDReturnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_on_dvd);

        final Bundle bundle;
        bundle = getIntent().getExtras();

        newDVDReturnHome = (Button) findViewById(R.id.newDVDReturnToHome);

        newDVDReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(NewOnDvd.this, Home.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

}
