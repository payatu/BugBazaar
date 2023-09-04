package com.BugBazaar.ui.cart;

import android.provider.BaseColumns;

public final class CartItemDBModel {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private CartItemDBModel() {
    }

    // Define the table contents.
    public static class CartItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "cart_items";
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
        public static final String COLUMN_PRODUCT_PRICE = "product_price";
        public static final String COLUMN_QUANTITY = "product_quantity";
        public static final String COLUMN_PRODIMAGE="product_image";

        // Add other fields if needed.

        // SQL command to create the table.
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_PRODUCT_NAME + " TEXT," +
                        COLUMN_PRODUCT_DESCRIPTION + " TEXT," +
                        COLUMN_PRODUCT_PRICE + " TEXT," +
                        COLUMN_QUANTITY + " INTEGER,"+
                        COLUMN_PRODIMAGE + " INTEGER)";

        // SQL command to delete the table.
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}

