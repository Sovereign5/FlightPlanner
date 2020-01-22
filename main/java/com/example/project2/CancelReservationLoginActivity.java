package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.DB.DBManager;
import com.example.project2.DB.DBManagerReservations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class CancelReservationLoginActivity extends AppCompatActivity {

    // Declarations
    private EditText mUsernameTextField;
    private EditText mPasswordTextField;
    private Button mLoginButton;
    DBManager dbManager = new DBManager(this);
    DBManagerReservations dbManagerReservations = new DBManagerReservations(this);
    int ticktock = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation_login);
        mUsernameTextField = (EditText) findViewById(R.id.usernameEditText);
        mPasswordTextField = (EditText) findViewById(R.id.passwordEditText);
        mLoginButton = (Button) findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManager.open();
                String usernameText = mUsernameTextField.getText().toString().trim();
                String passwordText = mPasswordTextField.getText().toString().trim();
                try{
                    if (usernameText.length()==0 || passwordText.length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Enter username/password", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //Log.i(TAG, usernameText + " / " + passwordText);
                        int login = dbManager.login(usernameText, passwordText);
                        if (login >= 1)
                        {
                            dbManagerReservations.open();
                            ArrayList<String> reservations = dbManagerReservations.getReservation(usernameText);
                            dbManagerReservations.close();
                            if (reservations.get(0) == null)
                            {
                                Toast.makeText(getApplicationContext(), "No Reservations on Account", Toast.LENGTH_SHORT).show();
                                ticktock++;
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_SHORT).show();
                                launchCancelFlightActivity(usernameText, passwordText);
                            }
                        } else
                        {
                            Toast.makeText(getApplicationContext(), "Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                            ticktock++;
                        }
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "No Reservations on Account", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (ticktock == 2)
        {
            Toast.makeText(getApplicationContext(), "Timeout!", Toast.LENGTH_SHORT).show();
            launchmain();
        }
    }
    private void launchCancelFlightActivity(String username, String pass) {
        Intent intent = new Intent(this, CancelReservationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Username", username);
        bundle.putString("Password", pass);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void launchmain()
    {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
