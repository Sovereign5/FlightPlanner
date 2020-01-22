package com.example.project2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManagerFlights {
    private Context context;
    private SQLiteDatabase databaseFlights;
    private SQLiteHelperFlights dbHelperFlights;

    public DBManagerFlights(Context c) {
        this.context = c;
    }

    public DBManagerFlights open() throws SQLException {
        this.dbHelperFlights = new SQLiteHelperFlights(this.context);
        this.databaseFlights = this.dbHelperFlights.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelperFlights.close();
    }

    public void insert(String flgtNumber, String depArvInfo, String depTime, int flgtCap, String prcInfo) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelperFlights.FLIGHTNUMBER, flgtNumber);
        contentValue.put(SQLiteHelperFlights.DEPARTUREARRIVALINFO, depArvInfo);
        contentValue.put(SQLiteHelperFlights.DEPARTURETIME, depTime);
        contentValue.put(SQLiteHelperFlights.PRICEINFORMATION, prcInfo);
        contentValue.put(SQLiteHelperFlights.FLIGHTCAPACITY, flgtCap);
        this.databaseFlights.insert(SQLiteHelperFlights.TABLE_NAME_FLIGHTS, null, contentValue);
    }


//    public Cursor fetch() {
//        Cursor cursor = this.databaseFlights.query(SQLiteHelperFlights.TABLE_NAME_FLIGHTS, new String[]{SQLiteHelperFlights._ID, SQLiteHelperFlights.FLIGHTNUMBER, SQLiteHelperFlights.DEPARTUREARRIVALINFO, SQLiteHelperFlights.DEPARTURETIME, SQLiteHelperFlights.FLIGHTCAPACITY, SQLiteHelperFlights.PRICEINFORMATION}, null, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//        return cursor;
//    }

    public int login(String flightNumber)
    {
        try
        {
            int i = 0;
            Cursor cursor = null;
            cursor = this.databaseFlights.rawQuery("Select * from FLIGHTS where flightNumber =" + "\""+ flightNumber.trim() + "\"", null);
            cursor.moveToFirst();
            i = cursor.getCount();
            cursor.close();
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public int checkForFlights(String departure, String arrival, int numberFlights)
    {
        String departmentArrivalInformation = departure + "/" + arrival;
        try
        {
            int i = 0;
            Cursor cursor = null;
            System.out.println(departmentArrivalInformation);
            cursor = this.databaseFlights.rawQuery("Select * from FLIGHTS where departureArrivalInfo =" + "\""+ departmentArrivalInformation.trim() + "\""+" and flightCapacity >=" + "\""+ numberFlights + "\"", null);
            cursor.moveToFirst();
            i = cursor.getCount();
            cursor.close();
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public String[] getInfo(String departure, String arrival, int numberFlights, int amount) {
        String departmentArrivalInformation = departure + "/" + arrival;
        String [] data = new String[amount];
        int i = 0;
        try {
            Cursor cursor = null;
            cursor = this.databaseFlights.rawQuery("Select * from FLIGHTS where departureArrivalInfo =" + "\""+ departmentArrivalInformation.trim() + "\""+" and flightCapacity >=" + "\""+ numberFlights + "\"", null);
            if (cursor.moveToFirst()) {
                do {
                    String flightNumberText = cursor.getString(cursor.getColumnIndex("flightNumber"));
                    String flightDepatureTime = cursor.getString(cursor.getColumnIndex("departureTime"));
                    String flightSeats = cursor.getString(cursor.getColumnIndex("flightCapacity"));
                    String flightPriceText = cursor.getString(cursor.getColumnIndex("priceInformation"));
                    String finalFlightInfo = flightNumberText + "|" + flightDepatureTime + "|" + flightSeats + "|" + flightPriceText + "|" + departmentArrivalInformation.trim();
                    data[i] = finalFlightInfo;
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getInfoFlightNumber(String flightNumber) {
        String finalFlightInfo = null;
        int i = 0;
        try {
            Cursor cursor = null;
            cursor = this.databaseFlights.rawQuery("Select * from FLIGHTS where flightNumber =" + "\""+ flightNumber.trim() + "\"", null);
            if (cursor.moveToFirst()) {
                do {
                    String flightDepartArriveInfo = cursor.getString(cursor.getColumnIndex("departureArrivalInfo"));
                    String flightDepatureTime = cursor.getString(cursor.getColumnIndex("departureTime"));
                    String flightSeats = cursor.getString(cursor.getColumnIndex("flightCapacity"));
                    String flightPriceText = cursor.getString(cursor.getColumnIndex("priceInformation"));
                    finalFlightInfo = flightDepartArriveInfo + "|" + flightDepatureTime + "|" + flightSeats + "|" + flightPriceText;
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFlightInfo;
    }

    public void delete(long _id) {
        this.databaseFlights.delete(SQLiteHelperFlights.TABLE_NAME_FLIGHTS, "_id=" + _id, null);
    }
}
