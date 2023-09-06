package com.BugBazaar.ui.cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.BugBazaar.R;
import com.BugBazaar.ui.Product;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder( CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);


        holder.itemName.setText(cartItem.getProductName());
        holder.itemPrice.setText("Price: ₹" + cartItem.getPrice());
        holder.itemQuantity.setText("Quantity: " + cartItem.getQuantity());
        holder.itemImage.setImageResource((int) cartItem.getImage());
        // Set the quantity text
        holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));


        // Set click listeners for increment and decrement buttons
        holder.incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the quantity of the current item
                cartItem.incrementQuantity();
                // Update the database with the new quantity
                updateQuantityInDatabase(cartItem);

                notifyDataSetChanged(); // Update the UI
            }
        });

        holder.decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrement the quantity of the current item, but ensure it doesn't go below 0
                if (cartItem.getQuantity() > 0) {
                    cartItem.decrementQuantity();

                    // Update the database with the new quantity
                    updateQuantityInDatabase(cartItem);
                    notifyDataSetChanged(); // Update the UI
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    private void updateQuantityInDatabase(CartItem cartItem) {
        long itemId = cartItem.getId(); // Get the item ID from the CartItem

        // Update the database with the new quantity using your CartDatabaseHelper
        CartDatabaseHelper dbHelper = new CartDatabaseHelper(context, "cart.db", null, 1);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CartItemDBModel.CartItemEntry.COLUMN_QUANTITY, cartItem.getQuantity());

        // Update the row with the matching item ID
        int rowsUpdated = database.update(
                CartItemDBModel.CartItemEntry.TABLE_NAME,
                values,
                CartItemDBModel.CartItemEntry._ID + " = ?",
                new String[]{String.valueOf(itemId)}
        );

        // Close the database connection
        dbHelper.close();

        if (rowsUpdated > 0) {
            Log.d("CartAdapter", "Quantity updated in the database.");
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;
        ImageView incrementButton;
        ImageView decrementButton;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cartItemImage);
            itemName = itemView.findViewById(R.id.cartItemName);
            itemPrice = itemView.findViewById(R.id.cartItemPrice);
            itemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            itemQuantity = itemView.findViewById(R.id.cartItemQuantity); // Initialize the quantity TextView
            incrementButton = itemView.findViewById(R.id.incrementButton); // Initialize the incrementButton
            decrementButton = itemView.findViewById(R.id.decrementButton); // Initialize the decrementButton


        }
    }
}
