package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Rating functionality of the app. Users can cancel to decide to not rate the movie, or they can
 * give the movie a rating based on a 1-5 star rating scale (in 0.5 star increments) and leave a
 * text comment for their rating. Ratings are saved based on user that submitted the rating as well
 * as the major of said user.
 *
 * Created on 2/27/16
 *
 * @author Snickers
 * @version 1.0
 */
public class MovieRatingActivity extends Activity {
    /**
     * TextView that allows the user to view/edit their text-based commentary on the movie.
     */
    private TextView userWordsRatingET;
    /**
     * 5-star rating system that allows a user to rate a movie from 0 to 5, in 0.5 increments.
     */
    private RatingBar movieRatingBar;
    /**
     * Reference to app's Firebase database, necessary to retrieve and persistently store the
     * ratings for a given movie.
     */
    private final Firebase ref = new Firebase("https://techflixandchill.firebaseio.com");

    private static final int ratingAdjuster = 4;

    private static final int minCommentAdjuster = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rating);
        Firebase.setAndroidContext(this);
        // allows for 4% increase in scoring algorithm
        final Bundle bundle = getIntent().getExtras();
        final String currentUser = bundle.getString("USERNAME");
        final String title = bundle.getString("movieTitle");
        final TextView ratingMovieTitleTV = (TextView) findViewById(R.id.ratingMovieTitleTV);
        userWordsRatingET = (EditText) findViewById(R.id.userWordsRatingET);
        movieRatingBar = (RatingBar) findViewById(R.id.movieRatingBar);
        final Button ratingSaveBtn = (Button) findViewById(R.id.ratingSaveBtn);
        final Button cancelRatingBtn = (Button) findViewById(R.id.cancelRatingBtn);

        //check to see if the current app user has already rated this movie
        //if they have, load that rating up into the rating screen
        final Firebase fetchRef = ref.child("ratingsByUser").child(currentUser).child(title);
        fetchRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Rating userRating = dataSnapshot.getValue(Rating.class);
                if (userRating != null) {
                    movieRatingBar.setRating(userRating.getNumericalRating());
                    userWordsRatingET.setText(userRating.getCommentRating());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do nothing
            }
        });


        ratingMovieTitleTV.setText(title);

        cancelRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(MovieRatingActivity.this, Home.class);
                final Bundle bundle2 = MovieRatingActivity.this.getIntent().getExtras();
                i.putExtras(bundle2);
                finish();
            }
        });

        ratingSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final float movieRating = movieRatingBar.getRating();
                final String major = MovieRatingActivity.this.getIntent().getStringExtra("MAJOR");
                final String username = MovieRatingActivity.this.getIntent().getStringExtra("USERNAME");
                final String title = MovieRatingActivity.this.getIntent().getStringExtra("movieTitle");
                final BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
                final String userWordsRating = userWordsRatingET.getText().toString();
                final Firebase userRateRef = ref.child("ratingsByUser");
                final Firebase majorRateRef = ref.child("ratingsByMajor");
                final Rating theRating = new Rating(movie, movieRating, userWordsRating, major, username);
                //set up Firebase references
                Firebase theUserRateRef = userRateRef.child(username);
                final Firebase theMajorRateRef = majorRateRef.child(major);
                theUserRateRef = theUserRateRef.child(title);
                theUserRateRef.setValue(theRating);

                final Firebase checkMajorRating = new Firebase("https://techflixandchill.firebaseio.com/ratingsByMajor/" + major + "/" + title);
                checkMajorRating.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        final Firebase addToMajorRef = theMajorRateRef.child(title);
                        if (snapshot.exists()) {
                            //deal with the program needing to adjust rating for major
                            final DataSnapshot previousRating = snapshot.child("numericalRating");
                            float prevRating = (float) previousRating.getValue();
                            float workingRating = calculateNewRating(prevRating, movieRating, userWordsRating);
                            final Rating updatedMajorRating = new Rating(movie, workingRating, userWordsRating, major, username);
                            addToMajorRef.setValue(updatedMajorRating);
                        } else {
                            addToMajorRef.setValue(theRating);

                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                    }
                });
                finish();
            }
        });
    }

    public static float calculateNewRating(float prevRating, float movieRating, String words) {
        if (words == null) {
            throw new IllegalArgumentException("Input comment cannot be null");
        }

        if (Math.abs(prevRating - movieRating) < 0.1 || Math.abs(movieRating) < 0.1) {
            return prevRating;
        } else {
            if (words.length() < minCommentAdjuster) {
                return ((prevRating * (minCommentAdjuster - words.length())
                        * ratingAdjuster) + movieRating) / (((minCommentAdjuster - words.length())
                        * ratingAdjuster) + 1);

            } else {
                return ((prevRating * ratingAdjuster) + movieRating) / (ratingAdjuster + 1);
            }
        }
    }
}
