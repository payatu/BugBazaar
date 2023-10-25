package com.BugBazaar.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.BugBazaar.R;

import java.util.List;

public class Deeplink extends AppCompatActivity {
    //private List<Product> products;
    WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);
        Intent intent = getIntent();
        Uri deeplink = intent.getData();

        // Deep link handling for item
        if (deeplink != null && "/cart/add".equals(deeplink.getPath())) {
            // Get the item parameter value from the URI
            String fetched_item = deeplink.getQueryParameter("item");
            if (fetched_item != null) {
                Log.d("Item fetched from deep link:", fetched_item);

                //Start NavigationDrawer_Dashboard to populate the product list
                Intent intentA = new Intent(this, NavigationDrawer_Dashboard.class);
                intentA.putExtra("fetched_item", fetched_item);
                startActivity(intentA);
            }
        }

        // Deep link handling for msg
        if (deeplink != null && "/offers".equals(deeplink.getPath())) {
            String message = deeplink.getQueryParameter("msg");
            if (message != null) {
                // Display the message, e.g., in a TextView
                TextView toolbarTitle = findViewById(R.id.toolbarTitle);
                toolbarTitle.setText("Offers");
                TextView messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setText(message);
            } else {
                TextView toolbarTitle = findViewById(R.id.toolbarTitle);
                toolbarTitle.setText("Offers");
                TextView messageTextView = findViewById(R.id.messageTextView);
                messageTextView.setText("Coming Soon...............!");
            }
        }

        // Deep link handling for url
        if (deeplink != null && "/web".equals(deeplink.getPath())) {
            String webViewUrl = deeplink.getQueryParameter("url");
            // Check if the "url" parameter contains "payatu.com"
            if (webViewUrl != null && webViewUrl.contains("payatu.com")) {
                TextView toolbarTitle = findViewById(R.id.toolbarTitle);
                toolbarTitle.setText("BugBazaar");
                webView = findViewById(R.id.deeplink_view);
                setupwebview(webView);
                this.webView.loadUrl(webViewUrl);
            }
        }
    }
        //Code to handle back button - Redirect to home page on back pressed
        public void onBackButtonClick (View view){
            //  onBackPressed(); // Navigate back to the previous activity
            Intent backtohome = new Intent(this, NavigationDrawer_Dashboard.class);
            startActivity(backtohome);
        }
    private void setupwebview (WebView webView){
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
    }
    }
