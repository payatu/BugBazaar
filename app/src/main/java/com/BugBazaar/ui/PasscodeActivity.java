package com.BugBazaar.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

public class PasscodeActivity extends AppCompatActivity {

    private EditText editTextPasscode;
    private TextView textViewDot;
    private ImageView btnBackspace; // Declare the ImageView for backspace button
    private Button btnValidate; // Declare the Button for validate button

    private String desiredPasscode = "1234"; // Replace with your desired passcode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        editTextPasscode = findViewById(R.id.editTextPasscode);
        textViewDot = findViewById(R.id.textViewDot);
        btnValidate = findViewById(R.id.btnValidate);
        btnBackspace = findViewById(R.id.btnBackspace); // Initialize the backspace ImageView

        // OnClickListener for the backspace ImageView
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current passcode text from the EditText
                String passcode = editTextPasscode.getText().toString();

                // If the passcode is not empty, remove the last character
                if (!passcode.isEmpty()) {
                    passcode = passcode.substring(0, passcode.length() - 1);
                }

                // Set the updated passcode text back to the EditText
                editTextPasscode.setText(passcode);
            }
        });

        //Passcode Validator code
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPasscode = editTextPasscode.getText().toString();
                if (enteredPasscode.equals(desiredPasscode)) {
                    // Passcode is correct, proceed to the next activity. Add next activity here....
                    Toast.makeText(PasscodeActivity.this, "Passcode correct!", Toast.LENGTH_SHORT).show();
                    // Implement your logic to proceed to the next activity here
                } else {
                    // Passcode is incorrect, show an error message
                    Toast.makeText(PasscodeActivity.this, "Incorrect passcode. Try again.", Toast.LENGTH_SHORT).show();
                    // Clear the entered passcode
                    editTextPasscode.setText("");
                    textViewDot.setText("Enter Passcode");
                }
            }
        });

        // Handle numpad buttons click
        findViewById(R.id.lbtn1).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn2).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn3).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn4).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn5).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn6).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn7).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn8).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn9).setOnClickListener(numpadClickListener);
        findViewById(R.id.lbtn0).setOnClickListener(numpadClickListener);
    }

    private View.OnClickListener numpadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String currentPasscode = editTextPasscode.getText().toString();
            if (currentPasscode.length() < 4) {
                String digit = ((Button) view).getText().toString();
                currentPasscode += digit;
                editTextPasscode.setText(currentPasscode);
            }
        }
    };
}
