package com.bug.hook;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;
import android.util.Log;

public class EmulatorCheckUtil {

    public static boolean emulatorCheck(Context context) {
        PackageManager packageManager = context.getPackageManager();

        boolean hasCameraFlash = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean hasFingerprintSensor = packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
        boolean hasVibration = hasVibrationCapability(context);

        return !(hasCameraFlash && hasFingerprintSensor && hasVibration);
    }

    private static boolean hasVibrationCapability(Context context) {
        try {
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                // Check if the device supports vibration
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return vibrator.hasVibrator();
                } else {
                    return true; // For devices before Android O, assume vibration support
                }
            }
        } catch (Exception e) {
            Log.e("EmulatorCheckUtil", "Error checking vibration capability: " + e.getMessage());
        }

        return false;
    }
}

