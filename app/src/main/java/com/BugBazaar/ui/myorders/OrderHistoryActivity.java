package com.BugBazaar.ui.myorders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DividerItemDecoration;

import java.util.List;

import com.BugBazaar.R;
import com.BugBazaar.ui.NavigationDrawer_Dashboard;

public class OrderHistoryActivity extends AppCompatActivity {
    private EditText searchBoxOrders;
    private RecyclerView recyclerView;
    private Button btnSearchOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize UI components
        searchBoxOrders = findViewById(R.id.searchBoxOrders);
        recyclerView = findViewById(R.id.orderHistoryRecyclerView);
        btnSearchOrders = findViewById(R.id.btnSearchOrders);

        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("My Orders");

        // NoOrder Components
        LinearLayout emptyLinearOH = findViewById(R.id.emptyLinearOH);
        ImageView orderEmptyImage = findViewById(R.id.orderEmptyImage);
        TextView orderEmptyTextView = findViewById(R.id.orderEmptyTextView);

        // Receive the Intent that started this activity
        Intent intent = getIntent();
        String orderId = intent.getStringExtra("order_id");

        if (orderId == null) {
            // OrderID is null, fetch and display all old orders from OrderHistory db
            loadAllOrderItems();
        } else {
            // Load orders by order ID
            filterOrdersByOrderID(orderId);
        }

        // Set an OnClickListener for the search button
        btnSearchOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the search text from the EditText
                String searchText = searchBoxOrders.getText().toString();

                // Check if the search text contains the blocked characters (single quote or hyphen) SQLi Challange.
                if (!searchText.contains("'") && !searchText.contains("-")) {
                    // If the search text is valid, perform the search
                    filterOrdersByOrderID(searchText);
                } else {
                    // If the search text contains blocked characters, show an error message or take appropriate action
                    // For example, you can display a Toast message:
                    Toast.makeText(getApplicationContext(), "Invalid search text. Please avoid using special characters", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Add this method to load all order items
    private void loadAllOrderItems() {
        OrderHistoryDatabaseHelper dbHelper = new OrderHistoryDatabaseHelper(this);
        List<OrderHistoryItem> orderItems = dbHelper.getAllOrderItemsz();

        if (orderItems.isEmpty()) {
            // Order Empty UI components show
            showEmptyOrderUI();
        } else {
            // Display the order items
            displayOrders(orderItems);
        }
    }

    // Add this method to show the empty order UI
    private void showEmptyOrderUI() {
        recyclerView.setVisibility(View.GONE);
        // Order Empty UI components show
        LinearLayout emptyLinearOH = findViewById(R.id.emptyLinearOH);
        ImageView orderEmptyImage = findViewById(R.id.orderEmptyImage);
        TextView orderEmptyTextView = findViewById(R.id.orderEmptyTextView);

        emptyLinearOH.setVisibility(View.VISIBLE);
        orderEmptyImage.setVisibility(View.VISIBLE);
        orderEmptyTextView.setVisibility(View.VISIBLE);
    }

    // Add this method to display the order items
    private void displayOrders(List<OrderHistoryItem> orderItems) {
        // Create an adapter for the RecyclerView
        OrderHistoryAdapter adapter = new OrderHistoryAdapter(orderItems, this);

        // Set the adapter for the RecyclerView
        recyclerView.setAdapter(adapter);
        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add dividers between items
        adapter.addDividers(recyclerView);
    }

    // Add this method to filter orders by order ID
    private void filterOrdersByOrderID(String orderID) {
        OrderHistoryDatabaseHelper dbHelper = new OrderHistoryDatabaseHelper(this);
        List<OrderHistoryItem> orderItems = dbHelper.searchOrdersByOrderID(orderID);

        if (orderItems.isEmpty()) {
            // If no matching orders are found, show a toast message.
            Toast.makeText(OrderHistoryActivity.this, "No matching orders found", Toast.LENGTH_SHORT).show();
            // You can choose to show an empty order UI or handle it differently.
        } else {
            // Display the matching orders in the RecyclerView
            displayOrders(orderItems);
        }
    }

    // Code to handle the back button
    public void onBackButtonClick(View view) {
        Intent intent = new Intent(OrderHistoryActivity.this, NavigationDrawer_Dashboard.class);
        startActivity(intent);
    }

    public void onBackPressed() {
        // Create an Intent to start the desired activity
        Intent intent = new Intent(this, NavigationDrawer_Dashboard.class);

        // Start the desired activity
        startActivity(intent);

        // Finish the current activity
        finish();
    }
}
