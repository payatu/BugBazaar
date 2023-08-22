package com.BugBazaar.ui;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        GridView gridView = findViewById(R.id.gridView);

        List<Product> productList = new ArrayList<>();
        // Add your product data here
        productList.add(new Product("Product 1", "Description 1", R.drawable.deadpool));
        productList.add(new Product("Product 2", "Description 2", R.drawable.jap));
        // Add more products

        ProductAdapter adapter = new ProductAdapter(this, productList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            // Handle item click, open detailed product page, etc.
            // You can use Intent to open a new activity for the detailed page.
        });
    }
}
