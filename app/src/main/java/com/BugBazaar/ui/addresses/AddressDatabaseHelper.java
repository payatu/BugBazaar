package com.BugBazaar.ui.addresses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AddressDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AddressDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ADDRESS = "addresses";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NICKNAME = "nickname";
    private static final String COLUMN_ADDRESS = "address";

    public AddressDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_ADDRESS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NICKNAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT)";
        db.execSQL(createTable);

        // Insert initial data into the database
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(db);
    }

    // Insert a new address into the database
// Insert a new address into the database
    public void insertAddress(String nickname, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction(); // Begin the transaction

        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NICKNAME, nickname);
            values.put(COLUMN_ADDRESS, address);

            // Insert data into the database
            db.insert(TABLE_ADDRESS, null, values);

            db.setTransactionSuccessful(); // Mark the transaction as successful
        } catch (Exception e) {
            // Handle any exceptions here
            Log.e("Database", "Error inserting address: " + e.getMessage());
        } finally {
            db.endTransaction(); // End the transaction
        }
        db.close();
    }


    // Retrieve an address by its nickname
    public List<AddressItem> searchAddressesByNickname(String nickname) {
        List<AddressItem> searchResults = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the query to search for addresses by nickname
        String query = "SELECT * FROM " + TABLE_ADDRESS +
                " WHERE " + COLUMN_NICKNAME + " LIKE '%" + nickname + "%' AND " + COLUMN_NICKNAME + " NOT LIKE '%super_secret0x%'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AddressItem addressItem = new AddressItem();
                addressItem.setId(cursor.getInt(0));
                addressItem.setNickname(cursor.getString(1));
                addressItem.setAddress(cursor.getString(2));
                searchResults.add(addressItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return searchResults;
    }


    // Retrieve all saved addresses
    public List<AddressItem> getAllAddresses() {
        List<AddressItem> addressList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ADDRESS + " WHERE " + COLUMN_NICKNAME + " NOT LIKE '%0xSuper_Secret0x%' ORDER BY " + COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                AddressItem addressItem = new AddressItem();
                addressItem.setId(cursor.getInt(0));
                addressItem.setNickname(cursor.getString(1));
                addressItem.setAddress(cursor.getString(2));
                addressList.add(addressItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return addressList;
    }
    private void insertInitialData(SQLiteDatabase db) {
        String[][] initialData = {
                {"0xSuper_Secret0x Address-1", "21 Jump Street, Camp road, Pune, Pin-411011, India"},
                {"Home", "Dunder Mifflin,1725 Slough Avenue, Suite 200, Scranton, Pennsylvania"},
                {"Office", "Ved InfoSec Consulting, 5th floor, Business Bay Tower, Milky Way Galaxy, Observable Universe"},
                {"0xSuper_Secret0x-2", "Dwight Schrute, 221-B, BAKER STREET, London, NW1, United Kingdom"},
        };

        // Insert initial data into the database
        for (String[] data : initialData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NICKNAME, data[0]);
            values.put(COLUMN_ADDRESS, data[1]);
            db.insert(TABLE_ADDRESS, null, values);
        }
    }


}
