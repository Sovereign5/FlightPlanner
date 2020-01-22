package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.project2.DB.Project2DAO;
import com.example.project2.DB.AppDatabase;
import androidx.room.Room;

import java.util.List;

public class AdminLogInformationActivity extends AppCompatActivity {

    // Interface Declarations
    private Button mContinueButton;
    private TextView mMainDisplay;

    List<AccountLog> accountLogs;
    Project2DAO projectDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_log_information);

        mContinueButton = (Button) findViewById(R.id.continueButton);
        mMainDisplay = (TextView) findViewById(R.id.mainDisplay);

        mContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAddNewFlightActivity();
            }
        });
        //refreshDisplay();
    }
    private void launchAddNewFlightActivity() {
        Intent intent = new Intent(this, AddNewFlightActivity.class);
        startActivity(intent);
    }
    public void refreshDisplay()
    {
        accountLogs = projectDAO.getAllAccountLogs();

        if (!accountLogs.isEmpty())
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (AccountLog accountLog : accountLogs)
            {
                if (accountLog != null)
                {
                    stringBuilder.append(accountLog.toString());
                    mMainDisplay.setText(stringBuilder.toString());
                }
            }
        }
    }
}
