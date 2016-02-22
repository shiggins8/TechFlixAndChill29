package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.snapshot.Index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Arrays;

/**
 * Created on 2/21/16.
 *
 * This page controls the "search for a movie" functionality of the application. Allows the users to
 * enter a title or keyword of a movie, and then returns a list of movies matching those terms.
 * Utilizes the Rotten Tomatoes API for this functionality.
 *
 * @author Snickers
 * @version 1.0
 */
public class SearchMovies extends Activity {

    Button searchReturnToHome, searchButton;
    TextView rentalText;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        final Bundle bundle;
        bundle = getIntent().getExtras();
        searchButton = (Button)findViewById(R.id.searchButton);
        rentalText = (TextView) findViewById(R.id.rentalText);
        search = (EditText) findViewById(R.id.search_EDT);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movieName = search.getText().toString();
                //convert any blank spaces in the movie title entered by user into + signs, which
                //is what Rotten Tomatoes uses to search
                char[] noSpaceName = movieName.toCharArray();
                for (int i = 0; i < noSpaceName.length; i++) {
                    if (noSpaceName[i] == 32) {
                        noSpaceName[i] = 43; //+ sign in ASCII
                    }
                }
                String finalMovieName = new String(noSpaceName);
                new JSONTask().execute("http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=" +
                         finalMovieName + "&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5");
            }
        });

        searchReturnToHome = (Button) findViewById(R.id.searchReturnToHome);

        searchReturnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(SearchMovies.this, Home.class);
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                StringBuffer finalBuffer = new StringBuffer();
                String finalJSON = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJSON);
                JSONArray parentArray = parentObject.getJSONArray("movies");
                for (int i = 0; i < 10; i++) {
                    try {
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        String movieName = finalObject.getString("title");
                        int year = finalObject.getInt("year");
                        String newLine = movieName + " - " + year + "\n";
                        finalBuffer.append(newLine);
                    } catch (Exception exception) {
                        //ignore
                    }
                }
                if (finalBuffer.length() == 0) {
                    return "No Movies Match This Description";
                }
                return finalBuffer.toString();

            } catch (IOException | JSONException exception) {
                exception.printStackTrace();
            } finally {
                try {
                    assert connection != null;
                    connection.disconnect();
                    assert reader != null;
                    reader.close();
                } catch (IOException | NullPointerException exception) {
                    exception.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            rentalText.setText(s);
        }
    }

}
