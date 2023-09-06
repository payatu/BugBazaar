package com.BugBazaar.ui.cart;
import static com.BugBazaar.ui.cart.CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME;
import static com.BugBazaar.ui.cart.CartItemDBModel.CartItemEntry.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;


public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 1;

    // SQL command to create the cart_items table
    private static final String SQL_CREATE_CART_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    CartItemDBModel.CartItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_PRODUCT_NAME + " TEXT," +
                    CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_PRICE + " INTEGER," +
                    CartItemDBModel.CartItemEntry.COLUMN_QUANTITY + " INTEGER,"+
                    CartItemDBModel.CartItemEntry.COLUMN_PRODIMAGE +" INTEGER"+
                    ");";

    // SQL command to delete the cart_items table
    private static final String SQL_DELETE_CART_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public CartDatabaseHelper(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int DATABASE_VERSION) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CART_TABLE);
        onCreate(db);
    }

    public long saveProductDetails(String ProductName, int ProductPrice, int ProductQuantity, int ProductImage) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("product_name", ProductName);
        cv.put("product_price", ProductPrice);
        cv.put("product_quantity", ProductQuantity);
        cv.put("product_image", ProductImage);
        long recordid = sqLiteDatabase.insert("cart_items", null, cv);
        return recordid;
    }
    public int getQuantityForProduct(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        int quantity = 0;

        String[] projection = {
                CartItemDBModel.CartItemEntry.COLUMN_QUANTITY
        };

        String selection = CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME + " = ?";
        String[] selectionArgs = { productName };

        Cursor cursor = db.query(
                CartItemDBModel.CartItemEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartItemDBModel.CartItemEntry.COLUMN_QUANTITY));
        }

        cursor.close();
        return quantity;
    }

    public int updateCartItem(CartItem cartItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_quantity", cartItem.getQuantity());

        int rowsAffected = db.update("cart_items", values, "product_name = ?", new String[]{cartItem.getProductName()});

        db.close();
        return rowsAffected;
    }

    // Method to add a CartItem to the database
    public long addCartItem(CartItem cartItem) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME, cartItem.getProductName());
        cv.put(CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_PRICE, cartItem.getPrice());
        cv.put(CartItemDBModel.CartItemEntry.COLUMN_QUANTITY, cartItem.getQuantity());
        cv.put(CartItemDBModel.CartItemEntry.COLUMN_PRODIMAGE, cartItem.getImage());

        // Insert the new item into the database
        long recordId = sqLiteDatabase.insert(CartItemDBModel.CartItemEntry.TABLE_NAME, null, cv);
        sqLiteDatabase.close(); // Close the database connection

        return recordId;
    }

    public List<CartItem> getAllRecords() {
        List<CartItem> cartItems = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM cart_items", null);
        CartItem cartItem;
        while (cursor.moveToNext()) {
            String product_name = cursor.getString(1);
            int product_price = cursor.getInt(2);
            int product_quantity = cursor.getInt(3);
            int product_image=cursor.getInt(4);

            //Things are getting added into list.
            cartItem = new CartItem(product_name, product_price, product_quantity, product_image);
            cartItems.add(cartItem);

        }
        return cartItems;
    }

}
