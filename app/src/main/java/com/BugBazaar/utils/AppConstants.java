package com.BugBazaar.utils;

import android.content.Context;

import com.cloudinary.android.MediaManager;

import java.util.HashMap;
import java.util.Map;

public class AppConstants {

    public static final String KEY_WEBVIEW_URL = "webViewUrl";
    public  static  final String Terms_Conditions_URL ="https://payatu.com";

Context context;


    public void initializeCloudinary() {
        // Create a configuration map
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", s.H("?\u0014.\b\"??,_*"));
        config.put("api_key", s.H("i[n_j[lYlYmZc]c"));
        config.put("api_secret", s.H("\u0004\u001Fl\u0002:\u001Do\"815(+18\u000F,>!\u0016*6o\u001B\u0002)c"));

        // Initialize MediaManager with the configuration
        MediaManager.init(context, config);
    }


}


