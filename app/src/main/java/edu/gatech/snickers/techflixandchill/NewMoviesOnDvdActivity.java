package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Provides the functionality for users to view movies recently released on DVD. Displays the
 * results in a list view, with selectable rows. Search calls are made using the Rotten Tomatoes
 * API.
 *
 * Created on 2/27/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class NewMoviesOnDvdActivity extends Activity {
    private RottenTomatoesClient client;
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;

    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        final ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        fetchNewOnDvd();
        setupMovieSelectedListener();
    }

    // Executes an API call to the box office endpoint, parses the results
    // Converts them into an array of movie objects and adds them to the adapter
    private void fetchNewOnDvd() {
        client = new RottenTomatoesClient();
        client.getNewOnDvdMovies(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    items = responseBody.getJSONArray("movies");
                    // Parse json array into array of model objects
                    final List<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                    // Load model objects into the adapter
                    for (final BoxOfficeMovie movie : movies) {
                        adapterMovies.add(movie); // add movie through the adapter
                    }
                    adapterMovies.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d("JSON", "onSuccess: found new on DVD, couldn't parse response from RT");
                }
            }
        });
    }

    /**
     * Selecting a row on the list view will allow the user to view the detailed view of the
     * movie itself.
     */
    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                final Intent i = new Intent(NewMoviesOnDvdActivity.this, BoxOfficeDetailActivity.class);
                final Bundle bundle2 = NewMoviesOnDvdActivity.this.getIntent().getExtras();
                //String username = bundle.getString("USERNAME");
                //String major = bundle.getString("MAJOR");
                //i.putExtra("USERNAME", username);
                //i.putExtra("MAJOR", major);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                final String title = adapterMovies.getItem(position).getTitle();
                i.putExtra("movieTitle", title);
                i.putExtras(bundle2);
                //finish();
                startActivity(i);
            }
        });
    }

}
