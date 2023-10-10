package com.BugBazaar.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.BugBazaar.R;
import com.BugBazaar.ui.cart.CartDatabaseHelper;
import com.BugBazaar.ui.cart.CartItem;
import com.BugBazaar.ui.cart.CartItemDBModel;

import android.database.Cursor;
import android.widget.Toast;

import java.util.List;

public class OrderSummary extends AppCompatActivity {
    private List<CartItem> cartItems;
    private TextView txtProdQuantityOS;
    private TextView txtTotalCostOS;
    private TextView txtFinalCostOS;
    private RadioGroup rbGroupPaymentOptions;
    private RadioButton rbPayViaWallet;
    private RadioButton rbPayViaRazorpay;
    Button btnProceedPaymentOS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Order Summary");
        txtProdQuantityOS=findViewById(R.id.txtProdQuantityOS);
        txtTotalCostOS=findViewById(R.id.txtTotalCostOS);
        txtFinalCostOS=findViewById(R.id.txtFinalCostOS);
        btnProceedPaymentOS=findViewById(R.id.btnProceedPaymentOS);

        // Initialize the RadioGroup and RadioButton elements
        rbGroupPaymentOptions = findViewById(R.id.rbGroupPaymentOptions);
        rbPayViaWallet = findViewById(R.id.rbPayViaWallet);
        rbPayViaRazorpay = findViewById(R.id.rbPayViaRazorpay);

        // Initialize your CartDatabaseHelper
        CartDatabaseHelper cartDBHelper = new CartDatabaseHelper(this, "cart.db", null, 1);
        // Initialize your cartItems list and populate it with all items from the database
        cartItems = cartDBHelper.getAllRecords();
        // Initialize prodQuantity to store the product quantity
        int prodQuantity = 0;
        // Iterate through the list of cart items and retrieve the product_quantity from the database
        for (CartItem cartItem : cartItems) {
            // Retrieve the product name for each item
            String productName = cartItem.getProductName();

            // Retrieve the product quantity from the database using a query
            int quantity = getProductQuantityFromDatabase(cartDBHelper, productName);

            // Add the quantity to prodQuantity
            prodQuantity += quantity;
            //Set the product quantity
            txtProdQuantityOS.setText(String.valueOf(prodQuantity));


            Intent intent = getIntent();
            int totalCost = intent.getIntExtra("totalPrice", 0);
            // Format the totalCost and set it in the TextView
            String formattedTotalCost = formatPrice(totalCost);
            txtTotalCostOS.setText(formattedTotalCost);

            int deliveryCharges=536;

            //Final Cost to be sent to Razorpay or wallet
            int finalCost = totalCost + deliveryCharges;
            String formattedFinalCost = formatPrice(finalCost);
            txtFinalCostOS.setText(formattedFinalCost);

            // Set an OnCheckedChangeListener to the RadioGroup
            rbGroupPaymentOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.d("RB Intitated","RB Intitated");
                    if (checkedId == R.id.rbPayViaWallet) {
                        rbPayViaWallet.setChecked(true);      // Set the "Pay via Wallet" RadioButton as selected
                        rbPayViaRazorpay.setChecked(false);    // Clear the selection of "Pay via Razorpay"
                        Toast.makeText(getApplicationContext(),"Pay Via Wallet",Toast.LENGTH_SHORT).show();
                    } else if (checkedId == R.id.rbPayViaRazorpay) {
                        rbPayViaWallet.setChecked(false);
                        rbPayViaRazorpay.setChecked(true);
                        Toast.makeText(getApplicationContext(),"Pay Via Razorpay",Toast.LENGTH_SHORT).show();
                    }else
                        Toast.makeText(getApplicationContext(),"No button selected",Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    // Define a method to retrieve product_quantity from the database based on product name
    private int getProductQuantityFromDatabase(CartDatabaseHelper dbHelper, String productName) {
        int quantity = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                CartItemDBModel.CartItemEntry.TABLE_NAME, // Table name
                new String[]{CartItemDBModel.CartItemEntry.COLUMN_QUANTITY}, // Columns to retrieve
                CartItemDBModel.CartItemEntry.COLUMN_PRODUCT_NAME + "=?", // Selection criteria
                new String[]{productName}, // Selection arguments
                null, // Group by
                null, // Having
                null // Order by
        );

        if (cursor != null && cursor.moveToFirst()) {
            quantity = cursor.getInt(cursor.getColumnIndexOrThrow(CartItemDBModel.CartItemEntry.COLUMN_QUANTITY));
            cursor.close();
        }

        return quantity;
    }
    private String formatPrice(int price) {
        return String.format("₹%,d", price);
    }
    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}