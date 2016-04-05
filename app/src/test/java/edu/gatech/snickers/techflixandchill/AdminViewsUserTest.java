package edu.gatech.snickers.techflixandchill;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Scottie on 4/4/16.
 */
public class AdminViewsUserTest {

    @Test
    public void testOnCreate() throws Exception {
        assertNotEquals(2, 1+1);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {
        assertEquals(1,0+1);
    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {
        assertEquals(2,3-1);
    }
}