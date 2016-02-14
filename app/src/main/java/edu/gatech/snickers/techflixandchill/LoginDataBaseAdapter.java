package edu.gatech.snickers.techflixandchill;

import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Scottie on 2/13/16.
 *
 * Class that allows us to edit and update our SQLite database.
 */
public class LoginDataBaseAdapter {

    static final String DATABASE_NAME = "login.db";
    static final int DATABASE_VERSION = 5;
    public static final int NAME_COLUMN = 7;

    /**
     * From T-Square Wiki: To use the site, a student must be a registered user. To register, a student
     * must enter their email, name, login/user name.
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

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public LoginDataBaseAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        db.close();
    }

    public SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String username, String password,String repassword,String securityhint, String major,
                            String email)
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
     * Currently, as coded, returns the password for a specific user.
     *
     * @param username the app account username
     * @return password for particular user, alert if user does not exist
     */
    public String getSinlgeEntry(String username)
    {
        Cursor cursor = db.query("LOGIN", null, " USERNAME=?", new String[]{username}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String repassword= cursor.getString(cursor.getColumnIndex("REPASSWORD"));
        cursor.close();
        return repassword;
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

    public String getAllTags(String a) {

        Cursor c = db.rawQuery("SELECT * FROM " + "LOGIN" + " where SECURITYHINT = '" +a + "'" , null);
        String str = null;
        if (c.moveToFirst()) {
            do {
                str = c.getString(c.getColumnIndex("PASSWORD"));
            } while (c.moveToNext());
        }
        return str;
    }

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

    public HashMap<String, String> getAnimalInfo(String id) {
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