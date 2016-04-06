package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.test.ActivityTestCase;
import android.test.ApplicationTestCase;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.FirebaseException;
import com.firebase.client.ValueEventListener;

import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.hamcrest.*;

import static org.junit.Assert.*;

/**
 * Created by Scottie on 4/4/16.
 *
 * JUnit tests for the checkSecuHint method
 */
public class MainActivityTest extends ActivityTestCase {
    public static final long TIMEOUT = 500;
    String testUsername;
    String testPassword;
    MainActivity tester;
    Firebase ref;

    // base test to ensure firebase is working
    @Test(timeout = TIMEOUT)
    public void testOnCreate() throws Exception {
        assertEquals("They aren't equal", 2, 3);
    }

    // check first parameter, ensure no null strings are passed
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullUsername() throws Exception {
        assertNotNull("The method doesn't exist", MainActivity.class.getMethod("performLogin", String.class, String.class));
        tester = new MainActivity();
        String testString = null;
        tester.checkSecuHint(testString, null, null);
    }

    // check second parameter, ensure no null TextViews are passed
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testNullTextView() throws Exception {
        tester = new MainActivity();
        TextView view = null;
        String testString = "hello";
        tester.checkSecuHint(testString, view, null);
    }

    // check third parameter, ensure no null Firebase references are passed
    @Test(timeout = TIMEOUT, expected = FirebaseException.class)
    public void testNullFirebase() throws Exception {
        tester = new MainActivity();
        ref = null;
        String testString = "hello";
        Context testContext = tester.getApplicationContext();
        tester.checkSecuHint(testString, null, ref);
    }

    @Test
    public void testIfUsernameActuallyExists() throws Exception {
        tester = new MainActivity();
        testUsername = "shiggins8";
        ref = new Firebase("https://techflixandchill.firebaseio.com");
        ref = ref.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(testUsername)) {
                    final User temp = snapshot.child(testUsername).getValue(User.class);
                    assertEquals("what you is you", temp.getSecurityHint());
                    //getPass.setText(temp.getSecurityHint());
                } else {
                    final User temp = snapshot.child(testUsername).getValue(User.class);
                    assertNull(temp.getSecurityHint());
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });
    }

    @Test
    public void test2() throws Exception {
        tester = new MainActivity();
        testUsername = "shiggins8";
        ref = new Firebase("https://techflixandchill.firebaseio.com");
        ref = ref.child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(testUsername)) {
                    final User temp = snapshot.child(testUsername).getValue(User.class);
                    assertEquals("what is you", temp.getSecurityHint());
                    //getPass.setText(temp.getSecurityHint());
                } else {
                    final User temp = snapshot.child(testUsername).getValue(User.class);
                    assertNotNull(temp.getSecurityHint());
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });
    }
}