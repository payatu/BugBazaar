package com.BugBazaar.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
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
        // Hide the keyboard and clear focus from the EditText
        View focusedView = getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
            focusedView.clearFocus();
        }

        //Product Search views find
        EditText searchEditText = findViewById(R.id.searchBox);
        Button searchButton = findViewById(R.id.btnSearch);


        // Find the GridView in the layout
        productGridView = findViewById(R.id.productGridView);

        // product data
        productList = new ArrayList<>();
        productList.add(new Product("Old Town Camera",getString(R.string.desc_cycle), R.drawable.item_camera,"₹3,000"));
        productList.add(new Product("Dumb Watch", getString(R.string.desc_cycle), R.drawable.item_watch,"₹2,400"));
        productList.add(new Product("Skate-Board", getString(R.string.desc_cycle), R.drawable.item_skateboard,"₹5,640"));
        productList.add(new Product("A Lazy BiCycle", getString(R.string.desc_cycle), R.drawable.item_cycle,"₹19,000"));
        productList.add(new Product("PineApple iPhone", getString(R.string.desc_cycle), R.drawable.item_iphone,"₹69,000"));
        productList.add(new Product("Z Box Gaming Controller", getString(R.string.desc_cycle), R.drawable.item_gc,"₹3,400"));
        productList.add(new Product("A Rat", getString(R.string.desc_cycle), R.drawable.item_mouse,"₹1,200"));
        productList.add(new Product("Spy TWS", getString(R.string.desc_cycle), R.drawable.item_tws,"₹4,200"));
        productList.add(new Product("VR device", getString(R.string.desc_cycle), R.drawable.item_vr,"₹24,000"));


        // Create and set the adapter for the GridView
        ProductAdapter adapter = new ProductAdapter(this, productList);
        productGridView.setAdapter(adapter);
        //Adding onClickListener to search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = String.valueOf(searchEditText.getText());
                Log.d("amit", String.valueOf(searchText.length()));

                if (searchText.length() <= 25) {

                    ArrayList<Product> filteredList = new ArrayList<>();
                    for (Product product : productList) {
                        if (product.getName().toLowerCase().contains(searchText)) {
                            filteredList.add(product);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        // Show "No products found" message
                        ProductAdapter adapter = new ProductAdapter(NavigationDrawer_Dashboard.this, new ArrayList<>());
                        productGridView.setAdapter(adapter);
                        Toast.makeText(NavigationDrawer_Dashboard.this, "No products found", Toast.LENGTH_LONG).show();
                    } else {
                        // Update the GridView adapter with filtered data
                        ProductAdapter adapter = new ProductAdapter(NavigationDrawer_Dashboard.this, filteredList);
                        productGridView.setAdapter(adapter);
                    }

                    // Hide the keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                String filteredList = null;
                Log.d("Excpetion",filteredList);
            }

        });

        //Drawer and Navigation bar layout view find
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
                Intent intent =new Intent(this, CartActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
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