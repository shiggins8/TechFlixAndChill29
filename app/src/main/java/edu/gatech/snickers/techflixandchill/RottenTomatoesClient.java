package edu.gatech.snickers.techflixandchill;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Basic client adapter file we created to hold all of the url's for our Rotten Tomatoes calls.
 * Key is stored as a final String variable, and the base api url is stored, whereupon it can be
 * appended depending on the specific search call that a user makes.
 *
 * Created on 2/26/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class RottenTomatoesClient {
    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private static final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private AsyncHttpClient client;

    public RottenTomatoesClient() {
        this.client = new AsyncHttpClient();
    }

    // http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=<key>
    public void getBoxOfficeMovies(JsonHttpResponseHandler handler) {
        final String url = getApiUrl("lists/movies/box_office.json");
        final RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    public void getNewOnDvdMovies(JsonHttpResponseHandler handler) {
        final String url = getApiUrl("lists/dvds/new_releases.json");
        final RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    public void getNewInTheaterMovies(JsonHttpResponseHandler handler) {
        final String url = getApiUrl("lists/movies/in_theaters.json");
        final RequestParams params = new RequestParams("apikey", API_KEY);
        client.get(url, params, handler);
    }

    public void getMovieSearch(String movieTitle, JsonHttpResponseHandler handler) {
        String url = getApiUrl("movies.json");
        url = url + "?q=" + movieTitle + "&page_limit=10&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
        final RequestParams params = new RequestParams();
        client.get(url, params, handler);
    }

    public void getRecommendations(int movieId, JsonHttpResponseHandler handler) {
        final String url = getApiUrl("movies/" + movieId + "/similar.json?limit=5&apikey=" + API_KEY);
        final RequestParams params = new RequestParams();
        client.get(url, params, handler);

        //http://api.rottentomatoes.com/api/public/v1.0/movies/10143/similar.json?limit=5&apikey=yedukp76ffytfuy24zsqk7f5
        //?limit=5&apikey=yedukp76ffytfuy24zsqk7f5


    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}