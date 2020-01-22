package com.example.project2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.DB.DBManager;
import androidx.room.Room;

import com.example.project2.DB.DBManagerReservations;
import com.example.project2.DB.Project2DAO;
import com.example.project2.DB.AppDatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CancelReservationConfirmationActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mGoBackButton;
    private Button mConfirmButton;
    private List<AccountLog> accountLogs;
    private Project2DAO Project2DAO;
    DBManagerReservations dbManagerReservations = new DBManagerReservations(this);
    private static DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation_confirmation);

        mTextView = (TextView) findViewById(R.id.textView);
        mGoBackButton = (Button) findViewById(R.id.confirmCancelButton);
        mConfirmButton = (Button) findViewById(R.id.confirmCancelReservationButton);

        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("Username");
        final String flightInfo = bundle.getString("Flight");
        final String[] flightInfoArray = flightInfo.split("\\|");
        final String reservation = flightInfoArray[3];
        //mTextView.setText(flightInfo.toString());

        // Go Back Button
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(getApplicationContext(), "TIME TO GO BACK", Toast.LENGTH_SHORT).show();
                                launchMainActivity();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getApplicationContext(), "Cancellation cancelled", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure you want to cancel?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                Toast.makeText(getApplicationContext(), "REMOVE " + flightInfo + " FROM ACCOUNT", Toast.LENGTH_SHORT).show();
                                dbManagerReservations.open();
                                System.out.println(reservation);
                                dbManagerReservations.delete(reservation);
                                dbManagerReservations.close();
                                launchMainActivity();

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                Toast.makeText(getApplicationContext(), "Confirmation Cancelled", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                String[] data = flightInfo.split("\\|");
                System.out.println(flightInfo);
                double totalPrice = Double.valueOf(data[4]);
                builder.setMessage("Are you sure you want to cancel this flight?: \n" +
                        "Username: " + username + "\n" +
                        "Flight Number: " + data[5] + "\n" +
                        "Departure: " + data[0] + "\n" +
                        "Arrival: " + data[1] + "\n" +
                        "Number of Tickets: " + data[2] + "\n" +
                        "Reservation Number: " + data[3] + "\n" +
                        "Total Amount: $" + df.format(totalPrice))
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });
    }

    void launchMainActivity() {
        Intent intent = new Intent(this, DisplayActivity.class);
        startActivity(intent);
    }
//    private void addToDataBase(String username, String flightID, String departArvInfo, String departTime, String tickets) {
//        String transactionType = "=Cancel Reservation";
//        String usernameSubmit = username;
//        String flightIDSubmit = flightID;
//        String departArvInfoSubmit = departArvInfo;
//        String departTimeInfo = departTime;
//        String ticketsInfo = tickets;
//        Date currentTime = Calendar.getInstance().getTime();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("hh.m. aa");
//        String output = dateFormat.format(currentTime);
//        AccountLog project2 = new AccountLog(transactionType, usernameSubmit, flightIDSubmit, departArvInfoSubmit, ticketsInfo, null, null, output);
//        Project2DAO.insert(project2);
//    }
}
/*public AccountLog(int transactionType, String customerUsername, String flightNumber,
                      String departureArrivalTime, int numberOfTickets, String reservationNumber,
                      int totalAmount, String currentTime)*/