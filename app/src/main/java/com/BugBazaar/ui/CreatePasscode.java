package com.BugBazaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.Models.User;
import com.BugBazaar.R;
import com.BugBazaar.controller.UserAuthSave;

import android.view.View;

public class CreatePasscode extends AppCompatActivity {

    private EditText editTextPasscode;
    private TextView textViewDot;

    private ImageView btnBackspace;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);

        editTextPasscode = findViewById(R.id.editTextPasscode);
        textViewDot = findViewById(R.id.textViewDot);
        btnBackspace = findViewById(R.id.btnBackspace);
        btnCreate = findViewById(R.id.btncreate);

        // Handle backspace button
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = editTextPasscode.getText().toString();
                if (!currentText.isEmpty()) {
                    editTextPasscode.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        // Handle number buttons
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

        // Handle this event when user clicks on Create button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredPasscode = editTextPasscode.getText().toString();

                if(enteredPasscode.length()!=4){

                    Toast.makeText(CreatePasscode.this, "Passcode of 4 numbers!", Toast.LENGTH_SHORT).show();
                    return;

                }

                UserAuthSave.savepasscode(Integer.parseInt(enteredPasscode));
                Toast.makeText(CreatePasscode.this, "  New Passcode created!", Toast.LENGTH_SHORT).show();
                CreateSessiontoken(UserAuthSave.getSavedUsername(),UserAuthSave.getpasscode(),UserAuthSave.getpasscode());

                startActivity(new Intent(getApplicationContext(),NavigationDrawer_Dashboard.class));



            }
        });
    }

    private void CreateSessiontoken(String savedUsername, String getpasscode, String getpasscode1) {



    }

    //Numpad click handling
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