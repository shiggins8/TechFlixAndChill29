package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Scottie on 2/13/16.
 *
 * Home screen of our app itself
 */
public class Home extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }
}