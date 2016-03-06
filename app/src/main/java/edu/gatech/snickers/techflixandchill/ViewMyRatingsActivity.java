package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;

public class ViewMyRatingsActivity extends Activity {
    private ListView lvRatings;
    private RatingsAdapter adapterRatings;
    final Firebase ref = new Firebase("https://techflixandchill.firebaseio.com");
    Button returnFromRatingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_ratings);
        returnFromRatingsBtn = (Button) findViewById(R.id.returnFromRatingsBtn);
        lvRatings = (ListView) findViewById(R.id.lvRatings);
        ArrayList<Rating> aRatings = new ArrayList<Rating>();
        adapterRatings = new RatingsAdapter(this, aRatings);
        lvRatings.setAdapter(adapterRatings);
        fetchUserRatings();
        setupMovieSelectedListener();

        returnFromRatingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Use Firebase to gather all of the ratings made by a particular user
    // Converts them into an array of rating objects and adds them to the adapter
    private void fetchUserRatings() {
        Bundle bundle = ViewMyRatingsActivity.this.getIntent().getExtras();
        String username = bundle.getString("USERNAME");
        try {
            Firebase userRatings = ref.child("ratingsByUser").child(username);
            userRatings.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<Rating> theRatings = new ArrayList<Rating>();
                    for (DataSnapshot child: dataSnapshot.getChildren()) {
                        Rating tempRating = child.getValue(Rating.class);
                        theRatings.add(tempRating);
                    }
                    for (Rating rating: theRatings) {
                        adapterRatings.add(rating);
                    }
                    adapterRatings.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception e) {
            //TODO fix this
            System.out.println("oops");
        }


    }

    public void setupMovieSelectedListener() {
        lvRatings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                System.out.println(adapterRatings.getItem(position).getMovie().getSynopsis());//right now, do nothing
                // Launch the detail view passing movie as an extra
//                Intent i = new Intent(BoxOfficeActivity.this, BoxOfficeDetailActivity.class);
//                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
//                finish();
//                startActivity(i);
            }
        });
    }

}