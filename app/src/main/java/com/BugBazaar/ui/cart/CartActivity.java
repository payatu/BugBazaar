package com.BugBazaar.ui.cart;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.BugBazaar.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincart);

        CartDatabaseHelper cartDBHelper = new CartDatabaseHelper(this, "cart.db", null, 1);

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("My Cart");

        // Find the RecyclerView in the layout
        cartRecyclerView = findViewById(R.id.cartRecyclerView);

        // Initialize your cartItems list and populate it with all items from the database
        cartItems = cartDBHelper.getAllRecords();

        // Create and set up the adapter
        cartAdapter = new CartAdapter(this, cartItems);

        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // Code to handle back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
