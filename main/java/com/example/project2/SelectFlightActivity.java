package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import com.example.project2.DB.DBManagerFlights;
import com.example.project2.DB.DBManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectFlightActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_flight);
        Bundle bundle = getIntent().getExtras();
        String departureCityText = bundle.getString("Departure");
        String arrivalCityText = bundle.getString("Arrival");
        final int numberOfTicketsText = bundle.getInt("Tickets");
        int amount = bundle.getInt("Amount");
        DBManagerFlights dbManagerFlights = new DBManagerFlights(this);

        dbManagerFlights.open();
        String[] data;
        data = dbManagerFlights.getInfo(departureCityText, arrivalCityText, numberOfTicketsText, amount);

        mListView=(ListView)findViewById(R.id.listView);
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> displayArrayList = new ArrayList<>();
        for (int i = 0; i < amount; i++)
        {
            arrayList.add(data[i]);
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SelectFlightActivity.this, LoginActivity.class);
                Object o =mListView.getItemAtPosition(position);
                String str = o.toString();
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                launchLoginActivity(str, numberOfTicketsText);

            }
        });
    }

    private void launchLoginActivity(String flight, int tickets) {
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Flight", flight);
        bundle.putInt("Tickets", tickets);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
