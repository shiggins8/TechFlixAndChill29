package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Provides the functionality for users to search for movies within the app. Displays the results in
 * a list view, with selectable rows. Search calls are made using the Rotten Tomatoes API.
 *
 * Created on 2/27/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class MovieSearchActivity extends Activity {
    /**
     * A list view that will display the movies in a vertical list, creating one row for each
     * movie that needs to be placed in the list, depending on query.
     */
    private ListView lvMovies;
    /**
     * Extension of an ArrayAdapter that populates the list view with movies matching the search
     * criteria.
     */
    private BoxOfficeMoviesAdapter adapterMovies;
    /**
     * Editable text field where the user can enter the title of a movie they want to search for.
     */
    private EditText searchEDT;
    /**
     * JSON object key to retrieve JSONObject movies from the Rotten Tomatoes API call response.
     */
    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        searchEDT = (EditText) findViewById(R.id.search_EDT);
        final Button searchButton = (Button) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvMovies = (ListView) findViewById(R.id.lvMovies);
                final ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
                adapterMovies = new BoxOfficeMoviesAdapter(MovieSearchActivity.this, aMovies);
                lvMovies.setAdapter(adapterMovies);
                fetchMovieSearch();
                setupMovieSelectedListener();
            }
        });
    }

    /**
     * Executes an API call to the box office endpoint, parses the results. Converts them into an
     * array of movie objects and adds them to the adapter.
     */
    private void fetchMovieSearch() {
        final String movieName = searchEDT.getText().toString();
        //convert any blank spaces in the movie title entered by user into + signs, which
        //is what Rotten Tomatoes uses to search
        final String finalMovieName = movieName.replaceAll("\\s", "+");
        final RottenTomatoesClient client = new RottenTomatoesClient();
        client.getMovieSearch(finalMovieName, new JsonHttpResponseHandler() {
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
                    Log.d("JSON", "onSuccess: found movies, couldn't parse response from RT");
                }
            }
        });
    }

    /**
     * Enables the user to select an individual row in the list view, and then an activity will
     * be started to view that movie, passing in information specific to the selected movie.
     */
    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                final Intent i = new Intent(MovieSearchActivity.this, BoxOfficeDetailActivity.class);
                final Bundle bundle2 = MovieSearchActivity.this.getIntent().getExtras();
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
