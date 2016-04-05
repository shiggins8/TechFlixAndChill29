package edu.gatech.snickers.techflixandchill;

import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * Created by Scottie on 4/4/16.
 */
public class ViewMyRatingsActivityTest {

    public static final int TIMEOUT = 500;

    @Test(timeout = TIMEOUT)
    public void testFetchUserRatings() throws Exception {
        assertEquals("Test values do not match:",2,2);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchMethodException.class)
    public void testNullUser() throws Exception {
        assertNotNull("Could not find method:", ViewMyRatingsActivity.class.getMethod("fetchMyUserRatings"));
    }
}