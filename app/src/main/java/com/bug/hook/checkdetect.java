package com.bug.hook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.BugBazaar.ui.Signin;
import com.BugBazaar.utils.AlertDialogManager;
import com.BugBazaar.ui.SplashActivity;

public class checkdetect {

    private AlertDialogManager alertDialogManager = new AlertDialogManager();

    public void someMethod(final Context context) {

        if ( emultorcheck.isEmulator() || AdbEnabled.AdbEnabled1(context)) {

            if (Looper.myLooper() == Looper.getMainLooper()) {
                // You are on the UI thread, you can show the dialog directly
                alertDialogManager.showRootedDeviceAlert(context, "adb enabled oR You are Using Emulator");

            } else {
                // You are on a non-UI thread, use a Handler to execute on the UI thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        alertDialogManager.showRootedDeviceAlert(context, "adb enabled");
                    }
                });
            }
        } else if (checkfrida()) {
            alertDialogManager.showRootedDeviceAlert(context.getApplicationContext(), "FRIDA");
        }

        else {
            launchapp(context);
        }

    }

    private void launchapp(Context context) {
        Intent intent = new Intent(context, Signin.class); // Replace YourActivity with the actual activity class
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }

    private boolean checkfrida() {

        if (runtime.areFridaFilesPresent() || runtime.isFridaDetectedinmounts() || runtime.isFridaServerRunning() || runtime.isFridaDetectedfile()) {

            return true;

        }
        return false;


    }

}
