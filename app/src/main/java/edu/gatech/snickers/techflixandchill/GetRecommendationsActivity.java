package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Provides the user with an option to request recommendations based upon a specific major. That is
 * the current functionality, eventually it will work so that they can request recommendations
 * based on a range of criteria. Further functionality will allow the user to have a menu of
 * selection criteria, to give them recommendations based upon different parameters (recency, the
 * movies they have rated, etc.)
 *
 * Created on 3/6/16. Last modified on 3/7/16
 *
 * @author Snickers
 * @version 2.0
 */
public class GetRecommendationsActivity extends Activity {
    private RottenTomatoesClient client;
    private ListView lvRecommendations;
    private BoxOfficeMoviesAdapter adapterMovies;
    private Spinner selectMajor;
    private TextView noMoviesMessage;

    public static final String MOVIE_DETAIL_KEY = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recommendations);

        lvRecommendations = (ListView) findViewById(R.id.lvRecommendations);
        final ArrayList<BoxOfficeMovie> aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new BoxOfficeMoviesAdapter(this, aMovies);
        lvRecommendations.setAdapter(adapterMovies);
        noMoviesMessage = (TextView) findViewById(R.id.noMoviesTextView);

        final Bundle bundle = GetRecommendationsActivity.this.getIntent().getExtras();
        final String major = bundle.getString("MAJOR");
        selectMajor = (Spinner) findViewById(R.id.chooseMajorSpinner);
        final String[] items = getResources().getStringArray(R.array.majors_array);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //make sure that spinner initially starts on their original major selection
        final int i = adapter.getPosition(major);
        selectMajor.setAdapter(adapter);
        selectMajor.setSelection(i, true);

        final Button majorRecommendationsBtn= (Button) findViewById(R.id.majorRecommendationsBtn);
        majorRecommendationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noMoviesMessage.setText("");
                lvRecommendations.clearAnimation();
                final String selectedMajor = selectMajor.getSelectedItem().toString();
                clearListView();
                fetchRecommendationsByMajor(selectedMajor);
                setupMovieSelectedListener();
                //TODO figure out how to display instructions if there are no items in list view
                //System.out.println("lv count = " + adapterMovies.getCount());
            }
        });

        //fetchRecommendationsByMajor();   should be doing this after a major is selected
        //setupMovieSelectedListener();
    }

    /**
     * Clear the list view and allow for the user to make a new request on a blank view.
     */
    private void clearListView() {
        adapterMovies.clear();
        noMoviesMessage.setText("");
    }

    // Executes an API call to the box office endpoint, parses the results
    // Converts them into an array of movie objects and adds them to the adapter
    private void fetchRecommendationsByMajor(String major) {
        client = new RottenTomatoesClient();
        final String messageMajor = major;
        //set up Firebase reference, pull all the top ratings from Firebase
        final Firebase majorRef = new Firebase("https://techflixandchill.firebaseio.com/ratingsByMajor/" + major);
        //gets the two movies with the highest ratings from that major
        //TODO handle case where repeates are put into the arraylist
        final Query queryRef = majorRef.orderByChild("numericalRating").limitToLast(2);
        //get the movie id's from these first two ratings
        queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            private int movieID1;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (final DataSnapshot rating : dataSnapshot.getChildren()) {
                        final Rating tempRating = rating.getValue(Rating.class);
                        movieID1 = tempRating.getMovie().getMovieID();
                        client.getRecommendations(movieID1, new JsonHttpResponseHandler() {
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
//                                    System.out.println(adapterMovies.isEmpty() + ": is it empty?");
//                                    adapterMovies.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    Log.d("JSON", "onSuccess: got movie, couldn't get recommends");
                                }
                            }
                        });
                    }
                    adapterMovies.notifyDataSetChanged();
                } else {
                    noMoviesMessage.setText("No data for " + messageMajor + ", sorry");
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do nothing right now
            }
        });
    }

    /**
     * Sets up the list view to allow selectability for each of the rows, which equates to movies.
     * Selecting amovie will open up the detailed view of that movie.
     */
    public void setupMovieSelectedListener() {
        lvRecommendations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                // Launch the detail view passing movie as an extra
                final Intent i = new Intent(GetRecommendationsActivity.this, BoxOfficeDetailActivity.class);
                final Bundle bundle2 = GetRecommendationsActivity.this.getIntent().getExtras();
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
