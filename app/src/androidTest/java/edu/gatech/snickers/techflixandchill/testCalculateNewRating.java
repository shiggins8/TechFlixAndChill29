package edu.gatech.snickers.techflixandchill;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by nirajsuresh on 4/5/16.
 */
public class testCalculateNewRating {

    @Test
    public void testNullComment() throws Exception {
        try {
            MovieRatingActivity.calculateNewRating(0, 0, "test");
        } catch (IllegalArgumentException e){
            assertEquals(e.getMessage(), "Input rating cannot be null");
        }
        assertEquals(true, false);
    }

    @Test
    public void testSameRating() throws Exception {
        assertEquals(MovieRatingActivity.calculateNewRating(5, 5, "test"), 5, 0.1);
    }

    @Test
    public void testWordLengthNormal() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(3, 5, "This movie could have been better"), 4.6, 0.1);
    }

    @Test
    public void testWordLengthZero() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(3, 5, ""), 4.95, 0.1);
    }

    @Test
    public void testWordLengthNine() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(3, 5, ""), 4.6, 0.1);
    }

    @Test
    public void testZeroRating() throws Exception {
        assertEquals(MovieRatingActivity
                .calculateNewRating(0, 4, ""), 4, 0.1);
    }

}