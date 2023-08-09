package com.BugBazaar.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.BugBazaar.R;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer_sample extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_sample);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.mainNavView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Navigation Drawer actions
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Adding actions for each items in navigation drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.itemHome) {
                // Handle item clicks here. Same for all items below. Maybe start that activity with Intent class.
                Toast.makeText(this, "Home is clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.itemMyProfile) {
                Toast.makeText(this, "My Profile is clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.itemAboutUs) {
                Toast.makeText(this, "About Us is clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.itemContactUs) {
                Toast.makeText(this, "Contact Us is clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.itemTerms_Conditions) {
                Toast.makeText(this, "Terms & Conditions is clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.itemLogout) {
                Toast.makeText(this, "Logout is clicked", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
