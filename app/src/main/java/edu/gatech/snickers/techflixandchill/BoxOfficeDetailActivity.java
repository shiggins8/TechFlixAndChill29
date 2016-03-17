package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//3rd party libraries
import com.squareup.picasso.Picasso;

/**
 * Detailed view of a movie, once it has been selected from a ListView on a movie results page.
 * Displays more information about the movie than the simplified view, then gives users an option to
 * rate the movie
 *
 * Created on 2/27/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class BoxOfficeDetailActivity extends Activity {
    /**
     * ImageView that will contain the movie poster image.
     */
    private ImageView ivPosterImage;
    /**
     * TextViews that will be populated with the title, synopsis, cast, and scores for a given
     * movie.
     */
    private TextView tvTitle, tvSynopsis, tvCast, tvAudienceScore, tvCriticsScore;
    /**
     * Button with functionality to take the user to a movie rating activity (screen).
     */
    private Button rateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_detail);
        // Fetch views
        rateBtn = (Button) findViewById(R.id.rateBtn);
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvAudienceScore =  (TextView) findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);
        // Use the movie to populate the data into our views
        final BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(BoxOfficeDetailActivity.this, MovieRatingActivity.class);
                final Bundle bundle2 = BoxOfficeDetailActivity.this.getIntent().getExtras();
                i.putExtras(bundle2);
                //i.putExtra("movieName", movie.getTitle());
                //i.putExtra("MAJOR", major);
                //i.putExtra("USERNAME", username);
                finish();
                startActivity(i);
            }
        });
    }

    /**
     * Replicate code for the android lifestyle cycling
     * @param savedInstanceState bundles saved state
     */
    public void onNewInstance(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_office_detail);
        // Fetch views
        rateBtn = (Button) findViewById(R.id.rateBtn);
        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvAudienceScore =  (TextView) findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);
        // Use the movie to populate the data into our views
        final BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent i = new Intent(BoxOfficeDetailActivity.this, MovieRatingActivity.class);
                final Bundle bundle2 = BoxOfficeDetailActivity.this.getIntent().getExtras();
                i.putExtras(bundle2);
                //i.putExtra("movieName", movie.getTitle());
                //i.putExtra("MAJOR", major);
                //i.putExtra("USERNAME", username);
                finish();
                startActivity(i);
            }
        });
    }

    /**
     * Populate the data for the given movie into the app UI.
     *
     * @param movie movie object that the app will display with detailed view
     */
    public void loadMovie(BoxOfficeMovie movie) {
        // Populate data
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText(Html.fromHtml("<b>Critics Score:</b> " + movie.getCriticsScore() + "%"));
        tvAudienceScore.setText(Html.fromHtml("<b>Audience Score:</b> " + movie.getAudienceScore() + "%"));
        tvCast.setText(movie.getCastList());
        tvSynopsis.setText(Html.fromHtml("<b>Synopsis:</b> " + movie.getSynopsis()));
        // R.drawable.large_movie_poster from
        // http://content8.flixster.com/movie/11/15/86/11158674_pro.jpg -->
        Picasso.with(this).load(movie.getLargePosterUrl()).placeholder(R.drawable.default_large_movie_poster).
                into(ivPosterImage);
        //Picasso.with(this).load("http://content8.flixster.com/movie/11/15/86/11158674_pro.jpg").placeholder(R.drawable.default_large_movie_poster).
        //        into(ivPosterImage);
    }

}