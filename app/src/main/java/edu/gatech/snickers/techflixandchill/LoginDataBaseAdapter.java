package edu.gatech.snickers.techflixandchill;

import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Snickers on 2/12/16.
 *
 * Class that allows us to edit and update the Android SQLite database. Included SQL command to
 * create the database table for user login information, and allows for further queries to get
 * information based on username and edit information. See individual methods for more detailed
 * description of class capabilities.
 *
 * @author Snickers
 * @version 1.1
 */
public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 5;
    public static final int NAME_COLUMN = 7;

    /**
     * From T-Square Wiki: To use the site, a student must be a registered user. To register, a student
     * must enter their email, name, login/user name. SQL command to create a database with these
     * particular columns.
     */
    static final String DATABASE_CREATE = "create table "+
            "LOGIN"+
            "( " +
            "ID integer primary key autoincrement,"+
            "Username text," +
            "PASSWORD text," +
            "REPASSWORD text," +
            "EMAIL text," +
            "SECURITYHINT text," +
            "Major text) ";

    public SQLiteDatabase db;
    private final Context context;
    private DataBaseHelper dbHelper;

    /**
     * General constructor to build the adapter based on a particular context
     *
     * @param _context context within which to build the database adapter
     */
    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * Method to access an editable version of the database, for insert, delete, and update functions.
     *
     * @return a database that can be edited
     * @throws SQLException if database does not exist or cannot be accessed
     */
    public LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    /**
     * Simple method to close the database and prevent memory leakage.
     */
    public void close()
    {
        db.close();
    }

    /**
     * A method to get the SQLite database
     *
     * @return the current instance of the database
     */
    public SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    /**
     * Method to add a row to the SQLite database, which corresponds to an App user registering
     * for a new account.
     *
     * @param username desired username
     * @param password desired password
     * @param repassword re-entered password to verify
     * @param securityhint desired security hint for the password
     * @param major desired major to be displayed
     * @param email desired email to be associated with the account
     */
    public void insertEntry(String username, String password,String repassword,String securityhint,
                            String major, String email)
    {
        ContentValues newValues = new ContentValues();
        newValues.put("USERNAME", username);
        newValues.put("PASSWORD", password);
        newValues.put("REPASSWORD",repassword);
        newValues.put("SECURITYHINT",securityhint);
        newValues.put("MAJOR", major);
        newValues.put("EMAIL", email);

        db.insert("LOGIN", null, newValues);
    }

    /**
     * Will delete a row from the database, called when a user opts to delete their account
     *
     * @param _username username corresponding with target row
     * @return the number of accounts deleted, should always be 1
     */
    public int deleteEntry(String _username)
    {
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{_username}) ;
        return numberOFEntriesDeleted;
    }

    /**
     * Method to get the password for a specific user. Useful in checking password once a
     * user is known.
     *
     * @param username username associated with a password
     * @return the String version of the password
     */
    public String getPassword(String username) {
        Cursor cursor = db.query("LOGIN", new String[]{"PASSWORD"}, "USERNAME=?", new String[]{username}, null, null, null, null);
        cursor.moveToFirst();
        String userPassword = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return userPassword;
    }

    /**
     * Method to get the stored security hint for a specific user. Utilized if the user clicks the
     * forgot my password option on the main screen.
     *
     * @param username of the person searching for their security hint
     * @return the security hint entered for that particular person
     */
    public String getHint(String username) {
        Cursor cursor = db.query("LOGIN", new String[]{"SECURITYHINT"}, "USERNAME=?", new String[]{username}, null, null, null, null);
        cursor.moveToFirst();
        String userHint = cursor.getString(cursor.getColumnIndex("SECURITYHINT"));
        cursor.close();
        return userHint;
    }

    /**
     * Currently, as coded, returns the details for a single user.
     *
     * @param username the app account username
     * @return ContentValues containing all info for a specific user
     */
    public ContentValues getSinlgeEntry(String username)
    {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{username}, null, null, null);
        ContentValues userDetails = new ContentValues();
        //username does not exist
        if(cursor.getCount()<1) {
            userDetails.put("STATUS", "DOES NOT EXIST");
            cursor.close();
            return userDetails;
        }
        //individually pull out info, place in ContentValues
        cursor.moveToFirst();
        userDetails.put("USERNAME", username);
        cursor.moveToFirst();
        userDetails.put("PASSWORD", cursor.getString(cursor.getColumnIndex("PASSWORD")));
        cursor.moveToFirst();
        userDetails.put("EMAIL", cursor.getString(cursor.getColumnIndex("EMAIL")));
        cursor.moveToFirst();
        userDetails.put("MAJOR", cursor.getString(cursor.getColumnIndex("Major")));
        cursor.moveToFirst();
        userDetails.put("SECURITYHINT", cursor.getString(cursor.getColumnIndex("SECURITYHINT")));
        cursor.close();
        return userDetails;
    }

    /**
     * Quick method that can be called to check to see if a username exists in the database
     *
     * @param username username to be checked for in database
     * @return boolean value of whether or not said username exists
     */
    public Boolean checkForUser(String username) {
        Cursor cursor = db.query("LOGIN", null, "USERNAME=?", new String[]{username}, null, null, null);
        if (cursor.getCount() < 1)//Username doesn't exist
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    /*
    public String getAllTags(String a) {

        Cursor c = db.rawQuery("SELECT * FROM " + "LOGIN" + " where SECURITYHINT = '" + a + "'", null);
        String str = null;
        if (c.moveToFirst()) {
            do {
                str = c.getString(c.getColumnIndex("PASSWORD"));
            } while (c.moveToNext());
        }
        return str;
    }*/

    /**
     * Method to update a row in the database. Allows a user to update their information via simply
     * updating the row in the database.
     *
     * @param username original user account username
     * @param newPassword new password, same if unchanged
     * @param newEmail new email, same if unchanged
     * @param newMajor new major, same if unchanged
     * @param newSecuHint new security hint, same if unchanged
     */
    public void updateEntry(String username, String newPassword, String newEmail, String newMajor,
                            String newSecuHint)
    {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("USERNAME", username);
        updatedValues.put("PASSWORD", newPassword);
        updatedValues.put("REPASSWORD",newPassword);
        updatedValues.put("SECURITYHINT",newSecuHint);
        updatedValues.put("EMAIL", newEmail);
        updatedValues.put("MAJOR", newMajor);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{username});
    }

    /**
     * Method that will return a HashMap of all of the users and their password and info from the
     * database. Currently, is not implemented, may be used in the future for admin purposes.
     *
     * @param id unique identifier for the data
     * @return a HashMap of all of the data in the database
     */
    public HashMap<String, String> getHashedInfo (String id) {
        HashMap<String, String> wordList = new HashMap<String, String>();
        String selectQuery = "SELECT * FROM LOGIN where SECURITYHINT='"+id+"'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                wordList.put("PASSWORD", cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return wordList;
    }
}