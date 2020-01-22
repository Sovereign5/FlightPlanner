package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.room.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2.DB.AppDatabase;
import com.example.project2.DB.DBManager;
import com.example.project2.DB.Project2DAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateAccountActivity extends AppCompatActivity {
    // Interface Declarations
    DBManager dbManager = new DBManager(this);
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mCreateButton;
    int ticktock = 0;

    private List<Project2DAO> project2;
    private Project2DAO Project2DAO;

//    private List<AccountLog> accountLogs;
//    private Project2DAO ProjectDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mUsernameEditText = (EditText) findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mCreateButton = (Button) findViewById(R.id.createButton);


        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUsernameEditText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                } else if (mPasswordEditText.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                } else if (!mUsernameEditText.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(getApplicationContext(), "Usernames must have at least one number in them.", Toast.LENGTH_SHORT).show();
                    ticktock++;
                } else if (!mPasswordEditText.getText().toString().matches(".*\\d.*")) {
                    Toast.makeText(getApplicationContext(), "Passwords must have at least one number in them.", Toast.LENGTH_SHORT).show();
                    ticktock++;
                } else {
                    try {
                        if (mUsernameEditText.getText().toString().trim().length() != 0) {
                            dbManager.open();
                            String userName = mUsernameEditText.getText().toString().trim();
                            String pass = mPasswordEditText.getText().toString().trim();
                            String query = "Select * From ACCOUNTS where userName = '" + userName + "'";
                            if (userName.equals("admin2")) {
                                Toast.makeText(getApplicationContext(), "Invalid account name", Toast.LENGTH_SHORT).show();
                                ticktock++;
                            } else {
                                int login = dbManager.loginCreate(userName, pass);
                                if (login >= 1) {
                                    Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
                                    ticktock++;
                                } else {
                                    dbManager.insert(userName, pass);
                                    Toast.makeText(getApplicationContext(), "Account added!", Toast.LENGTH_SHORT).show();
                                    //addToDataBase();
                                    finish();
                                    launchMain();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (ticktock == 2)
                {
                    Toast.makeText(getApplicationContext(), "Timeout!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private void addToDataBase() {
        String transactionType = "=new account";
        String username = mUsernameEditText.getText().toString().trim();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.m. aa");
        String output = dateFormat.format(currentTime);
        AccountLog project2 = new AccountLog(transactionType, username, null, null, null, null, null, output);
        Project2DAO.insert(project2);
    }
    private void launchMain()
    {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}
/*public AccountLog(int transactionType, String customerUsername, String flightNumber,
                      String departureArrivalTime, int numberOfTickets, String reservationNumber,
                      int totalAmount, String currentTime)*/