package com.BugBazaar.ui.components;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;

public class SMSService extends Service {
    public int onStartCommand(Intent intent, int flags, int startID){
        String phoneNumber=intent.getStringExtra("phone_number");
        String message = intent.getStringExtra("message");

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        // Stop the service after sending the SMS
        stopSelf();

        return Service.START_NOT_STICKY;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
