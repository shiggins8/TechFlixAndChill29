package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Snickers on 2/14/16.
 *
 * Last modified on 2/21/16.
 *
 * This class provides the users with a way to view their profile, as well as provides the option
 * to edit and save changes to their profile information. Updated to integrate Firebase database.
 *
 * @author Snickers
 * @version 1.1
 */
public class UserProfile extends Activity {

    private TextView userProfileUsernameTV, userProfilePasswordTV, userProfileEmailTV, userProfileSecuHintTV, userProfileNameTV, userProfileMajorTV;
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        userProfileUsernameTV = (TextView) findViewById(R.id.userProfileUsernameTV);
        userProfilePasswordTV = (TextView) findViewById(R.id.userProfilePasswordTV);
        userProfileEmailTV = (TextView) findViewById(R.id.userProfileEmailTV);
        userProfileSecuHintTV = (TextView) findViewById(R.id.userProfileSecuHintTV);
        userProfileMajorTV = (TextView) findViewById(R.id.userProfileMajorTV);
        userProfileNameTV = (TextView) findViewById(R.id.userProfileNameTV);

        final Button editProfileButton = (Button) findViewById(R.id.editProfileButton);
        final Button goHomeButton = (Button) findViewById(R.id.goHomeButton);

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://techflixandchill.firebaseio.com");

        final Bundle bundle;
        bundle = getIntent().getExtras();
        //Extract the data
        final String name = bundle.getString("NAME");
        final String username = bundle.getString("USERNAME");
        final String password = bundle.getString("PASSWORD");
        final String major = bundle.getString("MAJOR");
        final String email = bundle.getString("EMAIL");
        final String securityHint = bundle.getString("SECURITYHINT");

        final Firebase editRef = ref.child("users").child(username);
        editRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                userProfileNameTV.setText("Name: " + user.getName());
                userProfileUsernameTV.setText("Username: " + user.getUsername());
                userProfilePasswordTV.setText("Password: " + user.getPassword());
                userProfileEmailTV.setText("Email: " + user.getEmail());
                userProfileSecuHintTV.setText("Security Hint: " + user.getSecurityHint());
                userProfileMajorTV.setText("Major: " + user.getMajor());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //do nothing
            }
        });

        //allows the user to edit their profile and save those changes
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(UserProfile.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.edit_user);
                dialog.show();

                //set up all editable fields, populate with current values
                final TextView newUsername = (TextView) dialog.findViewById(R.id.editUsernameET);
                newUsername.setText(username);
                final EditText newPassword = (EditText) dialog.findViewById(R.id.editPasswordET);
                newPassword.setText(password);
                final EditText newEmail = (EditText) dialog.findViewById(R.id.editEmailET);
                newEmail.setText(email);
                final EditText newSecuHint = (EditText) dialog.findViewById(R.id.editSecuHintET);
                newSecuHint.setText(securityHint);
                final Spinner newMajor = (Spinner) dialog.findViewById(R.id.editMajorSpinner);
                final String[] items = getResources().getStringArray(R.array.majors_array);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserProfile.this, android.R.layout.simple_spinner_dropdown_item, items);
                //make sure that spinner initially starts on their original major selection
                final int i = adapter.getPosition(major);
                newMajor.setAdapter(adapter);
                newMajor.setSelection(i, true);

                final EditText newName = (EditText) dialog.findViewById(R.id.editNameET);
                newName.setText(name);

                final Button saveChanges = (Button) dialog.findViewById(R.id.save_changes_btn);
                final Button cancel = (Button) dialog.findViewById(R.id.cancel_changes_btn);

                saveChanges.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ref = new Firebase("https://techflixandchill.firebaseio.com");

                        //note: we don't allow users to change usernames at this time
                        //final String updatedUsername = newUsername.getText().toString();
                        final String updatedPassword = newPassword.getText().toString();
                        final String updatedEmail = newEmail.getText().toString();
                        final String updatedSecuHint = newSecuHint.getText().toString();
                        final String updatedMajor = newMajor.getSelectedItem().toString();
                        final String updatedName = newName.getText().toString();

                        //update database with new info
                        final Firebase editRef = ref.child("users").child(username);
                        editRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                editRef.child("password").setValue(updatedPassword);
                                editRef.child("email").setValue(updatedEmail);
                                editRef.child("securityHint").setValue(updatedSecuHint);
                                editRef.child("major").setValue(updatedMajor);
                                editRef.child("name").setValue(updatedName);
                                userProfileNameTV.setText("Name: " + updatedName);
                                userProfilePasswordTV.setText("Password: " + updatedPassword);
                                userProfileEmailTV.setText("Email: " + updatedEmail);
                                userProfileSecuHintTV.setText("Security Hint: " + updatedSecuHint);
                                userProfileMajorTV.setText("Major: " + updatedMajor);
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

                        final Intent iii = new Intent(UserProfile.this, Home.class);
                        bundle.putString("PASSWORD", updatedPassword);
                        bundle.putString("NAME", updatedName);
                        bundle.putString("MAJOR", updatedMajor);
                        bundle.putString("SECURITYHINT", updatedSecuHint);
                        bundle.putString("EMAIL", updatedEmail);
                        iii.putExtras(bundle);
                        finish();
                        startActivity(iii);
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
                final Intent i = new Intent(UserProfile.this,Home.class);
                i.putExtras(bundle);
                //start the activity
                startActivity(i);
            }
        });

    }

}
