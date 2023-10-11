package com.BugBazaar.ui.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.BugBazaar.R;
import com.BugBazaar.ui.cart.CartDatabaseHelper;
import com.BugBazaar.ui.cart.CartItem;
import com.BugBazaar.ui.cart.CartItemDBModel;
import com.BugBazaar.ui.cart.NotificationHelper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

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
    StringBuilder productnames = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Order Summary");
        txtProdQuantityOS = findViewById(R.id.txtProdQuantityOS);
        txtTotalCostOS = findViewById(R.id.txtTotalCostOS);
        txtFinalCostOS = findViewById(R.id.txtFinalCostOS);
        btnProceedPaymentOS = findViewById(R.id.btnProceedPaymentOS);

        // Initialize the RadioGroup and RadioButton elements
        rbGroupPaymentOptions = findViewById(R.id.rbGroupPaymentOptions);
        rbPayViaWallet = findViewById(R.id.rbPayViaWallet);
        rbPayViaRazorpay = findViewById(R.id.rbPayViaRazorpay);

        // Initialize Razorpay with your API key
        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_YEExgm42Uvy0u1");

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
        productnames.append(productName);
    }
        // Set the product quantity
        txtProdQuantityOS.setText(String.valueOf(prodQuantity));


        Intent intent = getIntent();
        int totalCost = intent.getIntExtra("totalPrice", 0);
        // Format the totalCost and set it in the TextView
        String formattedTotalCost = formatPrice(totalCost);
        txtTotalCostOS.setText(formattedTotalCost);

        int deliveryCharges = 536;

        // Final Cost to be sent to Razorpay or wallet
        int finalCost = totalCost + deliveryCharges;
        String formattedFinalCost = formatPrice(finalCost);
        txtFinalCostOS.setText(formattedFinalCost);

            // Set an OnClickListener to the "Proceed to Payment" button
            btnProceedPaymentOS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check which radio button is selected
                    int selectedRadioButtonId = rbGroupPaymentOptions.getCheckedRadioButtonId();

                    if (selectedRadioButtonId == R.id.rbPayViaWallet) {
                        // Handle payment via wallet (if applicable)
                    } else if (selectedRadioButtonId == R.id.rbPayViaRazorpay) {
                        int amountInPaise=finalCost*100;

                        try {
                            // You need to pass a JSONObject with payment details to Razorpay
                            JSONObject options = new JSONObject();
                            options.put("name", "BugBazaar Private Limited"); // Replace with your company name
                            options.put("description", "Order Payment");
                            options.put("currency", "INR"); // Replace with the appropriate currency code
                            options.put("amount", amountInPaise); // Amount should be in paise
                            options.put("prefill.email", "customer@example.com");
                            options.put("prefill.contact", "1234567890");

                            // Callback URL (optional, can be used for handling payment success or failure)
                            // options.put("callback_url", "your_callback_url");
                            checkout.open(OrderSummary.this,options);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // Open Razorpay payment dialog
                    }
                }
            });
    }
    public void onPaymentSuccess(String s) {
        Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();

       String message = productnames+"!your order is successful!!";
        NotificationHelper.showNotification(this, new StringBuilder(message));


        //Move to Order History Activity
        //Clear All Cart Items
    }

    public void onPaymentError(int code, String response) {
        // Handle payment error
        // This method is called when there is a payment error
        String message = productnames+"!your order is Failed!! try again";
        NotificationHelper.showNotification(this, new StringBuilder(message));
        // Log the error code and response for debugging
        Log.e("Razorpay Error", "Error Code: " + code);
        Log.e("Razorpay Error", "Error Response: " + response);

        // You can display an error message to the user or take appropriate action based on the error code and response
        // For example, you can show a Toast message with the error details:
      //  Toast.makeText(this, "Payment Error: " + response, Toast.LENGTH_SHORT).show();

        // You can also perform additional error handling based on the error code if needed
        switch (code) {
            case Checkout.NETWORK_ERROR:
                Toast.makeText(this, "Network Error: Please check the internet connection.", Toast.LENGTH_LONG).show();
                break;
            case Checkout.INVALID_OPTIONS:
                // Handle invalid payment options
                break;
            case Checkout.PAYMENT_CANCELED:
                Toast.makeText(this, "Payment Error: Payment Canceled by user.", Toast.LENGTH_LONG).show();
                break;
            // Add more cases for specific error codes as needed
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

    // Code to handle back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
