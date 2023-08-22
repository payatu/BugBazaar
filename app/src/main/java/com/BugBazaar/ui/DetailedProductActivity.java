package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.BugBazaar.R;
import com.BugBazaar.ui.Product;

public class DetailedProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);

        // Retrieve the product details passed from the adapter
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");

        // Use the product details to display the detailed information
        ImageView detailedImage = findViewById(R.id.detailedImage);
        TextView detailedName = findViewById(R.id.detailedName);
        TextView detailedDescription = findViewById(R.id.detailedDescription);

        detailedImage.setImageResource(product.getImageResId());
        detailedName.setText(product.getName());
        detailedDescription.setText(product.getDescription());
    }
}
