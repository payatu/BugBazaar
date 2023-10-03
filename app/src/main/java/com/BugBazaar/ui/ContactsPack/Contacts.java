package com.BugBazaar.ui.ContactsPack;

public class Contacts {
    private String name;
    private String phoneNumber;
    private boolean isSelected;

    public Contacts(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isSelected = false; // Initialize isSelected to false by default
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
