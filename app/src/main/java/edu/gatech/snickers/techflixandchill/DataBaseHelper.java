package edu.gatech.snickers.techflixandchill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Created by Scottie on 2/12/16.
 *
 * Class to enable us to be able to utilize the SQLite database on the Android device itself.
 * Utilizes the database creation command, written in SQL format, stored in the LoginDataBaseAdapter
 * class. Also dictates behavior of database when version number is upgraded.
 *
 * @author Snickers
 * @version 1.0
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Constructor to create a database helper. Allows for further use of the database within the app.
     *
     * @param context the context that the database will be referenced from
     * @param name the name of the database itself
     * @param factory allows for the use of cursors within the database
     * @param version integer version number of the database itself, allows for upgrading/downgrading
     */
    public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase _db) {
        _db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
        _db.execSQL("DROP TABLE IF EXISTS " + "LOGIN");
        onCreate(_db);
    }
}