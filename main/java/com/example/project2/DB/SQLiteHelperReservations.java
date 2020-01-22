package com.example.project2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperReservations extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_RESERVATIONS = "create table RESERVATIONS ( _id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, departureCityTime TEXT, arrivalCity TEXT, numberOfTickets TEXT, reservationNumber TEXT, totalPrice TEXT, flightNumber TEXT);";
    private static final String DB_NAME = "RESERVATION.DB";
    private static final int DB_VERSION = 1;
    public static final String USERNAME = "username";
    public static final String DEPARTURECITYTIME = "departureCityTime";
    public static final String ARRIVALCITY = "arrivalCity";
    public static final String NUMBEROFTICKETS = "numberOfTickets";
    public static final String RESERVATIONNUMBER = "reservationNumber";
    public static final String TOTALPRICE = "totalPrice";
    public static final String TABLE_NAME_RESERVATIONS = "RESERVATIONS";
    public static final String FLIGHTNUMBER  = "flightNumber";
    public static final String _id = "_id";

    public SQLiteHelperReservations(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_RESERVATIONS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS RESERVATIONS");
        onCreate(db);
    }
}
