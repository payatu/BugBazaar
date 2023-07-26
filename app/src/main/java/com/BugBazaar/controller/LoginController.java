package com.BugBazaar.controller;

import com.BugBazaar.Models.CredentialsLoader;
import com.BugBazaar.Models.User;

public class LoginController {
    private final User user;

    public LoginController() {
        user = CredentialsLoader.getUser();

    }

    public boolean validateLogin(String enteredUsername, String enteredPassword) {
        // Validate the entered credentials against the stored user credentials
        return enteredUsername.equals(user.getUsername()) && enteredPassword.equals(user.getPassword());
    }

    public String getUsername() {
        return user.getUsername();
    }
}
