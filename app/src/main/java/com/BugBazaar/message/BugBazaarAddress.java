package com.BugBazaar.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BugBazaarAddress extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "Address has been saved Successfully", Toast.LENGTH_SHORT).show();
    }
}

