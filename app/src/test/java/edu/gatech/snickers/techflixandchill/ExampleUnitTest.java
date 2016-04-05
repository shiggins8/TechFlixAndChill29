package edu.gatech.snickers.techflixandchill;

import android.test.InstrumentationTestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends InstrumentationTestCase{
    public void testAdditionIsCorrect() throws Exception {
        assertEquals(3, 2 + 2);
    }

    public void testAFailure() throws Exception {
        assertEquals(6, 2+3);
    }
}