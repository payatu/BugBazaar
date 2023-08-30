package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BugBazaar.R;

public class Cart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Cart");

        //Get intent from other activities
        Intent intent = getIntent();
        Product product=intent.getParcelableExtra("product");

        TextView txtProductName=findViewById(R.id.txtProductName);
        TextView txtProductPrice=findViewById(R.id.txtProductPrice);
        ImageView imgProduct=findViewById(R.id.imgProduct);

        txtProductName.setText(product.getName());
        txtProductPrice.setText(product.getPrice());
        imgProduct.setImageResource(product.getImageResId());

    }
    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}