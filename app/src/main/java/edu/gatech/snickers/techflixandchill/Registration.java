package edu.gatech.snickers.techflixandchill;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Snickers on 2/13/16. Last modified on 2/21/16.
 *
 * Screen that allows users to register an account with the app. They fill out
 * the appropriate fields and will be notified if the account is created
 * successfully. Incorrectly or incompletely filling out the form will display a
 * Toast error message instructing the user how to register properly. A completed registration
 * form will then enter and save the information within the Firebase database.
 *
 * @author Snickers
 * @version 1.1
 *
 */
public class Registration extends Activity{

    EditText password, repassword, securityhint, username, email, major, name;
    Button register,cancel;
    Spinner majorSpinner;
    CheckBox check;
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Firebase.setAndroidContext(this);

        name = (EditText) findViewById(R.id.name_edt);
        password = (EditText) findViewById(R.id.password_edt);
        repassword = (EditText) findViewById(R.id.repassword_edt);
        securityhint = (EditText) findViewById(R.id.securityhint_edt);
        username = (EditText) findViewById(R.id.username_edt);
        email = (EditText) findViewById(R.id.email_edt);
        //major = (EditText) findViewById(R.id.major_edt); //removed to add the spinner instead
        register = (Button) findViewById(R.id.register_btn);
        cancel = (Button) findViewById(R.id.cancel_btn);
        check = (CheckBox) findViewById(R.id.checkBox1);
        ref = new Firebase("https://techflixandchill.firebaseio.com");

        majorSpinner = (Spinner) findViewById(R.id.majorSpinner);
        String[] items = getResources().getStringArray(R.array.majors_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        majorSpinner.setAdapter(adapter);

        //code to add functionality to the checkbox
        check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = name.getText().toString();
                final String Pass = password.getText().toString();
                final String Secu = securityhint.getText().toString();
                final String Repass = repassword.getText().toString();
                final String user = username.getText().toString();
                final String mail = email.getText().toString();
                final String umajor = majorSpinner.getSelectedItem().toString();
                final Firebase userRef = ref.child("users");
                Firebase temp = userRef.child(user);

                //if the username the person enters has already been taken
                temp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast.makeText(getApplicationContext(), "Username already taken, please choose another", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            //check to see if registration form is incomplete
                            if (Pass.equals("") || Repass.equals("") || Secu.equals("") || user.equals("")
                                    || mail.equals("") || umajor.equals("")) {
                                Toast.makeText(getApplicationContext(), "Fill All Fields", Toast.LENGTH_LONG).show();
                                return;
                            }

                            //if re-entered password doesn't match original password
                            if (!Pass.equals(Repass)) {
                                Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                                return;
                            }

                            //successful registration
                            else {
                                // Save the Data in Database
                                // Create new user with attributes entered
                                //not admin, 0 on incorrect attempts, not blocked, not locked
                                User newUser = new User(Name, user, Pass, mail, Secu, umajor, false, 0, false, false);
                                // Create new child in users database
                                Firebase newref = userRef.child(user);
                                // Set value of child to user object
                                newref.setValue(newUser);
                                // reg_btn.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Registration.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
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