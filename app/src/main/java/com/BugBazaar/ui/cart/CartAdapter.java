package com.BugBazaar.ui.cart;

import android.content.Context;
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
        holder.itemPrice.setText("Price: ₹" + String.valueOf(cartItem.getPrice()));
        holder.itemQuantity.setText("Quantity: " + cartItem.getQuantity());
        Log.d("cartItemImage",String.valueOf(cartItem.getImage()));
        holder.itemImage.setImageResource((int) cartItem.getImage());
        // Set the product image here, if you're doing it dynamically.
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

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.cartItemImage);
            itemName = itemView.findViewById(R.id.cartItemName);
            itemPrice = itemView.findViewById(R.id.cartItemPrice);
            itemQuantity = itemView.findViewById(R.id.cartItemQuantity);

        }
    }
}
