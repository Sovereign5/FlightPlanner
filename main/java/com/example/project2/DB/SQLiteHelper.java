package com.example.project2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE_ACCOUNTS = " create table ACCOUNTS ( _id INTEGER PRIMARY KEY AUTOINCREMENT, password2 TEXT NOT NULL , userName TEXT , Reservations TEXT);";
    private static final String DB_NAME = "ACCOUNTS.DB";
    private static final int DB_VERSION = 2;
    public static final String PASSWORD = "password2";
    public static final String USERNAME = "userName";
    public static final String RESERVATIONS = "Reservations";
    public static final String TABLE_NAME_ACCOUNTS = "ACCOUNTS";
    public static final String _ID = "_id";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCOUNTS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS STUDENTS");
        onCreate(db);
    }
}
