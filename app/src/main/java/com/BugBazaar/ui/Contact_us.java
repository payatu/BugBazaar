package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.BugBazaar.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Contact_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Contact us");

        WebView webView = findViewById(R.id.webview2);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().getAllowFileAccessFromFileURLs();
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setWebContentsDebuggingEnabled(true);
        Uri uri = getIntent().getData();

        if (uri == null) {

            webView.loadUrl("file:///android_asset/contact-us.html");

        } else {

            webView.loadUrl(String.valueOf(uri));
        }

        class MyWebViewClient extends WebViewClient {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                // Check the requested URL and determine if you want to provide a custom response
                Uri uri = request.getUrl();

                if (uri == null) {
                    return null;
                }


                Log.d("checkurl", String.valueOf(uri.getPath().startsWith("/local_cache/")));
                // https://any.domain/local_cache/..%2Fshared_prefs%2Fuserinfo.xmlhttps://any.domain/local_cache/..%2Fshared_prefs%2Fuserinfo.xmlhttps://any.domain/local_cache/..%2Fshared_prefs%2Fuserinfo.xml
                // Example: If the requested URL ends with ".png", return a custom WebResourceResponse
                if (uri.getPath().startsWith("/local_cache/")) {
                    // Read the content of the cache file
                    File cacheDir = getCacheDir();
                    File cacheFile = new File(cacheDir, uri.getLastPathSegment());
                    Log.d("testcachelastsegment", uri.getLastPathSegment());
                    Log.d("testcachefile", String.valueOf(cacheFile));
                    if (cacheFile.exists()) {
                        try {
                            FileInputStream fileInputStream = new FileInputStream(cacheFile);
//                        Log.d("testcache ", fileInputStream.toString());
                            String mimeType = "text/html";
                            String encoding = "utf-8";
                            int statusCode = 200;
                            String reasonPhrase = "OK";
                            Map<String, String> headers = new HashMap<>();
                            headers.put("Access-Control-Allow-Origin", "*");
                            return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, headers, fileInputStream);
                        } catch (IOException e) {
                            Log.d("testerr", String.valueOf(e));
                            return null;
                        }
                    }


                    if (!cacheFile.exists()) {
                        try {
                            // Fetch the response from the URL


                            URL url = new URL(uri.toString().replace("/local_cache", ""));
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.connect();

                            // Check if the response is successful
                            int responseCode = connection.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                InputStream inputStream = connection.getInputStream();
                                OutputStream outputStream = new FileOutputStream(cacheFile);

                                Log.d("testuser", String.valueOf(outputStream));
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = inputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, bytesRead);
                                }
                                outputStream.close();
                                inputStream.close();

                                FileInputStream fileInputStream = new FileInputStream(cacheFile);
                                String mimeType = "text/html";
                                String encoding = "utf-8";
                                int statusCode = 200;
                                String reasonPhrase = "OK";
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Access-Control-Allow-Origin", "*");
                                return new WebResourceResponse(mimeType, encoding, statusCode, reasonPhrase, headers, fileInputStream);
                            }
                        } catch (IOException e) {
                            Log.e("testerr", "Failed to fetch and save cache file: " + e.getMessage());
                        }
                    }
                }

                // If the requested URL doesn't match "/local_cache/", continue with the default behavior
                return super.shouldInterceptRequest(view, request);
            }


        }
    }
    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}