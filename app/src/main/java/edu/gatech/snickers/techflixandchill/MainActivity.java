package edu.gatech.snickers.techflixandchill;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
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
 * that will provide them with their password, given their security hint is entered correctly.
 * Provides functionality and logic for entering the app.
 *
 * @author Snickers
 * @version 1.0
 */
public class MainActivity extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    Button login;
    Button registerr;
    EditText enterpassword, username;
    TextView forgetpass;
    private Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button) findViewById(R.id.login_btn);
        registerr = (Button) findViewById(R.id.register_btn);
        username = (EditText) findViewById(R.id.edt_username);
        enterpassword = (EditText) findViewById(R.id.password_edt);
        forgetpass = (TextView) findViewById(R.id.textView2);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://techflixandchill.firebaseio.com");

        registerr.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Registration.class);
                startActivity(i);
            }
        });

        //contains logic for login operation
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String Password = enterpassword.getText().toString();
                String Username = username.getText().toString();
                //check to see if user actually exists, proceed if they do
                checkUser(Username, Password, ref.child("users"));
//                if (loginDataBaseAdapter.checkForUser(Username)) {
//                    Toast.makeText(MainActivity.this, "Username does not exist within app", Toast.LENGTH_LONG).show();
//                } else {
//                    String storedPassword = loginDataBaseAdapter.getPassword(Username);
//
//                    if(Password.equals(storedPassword))
//                    {
//                        Toast.makeText(MainActivity.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
//                        Intent ii=new Intent(MainActivity.this,Home.class);
//                        //create bundle to pass along user data
//                        Bundle bundle = new Bundle();
//                        //Add the data to the bundle
//                        bundle.putString("USERNAME", Username);
//                        bundle.putString("PASSWORD", Password);
//                        //Add the bundle to the intent
//                        ii.putExtras(bundle);
//                        //start the activity
//                        startActivity(ii);
//                    }
//                    else
//                    if(Password.equals("")){
//                        Toast.makeText(MainActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
//                    }
//                }
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

                Button ok = (Button) dialog.findViewById(R.id.getpassword_btn);
                Button cancel = (Button) dialog.findViewById(R.id.cancel_btn);

                ok.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        String userName = security.getText().toString();
                        if(userName.equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "Please enter your username", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String storedHint = loginDataBaseAdapter.getHint(userName);
                            if(storedHint==null)
                            {
                                Toast.makeText(getApplicationContext(), "Please enter correct username", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d("GET HINT",storedHint);
                                getpass.setText(storedHint);
                            }
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

    public void checkUser(String username, String password, Firebase ref) {
        final String userName = username;
        final String passWord = password;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
                if (snapshot.hasChild(userName)) {
                    performLogin(userName, passWord);
                    Toast.makeText(MainActivity.this, "User does exist", Toast.LENGTH_SHORT).show();
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
        final String password = passWord;
        Firebase loginRef = ref.child("users").child(userName);
        loginRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String storedPassword = user.getPassword();
                if (storedPassword.equals(password)) {
                    Toast.makeText(MainActivity.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
                    Intent ii = new Intent(MainActivity.this, Home.class);
                    //create a bundle to pass along user data
                    Bundle bundle = new Bundle();
                    //Add data to bundle
                    bundle.putString("USERNAME", user.getUsername());
                    //Add bundle to pintent
                    ii.putExtras(bundle);
                    //start activity
                    startActivity(ii);
                } else {
                    if (password.equals("")) {
                        Toast.makeText(MainActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//        String storedPassword = loginDataBaseAdapter.getPassword(Username);
//
//                    if(Password.equals(storedPassword))
//                    {
//                        Toast.makeText(MainActivity.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
//                        Intent ii=new Intent(MainActivity.this,Home.class);
//                      //create bundle to pass along user data
//                        Bundle bundle = new Bundle();
//                        //Add the data to the bundle
//                        bundle.putString("USERNAME", Username);
//                        bundle.putString("PASSWORD", Password);
//                        //Add the bundle to the intent
//                        ii.putExtras(bundle);
//                        //start the activity
//                        startActivity(ii);
//                    }
//                    else
//                    if(Password.equals("")){
//                        Toast.makeText(MainActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
//                    }
//                    else
//                    {
//                        Toast.makeText(MainActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
//                    }
    }
}