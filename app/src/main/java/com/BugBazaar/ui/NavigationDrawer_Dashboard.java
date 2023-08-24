package com.BugBazaar.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.BugBazaar.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawer_Dashboard extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private GridView productGridView;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_dashboard);

        // product data
        productList = new ArrayList<>();
        productList.add(new Product("DeadPool", "A merc with mouth", R.drawable.deadpool));
        productList.add(new Product("Squirtle", "Evolution Level 1", R.drawable.jap));
        productList.add(new Product("PoolDead", "Bad parody of deadpool", R.drawable.deadpool));
        productList.add(new Product("Turtle", "Just a water turtle", R.drawable.jap));
        productList.add(new Product("Wolverine", "A guy who scratch his balls with claws", R.drawable.deadpool));
        productList.add(new Product("Tortoise", "Description 2", R.drawable.jap));
        productList.add(new Product("Product 1", "Description 1", R.drawable.deadpool));
        productList.add(new Product("Product 2", "Description 2", R.drawable.jap));
        // Find the GridView in the layout
        productGridView = findViewById(R.id.productGridView);

        // Create and set the adapter for the GridView
        ProductAdapter adapter = new ProductAdapter(this, productList);
        productGridView.setAdapter(adapter);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.mainNavView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Navigation Drawer actions
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Adding actions for each items in navigation drawer
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.itemHome) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, NavigationDrawer_Dashboard.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.itemMyProfile) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, MyProfile.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.itemWallet) {
                Toast.makeText(this, "Wallet is clicked", Toast.LENGTH_SHORT).show();
            }
            else if (itemId == R.id.itemCart) {
                Toast.makeText(this, "Cart is clicked", Toast.LENGTH_SHORT).show();
            }else if (itemId == R.id.itemContactUs) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, Contact_us.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.itemTerms_Conditions) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, TermsAndConditionsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.itemLoginLogout) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, Signin.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
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
    //TOggle icon code
    public void onToggleDrawerClick(View view) {
        // Toggle the navigation drawer
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void onBackPressed() {
        finishAffinity();
    }
}