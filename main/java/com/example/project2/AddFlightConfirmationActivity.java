package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.DB.DBManagerReservations;
import androidx.room.Room;
import com.example.project2.DB.Project2DAO;
import com.example.project2.DB.AppDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class AddFlightConfirmationActivity extends AppCompatActivity {

    // Declarations
    DBManagerReservations dbManagerReservations = new DBManagerReservations(this);
    private TextView mFlightTextView;
    private Button mGoBackButton;
    private Button mConfirmButton;

    private List<AccountLog> accountLog;
    private Project2DAO Project2DAO;
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight_confirmation);

        mFlightTextView = (TextView) findViewById(R.id.flightTextView);
        mConfirmButton = (Button) findViewById(R.id.confirmBUtton);
        mGoBackButton = (Button) findViewById(R.id.goBackButton);



        Bundle bundle = getIntent().getExtras();
        final String flightText = bundle.getString("Flight");
        final String usernameText = bundle.getString("Username");
        final int ticketsNumber = bundle.getInt("Tickets")
;

        mFlightTextView.setText(flightText);

        // If the user clicks to go forward
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(getApplicationContext(), "ADD " + flightText + "TO RESERVATIONS", Toast.LENGTH_SHORT).show();
                                String[] lister = flightText.split("\\|");
                                String[] splitter = lister[4].split("/");
                                String price = lister[3].replace("$", "");
                                double priceNumber = Double.parseDouble(price);
                                int seatsNumber = Integer.parseInt(lister[2]);
                                dbManagerReservations.open();
                                dbManagerReservations.insert(usernameText, splitter[0], lister[1], splitter[1], ticketsNumber, lister[0], priceNumber);
                                launchMainActivity();

                                /*
                                * I/System.out: Username I am looking up: Username1
                                    Otter201
                                    11:00(AM)
                                    5
                                    $200.50
                                    Monterey/Seattle
                                */
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getApplicationContext(), "Confirmation Cancelled", Toast.LENGTH_SHORT).show();
                                launchMainActivity();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String[] lister = flightText.split("\\|");
                String[] splitter = lister[4].split("/");
                String price = lister[3].replace("$", "");
                double priceNumber = Double.parseDouble(price);
                double totalPrice = priceNumber * ticketsNumber;
                builder.setMessage("Are you sure you want to confirm this flight?: \n"
                        + "Username: " + usernameText + "\n"
                        + "Flight Number: " + lister[0] + "\n"
                        + "Departure: " + splitter[0] + "/" + lister[1] + "\n"
                        + "Arrival: " + splitter[1] + "\n"
                        + "Number of Tickets: " + ticketsNumber + "\n"
                        + "Reservation Number: " + splitter[0] + usernameText + ticketsNumber + "\n"
                        + "Total Amount: $" + df.format(totalPrice))
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        // If the user clicks to go back
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(getApplicationContext(), "TIME TO GO BACK", Toast.LENGTH_SHORT).show();
                                launchMainActivity();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getApplicationContext(), "Cancellation cancelled", Toast.LENGTH_SHORT).show();
                                launchMainActivity();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String[] lister = flightText.split("\\|");
                String[] splitter = lister[4].split("/");
                String price = lister[3].replace("$", "");
                double priceNumber = Double.parseDouble(price);
                double totalPrice = priceNumber * ticketsNumber;
                builder.setMessage("Are you sure you want to cancel this flight?: \n"
                        + "Username: " + usernameText + "\n"
                        + "Flight Number: " + lister[0] + "\n"
                        + "Departure: " + splitter[0] + "/" + lister[1] + "\n"
                        + "Arrival: " + splitter[1] + "\n"
                        + "Number of Tickets: " + ticketsNumber + "\n"
                        + "Reservation Number: " + splitter[0] + usernameText + ticketsNumber + "\n"
                        + "Total Amount: $" + df.format(totalPrice))
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }
    void launchMainActivity()
    {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
    private void addToDataBase(String username, String flightID, String departArvInfo, String departTime, String tickets) {
        String transactionType = "=Make Reservation";
        String usernameSubmit = username;
        String flightIDSubmit = flightID;
        String departArvInfoSubmit = departArvInfo;
        String departTimeInfo = departTime;
        String ticketsInfo = tickets;
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.m. aa");
        String output = dateFormat.format(currentTime);
        AccountLog project2 = new AccountLog(transactionType, usernameSubmit, flightIDSubmit, departArvInfoSubmit, null, null, ticketsInfo, output);
        Project2DAO.insert(project2);
    }
}
/*public AccountLog(int transactionType, String customerUsername, String flightNumber,
                      String departureArrivalTime, int numberOfTickets, String reservationNumber,
                      int totalAmount, String currentTime)*/