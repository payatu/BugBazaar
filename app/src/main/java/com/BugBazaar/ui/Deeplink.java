package com.BugBazaar.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import com.BugBazaar.R;

import java.util.List;

public class Deeplink extends AppCompatActivity {
    //private List<Product> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        Uri deeplink = intent.getData();
        if (deeplink != null) {
            // Get the item parameter value from the URI
            String fetched_item = deeplink.getQueryParameter("item");
            Log.d("Item fetched from deep link:", fetched_item);

            //Start NavigationDrawer_Dashboard to populate the product list
            Intent intentA = new Intent(this, NavigationDrawer_Dashboard.class);
            intentA.putExtra("fetched_item", fetched_item);
            startActivity(intentA);

                }
            }
        }
