package com.BugBazaar.controller;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.BugBazaar.Models.UserProfileData;
import com.BugBazaar.provider.UserProfileContract;

public class ProfileDataManager {
    private final ContentResolver contentResolver;

    public ProfileDataManager(Context context) {
        contentResolver = context.getContentResolver();
    }

    public UserProfileData getUserProfileData() {
        Uri allProfilesUri = UserProfileContract.UserProfileEntry.CONTENT_URI;

        String[] projection = {
                UserProfileContract.UserProfileEntry.COLUMN_NAME,
                UserProfileContract.UserProfileEntry.COLUMN_EMAIL,
                UserProfileContract.UserProfileEntry.COLUMN_MOBILE,
                UserProfileContract.UserProfileEntry.COLUMN_ADDRESS
        };

        Cursor cursor = contentResolver.query(allProfilesUri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(UserProfileContract.UserProfileEntry.COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndex(UserProfileContract.UserProfileEntry.COLUMN_EMAIL));
            String mobile = cursor.getString(cursor.getColumnIndex(UserProfileContract.UserProfileEntry.COLUMN_MOBILE));
            String address = cursor.getString(cursor.getColumnIndex(UserProfileContract.UserProfileEntry.COLUMN_ADDRESS));

            cursor.close();

            return new UserProfileData(name, email, mobile, address);
        } else {
            return null;
        }
    }
}
