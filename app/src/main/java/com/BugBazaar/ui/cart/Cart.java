package com.BugBazaar.ui.cart;

import static com.BugBazaar.ui.cart.CartItemDBModel.CartItemEntry.COLUMN_PRODIMAGE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static Cart instance;
    private CartDatabaseHelper dbHelper;
    private SQLiteDatabase database;



    public long addCartItem(CartItem cartItem) {
        ContentValues values = new ContentValues();
        values.put(CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME, cartItem.getProductName());
        values.put(CartItemDBModel.CartItemEntry.COLUMN_QUANTITY, cartItem.getQuantity());
        values.put(CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_PRICE, cartItem.getPrice());
        //values.put(CartItemDBModel.CartItemEntry.COLUMN_PRODIMAGE, cartItem.getImage());

        return database.insert(CartItemDBModel.CartItemEntry.TABLE_NAME, null, values);
    }

    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        String[] projection = {
                CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME,
                CartItemDBModel.CartItemEntry.COLUMN_QUANTITY,
                CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_PRICE,
               // CartItemDBModel.CartItemEntry.COLUMN_PRODIMAGE
        };

        Cursor cursor = database.query(
                CartItemDBModel.CartItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String productName = cursor.getString(cursor.getColumnIndexOrThrow(CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartItemDBModel.CartItemEntry.COLUMN_QUANTITY));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow(CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_PRICE));
                int productImage=cursor.getInt(cursor.getColumnIndexOrThrow(CartItemDBModel.CartItemEntry.COLUMN_PRODIMAGE));
                CartItem cartItem = new CartItem(productName, quantity, price, productImage);
                cartItems.add(cartItem);
            }
            cursor.close();
        }

        return cartItems;
    }

    public void clearCart() {
        database.delete(CartItemDBModel.CartItemEntry.TABLE_NAME, null, null);
    }

    // Add methods for removing items, updating quantities, etc. as needed
}
