package com.example.project2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.project2.DB.AppDatabase;

import java.util.Date;
@Entity(tableName = AppDatabase.ACCOUNTLOG_TABLE)
public class AccountLog {
    @PrimaryKey(autoGenerate = true)
    private String mAccountNumber;
    private String mTransactionType;
    private String mCustomerUsername;
    private String mFlightNumber;
    private String mDepartureArrivalInformation;
    private String mNumberOfTickets;
    private String mReservationNumber;
    private String mTotalAmount;
    private Date mCurrentTime;

    public AccountLog(String transactionType, String customerUsername, String flightNumber,
                      String departureArrivalTime, String numberOfTickets, String reservationNumber,
                      String totalAmount, String currentTime)
    {
        mTransactionType = transactionType;
        mCustomerUsername = customerUsername;
        mFlightNumber = flightNumber;
        mDepartureArrivalInformation = departureArrivalTime;
        mNumberOfTickets = numberOfTickets;
        mReservationNumber = reservationNumber;
        mTotalAmount = totalAmount;
        mCurrentTime = new Date();
    }

    public String getmAccountNumber() {
        return mAccountNumber;
    }

    public void setmAccountNumber(String mAccountNumber) {
        this.mAccountNumber = mAccountNumber;
    }

    public String getmTransactionType() {
        return mTransactionType;
    }

    public void setmTransactionType(String mTransactionType) {
        this.mTransactionType = mTransactionType;
    }

    public String getmCustomerUsername() {
        return mCustomerUsername;
    }

    public void setmCustomerUsername(String mCustomerUsername) {
        this.mCustomerUsername = mCustomerUsername;
    }

    public String getmFlightNumber() {
        return mFlightNumber;
    }

    public void setmFlightNumber(String mFlightNumber) {
        this.mFlightNumber = mFlightNumber;
    }

    public String getmDepartureArrivalInformation() {
        return mDepartureArrivalInformation;
    }

    public void setmDepartureArrivalInformation(String mDepartureArrivalInformation) {
        this.mDepartureArrivalInformation = mDepartureArrivalInformation;
    }

    public String getmNumberOfTickets() {
        return mNumberOfTickets;
    }

    public void setmNumberOfTickets(String mNumberOfTickets) {
        this.mNumberOfTickets = mNumberOfTickets;
    }

    public String getmReservationNumber() {
        return mReservationNumber;
    }

    public void setmReservationNumber(String mReservationNumber) {
        this.mReservationNumber = mReservationNumber;
    }

    public String getmTotalAmount() {
        return mTotalAmount;
    }

    public void setmTotalAmount(String mTotalAmount) {
        this.mTotalAmount = mTotalAmount;
    }

    public Date getmCurrentTime() {
        return mCurrentTime;
    }

    public void setmCurrentTime(Date mCurrentTime) {
        this.mCurrentTime = mCurrentTime;
    }

    @Override
    public String toString()
    {
        return "AccountLog{" +
                "mTransactionType=" + mTransactionType +
                ", mCustomerUsername=" + mCustomerUsername +
                ", mFlightNumber=" + mFlightNumber +
                ", mDepartureArrivalInformation=" + mDepartureArrivalInformation +
                ", mNumberOfTickets=" + mNumberOfTickets +
                ", mReservationNumber=" + mReservationNumber +
                ", mTotalAmount=" + mTotalAmount +
                ", mCurrentTime=" + mCurrentTime +
                "}";
    }
}
