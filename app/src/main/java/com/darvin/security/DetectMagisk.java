package com.darvin.security;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.system.Os;
import android.util.Log;
import com.google.android.material.snackbar.Snackbar;

public class DetectMagisk {

    private static final String TAG = "MagiskDetector";
    private Context context;
    private IIsolatedService serviceBinder;
    private DetectionListener listener;

    public interface DetectionListener {
        void onMagiskDetected();
        void onMagiskNotDetected();
    }

    public DetectMagisk(Context context) {
        this.context = context;
    }

    public void setDetectionListener(DetectionListener listener) {
        this.listener = listener;
    }

    public void startMagiskDetection() {
        if (serviceBinder != null) {
            checkForMagisk();
        } else {
            bindIsolatedService();
        }
    }

    private void bindIsolatedService() {
        Intent serviceIntent = new Intent(context, IsolatedService.class);
        context.bindService(serviceIntent, isolatedServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void checkForMagisk() {
        try {
            Log.d(TAG, "UID:" + Os.getuid());
            if (serviceBinder.isMagiskPresent() || Native.isMagiskPresentNative() ) {
                Log.d("hacker","detect");

                if (listener != null) {
                    Log.d("hacker","detect");

                    listener.onMagiskDetected();
                }
            } else {
                if (listener != null) {
                    listener.onMagiskNotDetected();
                }
            }
        } catch (RemoteException e) {
            Log.d("hacker","error");
        }
    }

    private ServiceConnection isolatedServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            serviceBinder = IIsolatedService.Stub.asInterface(iBinder);
            Log.d(TAG, "Service bound");
            checkForMagisk();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "Service Unbound");
        }
    };
}
