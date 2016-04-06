package edu.gatech.snickers.techflixandchill;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * JUnit test for the calculateNewRating method in the MovieRatingActivity class
 *
 * Created by nirajsuresh on 4/5/16.
 * @version 1.0
 */
public class TestMovieRatingCalculation {

    /**
     * Test null comment input into method (should throw IllegalArgumentException)
     * @throws Exception
     */
    @Test
    public void testNullComment() throws Exception {
        try {
            MovieRatingActivity.calculateNewRating(0, 0, null);
        } catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "Input comment cannot be null");
        }
    }

    /**
     * Tests user rating == previous rating (shouldn't change the rating)
     * @throws Exception
     */
    @Test
    public void testSameRating() throws Exception {
        assertEquals(MovieRatingActivity.calculateNewRating(5, 5, "test"), 5, 0.1);
    }

    /**
     * Tests rating calculation when user comment is of sufficient length
     * @throws Exception
     */
    @Test
    public void testWordLengthNormal() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(5, 3, "This movie could have been better"), 4.6, 0.1);
    }

    /**
     * Tests rating calculation when user comment is of 0 length
     * @throws Exception
     */
    @Test
    public void testWordLengthZero() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(5, 3, ""), 4.95, 0.1);
    }

    /**
     * Tests rating calculation when user comment is of threshold length (9)
     * @throws Exception
     */
    @Test
    public void testWordLengthNine() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(5, 3, "This suck"), 4.6, 0.1);
    }

    /**
     * Tests rating calculation when user enters 0 for rating (should not change rating)
     * @throws Exception
     */
    @Test
    public void testZeroRating() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(4, 0, ""), 4, 0.1);
    }

}