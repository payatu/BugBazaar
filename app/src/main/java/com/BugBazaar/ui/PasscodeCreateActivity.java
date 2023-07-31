package com.BugBazaar.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

public class PasscodeCreateActivity extends AppCompatActivity {

    private EditText editTextPasscode;
    private Button btnCreatePasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpasscode);

        editTextPasscode = findViewById(R.id.editTextPasscode);
        btnCreatePasscode = findViewById(R.id.btnCreatePasscode);

        btnCreatePasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPasscode();
            }
        });
    }

    private void createPasscode() {
        String passcode = editTextPasscode.getText().toString();

        if (TextUtils.isEmpty(passcode)) {
            Toast.makeText(this, "Please enter a passcode.", Toast.LENGTH_SHORT).show();
        } else if (passcode.length() < 4) {
            Toast.makeText(this, "Passcode must be 4 digits.", Toast.LENGTH_SHORT).show();
        } else {
            // Save the passcode to your preference or database
            // You can use SharedPreferences to save the passcode
            // Replace "passcode_preference" with your preferred preference key
            // Save the passcode as a hashed or encrypted value for security purposes
            // For simplicity, we are not showing actual data storage here
            // Replace the code below with actual storage implementation

            // Example of saving the passcode using SharedPreferences
            // Note: This is not a secure way to store passcodes in production
            // Use secure methods like encryption for storing sensitive data
            getSharedPreferences("passcode_preference", MODE_PRIVATE)
                    .edit()
                    .putString("passcode", passcode)
                    .apply();

            // Passcode creation successful, show a success message
            Toast.makeText(this, "Passcode created successfully!", Toast.LENGTH_SHORT).show();

            // Optionally, navigate to the next activity or finish this activity
            // depending on your app's flow
        }
    }
}
