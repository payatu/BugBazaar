package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BugBazaar.R;
import com.BugBazaar.ui.cart.Cart;
import com.BugBazaar.ui.cart.CartActivity;
import com.BugBazaar.ui.cart.CartAdapter;
import com.BugBazaar.ui.cart.CartDatabaseHelper;
import com.BugBazaar.ui.cart.CartItem;

import java.util.ArrayList;
import java.util.List;


public class DetailedProductActivity extends AppCompatActivity {
    private List<CartItem> cartItems = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);


        CartDatabaseHelper cartDBHelper = new CartDatabaseHelper(this, "cart.db", null, 1);

        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Product Details");

        // Retrieve the product details passed from the adapter
        Product product = getIntent().getParcelableExtra("product");

        // Use the product details to display the detailed information
        ImageView detailedImage = findViewById(R.id.detailedImage);
        TextView detailedName = findViewById(R.id.detailedName);
        TextView detailedDescription = findViewById(R.id.detailedDescription);
        TextView detailedPrice = findViewById(R.id.detailedPrice);

        detailedImage.setImageResource(product.getImageResId());
        detailedName.setText(product.getName());
        detailedDescription.setText(product.getDescription());
        String stringPrice = "₹" + product.getPrice();
        detailedPrice.setText(stringPrice);

        // Add to cart button view
        Button addToCartButton = findViewById(R.id.addToCartButton);

// Handle "Add to Cart" button click
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the product details passed from the adapter
                Product product = getIntent().getParcelableExtra("product");

                // Create a CartItem instance with the clicked product and quantity 1
                String productName = product.getName();
                int productPrice = product.getPrice();
                int productImage = product.getImageResId();
                int quantity = 1;

                // Check if the item already exists in the cart
                boolean itemExists = false;
                for (CartItem cartItem : cartItems) {
                    if (cartItem.getProductName().equals(productName)) {
                        // If the item exists, update its quantity
                        int existingQuantity = cartItem.getQuantity();
                        cartItem.setQuantity(existingQuantity + quantity);
                        itemExists = true;
                        // Update the quantity in the database
                        cartDBHelper.updateCartItem(cartItem);
                        break;
                    }
                }

                if (!itemExists) {
                    // If the item is not already in the cart, add it
                    CartItem cartItem = new CartItem(productName, productPrice, quantity, productImage);
                    cartItems.add(cartItem);
                    // Save the product details to the SQLite database
                    long recordId = cartDBHelper.saveProductDetails(productName, productPrice, quantity, productImage);
                }

                // Start CartActivity without sending the product details as Parcelable
                Intent intent = new Intent(DetailedProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }
        // Code to handle back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}

