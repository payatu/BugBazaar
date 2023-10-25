package com.BugBazaar.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;
import com.BugBazaar.ui.detectAppInt.checkroot;
import com.BugBazaar.utils.AlertDialogManager;
import com.darvin.security.DetectMagisk;
import com.darvin.security.Native;
import com.google.android.material.snackbar.Snackbar;

public class SplashActivity extends AppCompatActivity  implements DetectMagisk.DetectionListener {

    private static final int SPLASH_TIMEOUT = 2000; // 2 seconds
    AlertDialogManager alertDialogManager = new AlertDialogManager();


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_splash);


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

                   boolean z = Native.isMagiskPresentNative();


                    DetectMagisk detectMagisk = new DetectMagisk(getApplicationContext());
                    detectMagisk.setDetectionListener(SplashActivity.this);
                    // Start Magisk detection
                    detectMagisk.startMagiskDetection();



//                    try {
//                       Log.d("helloamit", String.valueOf(detectMagisk.serviceBinder.isMagiskPresent()));
//                    } catch (RemoteException e) {
//                        throw new RuntimeException(e);
//                    }
//                    if (z) {
//
//                        Log.d("coolamit", String.valueOf(z));
//                    }
//                    Intent mainIntent = new Intent(SplashActivity.this, NavigationDrawer_Dashboard.class);
//                    startActivity(mainIntent);
//                    finish();

                }




                else if (switch3State) {

                    // switch1 is in the "OFF" state, perform a different operation if needed
                }

                else {

                    Intent mainIntent = new Intent(SplashActivity.this, NavigationDrawer_Dashboard.class);
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialogManager.showRootedDeviceAlert(SplashActivity.this,"Magisk ");

            }
        });

    }

    @Override
    public void onMagiskNotDetected() {

    }
}
