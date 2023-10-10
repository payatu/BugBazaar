package com.BugBazaar.provider;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

public class MyContactsProvider extends ContentProvider {
    // Define your authority and content URI
    public static final String AUTHORITY = "com.bugbazaar.mycontacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/contacts");

    @Override
    public boolean onCreate() {
        // Initialize your database or data source here
        // Return true if initialization is successful
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Query the ContactsContract to retrieve all contacts
        return getContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // Implement update operation if needed
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement delete operation if needed
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
