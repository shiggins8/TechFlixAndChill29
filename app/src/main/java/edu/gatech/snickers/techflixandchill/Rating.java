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
    /**
     * The actual movie object for the movie being rated, used to rapidly obtain all pertinent movie
     * info.
     */
    private BoxOfficeMovie movie;
    /**
     * Numerical rating of the movie.
     */
    private float numericalRating;
    /**
     * Text-based rating or review of the movie.
     */
    private String commentRating;
    /**
     * Georgia Tech major of the user rating this particular movie.
     */
    private String majorOfRater;
    /**
     * Username of the user rating this movie.
     */
    private String usernameOfRater;

    /**
     * Constructor to build a Rating object.
     *
     * @param pMovie BoxOfficeMovie object of the movie being rated
     * @param pNumericalRating numerical rating, taken from RatingBar
     * @param pCommentRating text-based rating
     * @param pMajorOfRater major of the user rating the movie
     * @param pUsernameOfRater username of the user rating the movie
     */
    public Rating(BoxOfficeMovie pMovie, float pNumericalRating, String pCommentRating, String pMajorOfRater,
                  String pUsernameOfRater) {
        this.movie = pMovie;
        this.numericalRating = pNumericalRating;
        this.commentRating = pCommentRating;
        this.majorOfRater = pMajorOfRater;
        this.usernameOfRater = pUsernameOfRater;
    }

    /**
     * Empty base constructor used by Firebase to create objects of type Rating.
     */
    public Rating() {
        //empty constructor for Firebase purposes
    }

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
}
