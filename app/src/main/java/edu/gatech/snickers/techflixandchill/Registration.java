package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Created by Scottie on 2/13/16.
 *
 * Screen that allows users to register an account with the app. They fill out
 * the appropriate fields and will be notified if the account is created
 * successfully. Incorrectly or incompletely filling out the form will display a
 * Toast error message instructing the user how to register properly.
 *
 */
public class Registration extends Activity{

    LoginDataBaseAdapter loginDataBaseAdapter;
    EditText password,repassword,securityhint,username,email,major;
    Button register,cancel;
    CheckBox check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        password = (EditText)findViewById(R.id.password_edt);
        repassword = (EditText)findViewById(R.id.repassword_edt);
        securityhint = (EditText)findViewById(R.id.securityhint_edt);
        username = (EditText) findViewById(R.id.username_edt);
        email = (EditText) findViewById(R.id.email_edt);
        major = (EditText) findViewById(R.id.major_edt);
        register = (Button)findViewById(R.id.register_btn);
        cancel = (Button)findViewById(R.id.cancel_btn);
        check = (CheckBox)findViewById(R.id.checkBox1);

        //code to add functionality to the checkbox
        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                else
                {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                String Pass = password.getText().toString();
                String Secu = securityhint.getText().toString();
                String Repass = repassword.getText().toString();
                String user = username.getText().toString();
                String mail = email.getText().toString();
                String umajor = major.getText().toString();

                //check to see if registration form is incomplete
                if(Pass.equals("")||Repass.equals("")||Secu.equals("") || user.equals("")
                        || mail.equals("") || umajor.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                    return;
                }

                //if re-entered password doesn't match original password
                if(!Pass.equals(Repass))
                {
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                    return;
                }

                //successful registration
                else
                {
                    // Save the Data in Database
                    loginDataBaseAdapter.insertEntry(user, Pass, Repass,Secu, umajor, mail);

// reg_btn.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    Log.d("PASSWORD",Pass);
                    Log.d("RE PASSWORD",Repass);
                    Log.d("SECURITY HINT",Secu);
                    Intent i=new Intent(Registration.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii=new Intent(Registration.this,MainActivity.class);
                startActivity(ii);
            }
        });
    }
}