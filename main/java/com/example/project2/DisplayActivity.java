package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2.DB.DBManager;
import com.example.project2.DB.DBManagerFlights;

import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    // Interface Declarations
    private Button mCreateAccountButton;
    private Button mReserveSeatButton;
    private Button mCancelReservationButton;
    private Button mManageSystemButton;
    DBManager dbManager = new DBManager(this);
    DBManagerFlights dbManagerFlights = new DBManagerFlights(this);

    private List<AccountLog> accountLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Interface Definitions
        mCreateAccountButton = (Button) findViewById(R.id.createAccountButton);
        mReserveSeatButton = (Button) findViewById(R.id.reserveSeatButton);
        mCancelReservationButton = (Button) findViewById(R.id.cancelReservationButton);
        mManageSystemButton = (Button) findViewById(R.id.manageSystemButton);


        // if mCreateAccountButton is pressed
        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreateAccountActivity();
            }
        });

        // if mReserveSeatButton is pressed
        mReserveSeatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                launchEnterDepartureAndArrivalInformationActivity();
            }
        });

        // if mCancelReservationButton is pressed
        mCancelReservationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchCancelReservationLoginActivity();
            }
        });

        // if mManageSystemButton is pressed
        mManageSystemButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchSystemActivity();
            }
        });
    }




    // Launcher for Create Account Activity
    private void launchCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    // Launcher for Reserve Flight (Login)
    private void launchEnterDepartureAndArrivalInformationActivity() {
        Intent intent = new Intent(this, EnterDepartureAndArrivalInformationActivity.class);
        startActivity(intent);
    }

    // Launcher for Cancel Reservation Activity
    private void launchCancelReservationLoginActivity()
    {
        Intent intent = new Intent(this, CancelReservationLoginActivity.class);
        startActivity(intent);
    }

    // Launcher for Main System Activity
    private void launchSystemActivity()
    {
        Intent intent = new Intent(this, SystemAdminLoginActivity.class);
        startActivity(intent);
    }
}

/* MAIN TODO
* */