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
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 3;

    /**
     * From T-Square Wiki: To use the site, a student must be a registered user. To register, a student
     * must enter their email, name, login/user name.
     */
    static final String DATABASE_CREATE = "create table "+
            "LOGIN"+
            "( " +
            "ID integer primary key autoincrement,"+
            "Username text" +
            "PASSWORD text," +
            "REPASSWORD text," +
            "EMAIL text" +
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

    public int deleteEntry(String password)
    {
        String where="PASSWORD=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{password}) ;
        return numberOFEntriesDeleted;
    }

    /**
     * Method to get the password for a specific user. Useful in checking password once a
     * user is known.
     * @param username username associated with a password
     * @return the String version of the password
     */
    public String getPassword(String username) {
        Cursor cursor = db.query("LOGIN", null, username, null, null, null, null, null);
        String userPassword = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return userPassword;
    }

    public String getSinlgeEntry(String password)
    {
        Cursor cursor = db.query("LOGIN", null, " PASSWORD=?", new String[]{password}, null, null, null);
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

    public void updateEntry(String password,String repassword)
    {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("PASSWORD", password);
        updatedValues.put("REPASSWORD",repassword);
        updatedValues.put("SECURITYHINT",repassword);

        String where="USERNAME = ?";
        db.update("LOGIN",updatedValues, where, new String[]{password});
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