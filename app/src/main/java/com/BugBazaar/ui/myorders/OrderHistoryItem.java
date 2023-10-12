package com.BugBazaar.ui.myorders;

import com.BugBazaar.ui.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryItem {
    private String orderID;
    private List<String> productNames; // List of product names
    private int finalCost;

    public OrderHistoryItem(String orderID, List<String> productNames, int finalCost) {
        this.orderID = orderID;
        this.productNames = productNames;
        this.finalCost = finalCost;
    }

    public String getOrderID() {
        return orderID;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public int getFinalCost() {
        return finalCost;
    }
}
