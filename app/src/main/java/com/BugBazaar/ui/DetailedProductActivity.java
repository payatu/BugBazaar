package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.BugBazaar.R;
import com.BugBazaar.ui.Product;

import java.net.Inet4Address;

public class DetailedProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_product);

        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Product Details");

        // Retrieve the product details passed from the adapter
        //Intent intent = getIntent();
        Product product = getIntent().getParcelableExtra("product");

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
                CartItem cartItem = new CartItem(product, 1);
//                cartItem=getIntent().getParcelableExtra("product");
                if(getIntent().getParcelableExtra("product")==null){

                    Log.d("amitcool", String.valueOf(cartItem));
                }


                // Create a CartItem instance with the clicked product and quantity 1
                //CartItem cartItem = new CartItem(product, 1);
                Intent intent =new Intent(getApplicationContext(), CartActivity.class);
                intent.putExtra("addedCartItem",cartItem);

                // Add the cartItem to the cart
                Cart.getInstance().addCartItem(cartItem);

                //Optionally, show a toast or a message to indicate the item was added to the cart
                    Toast.makeText(DetailedProductActivity.this, "Product has been added to cart", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
            }
        });
    }



    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

}
