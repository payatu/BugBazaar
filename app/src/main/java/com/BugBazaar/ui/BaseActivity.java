package com.BugBazaar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.BugBazaar.ui.Signin;

public class BaseActivity extends AppCompatActivity {
    protected SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the SessionManager in your base activity
        sessionManager = new SessionManager(this);

        // Check the session status
        if (sessionManager.getUserToken() == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Signin.class));
            finish();
        }
    }
}
