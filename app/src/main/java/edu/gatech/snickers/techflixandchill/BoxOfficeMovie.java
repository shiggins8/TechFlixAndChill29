package edu.gatech.snickers.techflixandchill;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Essentially just the Movie class for the application. Has several fields for details or data
 * about a movie that can be pulled and requested from Rotten Tomatoes. Compatible with Firebase.
 *
 * Created on 2/26/16.
 *
 * @author Snickers
 * @version 1.1
 */
public class BoxOfficeMovie implements Serializable {
    private static final long serialVersionUID = -8959832007991513854L;
    private String title;
    private int year;
    private String synopsis;
    private String posterUrl;
    private int criticsScore;
    private String critics_Rating;
    private ArrayList<String> castList;

    private String largePosterUrl;
    private int audienceScore;
    private String audience_Rating;

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public String getCastList() {
        return TextUtils.join(", ", castList);
    }

    public String getCriticsRating() {
        return critics_Rating;
    }

    public String getAudienceRating() {
        return audience_Rating;
    }

    // Returns a BoxOfficeMovie given the expected JSON
    // BoxOfficeMovie.fromJson(movieJsonDictionary)
    // Stores the `title`, `year`, `synopsis`, `poster` and `criticsScore`
    public static BoxOfficeMovie fromJson(JSONObject jsonObject) {
        BoxOfficeMovie b = new BoxOfficeMovie();
        try {
            // Deserialize json into object fields
            b.title = jsonObject.getString("title");
            b.year = jsonObject.getInt("year");
            if (jsonObject.getString("synopsis").equals("")) {
                b.synopsis = "No synopsis available.";
            } else {
                b.synopsis = jsonObject.getString("synopsis");
            }
            b.posterUrl = jsonObject.getJSONObject("posters").getString("thumbnail");
            b.largePosterUrl = jsonObject.getJSONObject("posters").getString("detailed");
            //int audienceScoreValue = jsonObject.getJSONObject("ratings").getInt("audience_score");
            try {
                b.audienceScore = jsonObject.getJSONObject("ratings").getInt("audience_score");
            } catch (Exception e) {
                //do nothing
            }
            try {
                b.criticsScore = jsonObject.getJSONObject("ratings").getInt("critics_score");
            } catch (Exception e) {
                //do nothing
            }
            try {
                b.audience_Rating = jsonObject.getJSONObject("ratings").getString("critics_rating");
            } catch (Exception e) {
                //do nothing
            }
            try {
                b.audience_Rating = jsonObject.getJSONObject("ratings").getString("audience_rating");
            } catch (Exception e) {
                //do nothing
            }

            // Construct simple array of cast names
            b.castList = new ArrayList<String>();
            JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast");
            for (int i = 0; i < abridgedCast.length(); i++) {
                b.castList.add(abridgedCast.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }

    // Decodes array of box office movie json results into movie model objects
    // BoxOfficeMovie.fromJson(jsonArrayOfMovies)
    public static ArrayList<BoxOfficeMovie> fromJson(JSONArray jsonArray) {
        ArrayList<BoxOfficeMovie> movies = new ArrayList<BoxOfficeMovie>(jsonArray.length());
        // Process each result in json array, decode and convert to movie
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            BoxOfficeMovie movie = BoxOfficeMovie.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }

        return movies;
    }

    public String getLargePosterUrl() {
        return largePosterUrl;
    }

    public int getAudienceScore() {
        return audienceScore;
    }
}
