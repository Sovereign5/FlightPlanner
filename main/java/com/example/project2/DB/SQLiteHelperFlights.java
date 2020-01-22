package com.example.project2.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelperFlights extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_FLIGHTS = " create table FLIGHTS ( _id INTEGER PRIMARY KEY AUTOINCREMENT, flightNumber TEXT NOT NULL , departureArrivalInfo TEXT , departureTime TEXT , flightCapacity INTEGER , priceInformation TEXT);";
    private static final String DB_NAME = "FLIGHTS.DB";
    private static final int DB_VERSION = 1;
    public static final String FLIGHTNUMBER = "flightNumber";
    public static final String DEPARTUREARRIVALINFO = "departureArrivalInfo";
    public static final String DEPARTURETIME = "departureTime";
    public static final String  FLIGHTCAPACITY = "flightCapacity";
    public static final String PRICEINFORMATION = "priceInformation";
    public static final String TABLE_NAME_FLIGHTS = "FLIGHTS";
    public static final String _ID = "_id";

    public SQLiteHelperFlights(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FLIGHTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS FLIGHTS");
        onCreate(db);
    }
}
