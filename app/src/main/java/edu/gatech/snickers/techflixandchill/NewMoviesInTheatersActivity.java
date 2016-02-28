package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

/**
 * Provides the functionality for users to view movies recently released in theaters. Displays
 * the results in a list view, with selectable rows. Search calls are made using the Rotten
 * Tomatoes API.
 *
 * Created on 2/27/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class NewMoviesInTheatersActivity extends Activity {
    RottenTomatoesClient client;
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;

    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        fetchNewInTheaters();
        setupMovieSelectedListener();
    }

    // Executes an API call to the box office endpoint, parses the results
    // Converts them into an array of movie objects and adds them to the adapter
    private void fetchNewInTheaters() {
        client = new RottenTomatoesClient();
        client.getNewInTheaterMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = responseBody.getJSONArray("movies");
                    // Parse json array into array of model objects
                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                    // Load model objects into the adapter
                    for (BoxOfficeMovie movie : movies) {
                        adapterMovies.add(movie); // add movie through the adapter
                    }
                    adapterMovies.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                Intent i = new Intent(NewMoviesInTheatersActivity.this, BoxOfficeDetailActivity.class);
                Bundle bundle2 = NewMoviesInTheatersActivity.this.getIntent().getExtras();
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                String title = adapterMovies.getItem(position).getTitle();
                i.putExtra("movieTitle", title);
                i.putExtras(bundle2);
                finish();
                startActivity(i);
            }
        });
    }

}
