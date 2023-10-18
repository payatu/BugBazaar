package com.BugBazaar.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.BugBazaar.ui.CreatePasscode;
import com.BugBazaar.ui.PasscodeActivity;
import com.BugBazaar.ui.Signin;

public class checkAuth extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is logged in
        UserAuthSave userAuthSave = new UserAuthSave(getApplicationContext());

        if (UserAuthSave.isLoggedIn()) {

            Toast.makeText(this, "Welcome back !!", Toast.LENGTH_SHORT).show();

            Log.d("passcodemait", String.valueOf(UserAuthSave.getpasscode_flag()));

            if (UserAuthSave.getpasscode_flag()) {

                startActivity(new Intent(this, PasscodeActivity.class));
            } else {
                startActivity(new Intent(this, CreatePasscode.class));


            }




        }
    }
}

