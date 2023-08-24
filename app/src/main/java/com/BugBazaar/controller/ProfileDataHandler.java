package com.BugBazaar.controller;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.BugBazaar.provider.UserProfileContract;

public class ProfileDataHandler {

    private Context context;

    public ProfileDataHandler(Context context) {
        this.context = context;

    }



    public boolean saveOrUpdateUserProfile(String name, String email, String mobile, String address) {
        ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(UserProfileContract.UserProfileEntry.COLUMN_NAME, name);
        values.put(UserProfileContract.UserProfileEntry.COLUMN_EMAIL, email);
        values.put(UserProfileContract.UserProfileEntry.COLUMN_MOBILE, mobile);
        values.put(UserProfileContract.UserProfileEntry.COLUMN_ADDRESS, address);

        // Check if the user profile already exists
        long existingProfileId = getUserProfileId(email);
        // You need to implement this method

        if (existingProfileId ==1) {
            // Profile exists, so update it
            Uri userProfileUri = ContentUris.withAppendedId(UserProfileContract.UserProfileEntry.CONTENT_URI, existingProfileId);
            int rowsUpdated = contentResolver.update(userProfileUri, values, null, null);
            return rowsUpdated > 0;
        } else {
            // Profile doesn't exist, so insert it
            Uri uri = UserProfileContract.UserProfileEntry.CONTENT_URI;
            try {
                Uri insertedUri = contentResolver.insert(uri, values);
                if (insertedUri != null) {
                    Log.d("amit", "Insertion successful. URI: " + insertedUri.toString());
                    return true;
                } else {
                    Log.d("amit", "Insertion failed. URI is null.");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("amit", "Insertion error: " + e.getMessage());
                return false;
            }
        }
    }

    // You need to implement this method to get the profile ID based on email
    private long getUserProfileId(String email) {
        ContentResolver contentResolver = context.getContentResolver();

        // Define the columns you want to retrieve (in this case, just the "_id" column)
        String[] projection = {UserProfileContract.UserProfileEntry.COLUMN_ID};

        // Define the selection criteria (WHERE clause)
        String selection = UserProfileContract.UserProfileEntry.COLUMN_EMAIL + "=?";

        // Define the selection arguments (the email to search for)
        String[] selectionArgs = {email};

        // Perform the query
        Cursor cursor = contentResolver.query(
                UserProfileContract.UserProfileEntry.CONTENT_URI,  // URI for the user_profiles table
                projection,      // Columns to retrieve
                selection,       // WHERE clause
                selectionArgs,   // Values for the WHERE clause
                null             // No sorting needed
        );

        if (cursor != null && ((Cursor) cursor).moveToFirst()) {
            // A matching user profile with the provided email was found
            @SuppressLint("Range") long profileId = cursor.getLong(cursor.getColumnIndex(UserProfileContract.UserProfileEntry.COLUMN_ID));
            cursor.close();
            Log.d("amitcool", "Profile ID: " + profileId);
            return profileId;
        } else {
            // No matching user profile with the provided email was found
            return -1;
        }
    }

}

