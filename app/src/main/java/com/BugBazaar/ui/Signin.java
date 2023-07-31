package com.BugBazaar.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.BugBazaar.Models.CredentialsLoader;
import com.BugBazaar.R;
import com.BugBazaar.utils.PermissionManager;
import com.BugBazaar.utils.PermissionCallback;


public class Signin extends AppCompatActivity implements PermissionCallback {
    private com.BugBazaar.controller.LoginController loginController;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginController = new com.BugBazaar.controller.LoginController();

        permissionManager = new PermissionManager(this, this);


        if (permissionManager.checkExternalStoragePermission()) {
            // Permission is already granted, proceed with your app logic here
            onPermissionGranted();
        } else {
            // Permission is not granted, request it
            permissionManager.requestExternalStoragePermission();
        }




        usernameEditText = findViewById(R.id.editUsername);
        passwordEditText = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.btnLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                boolean isLoggedin= loginController.validateLogin(username, password);
                if (isLoggedin) {
                    // Successful login, do something (e.g., start a new activity)
                    Toast.makeText(Signin.this, "Login successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),PasscodeActivity.class));
                } else {
                    // Failed login, show an error message
                    Toast.makeText(Signin.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied() {

    }


    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied() {

    }
}