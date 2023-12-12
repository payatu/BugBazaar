package com.BugBazaar.ui.detectAppInt;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.BugBazaar.ui.NavigationDrawer_Dashboard;
import com.BugBazaar.ui.Signin;
import com.BugBazaar.ui.SplashActivity;
import com.darvin.security.Native;
import com.scottyab.rootbeer.RootBeer;

public class checkroot {

    private Context context;  // Declare a Context variable

    // Constructor to set the Context
    public checkroot(Context context) {
        this.context = context;
    }

    public void checkRootBeer() {
        boolean z = Native.isMagiskPresentNative();
        RootBeer rootBeer = new RootBeer(context);
        if (rootBeer.isRooted() || z) {
            // Display a Toast message
            // Toast.makeText(context, "This device is rooted. Exiting in 3 seconds.", Toast.LENGTH_SHORT).show();

            // Show a dialog with the message
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("This device is rooted.");
            alertDialogBuilder.setMessage("Found!\uD83E\uDEDA!Exiting in 3 Seconds.");
            alertDialogBuilder.setCancelable(false);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            // Schedule a task to exit the application after 3 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();

                    // Finish the current activity
                    ((Activity) context).finish();

                }
            }, 3000); // 3000 milliseconds = 3 seconds
        }
        else {

         //   Log.d("hello","hello");

            Intent mainIntent = new Intent(context.getApplicationContext(), Signin.class);
            context.startActivity(mainIntent);


        }
    }
}
