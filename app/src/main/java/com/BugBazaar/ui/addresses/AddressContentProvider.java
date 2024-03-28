package com.BugBazaar.ui.addresses;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AddressContentProvider extends ContentProvider {
    private AddressDatabaseHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new AddressDatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Add condition to exclude addresses containing "0xSuper_Secret0x"
        String newSelection;
        if (selection != null && !selection.isEmpty()) {
            newSelection = selection + " AND " + AddressContract.AddressEntry.COLUMN_NICKNAME + " NOT LIKE '%0xSuper_Secret0x%'";
        } else {
            newSelection = AddressContract.AddressEntry.COLUMN_NICKNAME + " NOT LIKE '%0xSuper_Secret0x%'";
        }

        Cursor cursor = db.query(AddressContract.AddressEntry.TABLE_NAME,
                projection,
                newSelection,
                selectionArgs,
                null,
                null,
                sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = db.insert(AddressContract.AddressEntry.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    // Implement other necessary methods: update, delete, getType, etc.
}
