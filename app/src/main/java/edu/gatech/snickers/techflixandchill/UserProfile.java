package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Snickers on 2/14/16.
 *
 * This class provides the users with a way to view their profile, as well as provides the option
 * to edit and save changes to their profile information.
 *
 * @author Snickers
 * @version 1.0
 */
public class UserProfile extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    TextView userProfileUsernameTV, userProfilePasswordTV, userProfileEmailTV, userProfileSecuHintTV, userProfileMajorTV;
    Button editProfileButton, goHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter.open();

        userProfileUsernameTV = (TextView) findViewById(R.id.userProfileUsernameTV);
        userProfilePasswordTV = (TextView) findViewById(R.id.userProfilePasswordTV);
        userProfileEmailTV = (TextView) findViewById(R.id.userProfileEmailTV);
        userProfileSecuHintTV = (TextView) findViewById(R.id.userProfileSecuHintTV);
        userProfileMajorTV = (TextView) findViewById(R.id.userProfileMajorTV);

        editProfileButton = (Button) findViewById(R.id.editProfileButton);
        goHomeButton = (Button) findViewById(R.id.goHomeButton);

        final Bundle bundle;
        bundle = getIntent().getExtras();
        //Extract the data
        final String username = bundle.getString("USERNAME");
        final String password = bundle.getString("PASSWORD");

        final ContentValues userDetails = loginDataBaseAdapter.getSinlgeEntry(username);

        userProfileUsernameTV.setText("Username: " + userDetails.get("USERNAME").toString());
        userProfilePasswordTV.setText("Password: " + password);
        userProfileEmailTV.setText("Email: " + userDetails.get("EMAIL").toString());
        userProfileSecuHintTV.setText("Security Hint: " + userDetails.get("SECURITYHINT").toString());
        userProfileMajorTV.setText("Major: " + userDetails.get("MAJOR").toString());

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserProfile.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_user);
                dialog.show();

                //set up all editable fields, populate with current values
                final EditText newUsername = (EditText) dialog.findViewById(R.id.editUsernameET);
                newUsername.setText(username);
                final EditText newPassword = (EditText) dialog.findViewById(R.id.editPasswordET);
                newPassword.setText(password);
                final EditText newEmail = (EditText) dialog.findViewById(R.id.editEmailET);
                newEmail.setText(userDetails.get("EMAIL").toString());
                final EditText newSecuHint = (EditText) dialog.findViewById(R.id.editSecuHintET);
                newSecuHint.setText(userDetails.get("SECURITYHINT").toString());
                final EditText newMajor = (EditText) dialog.findViewById(R.id.editMajorET);
                newMajor.setText(userDetails.get("MAJOR").toString());

                Button saveChanges = (Button) dialog.findViewById(R.id.save_changes_btn);
                Button cancel = (Button) dialog.findViewById(R.id.cancel_changes_btn);

                saveChanges.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        String updatedUsername = newUsername.getText().toString();
                        String updatedPassword = newPassword.getText().toString();
                        String updatedEmail = newEmail.getText().toString();
                        String updatedSecuHint = newSecuHint.getText().toString();
                        String updatedMajor = newMajor.getText().toString();

                        //update the database with updated info
                        loginDataBaseAdapter.updateEntry(updatedUsername, updatedPassword, updatedEmail,
                                updatedMajor, updatedSecuHint);

                        Intent iii = getIntent();
                        finish();
                        startActivity(iii);

                        /*Intent i = new Intent(UserProfile.this, Home.class);
                        i.putExtras(bundle);

                        dialog.dismiss();

                        startActivity(i);*/
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        goHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserProfile.this,Home.class);
                i.putExtras(bundle);
                //start the activity
                startActivity(i);
            }
        });

    }

}
