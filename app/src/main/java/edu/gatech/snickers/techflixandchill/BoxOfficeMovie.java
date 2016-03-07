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
    private String criticsRating;
    private String castList;
    private String largePosterUrl;
    private int audienceScore;
    private String audienceRating;
    private int movieID;

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public BoxOfficeMovie() {
        //empty constructor for firebase
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public void setCriticsScore(int criticsScore) {
        this.criticsScore = criticsScore;
    }

    public String getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(String criticsRating) {
        this.criticsRating = criticsRating;
    }

    public String getCastList() {
        return castList;
    }

    public void setCastList(String castList) {
        this.castList = castList;
    }

    public String getLargePosterUrl() {
        return largePosterUrl;
    }

    public void setLargePosterUrl(String largePosterUrl) {
        this.largePosterUrl = largePosterUrl;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String getAudienceRating() {
        return audienceRating;
    }

    public void setAudienceRating(String audienceRating) {
        this.audienceRating = audienceRating;
    }

    // Returns a BoxOfficeMovie given the expected JSON
    // BoxOfficeMovie.fromJson(movieJsonDictionary)
    // Stores the `title`, `year`, `synopsis`, `poster` and `criticsScore`
    public static BoxOfficeMovie fromJson(JSONObject jsonObject) {
        BoxOfficeMovie b = new BoxOfficeMovie();
        try {
            // Deserialize json into object fields
            b.movieID= jsonObject.getInt("id");
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
                b.criticsRating = jsonObject.getJSONObject("ratings").getString("critics_rating");
            } catch (Exception e) {
                //do nothing
            }
            try {
                b.audienceRating = jsonObject.getJSONObject("ratings").getString("audience_rating");
            } catch (Exception e) {
                //do nothing
            }

            // Construct simple array of cast names
            ArrayList<Object> theCastList = new ArrayList<Object>();
            JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast");
            for (int i = 0; i < abridgedCast.length(); i++) {
                theCastList.add(abridgedCast.getJSONObject(i).getString("name"));
            }
            String tempList = theCastList.toString();
            int n = tempList.length();
            b.castList=tempList.substring(1,n-1);
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
}
