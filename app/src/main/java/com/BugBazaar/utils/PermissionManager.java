package com.BugBazaar.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;


import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    private Activity activity;
    private PermissionCallback permissionCallback;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public PermissionManager(Activity activity, PermissionCallback permissionCallback) {
        this.activity = activity;
        this.permissionCallback = permissionCallback;
    }

    public boolean checkExternalStoragePermission() {
        return ContextCompat.checkSelfPermission(activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestExternalStoragePermission() {
        ActivityCompat.requestPermissions(activity,
                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_EXTERNAL_STORAGE);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionCallback.onPermissionGranted();
            } else {
                permissionCallback.onPermissionDenied();
            }
        }
    }
}
