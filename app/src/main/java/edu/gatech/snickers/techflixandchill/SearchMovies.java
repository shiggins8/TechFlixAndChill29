package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchMovies extends Activity {

    Button searchReturnToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        final Bundle bundle;
        bundle = getIntent().getExtras();

        searchReturnToHome = (Button) findViewById(R.id.searchReturnToHome);

        searchReturnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(SearchMovies.this, Home.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

}
