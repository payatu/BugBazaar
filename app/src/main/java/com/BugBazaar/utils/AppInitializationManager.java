package com.BugBazaar.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppInitializationManager {
    private static final String PREF_NAME = "MyAppPreferences";

    public static boolean isFirstRun(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFirstRun", true);
    }

    public static void markFirstRunDone(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstRun", false);
        editor.apply();
    }

    public static void showNotification(Context context) {
        NotificationUtils.showCustomNotification(context.getApplicationContext());
        // Implement your notification logic here
        // This can be NotificationUtilsdone using local notifications or Firebase Cloud Messaging
    }
}
