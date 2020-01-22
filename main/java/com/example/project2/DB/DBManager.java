package com.example.project2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public DBManager(Context c) {
        this.context = c;
    }

    public DBManager open() throws SQLException {
        this.dbHelper = new SQLiteHelper(this.context);
        this.database = this.dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert(String name, String pass) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(SQLiteHelper.USERNAME, name);
        contentValue.put(SQLiteHelper.PASSWORD, pass);
        contentValue.put(SQLiteHelper.RESERVATIONS, "");
        this.database.insert(SQLiteHelper.TABLE_NAME_ACCOUNTS, null, contentValue);
    }

    public int login(String username, String password)
    {
        try
        {
            int i = 0;
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from ACCOUNTS where userName =" + "\""+ username.trim() + "\""+" AND password2 =" + "\""+ password.trim() + "\"", null);
            if (cursor.moveToFirst()) {
                i++;
            } while (cursor.moveToNext());
            cursor.close();
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public int loginCreate(String username, String password)
    {
        try
        {
            int i = 0;
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from ACCOUNTS where userName =" + "\""+ username.trim() + "\""+" OR password2 =" + "\""+ password.trim() + "\"", null);
            if (cursor.moveToFirst()) {
                i++;
            } while (cursor.moveToNext());
            cursor.close();
            return i;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public Cursor fetch() {
        Cursor cursor = this.database.query(SQLiteHelper.TABLE_NAME_ACCOUNTS, new String[]{SQLiteHelper._ID, SQLiteHelper.USERNAME, SQLiteHelper.PASSWORD}, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Maybe use this for updating the Reservations
    public int update(String name, String res) {
        System.out.println(name);
        String reservations = "";
        long _id = 0;
        try {
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from ACCOUNTS where userName =" + "\""+ name + "\"", null);
            if (cursor.moveToFirst())
            {
                do {
                    reservations = cursor.getString(cursor.getColumnIndex("Reservations"));
                    _id = cursor.getLong(cursor.getColumnIndex("_id"));
                } while (cursor.moveToNext());
            }
            res = reservations + " " + res;
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLiteHelper.RESERVATIONS, res);
            return this.database.update(SQLiteHelper.TABLE_NAME_ACCOUNTS, contentValues, "_id="+_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();

        String s;
        Cursor cursor = db.rawQuery("SELECT * FROM ACCOUNTS WHERE " + username + " =? AND " + password + " =? ", null);

        if (cursor.getCount() <= 0)
        {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }
    }

    public String getReservations(String username, String pass) {
        int i = 0;
        String userReservation = null;
        try {
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from ACCOUNTS where userName =" + "\""+ username.trim() + "\""+" and password2 =" + "\""+ pass.trim() + "\"", null);
            if (cursor.moveToFirst()) {
                do {
                    userReservation = cursor.getString(cursor.getColumnIndex("Reservations"));
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userReservation;
    }

    public int updateCancel(String name, String resRemove) {
        System.out.println("USERNAME: " + name);
        String reservations = "";
        long _id = 0;
        try {
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from ACCOUNTS where userName =" + "\""+ name + "\"", null);
            if (cursor.moveToFirst())
            {
                do {
                    reservations = cursor.getString(cursor.getColumnIndex("Reservations"));
                    _id = cursor.getLong(cursor.getColumnIndex("_id"));
                } while (cursor.moveToNext());
            }
            //res = reservations + " " + res;
            reservations = reservations.replace(resRemove, "");
            ContentValues contentValues = new ContentValues();
            contentValues.put(SQLiteHelper.RESERVATIONS, reservations);
            System.out.println("UPDATED RESERVATIONS LIST: " + reservations);
            return this.database.update(SQLiteHelper.TABLE_NAME_ACCOUNTS, contentValues, "_id="+_id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int checkReservations(String username, String password)
    {
        try
        {
            Cursor cursor = null;
            cursor = this.database.rawQuery("Select * from ACCOUNTS where userName =" + "\""+ username.trim() + "\""+" and password2 =" + "\""+ password.trim() + "\"", null);
            if (cursor.moveToFirst()) {
                do {
                    String reservations = cursor.getString(cursor.getColumnIndex("Reservations"));
                    if (reservations.trim().equals("")) {
                        return 0;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
            return 1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public void delete(long _id) {
        this.database.delete(SQLiteHelper.TABLE_NAME_ACCOUNTS, "_id=" + _id, null);
    }
}

/* DB TODO
* Create Reservations DB
*   - Username (Use to search with DBManager)
*   - Flight Number (Use to search with DBManagerFlights)
*   - Departure City, Departure Time
*   - Arrival City
*   - Number of Tickets
*   - Reservation Number (Long ID, maybe?)
*   - Total price (Number of tickets * price of tickets)
*
* Reservation DB is added to during
*   - Creating a Reservation
*   - Looking up Reservations for an account
*   - Deleting a reservation (Might be easier)
* */
