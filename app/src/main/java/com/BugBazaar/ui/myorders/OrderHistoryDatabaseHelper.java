package com.BugBazaar.ui.myorders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OrderHistory.db";

    public static class OrderHistoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "order_history";
        public static final String COLUMN_ORDER_ID = "order_id";  // Rename it to store order IDs
        public static final String COLUMN_PRODUCT_NAME = "product_name";
        public static final String COLUMN_PRODUCT_QUANTITY = "product_quantity";
        public static final String COLUMN_FINAL_COST = "final_cost";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + OrderHistoryEntry.TABLE_NAME + " (" +
                    OrderHistoryEntry._ID + " INTEGER PRIMARY KEY," +
                    OrderHistoryEntry.COLUMN_ORDER_ID + " TEXT," + // Add space and comma here
                    OrderHistoryEntry.COLUMN_PRODUCT_NAME + " TEXT," +
                    OrderHistoryEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER," +
                    OrderHistoryEntry.COLUMN_FINAL_COST + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + OrderHistoryEntry.TABLE_NAME;

    public OrderHistoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        insertInitialOrders(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public List<OrderHistoryItem> getAllOrderItemsz() {
        List<OrderHistoryItem> orderItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns to retrieve from the order items table
        String[] projection = {
                OrderHistoryEntry.COLUMN_ORDER_ID,
                OrderHistoryEntry.COLUMN_PRODUCT_NAME,
                OrderHistoryEntry.COLUMN_PRODUCT_QUANTITY,
                OrderHistoryEntry.COLUMN_FINAL_COST
        };

        // Query the database to retrieve all order items, excluding those with ORDER-9
        String selection = OrderHistoryEntry.COLUMN_ORDER_ID + " NOT LIKE '%ORDER-9%'";
        Cursor cursor = db.query(
                OrderHistoryEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                OrderHistoryEntry.COLUMN_ORDER_ID + " DESC"
        );

        String currentOrderID = null;
        List<String> productNames = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();
        int finalCost = 0;

        while (cursor.moveToNext()) {
            String orderID = cursor.getString(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_ORDER_ID));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_PRODUCT_NAME));
            int productQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_PRODUCT_QUANTITY));
            int currentFinalCost = cursor.getInt(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_FINAL_COST));

            if (currentOrderID == null || !currentOrderID.equals(orderID)) {
                // If a new order ID is encountered, create a new OrderHistoryItem
                if (currentOrderID != null) {
                    OrderHistoryItem orderItem = new OrderHistoryItem(currentOrderID, productNames, productQuantities, finalCost);
                    orderItems.add(orderItem);
                }

                // Initialize values for the new order
                currentOrderID = orderID;
                productNames = new ArrayList<>();
                productQuantities = new ArrayList<>();
                finalCost = 0;
            }

            // Add the product name and quantity to the lists
            productNames.add(productName);
            productQuantities.add(productQuantity);
            // Accumulate the final cost
            finalCost += currentFinalCost;
        }

        // Add the last order to the list
        if (currentOrderID != null) {
            OrderHistoryItem orderItem = new OrderHistoryItem(currentOrderID, productNames, productQuantities, finalCost);
            orderItems.add(orderItem);
        }

        cursor.close();
        return orderItems;
    }

    public List<OrderHistoryItem> searchOrdersByOrderID(String orderID) {
        List<OrderHistoryItem> orderItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the raw SQL query
        // Define the raw SQL query to exclude orders containing "99"
        String query = "SELECT * FROM " + OrderHistoryEntry.TABLE_NAME +
                " WHERE " + OrderHistoryEntry.COLUMN_ORDER_ID + " LIKE '%" + orderID + "%' AND " +
                OrderHistoryEntry.COLUMN_ORDER_ID + " NOT LIKE '%ORDER-9%'";


        // Execute the raw SQL query
        Cursor cursor = db.rawQuery(query, null);

        String currentOrderID = null;
        List<String> productNames = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();
        int finalCost = 0;

        while (cursor.moveToNext()) {
            String queriedOrderID = cursor.getString(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_ORDER_ID));
            String productName = cursor.getString(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_PRODUCT_NAME));
            int productQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_PRODUCT_QUANTITY));
            int currentFinalCost = cursor.getInt(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_FINAL_COST));

            if (currentOrderID == null || !currentOrderID.equals(queriedOrderID)) {
                if (currentOrderID != null) {
                    OrderHistoryItem orderItem = new OrderHistoryItem(currentOrderID, productNames, productQuantities, finalCost);
                    orderItems.add(orderItem);
                }

                currentOrderID = queriedOrderID;
                productNames = new ArrayList<>();
                productQuantities = new ArrayList<>();
                finalCost = 0;
            }

            productNames.add(productName);
            productQuantities.add(productQuantity);
            finalCost += currentFinalCost;
        }

        if (currentOrderID != null) {
            OrderHistoryItem orderItem = new OrderHistoryItem(currentOrderID, productNames, productQuantities, finalCost);
            orderItems.add(orderItem);
        }

        cursor.close();
        return orderItems;
    }

       public String findLastOrderID(SQLiteDatabase db) {
        String lastOrderID = "ORDER-100"; // A default order ID if no records exist yet
        Cursor cursor = db.query(
                OrderHistoryEntry.TABLE_NAME,
                new String[]{OrderHistoryEntry.COLUMN_ORDER_ID},
                null,
                null,
                null,
                null,
                OrderHistoryEntry._ID + " DESC",
                "1"
        );

        if (cursor.moveToFirst()) {
            lastOrderID = cursor.getString(cursor.getColumnIndexOrThrow(OrderHistoryEntry.COLUMN_ORDER_ID));
            // Check if the lastOrderID contains "99"
            if (lastOrderID.contains("99")) {
                // Extract the numeric part of the lastOrderID
                String numericPart = lastOrderID.substring("ORDER-".length());
                try {
                    int lastOrderNumber = Integer.parseInt(numericPart);
                    // Increment the order number
                    lastOrderNumber++;
                    lastOrderID = "ORDER-" + lastOrderNumber;
                } catch (NumberFormatException e) {
                    // Handle the case where the order ID doesn't have a valid number
                    lastOrderID = "ORDER-101"; // Fallback to a default number
                }
            }
        }

        cursor.close();
        return lastOrderID;
    }
    private void insertInitialOrders(SQLiteDatabase db) {
        String[][] initialData = {
                {"ORDER-99", "Product Name 1", "3", "200"}
                // Add more initial data here
        };

        // Insert initial data into the database
        for (String[] data : initialData) {
            ContentValues values = new ContentValues();
            values.put(OrderHistoryEntry.COLUMN_ORDER_ID, data[0]);
            values.put(OrderHistoryEntry.COLUMN_PRODUCT_NAME, data[1]);
            values.put(OrderHistoryEntry.COLUMN_PRODUCT_QUANTITY, data[2]);
            values.put(OrderHistoryEntry.COLUMN_FINAL_COST, data[3]);
            db.insert(OrderHistoryEntry.TABLE_NAME, null, values);
        }
    }



    public String incrementOrderID(String lastOrderID) {
        String numericPart = lastOrderID.substring("ORDER-".length());
        int orderNumber = Integer.parseInt(numericPart);
        orderNumber++;
        return "ORDER-" + orderNumber;
    }
}