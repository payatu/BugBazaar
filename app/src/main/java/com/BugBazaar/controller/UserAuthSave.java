package com.BugBazaar.controller;

import android.content.Context;
import android.content.SharedPreferences;

public class UserAuthSave {

    private static final String USER_PREFERENCES = "user_auth";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_LOGGED_IN = "logged_in";

    private static final String keypasscode = "passcode";
    private static final String keypasscode_flag = "passcode_flag";



    private static SharedPreferences sharedPreferences;

    public UserAuthSave(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void saveUserCredentials(String username, String password, boolean loggedIn) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putBoolean(KEY_LOGGED_IN, loggedIn);
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
