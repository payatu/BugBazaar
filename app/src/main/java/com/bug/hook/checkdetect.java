package com.bug.hook;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.BugBazaar.utils.AlertDialogManager;
import com.BugBazaar.ui.SplashActivity;

public class checkdetect {

    private AlertDialogManager alertDialogManager = new AlertDialogManager();

    public void someMethod(final Context context) {

        if (AdbEnabled.AdbEnabled1(context) || emultorcheck.isEmulator(context)) {

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
        }
    }
}
