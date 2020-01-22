package com.example.project2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBManagerReservations {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelperReservations dbHelper;

    public DBManagerReservations(Context c) {
        this.context = c;
    }

    public DBManagerReservations open() throws SQLException {
        this.dbHelper = new SQLiteHelperReservations(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(String username, String departureCity, String departureTime, String arrivalCity, int numberOfTickets, String flightNumber, double price) {
        String departureCityTime = departureCity + "/" + departureTime;
        double totalPrice = numberOfTickets * price;
        String reservationNumber = flightNumber + username + numberOfTickets;
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelperReservations.USERNAME, username);
        contentValues.put(SQLiteHelperReservations.DEPARTURECITYTIME, departureCityTime);
        contentValues.put(SQLiteHelperReservations.ARRIVALCITY, arrivalCity);
        contentValues.put(SQLiteHelperReservations.NUMBEROFTICKETS, numberOfTickets);
        contentValues.put(SQLiteHelperReservations.RESERVATIONNUMBER, reservationNumber);
        contentValues.put(SQLiteHelperReservations.TOTALPRICE, totalPrice);
        contentValues.put(SQLiteHelperReservations.FLIGHTNUMBER, flightNumber);
        this.database.insert(SQLiteHelperReservations.TABLE_NAME_RESERVATIONS, null, contentValues);
    }

    public ArrayList getReservation(String username) {
        ArrayList<String> reservations = new ArrayList<>();
        int i = 0;
        try {
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from RESERVATIONS where username =" + "\"" + username + "\"", null);
            if (cursor.moveToFirst())
            {
                do {
                    String departureCityTimeText = cursor.getString(cursor.getColumnIndex("departureCityTime"));
                    String arrivalCityNumber = cursor.getString(cursor.getColumnIndex("arrivalCity"));
                    int numberOfTicketsInt = cursor.getInt(cursor.getColumnIndex("numberOfTickets"));
                    String numberOfTicketsText = String.valueOf(numberOfTicketsInt);
                    String reservationNumberText = cursor.getString(cursor.getColumnIndex("reservationNumber"));
                    double priceNumber = cursor.getDouble(cursor.getColumnIndex("totalPrice"));
                    String flightNumber = cursor.getString(cursor.getColumnIndex("flightNumber"));
                    String priceText = String.valueOf(priceNumber);
                    String finalText = departureCityTimeText + "|" + arrivalCityNumber + "|" + numberOfTicketsText  + "|" + reservationNumberText  + "|" +priceText + "|" + flightNumber;
                    reservations.add(finalText);
                    i++;
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return reservations;
    }

    public void delete(String flightReservation)
    {
        try {
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from RESERVATIONS where reservationNumber =" + "\""+ flightReservation + "\"", null);
            if (cursor.moveToFirst())
            {
                long _id = cursor.getLong(cursor.getColumnIndex("_id"));
                this.database.delete(SQLiteHelperReservations.TABLE_NAME_RESERVATIONS, "_id=" +  _id, null);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
