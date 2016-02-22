package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewOnDvd extends Activity {

    Button newDVDReturnHome;
    TextView rentalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_on_dvd);

        rentalText = (TextView) findViewById(R.id.rentalText);
        new JSONTask().execute("http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/" +
                "new_releases.json?page_limit=10&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5");

        final Bundle bundle;
        bundle = getIntent().getExtras();

        newDVDReturnHome = (Button) findViewById(R.id.newDVDReturnToHome);

        newDVDReturnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //return to the MainActivity
                Intent i = new Intent(NewOnDvd.this, Home.class);
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
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    String movieName = finalObject.getString("title");
                    int year = finalObject.getInt("year");
                    String newLine = movieName + " - " + year + "\n";
                    finalBuffer.append(newLine);
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
