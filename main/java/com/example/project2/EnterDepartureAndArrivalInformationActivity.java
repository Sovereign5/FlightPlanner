package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.DB.DBManagerFlights;

public class EnterDepartureAndArrivalInformationActivity extends AppCompatActivity {

    // Interface Declarations
    private EditText mDepatureCityEditText;
    private EditText mArrivalCityEditText;
    private EditText mNumberOfTicketsEditText;
    private Button mSearchFlightsButton;
    DBManagerFlights dbManagerFlights = new DBManagerFlights(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_departure_and_arrival_information);

        // Interface Definitions
        mDepatureCityEditText = (EditText) findViewById(R.id.depatureEditText);
        mArrivalCityEditText = (EditText) findViewById(R.id.arrivalEditText);
        mNumberOfTicketsEditText = (EditText) findViewById(R.id.numberOfTicketsEditText);
        mSearchFlightsButton = (Button) findViewById(R.id.searchFlightsButton);

        // When search button is pressed
        mSearchFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManagerFlights.open();
                String depatureCityText = mDepatureCityEditText.getText().toString().trim();
                String arrivalCityText = mArrivalCityEditText.getText().toString().trim();
                String numberOfTicketsText = mNumberOfTicketsEditText.getText().toString().trim();
                int numberOfTicketsNumber = Integer.parseInt(numberOfTicketsText);
                try {
                    if (depatureCityText.length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter a departure city.", Toast.LENGTH_SHORT).show();
                    }
                    else if (arrivalCityText.length() == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Please enter arrival city.", Toast.LENGTH_SHORT).show();
                    }
                    else if (numberOfTicketsNumber == 0)
                    {
                        Toast.makeText(getApplicationContext(), "Please enter a number of tickets.", Toast.LENGTH_SHORT).show();
                    }
                    else if (numberOfTicketsNumber > 7)
                    {
                        Toast.makeText(getApplicationContext(), "Cannot order more than 7 tickets", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        int numberOfFlights = dbManagerFlights.checkForFlights(depatureCityText, arrivalCityText, numberOfTicketsNumber);
                        if (numberOfFlights == 0)
                        {
                            Toast.makeText(getApplicationContext(), "No flights with those cities.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            // Send to next screen
                            Toast.makeText(getApplicationContext(), numberOfFlights + " flights found!", Toast.LENGTH_SHORT).show();
                            launchSelectFlightActivity(depatureCityText, arrivalCityText, numberOfTicketsText, numberOfFlights);
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void launchSelectFlightActivity(String departure, String arrival, String tickets, int amount) {
        Intent intent = new Intent(this, SelectFlightActivity.class);
        Bundle bundle = new Bundle();
        int ticketsNumber = Integer.valueOf(tickets);
        bundle.putString("Departure", departure);
        bundle.putString("Arrival", arrival);
        bundle.putInt("Tickets", ticketsNumber);
        bundle.putInt("Amount", amount);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
