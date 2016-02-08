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

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button bRegister;
    EditText etName, etAge, etUsername, etPassword, etMajor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etAge = (EditText) findViewById(R.id.etAge);
        etMajor = (EditText) findViewById(R.id.etMajor);

        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bRegister:
                if (etName.getText().toString().equals("") | etUsername.getText().toString().equals("") | etPassword.getText().toString().equals("") | etAge.getText().toString().equals("")) {
                    new AlertDialog.Builder(this).setTitle("Oops!").setMessage("Must fill in all fields above!").setNeutralButton("Close", null).show();
                } else {
                    String name = etName.getText().toString();
                    String username = etUsername.getText().toString();
                    String password = etPassword.getText().toString();
                    int age = Integer.parseInt(etAge.getText().toString());

                    User newUser1 = new User(name, age, username, password);

                    startActivity(new Intent(this, MainApp.class));
                }

                break;
        }
    }
}