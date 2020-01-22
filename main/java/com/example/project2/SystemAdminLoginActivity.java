package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SystemAdminLoginActivity extends AppCompatActivity {

    // Interface Declarations
    private EditText mAdminUsernameEditText;
    private EditText mAdminPasswordEditText;
    private Button mAdminLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_admin_login);

        // Interface Definitions
        mAdminUsernameEditText = (EditText) findViewById(R.id.adminUsernameEditText);
        mAdminPasswordEditText = (EditText) findViewById(R.id.adminPasswordEditText);
        mAdminLoginButton = (Button) findViewById(R.id.adminLoginButton);

        mAdminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mAdminUsernameEditText.getText().toString().trim();
                String pass = mAdminPasswordEditText.getText().toString().trim();

                if (!username.equals("admin2") || (!pass.equals("admin2")))
                {
                    Toast.makeText(getApplicationContext(), "Information incorrect.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    launchAdminLogInformationActivity();
                }
            }
        });

    }
    private void launchAdminLogInformationActivity() {
        Intent intent = new Intent(this, AdminLogInformationActivity.class);
        startActivity(intent);
    }
}
