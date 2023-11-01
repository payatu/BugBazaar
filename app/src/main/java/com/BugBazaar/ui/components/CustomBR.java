package com.BugBazaar.ui.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class CustomBR extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String intData = intent.getStringExtra("command");
        String strMatch = "CxIxD";
        if (intData.equals(strMatch)) {
            Intent intx = new Intent(context, ved.class);
            intx.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag
            try {
                context.startActivity(intx);
            } catch (Exception e) {
                Log.d("Excep", String.valueOf(e));
            }

        } else {
            Log.d("intentEmpty", "getStringExtra is empty.");
        }
    }
}
