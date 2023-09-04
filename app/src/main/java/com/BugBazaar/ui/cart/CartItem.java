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

    public CartItem(Product product, String productName, int price, int quantity, long productimage) {
        this.product = product;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
       this.productimage = productimage;
    }

    // Constructor for creating a CartItem without a Product object
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
    public long getImage(){
        return productimage;
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(product, i);
        parcel.writeString(productName);
        parcel.writeInt(price);
        parcel.writeInt(quantity);
        long imageResID=productimage;
        Log.d("parcelIntProdimg",String.valueOf(imageResID));
        parcel.writeLong(imageResID);
        Log.d("writeLongCheck","Long sent");
    }
}
