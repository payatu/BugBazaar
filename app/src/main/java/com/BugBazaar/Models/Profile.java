package com.BugBazaar.Models;

import android.net.Uri;

public class Profile {
    public Profile(Uri profilePictureUri, String name, String bio) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;
    private String mobileNumber;
    private String name;

    public void setProfilePictureUri(Uri profilePictureUri) {
    }

    // Constructors, getters, and setters
    // ...
}
