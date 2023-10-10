package com.BugBazaar.ui.payment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener; // Import the PaymentResultListener
import com.BugBazaar.R;
import org.json.JSONException;
import org.json.JSONObject;

public class TestRazorPay extends AppCompatActivity implements PaymentResultListener { // Implement the interface
    private Button btnProceedPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_razor_pay);
        btnProceedPayment = findViewById(R.id.btnProceedPayment);

        // Initialize Razorpay with your API key
        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_YEExgm42Uvy0u1");

        btnProceedPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create payment options
                JSONObject options = new JSONObject();
                try {
                    options.put("name", "BugBazaar Retailers");
                    options.put("description", "Purchase Description");
                    //options.put("image", "YOUR_LOGO_URL");
                    options.put("currency", "INR"); // Change to your currency code
                    options.put("amount", "10000"); // Change to the actual amount in paise
                    //options.put("order_id", "23287827788382387231923921392173123123213"); // The order ID received from your server
                    options.put("prefill.email", "customer@example.com");
                    options.put("prefill.contact", "1234567890");
                } catch (JSONException e) {
                    e.printStackTrace();
                    // Handle JSON exception here
                }

                // Open Razorpay payment dialog
                try {
                    checkout.open(TestRazorPay.this, options);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle any exceptions that may occur while opening the payment dialog
                }
            }
        });
    }

    public void onPaymentSuccess(String s) {
       Toast.makeText(this,"Payment Successful",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int code, String response) {
        // Handle payment error
        // This method is called when there is a payment error

        // Log the error code and response for debugging
        Log.e("Razorpay Error", "Error Code: " + code);
        Log.e("Razorpay Error", "Error Response: " + response);

        // You can display an error message to the user or take appropriate action based on the error code and response
        // For example, you can show a Toast message with the error details:
        Toast.makeText(this, "Payment Error: " + response, Toast.LENGTH_SHORT).show();

        // You can also perform additional error handling based on the error code if needed
        switch (code) {
            case Checkout.NETWORK_ERROR:
                // Handle network-related errors
                break;
            case Checkout.INVALID_OPTIONS:
                // Handle invalid payment options
                break;
            case Checkout.PAYMENT_CANCELED:
                // Handle payment cancellation by the user
                break;
            // Add more cases for specific error codes as needed
        }
    }
}
