package com.BugBazaar.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.BugBazaar.ui.SessionManager;

public class UserAuthSave {

    private static final String USER_PREFERENCES = "user_auth"; // shared_pref name
    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String keypasscode = "passcode";
    private static final String keypasscode_flag = "passcode_flag";

    private static SharedPreferences sharedPreferences;
    private SessionManager sessionManager;  // Move the initialization to a constructor

    public UserAuthSave(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
        sessionManager = new SessionManager(context);  // Initialize SessionManager in the constructor
    }

    public void saveUserData(String randomToken, boolean loggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // Use SessionManager to save the user token and login state
        sessionManager.setUserToken(randomToken);
        sessionManager.setLoggedIn(loggedIn);
        editor.apply();
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        UserAuthSave.sharedPreferences = sharedPreferences;
    }

    public static void savepasscode(int passcode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(keypasscode, String.valueOf(passcode));
        editor.putBoolean(keypasscode_flag, true);
        editor.apply();
    }

    public static String getSavedUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");
    }

    public static boolean getpasscode_flag() {
        return sharedPreferences.getBoolean(keypasscode_flag, false);
    }

    public static String getpasscode() {
        return sharedPreferences.getString("passcode", "");
    }

    public static String getSavedPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    public static boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }
}
