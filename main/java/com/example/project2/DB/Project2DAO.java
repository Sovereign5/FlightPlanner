package com.example.project2.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project2.AccountLog;
import java.util.List;

@Dao
public interface Project2DAO {
    @Insert
    void insert(AccountLog... accountLogs);

    @Update
    void update(AccountLog... accountLogs);

    @Delete
    void delete(AccountLog... accountLogs);

    @Query("Select * FROM " + AppDatabase.ACCOUNTLOG_TABLE + " ORDER BY mCurrentTime DESC")
    List<AccountLog> getAllAccountLogs();

    @Query("SELECT * FROM " + AppDatabase.ACCOUNTLOG_TABLE + " WHERE mAccountNumber = :accountId")
    AccountLog getAccountsById(int accountId);
}
