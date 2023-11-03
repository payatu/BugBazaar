package com.BugBazaar.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class AlertDialogManager {

    public void showRootedDeviceAlert(final Context context, String message) {
        if (context instanceof Activity) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("This device is rooted.");
            alertDialogBuilder.setMessage("Found =>> " + message + "! Exiting in 3 Seconds.");
            alertDialogBuilder.setCancelable(false);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            // Schedule a task to exit the application after 3 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss(); // Dismiss the dialog if it's still visible
                    ((Activity) context).finish();
                }
            }, 3000);
        }
    }
}
