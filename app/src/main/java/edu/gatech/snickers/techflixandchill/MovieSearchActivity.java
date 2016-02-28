package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import cz.msebera.android.httpclient.Header;

public class MovieSearchActivity extends Activity {
    RottenTomatoesClient client;
    private ListView lvMovies;
    private BoxOfficeMoviesAdapter adapterMovies;
    private EditText search_EDT;
    Button searchButton;

    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
        search_EDT = (EditText) findViewById(R.id.search_EDT);
        searchButton = (Button) findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvMovies = (ListView) findViewById(R.id.lvMovies);
                ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
                adapterMovies = new BoxOfficeMoviesAdapter(MovieSearchActivity.this, aMovies);
                lvMovies.setAdapter(adapterMovies);
                fetchMovieSearch();
                setupMovieSelectedListener();
            }
        });
    }

    // Executes an API call to the box office endpoint, parses the results
    // Converts them into an array of movie objects and adds them to the adapter
    private void fetchMovieSearch() {
        String movieName = search_EDT.getText().toString();
        //convert any blank spaces in the movie title entered by user into + signs, which
        //is what Rotten Tomatoes uses to search
        String finalMovieName = movieName.replaceAll("\\s", "+");
        client = new RottenTomatoesClient();
        client.getMovieSearch(finalMovieName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
                JSONArray items = null;
                try {
                    // Get the movies json array
                    System.out.println(responseBody.toString());
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
                Intent i = new Intent(MovieSearchActivity.this, BoxOfficeDetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

}
