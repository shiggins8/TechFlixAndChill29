package edu.gatech.snickers.techflixandchill;

import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created on 2/12/16.
 *
 * Main activity of our application. Allows users to choose between logging in and registering an
 * account with the app. If the user has forgotten their password, there is a link they can select
 * that will provide them with their security hint.Provides functionality and logic for entering
 * the app. Registered user data is stored in a Firebase database online, using JSON structure.
 *
 * @author Snickers
 * @version 2.0
 */
public class MainActivity extends Activity {
    private EditText enterpassword, username;
    private Firebase ref;
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = (Button) findViewById(R.id.login_btn);
        final Button registerr = (Button) findViewById(R.id.register_btn);
        username = (EditText) findViewById(R.id.edt_username);
        enterpassword = (EditText) findViewById(R.id.password_edt);
        final TextView forgetpass = (TextView) findViewById(R.id.textView2);
        checked = false;
        final CheckBox loginAsAdmin = (CheckBox) findViewById(R.id.loginAsAdmin);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://techflixandchill.firebaseio.com");


        registerr.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });

        //contains logic for login operation
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String enteredPassword = enterpassword.getText().toString();
                final String enteredUsername = username.getText().toString();
                //check to see if user actually exists, proceed if they do
                checkUser(enteredUsername, enteredPassword, ref.child("users"));
            }
        });

        loginAsAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked = isChecked;
            }
        });

        forgetpass.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forget_search);
                dialog.show();

                final EditText security = (EditText) dialog.findViewById(R.id.securityhint_edt);
                final TextView getpass=(TextView)dialog.findViewById(R.id.textView3);

                final Button ok = (Button) dialog.findViewById(R.id.getpassword_btn);
                final Button cancel = (Button) dialog.findViewById(R.id.cancel_btn);

                ok.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        final String userName = security.getText().toString();
                        final String nullstring = "";
                        if(userName.equals(nullstring)) {
                            Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                        } else {
                            checkSecuHint(userName, getpass, ref.child("users"));
                        }
                    }
                });

                //user decides to cancel, dismisses dialog window
                cancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    };

    public void checkUser(String enteredUsername, String password, Firebase fireRef) {
        final String userName = enteredUsername;
        final String passWord = password;
        fireRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
                if (snapshot.hasChild(userName)) {
                    performLogin(userName, passWord);
                    //Toast.makeText(MainActivity.this, "User does exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "User does NOT exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });
    }

    public void checkSecuHint(String enteredUsername, TextView getpass, Firebase fireRef) {
        final String userName = enteredUsername;
        final TextView getPass = getpass;
        fireRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
                if (snapshot.hasChild(userName)) {
                    final User temp = snapshot.child(userName).getValue(User.class);
                    getPass.setText(temp.getSecurityHint());
                    //Toast.makeText(MainActivity.this, "User does exist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "User does NOT exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError arg0) {
            }
        });
    }

    public void performLogin(String userName, String passWord) {
        final int passwordThreshold = 3;
        final String nullstring = "";
        final String password = passWord;
        final Firebase loginRef = ref.child("users").child(userName);
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                final boolean areTheyBlocked = user.isBlocked();
                final boolean areTheyLocked = user.isLocked();
                if (areTheyBlocked) {
                    Toast.makeText(MainActivity.this, "Your account is blocked, please contact an administrator", Toast.LENGTH_LONG).show();
                } else if (areTheyLocked) {
                    Toast.makeText(MainActivity.this, "You have entered the wrong password too many times and your account is locked, please contact an administrator", Toast.LENGTH_LONG).show();
                } else {
                    final String storedPassword = user.getPassword();
                    if (storedPassword.equals(password)) {
                        if (checked && !user.isAdmin()) {
                            Toast.makeText(MainActivity.this, "Cannot login as admin", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
                            user.setIncorrectPasswordCounter(0);
                            //create a bundle to pass along user data
                            final Bundle bundle = new Bundle();
                            //Add data to bundle
                            bundle.putString("USERNAME", user.getUsername());
                            bundle.putString("PASSWORD", user.getPassword());
                            bundle.putString("NAME", user.getName());
                            bundle.putString("MAJOR", user.getMajor());
                            bundle.putString("SECURITYHINT", user.getSecurityHint());
                            bundle.putString("EMAIL", user.getEmail());
                            final Intent ii = new Intent(MainActivity.this, Home.class);
                            final Intent iii = new Intent(MainActivity.this, AdminHome.class);
                            //Add bundle to intent
                            ii.putExtras(bundle);
                            iii.putExtras(bundle);
                            if (checked && user.isAdmin()) {
                                startActivity(iii);
                            } else {
                                startActivity(ii);
                            }
                        }
                    } else {
                        if (password.equals(nullstring)) {
                            Toast.makeText(MainActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                            final long t = (long) dataSnapshot.child("incorrectPasswordCounter").getValue();
                            int temp = (int) t;
                            temp++;
                            if (temp == passwordThreshold) {
                                loginRef.child("locked").setValue(true);
                                //user.setIsLocked(true);
                                loginRef.child("incorrectPasswordCounter").setValue(0);
                                //user.setIncorrectPasswordCounter(0);
                            } else {
                                loginRef.child("incorrectPasswordCounter").setValue(temp);
                            }

                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}