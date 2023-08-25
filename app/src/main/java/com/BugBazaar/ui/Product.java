package com.BugBazaar.ui;
import android.os.Parcel;
import android.os.Parcelable;
public class Product implements Parcelable {
    private int imageResId;
    private String name;
    private String description;
    private String price;

    public Product(String name, String description, int imageResId, String price) {
        this.imageResId = imageResId;
        this.name = name;
        this.description = description;
        this.price=price;
    }
    // Rest of the Parcelable implementation...
    protected Product(Parcel in) {
        imageResId = in.readInt();
        name = in.readString();
        description = in.readString();
        price=in.readString();
    }
    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }
        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getPrice() {
        return price;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(imageResId);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(price);
    }
}

