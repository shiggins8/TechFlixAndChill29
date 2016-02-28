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

public class BoxOfficeDetailActivity extends Activity {
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    Button rateBtn;

    //TODO fix the xml file to set the correct dimensions for the poster, go back and get the movie url

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
        System.out.println("from on create in detail activity movie title = " + (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY));
        BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        System.out.println("from on create in detail activity movie object: " + movie.getTitle());
        loadMovie(movie);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoxOfficeDetailActivity.this, MovieRatingActivity.class);
                Bundle bundle2 = BoxOfficeDetailActivity.this.getIntent().getExtras();
                i.putExtras(bundle2);
                //i.putExtra("movieName", movie.getTitle());
                //i.putExtra("MAJOR", major);
                //i.putExtra("USERNAME", username);
                finish();
                startActivity(i);
            }
        });
    }

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
        System.out.println("from on create in detail activity movie title = " + (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY));
        BoxOfficeMovie movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        System.out.println("from on create in detail activity movie object: " + movie.getTitle());
        loadMovie(movie);

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BoxOfficeDetailActivity.this, MovieRatingActivity.class);
                Bundle bundle2 = BoxOfficeDetailActivity.this.getIntent().getExtras();
                i.putExtras(bundle2);
                //i.putExtra("movieName", movie.getTitle());
                //i.putExtra("MAJOR", major);
                //i.putExtra("USERNAME", username);
                finish();
                startActivity(i);
            }
        });
    }

    // Populate the data for the movie
    public void loadMovie(BoxOfficeMovie movie) {
        // Populate data
        tvTitle.setText(movie.getTitle());
        //TODO is this where the error is - YES YES YES
        System.out.println("detail activity says title is: " + movie.getTitle());
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