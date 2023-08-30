package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BugBazaar.R;
import com.BugBazaar.ui.Product;

public class DetailedProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Product Details");

        // Retrieve the product details passed from the adapter
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");

        // Use the product details to display the detailed information
        ImageView detailedImage = findViewById(R.id.detailedImage);
        TextView detailedName = findViewById(R.id.detailedName);
        TextView detailedDescription = findViewById(R.id.detailedDescription);
        TextView detailedPrice=findViewById(R.id.detailedPrice);

        detailedImage.setImageResource(product.getImageResId());
        detailedName.setText(product.getName());
        detailedDescription.setText(product.getDescription());
        detailedPrice.setText(product.getPrice());
        //Add to cart button view
        Button addToCartButton = findViewById(R.id.addToCartButton);
        // Handle "Add to Cart" button click
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start AddToCartActivity
                Intent addToCartIntent = new Intent(DetailedProductActivity.this, Cart.class);
                // Pass the product details to the intent
                addToCartIntent.putExtra("product", product);
                // Start the AddToCartActivity
                startActivity(addToCartIntent);
            }
        });
    }



    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

}
