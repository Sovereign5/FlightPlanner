package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.DB.DBManagerFlights;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewFlightActivity extends AppCompatActivity {

    // Interface Declarations
    private EditText mFlightNumberEditText;
    private EditText mDepartureCityExitText;
    private EditText mArrivalCityEditText;
    private EditText mDepartureTimeEditText;
    private EditText mFlightCapacityEditText;
    private EditText mPriceInformationEditText;
    private Button mCreateFlightButton;

    // Create Database object
    DBManagerFlights dbManagerFlights = new DBManagerFlights(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_flight);

        // Interface Definitions
        mFlightNumberEditText = (EditText) findViewById(R.id.flightNumberEditText);
        mDepartureCityExitText = (EditText) findViewById(R.id.departureCityEditText);
        mArrivalCityEditText = (EditText) findViewById(R.id.arrivalCityEditText);
        mDepartureTimeEditText = (EditText) findViewById(R.id.departureTimeEditText);
        mFlightCapacityEditText = (EditText) findViewById(R.id.flightCapacityEditText);
        mPriceInformationEditText = (EditText) findViewById(R.id.priceInformationEditText);
        mCreateFlightButton = (Button) findViewById(R.id.createFlightButton);

        mCreateFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFlightNumberEditText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter Flight Number", Toast.LENGTH_SHORT).show();
                }
                else if (mDepartureCityExitText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter Departure City", Toast.LENGTH_SHORT).show();
                }
                else if (mArrivalCityEditText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please Enter Arrival City", Toast.LENGTH_SHORT).show();
                }
                else if (mDepartureTimeEditText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter Departure Time", Toast.LENGTH_SHORT).show();
                }
                else if (mFlightCapacityEditText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter Flight Capacity", Toast.LENGTH_SHORT).show();
                }
                else if (mPriceInformationEditText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Please enter Price Information", Toast.LENGTH_SHORT).show();
                }
                else {
                    try
                    {
                        dbManagerFlights.open();
                        String mDepartureArrivalInformation = mDepartureCityExitText.getText().toString().trim() + "/" + mArrivalCityEditText.getText().toString().trim();
                        final String flightNumber = mFlightNumberEditText.getText().toString().trim();
                        final String departureArrivalInformation = mDepartureArrivalInformation.trim();
                        final String departureTime = mDepartureTimeEditText.getText().toString().trim();
                        String flightCapacity = mFlightCapacityEditText.getText().toString().trim();
                        final int flightCapacityInt = Integer.parseInt(flightCapacity);
                        final String priceInformation = mPriceInformationEditText.getText().toString().trim();
                        String query = "Select * From FLIGHTS where flightNumber = '"+flightNumber+"'";
                        int login = dbManagerFlights.login(flightNumber);
                        if (login == 0)
                        {
                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which){
                                        case DialogInterface.BUTTON_POSITIVE:
                                            dbManagerFlights.insert(flightNumber, departureArrivalInformation, departureTime, flightCapacityInt, priceInformation);
                                            Toast.makeText(getApplicationContext(), "Flight Created!", Toast.LENGTH_SHORT).show();
                                            launchMain();
                                            break;

                                        case DialogInterface.BUTTON_NEGATIVE:
                                            Toast.makeText(getApplicationContext(), "Flight Cancelled", Toast.LENGTH_SHORT).show();
                                            launchMain();
                                            break;
                                    }
                                }
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setMessage("Are you sure you want to confirm this flight?" + "\n" +
                                    "Flight Number: " + flightNumber + "\n" +
                                    "Flight Departure/Arrival City: " + departureArrivalInformation + "\n" +
                                    "Flight Departure Time: " + departureTime + "\n" +
                                    "Flight Capacity" + flightCapacity + "\n" +
                                    "Ticket Price" + priceInformation)
                                    .setPositiveButton("Yes", dialogClickListener)
                                    .setNegativeButton("No", dialogClickListener).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Flight already exists.", Toast.LENGTH_SHORT).show();
                            launchMain();

                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void launchMain()
    {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
}