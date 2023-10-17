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
import com.BugBazaar.controller.UserAuthSave;
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


        UserAuthSave userAuthSave = new UserAuthSave(getApplicationContext()); // 'this' refers to the Activity's context


        if(UserAuthSave.isLoggedIn()){

            Toast.makeText(Signin.this, "Welcome back !!", Toast.LENGTH_SHORT).show();

            Log.d("passcodemait", String.valueOf(UserAuthSave.getpasscode_flag()));

            if(UserAuthSave.getpasscode_flag()){

                startActivity(new Intent(this,PasscodeActivity.class));
            }

            else {
                startActivity(new Intent(this,CreatePasscode.class));


            }

            return;






        }

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

                    UserAuthSave.saveUserCredentials(username,password, true);

                    // Successful login, do something (e.g., start a new activity)
                    Toast.makeText(Signin.this, "Login successful!", Toast.LENGTH_SHORT).show();


                        startActivity(new Intent(getApplicationContext(),CreatePasscode.class));
                } else {

//                    UserAuthSave.saveUserCredentials(username,password, false);
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

}