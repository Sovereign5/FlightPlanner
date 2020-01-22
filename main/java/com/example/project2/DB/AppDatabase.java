package com.example.project2.DB;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.project2.DB.typeConverter.DataTypeConverter;
import com.example.project2.AccountLog;

@Database(entities = {AccountLog.class}, version = 1)
@TypeConverters(DataTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DBNAME = "db-accountlog";
    public static final String ACCOUNTLOG_TABLE = "accountlog";

    public abstract Project2DAO getProject2DAO();
}
