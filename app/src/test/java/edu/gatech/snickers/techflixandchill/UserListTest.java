package edu.gatech.snickers.techflixandchill;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Scottie on 4/1/16.
 *
 * JUnit4 unit tests to test the manageUserInfo() method found in the UserListTest class. Runs
 * through multiple tests to ensure that the method works properly with invalid parameters,
 * parameters of different lengths, etc. Also ensures that the method can be found properly within
 * the class and called by the application.
 *
 * @author Scottie
 * @version 1.1
 */
public class UserListTest {
    // default timeout time, will catch any misguided loops/conditional statements
    public static final int TIMEOUT = 400;
    UserList tester;
    ArrayList<User> nullTestList = null;
    ArrayList<User> zeroUserTestList = new ArrayList<>();
    ArrayList<User> oneUserTestList = new ArrayList<>();
    ArrayList<User> twoUserTestList = new ArrayList<>();
    ArrayList<User> variousUserTestList = new ArrayList<>();
    User user1 = new User("scott", "shiggins8", "password", "skot@gmail.com", "what am I",
            "Computer Science", true, 0, false, false);
    User user2 = new User("aaron", "apatel3", "pass", "aaron@yahoo.com", "shorter version",
            "Biomedical Engineer", false, 0, false, false);
    User user3 = new User("niraj", "nsuresh3", "bentobus", "niraj@gmail.com", "favorite food",
            "Aerospace Engineer", true, 0, false, false);

    /**
     * Test to see if the method properly handles a null parameter being passed in.
     *
     * @throws Exception IllegalArgumentException for null parameters
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullParameter_throwException() throws Exception {
        tester = new UserList();
        tester.manageUserInfo(nullTestList);
    }

    /**
     * Test for a parameter array that is not null, but length is zero
     *
     * @throws Exception if the method functions improperly
     */
    @Test(timeout = TIMEOUT)
    public void testLengthIsZero() throws Exception {
        tester = new UserList();
        oneUserTestList.add(0, user1);
        twoUserTestList.add(0, user1);
        twoUserTestList.add(1, user2);
        variousUserTestList.add(0, user1);
        variousUserTestList.add(1, user2);
        variousUserTestList.add(2, user3);

        assertNull(tester.manageUserInfo(zeroUserTestList));
    }

    /**
     * Test when the array list of users has size of one, ensure that the method returns an
     * equal ArrayList.
     *
     * @throws Exception ifthe method returns anything other than aforementioned return
     */
    @Test(timeout = TIMEOUT)
    public void testLengthIsOne() throws Exception {
        tester = new UserList();
        oneUserTestList.add(0, user1);
        twoUserTestList.add(0, user1);
        twoUserTestList.add(1, user2);
        variousUserTestList.add(0, user1);
        variousUserTestList.add(1, user2);
        variousUserTestList.add(2, user3);

        assertEquals(oneUserTestList, tester.manageUserInfo(oneUserTestList));
    }

    /**
     * Test when the array list of users has size of two, that it returns an ArrayList of only
     * the first User object.
     *
     * @throws Exception if the method returns an ArrayList of anything other than one user
     */
    @Test(timeout = TIMEOUT)
    public void testLengthIsTwo() throws Exception {
        tester = new UserList();
        oneUserTestList.add(0, user1);
        twoUserTestList.add(0, user1);
        twoUserTestList.add(1, user2);
        variousUserTestList.add(0, user1);
        variousUserTestList.add(1, user2);
        variousUserTestList.add(2, user3);

        assertEquals(oneUserTestList, tester.manageUserInfo(twoUserTestList));
    }

    /**
     * Test when the array list of users has a size greater than two, works continuously for all
     * sizes larger than two.
     *
     * @throws Exception if the method returns an ArrayList of length != 2
     */
    @Test(timeout = TIMEOUT)
    public void testLengthIsGreaterThanTwo() throws Exception {
        tester = new UserList();
        oneUserTestList.add(0, user1);
        twoUserTestList.add(0, user1);
        twoUserTestList.add(1, user2);
        variousUserTestList.add(0, user1);
        variousUserTestList.add(1, user2);
        variousUserTestList.add(2, user3);

        assertEquals(variousUserTestList, tester.manageUserInfo(variousUserTestList));
    }

    /**
     * Supplementary test method for testLengthIsGreaterThanTwo(). Creates an ArrayList with 200
     * distinct User objects, calls the method, ensures that it returns an equal ArrayList.
     *
     * @throws Exception if returned ArrayList does not match the 200 User ArrayList
     */
    @Test(timeout = TIMEOUT)
    public void testLengthIsSuperLong() throws Exception {
        tester = new UserList();
        ArrayList<User> testListLong = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            testListLong.add(i, new User("test" + i, "test" + i, "test" + i, "test @ gmail.com",
                "test", "Computer Science", true, 0, false, false));
        }

        assertEquals(testListLong, tester.manageUserInfo(testListLong));
    }

    /**
     * Simple test to ensure that the UserList can be found from the application, and it is a
     * valid class
     *
     * @throws Exception if UserList class cannot be found
     */
    @Test(timeout = TIMEOUT)
    public void testClassExists() throws Exception {
        tester = new UserList();
        assertNotNull(tester.getClass());
    }

    /**
     * Simple test to ensure that the method can be called from the class itself
     *
     * @throws Exception upon timeout or inability to find the method within UserList class
     */
    @Test(timeout = TIMEOUT)
    public void testMethodExists() throws Exception {
        assertNotNull(UserList.class.getMethod("manageUserInfo", List.class));
    }
}