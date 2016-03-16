package edu.gatech.snickers.techflixandchill;

/**
 * Basic class for creating movie rating objects within the app. Firebase compatible. Allows for
 * a numerical rating to be stored, as well as a text-based comment or review from the user.
 * Stores the username as well as user's major for identification purposes and storing within the
 * databases.
 *
 * Created on 2/27/16.
 *
 * @author Snickers
 * @version 1.0
 */
public class Rating {
    private BoxOfficeMovie movie;

    private float numericalRating;
    private String commentRating;
    private String majorOfRater;
    private String usernameOfRater;

    public BoxOfficeMovie getMovie() {
        return movie;
    }

    public void setMovie(BoxOfficeMovie pMovie) {
        this.movie = pMovie;
    }

    public float getNumericalRating() {
        return numericalRating;
    }

    public void setNumericalRating(float pNumericalRating) {
        this.numericalRating = pNumericalRating;
    }

    public String getCommentRating() {
        return commentRating;
    }

    public void setCommentRating(String pCommentRating) {
        this.commentRating = pCommentRating;
    }

    public String getMajorOfRater() {
        return majorOfRater;
    }

    public void setMajorOfRater(String pMajorOfRater) {
        this.majorOfRater = pMajorOfRater;
    }

    public String getUsernameOfRater() {
        return usernameOfRater;
    }

    public void setUsernameOfRater(String pUsernameOfRater) {
        this.usernameOfRater = pUsernameOfRater;
    }

    public Rating() {
        //empty constructor for Firebase purposes
    }

    public Rating(BoxOfficeMovie pMovie, float pNumericalRating, String pCommentRating, String pMajorOfRater,
                  String pUsernameOfRater) {
        this.movie = pMovie;
        this.numericalRating = pNumericalRating;
        this.commentRating = pCommentRating;
        this.majorOfRater = pMajorOfRater;
        this.usernameOfRater = pUsernameOfRater;
    }


}
