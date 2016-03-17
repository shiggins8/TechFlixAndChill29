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
 * Basic class for the displaying of the movies. Creates an ArrayList of movie objects utilizing
 * the adapter class that we created, sets up a dynamic ListView, and then populates the ListView
 * with the movies from the ArrayList. Creates and sets up clickable logic for individual items
 * within the ListView - clicking a movie will take the user to a more detailed view of the movie.
 *
 * Created on 2/26/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class BoxOfficeActivity extends Activity {
    /**
     * Client that allows an activity to asynchronously make API calls to Rotten Tomatoes API.
     */
    private RottenTomatoesClient client;
    /**
     * A list view that will display the movies in a vertical list, creating one row for each
     * movie that needs to be placed in the list, depending on query.
     */
    private ListView lvMovies;
    /**
     * Implementation of ArrayAdapter that takes an array of movies and automatically populates
     * the list view.
     */
    private BoxOfficeMoviesAdapter adapterMovies;

    /**
     * Keyword for the JSON object to find the movie in the JSONarray that is returned.
     */
    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        final ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvMovies.setAdapter(adapterMovies);
        fetchBoxOfficeMovies();
        setupMovieSelectedListener();
    }

    /**
     * Executes an API call to the box office endpoint, parses the results, converts them into an
     * array of movie objects and adds them to the adapter.
     */
    private void fetchBoxOfficeMovies() {
        client = new RottenTomatoesClient();
        client.getBoxOfficeMovies(new JsonHttpResponseHandler() {
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
                    Log.d("JSON", "onSuccess: failed to find movies in the array");
                }
            }
        });
    }

    /**
     * Selecting a movie (row in the list view) will open up the detailed view of that activity.
     */
    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                final Intent i = new Intent(BoxOfficeActivity.this, BoxOfficeDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                finish();
                startActivity(i);
            }
        });
    }

}
