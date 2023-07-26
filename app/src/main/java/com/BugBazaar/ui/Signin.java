package com.BugBazaar.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.BugBazaar.Models.CredentialsLoader;
import com.BugBazaar.R;

public class Signin extends AppCompatActivity {
    private com.BugBazaar.controller.LoginController loginController;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        String obfuscatedUsername = CredentialsLoader.getUsername();
//        String obfuscatedPassword = CredentialsLoader.getPassword();
        loginController = new com.BugBazaar.controller.LoginController();

        usernameEditText = findViewById(R.id.editUsername);
        passwordEditText = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                boolean isLoggedin= loginController.validateLogin("admin", "BugBazaarSeccool");
                if (isLoggedin) {
                    // Successful login, do something (e.g., start a new activity)
                    Toast.makeText(Signin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                } else {
                    // Failed login, show an error message
                    Toast.makeText(Signin.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}