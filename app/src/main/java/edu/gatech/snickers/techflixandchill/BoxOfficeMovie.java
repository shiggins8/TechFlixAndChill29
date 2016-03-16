package edu.gatech.snickers.techflixandchill;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    public void setMovieID(int pMovieID) {
        this.movieID = pMovieID;
    }

    public BoxOfficeMovie() {
        //empty constructor for firebase
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String pTitle) {
        this.title = pTitle;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int pYear) {
        this.year = pYear;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String pSynopsis) {
        this.synopsis = pSynopsis;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String pPosterUrl) {
        this.posterUrl = pPosterUrl;
    }

    public int getCriticsScore() {
        return criticsScore;
    }

    public void setCriticsScore(int pCriticsScore) {
        this.criticsScore = pCriticsScore;
    }

    public String getCriticsRating() {
        return criticsRating;
    }

    public void setCriticsRating(String pCriticsRating) {
        this.criticsRating = pCriticsRating;
    }

    public String getCastList() {
        return castList;
    }

    public void setCastList(String pCastList) {
        this.castList = pCastList;
    }

    public String getLargePosterUrl() {
        return largePosterUrl;
    }

    public void setLargePosterUrl(String pLargePosterUrl) {
        this.largePosterUrl = pLargePosterUrl;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int pAudienceScore) {
        this.audienceScore = pAudienceScore;
    }

    public String getAudienceRating() {
        return audienceRating;
    }

    public void setAudienceRating(String pAudienceRating) {
        this.audienceRating = pAudienceRating;
    }

    // Returns a BoxOfficeMovie given the expected JSON
    // BoxOfficeMovie.fromJson(movieJsonDictionary)
    // Stores the `title`, `year`, `synopsis`, `poster` and `criticsScore`
    public static BoxOfficeMovie fromJson(JSONObject jsonObject) {
        final String ratingVariable = "ratings";
        final String json = "JSON";
        final String nullstring = "";

        final BoxOfficeMovie b = new BoxOfficeMovie();
        try {
            // Deserialize json into object fields
            b.movieID= jsonObject.getInt("id");
            b.title = jsonObject.getString("title");
            b.year = jsonObject.getInt("year");
            if (jsonObject.getString("synopsis").equals(nullstring)) {
                b.synopsis = "No synopsis available.";
            } else {
                b.synopsis = jsonObject.getString("synopsis");
            }
            b.posterUrl = jsonObject.getJSONObject("posters").getString("thumbnail");
            b.largePosterUrl = jsonObject.getJSONObject("posters").getString("detailed");
            //int audienceScoreValue = jsonObject.getJSONObject("ratings").getInt("audience_score");
            try {
                b.audienceScore = jsonObject.getJSONObject(ratingVariable).getInt("audience_score");
            } catch (JSONException e) {
                Log.d(json, "fromJson: audience_score");
            }
            try {
                b.criticsScore = jsonObject.getJSONObject(ratingVariable).getInt("critics_score");
            } catch (JSONException e) {
                Log.d(json, "fromJson: critics_score");
            }
            try {
                b.criticsRating = jsonObject.getJSONObject(ratingVariable).getString("critics_rating");
            } catch (JSONException e) {
                Log.d(json, "fromJson: critics_rating");
            }
            try {
                b.audienceRating = jsonObject.getJSONObject(ratingVariable).getString("audience_rating");
            } catch (JSONException e) {
                Log.d(json, "fromJson: audience_rating");
            }

            // Construct simple array of cast names
            final ArrayList<Object> theCastList = new ArrayList<Object>();
            final JSONArray abridgedCast = jsonObject.getJSONArray("abridged_cast");
            for (int i = 0; i < abridgedCast.length(); i++) {
                theCastList.add(abridgedCast.getJSONObject(i).getString("name"));
            }
            final String tempList = theCastList.toString();
            final int n = tempList.length();
            b.castList=tempList.substring(1,n-1);
        } catch (JSONException e) {
            Log.d("JSON", "fromJson: failed to deserialize the JSONobject into a movie");
            return null;
        }
        // Return new object
        return b;
    }

    // Decodes array of box office movie json results into movie model objects
    // BoxOfficeMovie.fromJson(jsonArrayOfMovies)
    public static List<BoxOfficeMovie> fromJson(JSONArray jsonArray) {
        final List<BoxOfficeMovie> movies = new ArrayList<BoxOfficeMovie>(jsonArray.length());
        // Process each result in json array, decode and convert to movie
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject moviesJson = null;
            try {
                moviesJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.d("JSON", "fromJson: couldn't process the JSON array into movies");
                continue;
            }

            final BoxOfficeMovie movie = BoxOfficeMovie.fromJson(moviesJson);
            if (movie != null) {
                movies.add(movie);
            }
        }
        return movies;
    }
}
