package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
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
public class GetRecommendationsActivity extends Activity {
    RottenTomatoesClient client;
    private ListView lvRecommendations;
    private BoxOfficeMoviesAdapter adapterMovies;

    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recommendations);

        lvRecommendations = (ListView) findViewById(R.id.lvRecommendations);
        ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvRecommendations.setAdapter(adapterMovies);
        fetchRecommendationsByMajor();
        setupMovieSelectedListener();
    }

    // Executes an API call to the box office endpoint, parses the results
    // Converts them into an array of movie objects and adds them to the adapter
    private void fetchRecommendationsByMajor() {
        client = new RottenTomatoesClient();
        Bundle bundle = GetRecommendationsActivity.this.getIntent().getExtras();
        String major = bundle.getString("MAJOR");
        //set up Firebase reference, pull all the top ratings from Firebase
        final Firebase majorRef = new Firebase("https://techflixandchill.firebaseio.com/ratingsByMajor/" + major);
        //gets the two movies with the highest ratings from that major
        //TODO handle case where there aren't at least 2 references to go off of
        //TODO handle case where repeates are put into the arraylist
        Query queryRef = majorRef.orderByChild("numericalRating").limitToLast(2);
        //get the movie id's from these first two ratings
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int movieID1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.getChildrenCount());
                for (DataSnapshot rating : dataSnapshot.getChildren()) {
                    Rating tempRating = rating.getValue(Rating.class);
                    movieID1 = tempRating.getMovie().getMovieID();
                    System.out.println("movie title: " + tempRating.getMovie().getTitle());
                    client.getRecommendations(movieID1, new JsonHttpResponseHandler() {
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
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do nothing right now
            }
        });


//
//        client.getRecommendations(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject responseBody) {
//                JSONArray items = null;
//                try {
//                    // Get the movies json array
//                    items = responseBody.getJSONArray("movies");
//                    // Parse json array into array of model objects
//                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
//                    // Load model objects into the adapter
//                    for (BoxOfficeMovie movie : movies) {
//                        adapterMovies.add(movie); // add movie through the adapter
//                    }
//                    adapterMovies.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public void setupMovieSelectedListener() {
        lvRecommendations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                Intent i = new Intent(GetRecommendationsActivity.this, BoxOfficeDetailActivity.class);
                Bundle bundle2 = GetRecommendationsActivity.this.getIntent().getExtras();
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                String title = adapterMovies.getItem(position).getTitle();
                i.putExtra("movieTitle", title);
                i.putExtras(bundle2);
                //finish();
                startActivity(i);
            }
        });
    }

}
