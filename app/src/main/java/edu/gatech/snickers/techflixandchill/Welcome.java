package edu.gatech.snickers.techflixandchill;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;

public class Welcome extends AppCompatActivity implements View.OnClickListener {

    Button bWelcomeLogin, bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        bWelcomeLogin = (Button) findViewById(R.id.bWelcomeLogin);

        bWelcomeLogin.setOnClickListener(this);

        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bWelcomeLogin:

                startActivity(new Intent(this, Login.class));

                break;

            case R.id.bRegister:

                startActivity(new Intent(this, Register.class));

                break;
        }
    }

}
