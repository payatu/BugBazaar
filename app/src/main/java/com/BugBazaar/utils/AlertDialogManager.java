package com.BugBazaar.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.BugBazaar.ui.Signin;
import com.BugBazaar.ui.SplashActivity;

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
            }, 2000);
        }
    }


    public void showNormalDeviceAlert(final Context context, String message) {
        if (context instanceof Activity) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Welcome!! Device!!.");
            alertDialogBuilder.setCancelable(false);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

            // Schedule a task to exit the application after 3 seconds
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss(); // Dismiss the dialog if it's still visible
                    launchapp(((Activity) context));

                }
            }, 2000);
        }
    }

    private void launchapp(Activity context) {

        Intent mainIntent = new Intent(context, Signin.class);
        context.startActivity(mainIntent);
        context.finish();
    }

}
