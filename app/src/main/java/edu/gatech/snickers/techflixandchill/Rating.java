package edu.gatech.snickers.techflixandchill;

/**
 * Created by Scottie on 2/27/16.
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
