package edu.gatech.snickers.techflixandchill;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.*;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button bLogin;
    EditText etUsername, etPassword;
    TextView tvRegisterLink;

    //UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

        //userLocalStore = new UserLocalStore(this);

        //TODO add a way to cancel login and return to welcome screen
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bLogin:

                if (etUsername.getText().toString().equals("user") && etPassword.getText().toString().equals("password")) {
                    //User user = new User(null, null);
                    //userLocalStore.storeUserData(user);

                    //userLocalStore.setUserLoggedIn(true);
                    startActivity(new Intent(this, MainApp.class));
                    break;
                } else {
                    new AlertDialog.Builder(this).setTitle("Oops!").setMessage("Incorrect username or password!").setNeutralButton("Close", null).show();
                    break;
                }
            case R.id.tvRegisterLink:

                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}