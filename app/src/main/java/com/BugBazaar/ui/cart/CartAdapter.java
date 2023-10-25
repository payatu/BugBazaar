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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.BugBazaar.R;

import java.util.List;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    public interface UpdateTotalCostListener {
        void updateTotalCost();
    }
    private Context context;
    private List<CartItem> cartItems;
    private CartDatabaseHelper cartDBHelper;
    private UpdateTotalCostListener listener;

    public CartAdapter(Context context, List<CartItem> cartItems, CartDatabaseHelper cartDBHelper, UpdateTotalCostListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.cartDBHelper = cartDBHelper;
        this.listener = listener;
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
        holder.incrementButton.setOnClickListener(v -> {
            cartItem.incrementQuantity(context); // Pass the context to update the database
            notifyDataSetChanged(); // Update the UI
            listener.updateTotalCost(); // Update the total cost
        });

        holder.decrementButton.setOnClickListener(v -> {
            // Decrement the quantity of the current item
            cartItem.decrementQuantity(context); // Pass the context to update the database
            notifyDataSetChanged(); // Update the UI
            listener.updateTotalCost(); // Update the total cost
        });
        holder.removeFromCart.setOnClickListener(v -> {
                // Remove the item from the database
                cartItem.removeItem(context); // Pass the context to update the database
                Toast.makeText(context, "Product has been removed from Cart", Toast.LENGTH_SHORT).show();

            // Remove the item from the list and update the UI
                cartItems.remove(position);
                notifyDataSetChanged();
        });

    }


    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;
        ImageView incrementButton;
        ImageView decrementButton;
        ImageView removeFromCart;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cartItemImage);
            itemName = itemView.findViewById(R.id.cartItemName);
            itemPrice = itemView.findViewById(R.id.cartItemPrice);
            itemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            itemQuantity = itemView.findViewById(R.id.cartItemQuantity); // Initialize the quantity TextView
            incrementButton = itemView.findViewById(R.id.incrementButton); // Initialize the incrementButton
            decrementButton = itemView.findViewById(R.id.decrementButton); // Initialize the decrementButton
            removeFromCart=itemView.findViewById(R.id.removeFromCart);//Remove from Cart

        }
    }
}
