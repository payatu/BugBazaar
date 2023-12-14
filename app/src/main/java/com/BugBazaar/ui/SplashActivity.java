package com.BugBazaar.ui;


import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.BugBazaar.R;
import com.BugBazaar.ui.detectAppInt.checkroot;
import com.BugBazaar.utils.AlertDialogManager;
import com.bug.hook.checkdetect;
import com.bug.hook.runtime;
import com.bug.hook.*;
import com.darvin.security.DetectMagisk;
import com.darvin.security.Native;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SplashActivity extends AppCompatActivity  implements DetectMagisk.DetectionListener {

    private static final int SPLASH_TIMEOUT = 2000; //half second

    AlertDialogManager alertDialogManager = new AlertDialogManager();


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Fetch the switch states
                boolean[] switchStates = Checklevel();
                boolean switch1State = switchStates[0];
                boolean switch2State = switchStates[1];
                boolean switch3State = switchStates[2];

                // Perform an operation on switch1State
                if (switch1State) {
                    showToast("Easy Protection");

                    // Create an instance of the checkroot class and pass the current context
                    checkroot rootChecker = new checkroot(SplashActivity.this);

                    // Call the checkRootBeer method
                    rootChecker.checkRootBeer();


                } else if (switch2State) {


                    //Log.d("hacker","hacker");

                    DetectMagisk detectMagisk = new DetectMagisk(getApplicationContext());
                    detectMagisk.setDetectionListener(SplashActivity.this);
                    // Start Magisk detection
                    detectMagisk.startMagiskDetection();

                }




                else if (switch3State) {


                    alertDialogManager.showRootedDeviceAlert(SplashActivity.this,"WE ARE IN PROGRESS");
                    finishAffinity();

                }

                else {

                   // Log.d("hello","hello");
                    Intent mainIntent = new Intent(SplashActivity.this, Signin.class);
                    startActivity(mainIntent);
                    finish();
                }


            }
        }, SPLASH_TIMEOUT);
    }

    // Fetch and return the switch states
    public boolean[] Checklevel() {
        // Fetch the values from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SwitchStatePrefs", Context.MODE_PRIVATE);

        boolean switch1State = sharedPreferences.getBoolean("switch1_state", false);
        boolean switch2State = sharedPreferences.getBoolean("switch2_state", false);
        boolean switch3State = sharedPreferences.getBoolean("switch3_state", false);

        // Return the switch states as an array
        return new boolean[]{switch1State, switch2State, switch3State};
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onMagiskDetected() {


      //  Log.d("hello", String.valueOf(emultorcheck.isEmulator(getApplicationContext())));


       // Log.d("hello", String.valueOf(runtime.isFridaServerRunning()));


            runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Log.d("frida", String.valueOf(runtime.isFridaDetected()));

                alertDialogManager.showRootedDeviceAlert(SplashActivity.this,"Magisk");

            }
        });

    }

    @Override
    public void onMagiskNotDetected() {

        checkdetect checkdetect = new checkdetect();
        checkdetect.someMethod(SplashActivity.this);


    }




}
