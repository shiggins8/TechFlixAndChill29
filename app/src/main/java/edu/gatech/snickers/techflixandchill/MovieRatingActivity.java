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
    TextView ratingMovieTitleTV, userWordsRatingET;
    RatingBar movieRatingBar;
    Button ratingSaveBtn, cancelRatingBtn;
    final Firebase ref = new Firebase("https://techflixandchill.firebaseio.com");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rating);
        Firebase.setAndroidContext(this);
        Bundle bundle = getIntent().getExtras();
        String currentUser = bundle.getString("USERNAME");
        String title = bundle.getString("movieTitle");
        ratingMovieTitleTV = (TextView) findViewById(R.id.ratingMovieTitleTV);
        userWordsRatingET = (EditText) findViewById(R.id.userWordsRatingET);
        movieRatingBar = (RatingBar) findViewById(R.id.movieRatingBar);
        ratingSaveBtn = (Button) findViewById(R.id.ratingSaveBtn);
        cancelRatingBtn = (Button) findViewById(R.id.cancelRatingBtn);

        //check to see if the current app user has already rated this movie
        //if they have, load that rating up into the rating screen
        Firebase fetchRef = ref.child("ratingsByUser").child(currentUser).child(title);
        fetchRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Rating userRating = dataSnapshot.getValue(Rating.class);
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
                Intent i = new Intent(MovieRatingActivity.this, Home.class);
                Bundle bundle2 = MovieRatingActivity.this.getIntent().getExtras();
                i.putExtras(bundle2);
                finish();
            }
        });

        ratingSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float movieRating = movieRatingBar.getRating();
                final String major = MovieRatingActivity.this.getIntent().getStringExtra("MAJOR");
                final String username = MovieRatingActivity.this.getIntent().getStringExtra("USERNAME");
                final String title = MovieRatingActivity.this.getIntent().getStringExtra("movieTitle");
                BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
                String userWordsRating = userWordsRatingET.getText().toString();
                Firebase userRateRef = ref.child("ratingsByUser");
                Firebase majorRateRef = ref.child("ratingsByMajor");
                Rating theRating = new Rating(movie, movieRating, userWordsRating, major, username);
                Firebase theUserRateRef = userRateRef.child(username);
                Firebase theMajorRateRef = majorRateRef.child(major);
                theUserRateRef = theUserRateRef.child(title);
                theUserRateRef.setValue(theRating);
                theMajorRateRef = theMajorRateRef.child(title);
                theMajorRateRef.setValue(theRating);
//Note: i had to go through some debugging to get the rating to save and close and then go home fine
                //leave this code here for now, just in case
//                Bundle returnBundle = MovieRatingActivity.this.getIntent().getExtras();
//                Intent i = new Intent(MovieRatingActivity.this, Home.class);
//                i.putExtras(returnBundle);
//                startActivity(i);
                finish();
            }
        });
    }

}
