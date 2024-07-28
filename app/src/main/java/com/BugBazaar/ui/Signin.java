package com.BugBazaar.ui;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.BugBazaar.R;
import com.BugBazaar.controller.UserAuthSave;
import com.BugBazaar.utils.PermissionManager;
import com.BugBazaar.utils.PermissionCallback;

import java.math.BigInteger;
import java.security.SecureRandom;


public class Signin extends AppCompatActivity implements PermissionCallback {
    private com.BugBazaar.controller.LoginController loginController;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        usernameEditText = findViewById(R.id.editUsername);
        passwordEditText = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.btnLogin);
        // Initialize the SessionManager in your activity
        SessionManager sessionManager = new SessionManager(this);
        UserAuthSave userAuthSave = new UserAuthSave(getApplicationContext()); // 'this' refers to the Activity's context

        Intent getIntent=getIntent();
        boolean isNavigatedHere = getIntent.getBooleanExtra("isNavigatedhere",false);

        if(sessionManager.isLoggedIn()){

            Toast.makeText(Signin.this, "You are already logged in. Welcome back !!", Toast.LENGTH_SHORT).show();
            //If getpasscode_flag is true, navigate to "Enter passcode" activity

            if(userAuthSave.getpasscode_flag()){
                startActivity(new Intent(this,PasscodeActivity.class));
            }
            //If getpasscode_flag is false, navigate to "Create passcode" activity
             else{
                Toast.makeText(getApplicationContext(),"Please create 4 digit passcode",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,CreatePasscode.class));
            }
            finish(); // Finish the Signin activity to prevent it from being visib
        } else if(!sessionManager.isLoggedIn() && isNavigatedHere==true){
        }
        else {
            // User is not logged in, navigate directly to the main dashboard
            startActivity(new Intent(this, NavigationDrawer_Dashboard.class));
            finish(); // Finish the Signin activity to prevent it from being visible
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


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                //This will fetch hex username and password  from CredentialLoader and compare it with user provided values class.
                // It will return true if values are correct. Will return false if values are incorrect.
                boolean isLoggedin= loginController.validateLogin(username, password);

                if (isLoggedin==true) {
                    String randomToken = TokenGenerator.generateRandomToken(64);
                    sessionManager.setLoggedIn(true);
                    userAuthSave.saveUserData( randomToken,isLoggedin);
                    userAuthSave.saveuserCred(username,password);
                    sessionManager.setLoggedIn(true);
                    // Successful login, do something (e.g., start a new activity)
                    Toast.makeText(Signin.this, "Login successful!", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(getApplicationContext(),CreatePasscode.class));
                } else {
                    // Failed login, show an error message
                    Toast.makeText(Signin.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static class TokenGenerator {

        public static String generateRandomToken(int length) {
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[length / 2];
            random.nextBytes(bytes);
            BigInteger tokenValue = new BigInteger(1, bytes);

            // Convert the random bytes to a hexadecimal string
            String token = tokenValue.toString(16);

            // If the generated token is shorter than the desired length, pad with zeros
            while (token.length() < length) {
                token = "0" + token;
            }

            return token;
        }
    }

    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied() {

    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,NavigationDrawer_Dashboard.class));
        finish();


    }


}