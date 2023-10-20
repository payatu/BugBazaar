package com.BugBazaar.ui;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "SessionStorage";
    private static final String KEY_USER_TOKEN = "userToken";
    private static final String KEY_LOGGED_IN = "loggedIn";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedIn(boolean loggedIn) {
        editor.putBoolean(KEY_LOGGED_IN, loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public void setUserToken(String token) {
        editor.putString(KEY_USER_TOKEN, token);
        editor.apply();
    }

    public String getUserToken() {
        return sharedPreferences.getString(KEY_USER_TOKEN, null);
    }

    public void logout() {
        editor.remove(KEY_LOGGED_IN);
        editor.remove(KEY_USER_TOKEN);
        editor.apply();
    }
}


