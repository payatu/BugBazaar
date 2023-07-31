package com.BugBazaar.Models;

import android.net.Uri;

public class Profile {
    private Uri profilePictureUri;
    private String name;
    private String bio;
    private String contactInfo;

    public Profile(Uri profilePictureUri, String name, String bio) {
        this.profilePictureUri = profilePictureUri;
        this.name = name;
        this.bio = bio;
        this.contactInfo = contactInfo;
    }

    public Uri getProfilePictureUri() {
        return profilePictureUri;
    }

    public void setProfilePictureUri(Uri profilePictureUri) {
        this.profilePictureUri = profilePictureUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
