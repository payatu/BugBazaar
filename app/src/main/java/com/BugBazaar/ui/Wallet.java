package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.BugBazaar.R;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.BugBazaar.controller.checkAuth;
import com.BugBazaar.ui.payment.OrderSummary;
import com.BugBazaar.utils.AppInitializationManager;
import com.BugBazaar.utils.DiscountDataManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;



public class Wallet extends BaseActivity implements PaymentResultListener {
    private static final String WALLET_BALANCE_KEY = "wallet_balance";
    private TextView walletBalanceW;
    private RadioGroup rbGroupPaymentOptionsW;

    private SharedPreferences sharedPreferences;
    private SharedPreferences sessionManager;
    private int newAmount;
    private EditText enterAmountW;
    private TextView finalAmountW;
    private int edtWalletAmount=0;
    int additionalAmount=0;
    boolean promoredeem=true;
    double promoCodeAmount = DiscountDataManager.getInstance().getDiscountPrice();
// Now you can use the 'discountPrice' in your destination activity.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyWalletPrefs", Context.MODE_PRIVATE);

        // Fetch the promoredeem value from shared preferences with a default of false
        promoredeem = sharedPreferences.getBoolean("promoredeem", false);

        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Wallet");

        walletBalanceW = findViewById(R.id.walletBalanceW);
        finalAmountW = findViewById(R.id.finalAmountW);
        enterAmountW = findViewById(R.id.enterAmountW);
        CheckBox promoCheckboxW = findViewById(R.id.promoCheckboxW);
        RadioButton walletPayViaRazorpayW = findViewById(R.id.walletPayViaRazorpayW);
        Button btnProceedWallet = findViewById(R.id.btnProceedWallet);
        rbGroupPaymentOptionsW = findViewById(R.id.rbGroupPaymentOptionsW);

        // Set the wallet balance from SharedPreferences
        int initialBalance = sharedPreferences.getInt(WALLET_BALANCE_KEY, 12000);
        String wallBalanceStr = String.valueOf(initialBalance);
        wallBalanceStr = formatPrice(initialBalance);
        walletBalanceW.setText(wallBalanceStr); // Display the balance

        // Initialize Razorpay with your API key
        Checkout.preload(getApplicationContext());
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_YEExgm42Uvy0u1");

        // Add a text change listener to the EditText
        enterAmountW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update finalAmountW as the user types or modifies the amount
                updateFinalAmount(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        SessionManager sessionManager=new SessionManager (this);
        boolean isPromotionNofiyToUser = sessionManager.getIsPromotionalNotifSent();
        // Add a listener for the promoCheckbox

        if(!promoredeem && isPromotionNofiyToUser==true){
            CheckBox promoCheckboxWW=findViewById(R.id.promoCheckboxW);
            promoCheckboxWW.setVisibility(View.VISIBLE);
        }


        promoCheckboxW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!promoredeem) {
                    try {
                        int enteredAmount = Integer.parseInt(enterAmountW.getText().toString());
                        if (isChecked) {
                            enteredAmount += promoCodeAmount * 100;

                        }
                        // Calculate the new amount in paise
                        newAmount = enteredAmount * 100;
                        updateFinalAmount(Integer.toString(enteredAmount));
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Invalid amount entered.", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    promoCheckboxW.setVisibility(View.GONE);
                }
            }
        });


    
        enterAmountW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int enteredAmount = Integer.parseInt(s.toString());
                    if (promoCheckboxW.isChecked()) {
                        enteredAmount += promoCodeAmount*100;
                    }
                    // Calculate the new amount in paise
                    newAmount = enteredAmount * 100;
                    updateFinalAmount(Integer.toString(enteredAmount));
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid amount entered.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        int selectedRadioButtonId = rbGroupPaymentOptionsW.getCheckedRadioButtonId();

        if (selectedRadioButtonId == R.id.walletPayViaRazorpayW) {

            int amountInPaise = edtWalletAmount * 100;
            additionalAmount=edtWalletAmount;
            newAmount = amountInPaise; // Store the amount for later use

            // Calculate the final amount
            int finalAmount = amountInPaise;

            // Update the final amount TextView
            finalAmountW.setText(formatPrice(finalAmount));
        }

        btnProceedWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredValue = enterAmountW.getText().toString();
                if (!enteredValue.isEmpty()) {
                    try {
                        edtWalletAmount = Integer.parseInt(enteredValue);
                        if (promoCheckboxW.isChecked()) {
                            edtWalletAmount += promoCodeAmount*100;
                        }
                        int amountInPaise = edtWalletAmount * 100;
                        newAmount = amountInPaise;
                        // Continue with the rest of your code to open Razorpay with the newAmount
                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Invalid amount entered.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter an amount.", Toast.LENGTH_SHORT).show();
                }
                try {
                    // You need to pass a JSONObject with payment details to Razorpay
                    JSONObject options = new JSONObject();
                    options.put("name", "BugBazaar Private Limited"); // Replace with your company name
                    options.put("description", "Add money to wallet");
                    options.put("currency", "INR"); // Replace with the appropriate currency code
                    Log.d("newAmount2",String.valueOf(newAmount));
                    options.put("amount", newAmount); // Amount should be in paise
                    options.put("prefill.email", "customer@example.com");
                    options.put("prefill.contact", "1234567890");

                    // Callback URL (optional, can be used for handling payment success or failure)
                    // options.put("callback_url", "your_callback_url");
                    checkout.open(Wallet.this, options);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onPaymentSuccess(String s) {
        if (!promoredeem) { // Only set it to true if it's not already true
            promoredeem = true; // Set promoredeem to true

            // Store the updated promoredeem value in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("promoredeem", true);
            editor.apply();

        }

        // Clear the EditText after a successful payment
        EditText enterAmountEditText = findViewById(R.id.enterAmountW);
        enterAmountEditText.setText("");
        enterAmountEditText.clearFocus();
        CheckBox promoCheckboxW=findViewById(R.id.promoCheckboxW);
        promoCheckboxW.clearFocus();
        promoCheckboxW.setChecked(false);

        // Retrieve the current wallet balance from SharedPreferences
        int currentBalance = sharedPreferences.getInt(WALLET_BALANCE_KEY, 12000);

        // Calculate the new wallet balance
        int updatedBalance = currentBalance + edtWalletAmount;


        // Save the updated wallet balance back to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(WALLET_BALANCE_KEY, updatedBalance);
        editor.apply();

        // Update the displayed wallet balance
        walletBalanceW.setText(formatPrice(updatedBalance));

        Toast.makeText(this, "Added money to the wallet.", Toast.LENGTH_SHORT).show();
    }

    public void onPaymentError(int code, String response) {
        // Clear the EditText after a successful payment
        EditText enterAmountEditText = findViewById(R.id.enterAmountW);
        enterAmountEditText.setText("");

        switch (code) {
            case Checkout.NETWORK_ERROR:
                Toast.makeText(this, "Network Error: Please check the internet connection.", Toast.LENGTH_LONG).show();
                break;
            case Checkout.INVALID_OPTIONS:
                // Handle invalid payment options
                break;
            case Checkout.PAYMENT_CANCELED:
                Toast.makeText(this, "Payment Error: Payment Canceled by the user.", Toast.LENGTH_LONG).show();
                break;
            // Add more cases for specific error codes as needed
        }
    }

    private String formatPrice(int price) {
        return String.format("₹%,d", price);
    }

    // Code to handle the back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

    // Update finalAmountW with the entered amount
    private void updateFinalAmount(String enteredValue) {
        if (!enteredValue.isEmpty()) {
            try {
                int edtWalletAmount = Integer.parseInt(enteredValue);
                // Perform any calculations if needed
                finalAmountW.setText(formatPrice(edtWalletAmount));
            } catch (NumberFormatException e) {
                finalAmountW.setText("Invalid amount");
            }
        } else {
            finalAmountW.setText("₹");
        }
    }
    private void setPromoRedeem(boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("promoredeem", value);
        editor.apply();
    }
    private boolean getPromoRedeem() {
        return sharedPreferences.getBoolean("promoredeem", false); // The second parameter is the default value if it's not found in shared preferences
    }
}
