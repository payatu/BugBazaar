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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);


        CartDatabaseHelper cartDBHelper=new CartDatabaseHelper(this,"cart.db",null,1);

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
        String stringPrice = "₹" +product.getPrice() ;
        detailedPrice.setText(stringPrice);

        // Add to cart button view
        Button addToCartButton = findViewById(R.id.addToCartButton);



        // Handle "Add to Cart" button click
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a CartItem instance with the clicked product and quantity 1
                String productName = product.getName();
                int productPrice = product.getPrice();
                int productImage=product.getImageResId();
                Log.d("prodImg",String.valueOf(productImage));

                int quantity = 1;

                List<CartItem> cartItems = new ArrayList<>();

                // Create a new CartItem with the updated quantity
                CartItem cartItem = new CartItem(productName, productPrice, quantity, productImage);
                //Log.d("cartItemsProdImg",String.valueOf(cartItem.getImage()));
                cartItems.add(cartItem);
                // Save the product details to the SQLite database
                long recordId = cartDBHelper.saveProductDetails(productName, productPrice, quantity, productImage);
                // Optionally, show a toast or a message to indicate the item was added to the cart
                Toast.makeText(DetailedProductActivity.this, "Product has been added to cart", Toast.LENGTH_SHORT).show();
                cartItems = cartDBHelper.getAllRecords();
                //Log.d("getAllRecords",String.valueOf(cartDBHelper.getAllRecords()));
                ArrayList<CartItem> cartItemList = new ArrayList<>(cartItems);

                Intent intent = new Intent(DetailedProductActivity.this, CartActivity.class);
                intent.putParcelableArrayListExtra("cartItems", cartItemList);
                startActivity(intent);

            }
        });

    }

    // Code to handle back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}

