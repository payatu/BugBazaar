package com.BugBazaar.ui.myorders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.BugBazaar.R;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Product Details");

        // Receive the Intent that started this activity
        Intent intent = getIntent();
        String orderId = intent.getStringExtra("order_id");
        Log.d("OrderHistoryActivity", "Order ID: " + orderId);

        // Use the OrderHistoryDatabaseHelper to fetch order details from the database
        OrderHistoryDatabaseHelper dbHelper = new OrderHistoryDatabaseHelper(this);
        List<String> productNames = dbHelper.getAllOrderProducts(orderId);
        int finalCost = dbHelper.getFinalCostForOrder(orderId); // Make sure you implement this method in the database helper

        // Create an OrderHistoryItem with the list of product names and order total
        OrderHistoryItem combinedOrderItem = new OrderHistoryItem(orderId, productNames, finalCost);
        List<OrderHistoryItem> orderItems = new ArrayList<>();
        orderItems.add(combinedOrderItem);

        // Find the RecyclerView in the layout
        RecyclerView recyclerView = findViewById(R.id.orderHistoryRecyclerView);
        // Create an adapter for the RecyclerView
        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orderItems, this);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(adapter);
        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add dividers between items
        adapter.addDividers(recyclerView);
    }

    // Code to handle the back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
