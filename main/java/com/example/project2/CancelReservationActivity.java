package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.DB.DBManager;
import com.example.project2.DB.DBManagerReservations;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CancelReservationActivity extends AppCompatActivity {
    private ListView mListView;
    DBManager dbManager = new DBManager(this);
    DBManagerReservations dbManagerReservations = new DBManagerReservations(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_reservation);

        Bundle bundle = getIntent().getExtras();
        final String username = bundle.getString("Username").trim();
        String pass = bundle.getString("Password").trim();
        mListView=(ListView)findViewById(R.id.listView);

        dbManagerReservations.open();
        ArrayList<String> reservations = dbManagerReservations.getReservation(username);
        for (int i = 0; i < reservations.size(); i++)
        {

        }
        dbManagerReservations.close();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, reservations);

        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CancelReservationActivity.this, CancelReservationConfirmationActivity.class);
                Object o= mListView.getItemAtPosition(position);
                String str = o.toString();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                launchCancelReservationConfirmationActivity(username, str);
            }
        });
    }
    private void launchCancelReservationConfirmationActivity(String username, String flightInfo)
    {
        Intent intent = new Intent(this, CancelReservationConfirmationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Username", username);
        bundle.putString("Flight", flightInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
