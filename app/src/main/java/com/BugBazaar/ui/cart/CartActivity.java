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

        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("My Cart");

        // Find the RecyclerView in the layout
        cartRecyclerView = findViewById(R.id.cartRecyclerView);

        // Initialize your cartItems list and populate it
        cartItems = new ArrayList<>();

        // Retrieve the ArrayList<CartItem> extra from the intent
        Intent intent = getIntent();
        ArrayList<CartItem> receivedCartItems = intent.getParcelableArrayListExtra("cartItems");

        if (receivedCartItems != null) {
            cartItems.addAll(receivedCartItems);

        }

        // Create and set up the adapter
        cartAdapter = new CartAdapter(this, cartItems);
        cartRecyclerView.setAdapter(cartAdapter);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Notify the adapter that the dataset has changed
        cartAdapter.notifyDataSetChanged();
    }

    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}

