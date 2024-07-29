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
    TextView messageTextView;
    TextView toolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_link);

        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("DeepLink");

        messageTextView= findViewById(R.id.messageTextView);
        Intent intent = getIntent();
        Uri deeplink = intent.getData();
        toolbarTitle = findViewById(R.id.toolbarTitle);

        // Deep link handling for item
        if (deeplink != null && "/cart/add".equals(deeplink.getPath())) {
            // Get the item parameter value from the URI
            String fetched_item = deeplink.getQueryParameter("item");
            if (fetched_item != null) {
                //Log.d("Item fetched from deep link:", fetched_item);

                //Start NavigationDrawer_Dashboard to populate the product list
                Intent intentA = new Intent(this, NavigationDrawer_Dashboard.class);
                intentA.putExtra("fetched_item", fetched_item);
                startActivity(intentA);
            }
        }

        // Deep link handling for msg
        if (deeplink != null && "/offers".equals(deeplink.getPath())) {
            String message = deeplink.getQueryParameter("textMsg");
            String offer = deeplink.getQueryParameter("offer");
            if (message != null && offer==null) {
                messageTextView.setText(message);

            }
            else if(message!=null & offer!=null){
                Intent intentA = new Intent(this, NavigationDrawer_Dashboard.class);
                intentA.setData(Uri.parse(offer));
                startActivity(intentA);

            }


        }

        // Deep link handling for url
        if (deeplink != null && "/web".equals(deeplink.getPath())) {
            String webViewUrl = deeplink.getQueryParameter("urlToLoad");
            // Check if the "url" parameter contains "payatu.com"
            if (webViewUrl != null && webViewUrl.contains("payatu.com")) {

                webView = findViewById(R.id.deeplink_view);
                setupwebview(webView);
                this.webView.loadUrl(webViewUrl);
                messageTextView.setVisibility(View.GONE);
            }
            else{
                messageTextView.setText("Host is invalid.");
            }
        }
    }
    //Code to handle back button - Redirect to home page on back pressed

    private void setupwebview (WebView webView){
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
    }
    public void onBackButtonClick (View view){
        //  onBackPressed(); // Navigate back to the previous activity
        Intent backtohome = new Intent(this, NavigationDrawer_Dashboard.class);
        startActivity(backtohome);
    }
}