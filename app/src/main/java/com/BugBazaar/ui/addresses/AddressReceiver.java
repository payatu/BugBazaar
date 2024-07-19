package com.BugBazaar.ui.addresses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AddressReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context.getApplicationContext(), "address hacker changes", Toast.LENGTH_SHORT).show();
    }
}
