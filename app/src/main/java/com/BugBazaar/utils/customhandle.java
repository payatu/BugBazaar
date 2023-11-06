package com.BugBazaar.utils;

import android.net.Uri;

import androidx.browser.customtabs.CustomTabsIntent;

import com.BugBazaar.ExternalAuthLogin;

public class customhandle {
    public static void openCustomTab(ExternalAuthLogin externalAuthLogin, Uri.Builder buildUpon) {

        CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
        customTabsIntent.launchUrl(externalAuthLogin, buildUpon.build());


    }
}
