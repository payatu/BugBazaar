package com.BugBazaar.Models;

public class UserProfileData {
    private final String name;
    private final String email;
    private final String mobile;
    private final String address;

    public UserProfileData(String name, String email, String mobile, String address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }
}
