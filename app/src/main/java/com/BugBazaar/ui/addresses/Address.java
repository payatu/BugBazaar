package com.BugBazaar.ui.addresses;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.BugBazaar.R;
import com.BugBazaar.ui.BaseActivity;
import com.BugBazaar.ui.SessionManager;
import com.BugBazaar.ui.Signin;

import java.util.List;

public class Address extends BaseActivity {
    private SessionManager sessionManager;
    private EditText editTextNewAddrNickName;
    private EditText editTextNewAddress;
    private EditText searchBoxEditText;
    private Button btnSaveAddress;
    private Button btnSearchAddress;
    private LinearLayout addressListLayout;
    private SQLiteDatabase database;
    private AddressDatabaseHelper dbHelper; // Declare dbHelper here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        addressListLayout = findViewById(R.id.addressListLayout);
        //Session check
        sessionManager = new SessionManager(this);
        if (sessionManager.getUserToken()==null) {
            Toast.makeText(getApplicationContext(),"Please login first",Toast.LENGTH_SHORT).show();
            // The user is not logged in; redirect to the login activity
            startActivity(new Intent(this, Signin.class));
            finish();  // Prevent going back to the previous activity
        }

        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Addresses");

        // Assuming you have a LinearLayout where you want to add the custom layouts
        LinearLayout addressListLayout = findViewById(R.id.addressListLayout);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Button btnSearchAddress = findViewById(R.id.btnSearchAddress);
        EditText searchBox = findViewById(R.id.searchBoxAddress);


        // Initialize dbHelper and declare it as final
        AddressDatabaseHelper dbHelper = new AddressDatabaseHelper(this);

        // Inside your Address.java activity
        Button btnSaveAddress = findViewById(R.id.btnSaveAddress);
        EditText editNickName = findViewById(R.id.editTextNewAddrNickName);
        EditText editAddress = findViewById(R.id.editTextNewAddress);

        List<AddressItem> addresses = dbHelper.getAllAddresses();
        for (AddressItem item : addresses) {
            View customAddressView = inflater.inflate(R.layout.item_address, null);
            TextView nicknameTextView = customAddressView.findViewById(R.id.nicknameTextView);
            TextView addressTextView = customAddressView.findViewById(R.id.addressTextView);
            // Set the nickname and detailed address for this address item
            nicknameTextView.setText(item.getNickname());
            addressTextView.setText(item.getAddress());

            // Add the custom layout to the main layout
            addressListLayout.addView(customAddressView);
        }


        btnSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickname = editNickName.getText().toString();
                String address = editAddress.getText().toString();

                if (nickname.isEmpty() || address.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter address type and detailed Address", Toast.LENGTH_SHORT).show();
                } else if (address.length() < 10) {
                    Toast.makeText(getApplicationContext(), "Address must have at least 10 characters", Toast.LENGTH_SHORT).show();
                } else {
                    addressListLayout.removeAllViews();
                    // Insert data into the SQLite database
                    dbHelper.insertAddress(nickname, address);
                    List<AddressItem> addresses = dbHelper.getAllAddresses();
                    Toast.makeText(getApplicationContext(), "Address has been saved Successfully", Toast.LENGTH_SHORT).show();

                    for (AddressItem item : addresses) {
                        View customAddressView = inflater.inflate(R.layout.item_address, null);
                        TextView nicknameTextView = customAddressView.findViewById(R.id.nicknameTextView);
                        TextView addressTextView = customAddressView.findViewById(R.id.addressTextView);
                        // Set the nickname and detailed address for this address item
                        nicknameTextView.setText(item.getNickname());
                        addressTextView.setText(item.getAddress());
                        // Add the custom layout to the main layout
                        addressListLayout.addView(customAddressView);
                    }
                    // Clear input fields
                    editNickName.setText("");
                    editNickName.clearFocus();
                    editAddress.setText("");
                    editAddress.clearFocus();
                }
            }
        });


        btnSearchAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear previous search results
                addressListLayout.removeAllViews();
                String nickname = searchBox.getText().toString();

                if (nickname.equals("'--") || nickname.equals("\"--") || nickname.equals("--")) {
                    // Display a toast error
                    Toast.makeText(getApplicationContext(), "Sorry! This input is not allowed", Toast.LENGTH_SHORT).show();
                } else {
                    // Query the database for the address with the entered nickname
                    List<AddressItem> searchResults = dbHelper.searchAddressesByNickname(nickname);

                    if (searchResults != null && !searchResults.isEmpty()) {
                        for (AddressItem item : searchResults) {
                            View customAddressView = inflater.inflate(R.layout.item_address, null);
                            TextView nicknameTextView = customAddressView.findViewById(R.id.nicknameTextView);
                            TextView addressTextView = customAddressView.findViewById(R.id.addressTextView);
                            // Set the nickname and detailed address for this address item
                            nicknameTextView.setText(item.getNickname());
                            addressTextView.setText(item.getAddress());

                            // Add the custom layout to the main layout
                            addressListLayout.addView(customAddressView);
                        }
                    } else {
                        // Handle the case when no results are found
                        // For example, you can display a message like this:
                        TextView noResultTextView = new TextView(Address.this);
                        noResultTextView.setText("No matching addresses found.");
                        noResultTextView.setTextSize(20);
                        noResultTextView.setTextColor(getResources().getColor(R.color.black));
                        addressListLayout.addView(noResultTextView);
                    }
                }
            }
        });
    }
        //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

}
