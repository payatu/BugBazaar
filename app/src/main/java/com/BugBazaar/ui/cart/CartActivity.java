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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincart);

        CartDatabaseHelper cartDBHelper=new CartDatabaseHelper(this,"cart.db",null,1);

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("My Cart");

        // Find the RecyclerView in the layout
        cartRecyclerView = findViewById(R.id.cartRecyclerView);

        // Initialize your cartItems list and populate it
        cartItems = new ArrayList<>();

        // Retrieve the ArrayList<CartItem> extra from the intent
        Intent intent = getIntent();
        ArrayList<CartItem> receivedCartItems = intent.getParcelableArrayListExtra("cartItems");

        // Log the product names from the initial cartItems list
        // Adding new cart items (receivedCartItems) to old list (cartItems)
        if (receivedCartItems != null) {
            for (CartItem cartItem : receivedCartItems) {
                // Check if the item already exists in cartItems
                boolean itemExists = false;
                for (CartItem existingItem : cartItems) {
                    if (existingItem.getProductName().equals(cartItem.getProductName())) {
                        // If the item exists, update its quantity
                        int existingQuantity = existingItem.getQuantity();
                        existingItem.setQuantity(existingQuantity + cartItem.getQuantity());
                        itemExists = true;
                        // Save the product details to the SQLite database

                        break;
                    }
                }

                if (!itemExists) {
                    // If the item is not already in cartItems, add it
                    cartItems.add(cartItem);
                }
            }
        }

        // Create and set up the adapter
        cartAdapter = new CartAdapter(this, cartItems);

        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Notify the adapter that the dataset has changed
        cartAdapter.notifyDataSetChanged();
    }

    // Code to handle back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
