package com.BugBazaar.ui.myorders;

import com.BugBazaar.ui.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryItem {
    private String orderID;
    private List<String> productNames;
    private List<Integer> productQuantities;
    private int finalCost;
    private List<Integer> productImages;

    public OrderHistoryItem(String orderID, List<String> productNames, List<Integer> productQuantities, int finalCost) {
        this.orderID = orderID;
        this.productNames = productNames;
        this.productQuantities = productQuantities;
        this.finalCost = finalCost;
    }

    public String getOrderID() {
        return orderID;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public List<Integer> getProductQuantities() {
        return productQuantities;
    }

    public int getFinalCost() {
        return finalCost;
    }
}
