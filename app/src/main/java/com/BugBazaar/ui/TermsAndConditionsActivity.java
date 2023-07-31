package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BugBazaar.R;
import com.BugBazaar.utils.AppConstants;
import android.webkit.SafeBrowsingResponse;

public class TermsAndConditionsActivity extends AppCompatActivity {
    WebView webView;

    private String webViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        webView = findViewById(R.id.terms_condition_view);

        setupwebview(webView);
        if (getIntent().getExtras() != null) {
            if (getIntent().hasExtra(AppConstants.KEY_WEBVIEW_URL)) {
                this.webViewUrl = getIntent().getExtras().getString(AppConstants.KEY_WEBVIEW_URL);
                startWebView(this.webViewUrl);
            }


            }

        else {
            startWebView(AppConstants.Terms_Conditions_URL);


        }



}

    private void setupwebview(WebView webView) {
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);


    }

    private void startWebView(String webViewUrl) {

        if (webViewUrl.contains("bugbazaar") && webViewUrl.contains("bugbazaar.com")) {
            webView.loadUrl(webViewUrl);
            return;


        }

        else {
            webView.loadUrl(AppConstants.Terms_Conditions_URL);


        }


    }
}