package com.BugBazaar.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

public class PasscodeActivity extends AppCompatActivity {

    private EditText editTextPasscode;
    private Button btnValidate;

    //Hardcoded Passcode is set here. You can remove it
    private static final String CORRECT_PASSCODE = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        editTextPasscode = findViewById(R.id.editTextPasscode);
        btnValidate = findViewById(R.id.btnValidate);

        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePasscode();
            }
        });
    }

    //function for validating Passcode
    private void validatePasscode() {
        String enteredPasscode = editTextPasscode.getText().toString();

        if (TextUtils.isEmpty(enteredPasscode)) {
            Toast.makeText(this, "Please enter the passcode.", Toast.LENGTH_SHORT).show();
        } else {
            if (enteredPasscode.equals(CORRECT_PASSCODE)) {
                // Passcode is correct, do your action here
                Toast.makeText(this, "Passcode is correct!", Toast.LENGTH_SHORT).show();
            } else {
                // Passcode is incorrect, show an error message
                Toast.makeText(this, "Incorrect passcode. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
