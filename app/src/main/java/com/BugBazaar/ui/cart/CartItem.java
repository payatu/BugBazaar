package com.BugBazaar.ui.cart;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.BugBazaar.ui.Product;

public class CartItem implements Parcelable {
    private Product product;
    private String productName;
    private int price;
    private int quantity;
    private long productimage;
    private long id; // Unique identifier for the item in the database


// Constructor for creating a CartItem without a Product object
public CartItem(String productName, int price, int quantity, long productimage) {
    this.productName = productName;
    this.price = price;
    this.quantity = quantity;
    this.productimage = productimage;
}



    protected CartItem(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        productName = in.readString();
        price = in.readInt();
        quantity = in.readInt();
        productimage=in.readLong();
    }

    // Constructor for creating a CartItem without a Product object
    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };


    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
    // Add a setter method for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public long getImage(){ return productimage; }
    // Add a getter and setter for the ID
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    // Increment the quantity by 1
    public void incrementQuantity(Context context) {
        if (quantity < Integer.MAX_VALUE) { // To avoid overflow
            quantity++;
            // Update the database with the new quantity
            CartDatabaseHelper dbHelper = new CartDatabaseHelper(context, "cart.db", null, 1);
            dbHelper.updateCartItem(this);
        }
    }

    // Decrement the quantity by 1
    public void decrementQuantity(Context context) {
        if (quantity > 0) {
            quantity--;
            if (quantity == 0) {
                // Remove the product from the database if quantity becomes 0
                CartDatabaseHelper dbHelper = new CartDatabaseHelper(context, "cart.db", null, 1);
                dbHelper.removeCartItem(this); // Pass the item's ID for removal
                //clear_item("",0);
            } else {
                // Update the database with the new quantity
                CartDatabaseHelper dbHelper = new CartDatabaseHelper(context, "cart.db", null, 1);
                dbHelper.updateCartItem(this);
            }
        }
    }
    public void removeItem(Context context){
        quantity=0;
        // Update the database with the new quantity
        CartDatabaseHelper dbHelper = new CartDatabaseHelper(context, "cart.db", null, 1);
        dbHelper.removeCartItem(this); // Added removeCartItem instead of updateCartItem that was called earlier
        // dbHelper.updateCartItem(this);
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeString(productName);
        parcel.writeInt(price);
        parcel.writeInt(quantity);
        long imageResID=productimage;
        parcel.writeLong(imageResID);
    }
}
