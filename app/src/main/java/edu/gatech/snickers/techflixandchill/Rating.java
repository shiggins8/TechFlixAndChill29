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
    private float numericalRating;
    private String commentRating;
    private String majorOfRater;

    public Rating() {
        //empty constructor for Firebase purposes
    }

    public Rating(float numericalRating, String commentRating, String majorOfRater,
                  String usernameOfRater) {
        this.numericalRating = numericalRating;
        this.commentRating = commentRating;
        this.majorOfRater = majorOfRater;
        this.usernameOfRater = usernameOfRater;
    }

    public void setUsernameOfRater(String usernameOfRater) {
        this.usernameOfRater = usernameOfRater;
    }

    public void setNumericalRating(float numericalRating) {
        this.numericalRating = numericalRating;
    }

    public void setCommentRating(String commentRating) {
        this.commentRating = commentRating;
    }

    public void setMajorOfRater(String majorOfRater) {
        this.majorOfRater = majorOfRater;
    }

    private String usernameOfRater;

    public String getUsernameOfRater() {
        return usernameOfRater;
    }

    public float getNumericalRating() {
        return numericalRating;
    }

    public String getCommentRating() {
        return commentRating;
    }

    public String getMajorOfRater() {
        return majorOfRater;
    }
}
