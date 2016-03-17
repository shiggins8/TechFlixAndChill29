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

    /**
     * Get the BoxOfficeMovie object the rating is about.
     *
     * @return the BoxOfficeMovie object of the rating.
     */
    public BoxOfficeMovie getMovie() {
        return movie;
    }

    /**
     * Set the BoxOfficeMovie object of the rating.
     *
     * @param pMovie BoxOfficeMovie that the rating pertains to
     */
    public void setMovie(BoxOfficeMovie pMovie) {
        this.movie = pMovie;
    }

    /**
     * Get the numerical rating of the movie, as a float.
     *
     * @return float numerical rating of the movie
     */
    public float getNumericalRating() {
        return numericalRating;
    }

    /**
     * Set numerical rating of a movie.
     *
     * @param pNumericalRating float numerical rating
     */
    public void setNumericalRating(float pNumericalRating) {
        this.numericalRating = pNumericalRating;
    }

    /**
     * Get the text-based comment rating of a movie.
     *
     * @return String containing comment rating of a movie
     */
    public String getCommentRating() {
        return commentRating;
    }

    /**
     * Set the comment rating of a particular movie.
     *
     * @param pCommentRating the String representing the comment rating
     */
    public void setCommentRating(String pCommentRating) {
        this.commentRating = pCommentRating;
    }

    /**
     * Get the major of the user that made this particular rating.
     *
     * @return major of the user that made the rating
     */
    public String getMajorOfRater() {
        return majorOfRater;
    }

    /**
     * Set the major of the user that created this rating.
     *
     * @param pMajorOfRater String representing major of the user
     */
    public void setMajorOfRater(String pMajorOfRater) {
        this.majorOfRater = pMajorOfRater;
    }

    /**
     * Get the username of the user that created this rating.
     *
     * @return String representing the username of the user that made this rating
     */
    public String getUsernameOfRater() {
        return usernameOfRater;
    }

    /**
     * Set the username of the user that made this rating.
     *
     * @param pUsernameOfRater String representing the username of the user that made the rating
     */
    public void setUsernameOfRater(String pUsernameOfRater) {
        this.usernameOfRater = pUsernameOfRater;
    }
}
