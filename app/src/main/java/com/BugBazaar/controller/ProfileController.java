package com.BugBazaar.controller;

import android.net.Uri;

import com.BugBazaar.Models.Profile;

import android.net.Uri;

public class ProfileController {

    private final Profile profile;

    public ProfileController(Uri profilePictureUri, String name, String bio) {
        profile = new Profile(profilePictureUri, name, bio);
    }

    public Profile getProfile() {
        return profile;
    }

    public void updateProfilePicture(Uri profilePictureUri) {
        profile.setProfilePictureUri(profilePictureUri);
    }
}
