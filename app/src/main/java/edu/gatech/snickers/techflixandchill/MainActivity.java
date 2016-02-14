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

/**
 * Created on 2/12/16. Last modified on 2/13/16.
 *
 * Main activity of our application. Allows users to choose
 * between logging in and registering an account with the app.
 */
public class MainActivity extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter;
    Button login;
    Button registerr;
    EditText enterpassword, username;
    TextView forgetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login=(Button)findViewById(R.id.login_btn);
        registerr=(Button)findViewById(R.id.register_btn);
        username = (EditText) findViewById(R.id.edt_username);
        enterpassword=(EditText)findViewById(R.id.password_edt);
        forgetpass=(TextView)findViewById(R.id.textView2);

        loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter.open();
        //THIS IS JUST FOR TESTING PURPOSES TO START WITH A HARD CODED ROW IN DATABASE
        //TODO remove this block of code, no longer needed, code works and passes test
        //loginDataBaseAdapter.insertEntry("tester", "tester", "tester", "tester", "tester", "tester");

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
                if (!loginDataBaseAdapter.checkForUser(Username)) {
                    Toast.makeText(MainActivity.this, "Username does not exist within app", Toast.LENGTH_LONG).show();
                } else {
                    String storedPassword = loginDataBaseAdapter.getPassword(Username);

                    if(Password.equals(storedPassword))
                    {
                        Toast.makeText(MainActivity.this, "Congrats: Login Successfully", Toast.LENGTH_LONG).show();
                        Intent ii=new Intent(MainActivity.this,Home.class);
                        //create bundle to pass along user data
                        String nextUsername = Username;
                        String nextPassword = Password;
                        //Create the bundle
                        Bundle bundle = new Bundle();
                        //Add the data to the bundle
                        bundle.putString("USERNAME", nextUsername);
                        bundle.putString("PASSWORD", nextPassword);
                        //Add the bundle to the intent
                        ii.putExtras(bundle);
                        //start the activity
                        startActivity(ii);
                    }
                    else
                    if(Password.equals("")){
                        Toast.makeText(MainActivity.this, "Please Enter Your Password", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Password Incorrect", Toast.LENGTH_LONG).show();
                    }
                }
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

                final EditText security=(EditText)dialog.findViewById(R.id.securityhint_edt);
                final TextView getpass=(TextView)dialog.findViewById(R.id.textView3);

                Button ok=(Button)dialog.findViewById(R.id.getpassword_btn);
                Button cancel=(Button)dialog.findViewById(R.id.cancel_btn);

                ok.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {

                        String userName=security.getText().toString();
                        if(userName.equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "Please enter your securityhint", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String storedPassword=loginDataBaseAdapter.getAllTags(userName);
                            if(storedPassword==null)
                            {
                                Toast.makeText(getApplicationContext(), "Please enter correct securityhint", Toast.LENGTH_SHORT).show();
                            }else{
                                Log.d("GET PASSWORD",storedPassword);
                                getpass.setText(storedPassword);
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        loginDataBaseAdapter.close();
    }

}