package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.DB.DBManager;

public class LoginActivity extends AppCompatActivity {

    // Interface Declarations
    private EditText mUsernameTextField;
    private EditText mPasswordTextField;
    private Button mLoginButton;
    private TextView mTextView;
    private static final String TAG = "LoginActivity";
    DBManager dbManager = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Interface Definitions
        mUsernameTextField = (EditText) findViewById(R.id.usernameEditText);
        mPasswordTextField = (EditText) findViewById(R.id.passwordEditText);
        mLoginButton = (Button) findViewById(R.id.loginButton);
        mTextView = (TextView) findViewById(R.id.textView);

        Bundle bundle = getIntent().getExtras();
        final String flightInfo = bundle.getString("Flight");
        final int tickets = bundle.getInt("Tickets");

        // If button is pressed, switch views
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dbManager.open();
                int tiktok=0;
                String usernameText = mUsernameTextField.getText().toString().trim();
                String passwordText = mPasswordTextField.getText().toString().trim();
                try{
                    if (usernameText.length()==0 || passwordText.length()==0)
                    {
                        Toast.makeText(getApplicationContext(), "Enter username/password", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Log.i(TAG, usernameText + " / " + passwordText);
                        int login = dbManager.login(usernameText, passwordText);
                        if (login >= 1)
                        {
                            Toast.makeText(getApplicationContext(), "HELLO", Toast.LENGTH_SHORT).show();
                            String reservations = dbManager.getReservations(usernameText, passwordText);
                            System.out.println("RESERVATIONS FOR ACCOUNT: " + reservations);
                            launchAddFlightConfirmationActivity(usernameText, flightInfo, tickets);
                        } else
                        {
                            Toast.makeText(getApplicationContext(), "Account Doesn't Exist", Toast.LENGTH_SHORT).show();
                            tiktok++;
                        }
                    }
                }
                catch(Exception e)
                {
                    mTextView.setText("ERROR");
                }
                if (tiktok==2)
                {
                    Toast.makeText(getApplicationContext(), "Timeout!", Toast.LENGTH_SHORT).show();
                    launchMain();
                }
            }
        });
    }

    // Launcher for main activity
    private void launchAddFlightConfirmationActivity(String username, String flight, int tickets) {
        Intent intent = new Intent(this, AddFlightConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Username", username);
        bundle.putString("Flight", flight);
        bundle.putInt("Tickets", tickets);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void launchMain() {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
