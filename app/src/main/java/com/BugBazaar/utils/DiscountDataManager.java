package com.BugBazaar.utils;

public class DiscountDataManager {
    private static DiscountDataManager instance;
    private double discountPrice;

    private DiscountDataManager() {
        // Initialize the discountPrice or set it to a default value.
        discountPrice = 0.0;
    }

    public static synchronized DiscountDataManager getInstance() {
        if (instance == null) {
            instance = new DiscountDataManager();
        }
        return instance;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}

