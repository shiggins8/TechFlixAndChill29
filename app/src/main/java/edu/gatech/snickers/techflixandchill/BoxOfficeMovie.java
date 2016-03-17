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
    /**
     * Serializable ID that allows the movies to be serialized and deserialized properly.
     */
    private static final long serialVersionUID = -8959832007991513854L;
    /**
     * Title of the movie;
     */
    private String title;
    /**
     * Year that the movie was released.
     */
    private int year;
    /**
     * Text synopsis of the movie.
     */
    private String synopsis;
    /**
     * Url to the poster image.
     */
    private String posterUrl;
    /**
     * Numerical critics score.
     */
    private int criticsScore;
    /**
     * Text critics score.
     */
    private String criticsRating;
    /**
     * List of top cast members in the movie.
     */
    private String castList;
    /**
     * URL to the larger version of the movie poster.
     */
    private String largePosterUrl;
    /**
     * Numerical audience score.
     */
    private int audienceScore;
    /**
     * Text version of the audience rating/score.
     */
    private String audienceRating;
    /**
     * Rotten Tomatoes numerical movie ID. Unique to Rotten Tomatoes system.
     */
    private int movieID;

    /**
     * Getter to obtain numerical movie ID.
     *
     * @return numerical Rotten Tomatoes ID of a movie
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * Set the numerical ID of a  movie.
     *
     * @param pMovieID the int number ID of a movie in the Rotten Tomatoes system
     */
    public void setMovieID(int pMovieID) {
        this.movieID = pMovieID;
    }

    /**
     * Empty base constructor utilized with the Firebase system to populate movies within the app.
     */
    public BoxOfficeMovie() {
        //empty constructor for Firebase
    }

    /**
     * Getter method to obtain title of movie.
     *
     * @return the String containing the title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter to set the title of the movie.
     *
     * @param pTitle String title of the movie
     */
    public void setTitle(String pTitle) {
        this.title = pTitle;
    }

    /**
     * Getter to obtain year that the movie was released.
     *
     * @return int value representing year movie was released
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter to set the year the movie was released.
     *
     * @param pYear int year that the movie was released
     */
    public void setYear(int pYear) {
        this.year = pYear;
    }

    /**
     * Getter to get the text synopsis of the movie.
     *
     * @return String version of the synopsis of the movie
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Setter to set the synopsis of a movie.
     *
     * @param pSynopsis String synopsis of the movie
     */
    public void setSynopsis(String pSynopsis) {
        this.synopsis = pSynopsis;
    }

    /**
     * Getter to get the String url of the location of the movie poster image.
     *
     * @return Url where the system can find the poster image for a movie
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * Setter to set the url location of the poster image.
     *
     * @param pPosterUrl url of the poster image
     */
    public void setPosterUrl(String pPosterUrl) {
        this.posterUrl = pPosterUrl;
    }

    /**
     * Getter to get the numerical critics score of a movie. Will be converted to percentage
     * within the application.
     *
     * @return numerical int score of a movie
     */
    public int getCriticsScore() {
        return criticsScore;
    }

    /**
     * Set the numerical critics score of the movie.
     *
     * @param pCriticsScore numerical critics score
     */
    public void setCriticsScore(int pCriticsScore) {
        this.criticsScore = pCriticsScore;
    }

    /**
     * Get the String critics rating of a movie.
     *
     * @return string version of text-based critics rating
     */
    public String getCriticsRating() {
        return criticsRating;
    }

    /**
     * Set the text-based critics rating.
     *
     * @param pCriticsRating string critics rating to be assigned to the movie
     */
    public void setCriticsRating(String pCriticsRating) {
        this.criticsRating = pCriticsRating;
    }

    /**
     * Get the String version of the cast list of the movie.
     *
     * @return String of cast members
     */
    public String getCastList() {
        return castList;
    }

    /**
     * Set the cast list of the movie.
     *
     * @param pCastList string version of cast members, in a list
     */
    public void setCastList(String pCastList) {
        this.castList = pCastList;
    }

    /**
     * Get the url containing the larger, more high-def movie url.
     *
     * @return string containing url of larger poster image
     */
    public String getLargePosterUrl() {
        return largePosterUrl;
    }

    /**
     * Set the larger poster image url.
     *
     * @param pLargePosterUrl string of the larger poster url
     */
    public void setLargePosterUrl(String pLargePosterUrl) {
        this.largePosterUrl = pLargePosterUrl;
    }

    /**
     * Get the numerical audience score, which will be turned into a percent within the app.
     *
     * @return numerical version of audience score
     */
    public int getAudienceScore() {
        return audienceScore;
    }

    /**
     * Set the audience score to a numerical value.
     *
     * @param pAudienceScore int of the audience score
     */
    public void setAudienceScore(int pAudienceScore) {
        this.audienceScore = pAudienceScore;
    }

    /**
     * Get the text-based audience rating of the movie.
     *
     * @return String containing the text-based rating of a movie
     */
    public String getAudienceRating() {
        return audienceRating;
    }

    /**
     * Set the text-based audience rating.
     *
     * @param pAudienceRating String version of text-based rating
     */
    public void setAudienceRating(String pAudienceRating) {
        this.audienceRating = pAudienceRating;
    }

    /**
     * Returns a BoxOfficeMovie given the expected JSON BoxOfficeMovie.fromJson(movieJsonDictionary)
     * Stores the `title`, `year`, `synopsis`, `poster` and `criticsScore`.
     *
     * @param jsonObject JSON object obtained from Rotten Tomatoes API call
     * @return a BoxOfficeMovie object populated with data from the JSONObject
     */
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

    /**
     * Decodes array of box office movie json results into movie model objects. BoxOfficeMovie.
     * fromJson(jsonArrayOfMovies).
     *
     * @param jsonArray A JSONArray of movies, obtained from Rotten Tomatoes API call
     * @return a java List of BoxOfficeMovies obtained from the JSONArray
     */
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
