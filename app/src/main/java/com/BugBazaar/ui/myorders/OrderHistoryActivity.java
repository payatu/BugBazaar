package com.BugBazaar.ui.myorders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.BugBazaar.R;
import java.util.ArrayList;
import java.util.List;

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

        if (orderId == null) {
            // OrderID is null, fetch and display all orders
            Toast.makeText(getApplicationContext(), "OrderID is null", Toast.LENGTH_SHORT).show();

            // Use the OrderHistoryDatabaseHelper to fetch all order items from the database
            OrderHistoryDatabaseHelper dbHelper = new OrderHistoryDatabaseHelper(this);
            List<OrderHistoryItem> orderItems = dbHelper.getAllOrderItemsz();

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
        } else {
            OrderHistoryDatabaseHelper dbHelper = new OrderHistoryDatabaseHelper(this);
            List<OrderHistoryItem> orderItems = dbHelper.getAllOrderItemsz();

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
    }

        private void loadAndLogAllItemsFromDatabase() {
        // Load all items from the database and log them
        OrderHistoryDatabaseHelper dbHelper = new OrderHistoryDatabaseHelper(this);
        List<OrderHistoryItem> allItems = dbHelper.getAllOrderItemsz();

        for (OrderHistoryItem item : allItems) {
            Log.d("OrderHistoryActivity", "Order ID: " + item.getOrderID());
            Log.d("OrderHistoryActivity", "Product Names:");
            for (String productName : item.getProductNames()) {
                Log.d("OrderHistoryActivity", "  - " + productName);
            }
            Log.d("OrderHistoryActivity", "Order Total: " + item.getFinalCost());
        }
    }

    // Code to handle the back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
