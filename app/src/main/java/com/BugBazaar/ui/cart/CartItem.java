package com.BugBazaar.ui.cart;

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
    private int quantityFromDatabase;

    public CartItem(Product product, String productName, int price, int quantity, long productimage) {
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
       this.productimage = productimage;
    }

// Constructor for creating a CartItem without a Product object
    public CartItem(String productName, int price, int quantity, long productimage) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productimage = productimage;
        //this.quantityFromDatabase = 0;
    }


    protected CartItem(Parcel in) {
        product = in.readParcelable(Product.class.getClassLoader());
        productName = in.readString();
        price = in.readInt();
        quantity = in.readInt();
        productimage=in.readLong();
    }

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

    public Product getProduct() {
        return product;
    }

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
    public void incrementQuantity() {
        quantity++;
    }

    // Decrement the quantity by 1
    public void decrementQuantity() {
        if (quantity > 0) {
            quantity--;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
    // Add a getter for the quantity from the database
    public int getQuantityFromDatabase() {
        return quantityFromDatabase;
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
