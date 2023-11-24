package com.BugBazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.BugBazaar.ui.SessionManager;
import com.BugBazaar.utils.customhandle;

public class ExternalAuthLogin extends AppCompatActivity {

    private static final String EXTRA_REDIRECT_URL = "ExternalAuthLoginActivity.EXTRA_REDIRECT_URL";
    private boolean customTabStarting;
    private static final String SESSION_QUERY_PARAM = "_sid_";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (bundle == null) {
            this.customTabStarting = true;

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.customTabStarting){

            this.customTabStarting = false;
            launchCustomTabs();
        }

    }

    private void launchCustomTabs() {
        SessionManager sessionManager =new SessionManager(this);
        Uri.Builder buildUpon = Uri.parse(getRedirectUrl()).buildUpon();
        buildUpon.appendQueryParameter(SESSION_QUERY_PARAM, sessionManager.getUserToken());
        customhandle.openCustomTab(this, buildUpon);


    }



    private final String getRedirectUrl() {
        String stringExtra = getIntent().getStringExtra(EXTRA_REDIRECT_URL);
        return stringExtra == null ? "" : stringExtra;
    }
}