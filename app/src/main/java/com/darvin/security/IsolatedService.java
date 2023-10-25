package com.darvin.security;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.system.Os;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* loaded from: classes.dex */
public class IsolatedService extends Service {
    private static final String TAG = "DetectMagisk-Isolated";
    private String[] blackListedMountPaths = {"/sbin/.magisk/", "/sbin/.core/mirror", "/sbin/.core/img", "/sbin/.core/db-0/magisk.db"};
    private final IIsolatedService.Stub mBinder = new IIsolatedService.Stub() { // from class: com.darvin.security.IsolatedService.1
        @Override // com.darvin.security.IIsolatedService
        public boolean isMagiskPresent() {
            String[] strArr;
            Binder.clearCallingIdentity();
            Log.d(IsolatedService.TAG, "Isolated UID:" + Os.getuid());
            boolean z = false;
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(String.format("/proc/%d/mounts", Integer.valueOf(Process.myPid()))));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                int i = 0;
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    int i2 = i;
                    for (String str : IsolatedService.this.blackListedMountPaths) {
                        if (readLine.contains(str)) {
                            Log.d(IsolatedService.TAG, "Blacklisted Path found " + str);
                            i2++;
                        }
                    }
                    i = i2;
                }
                bufferedReader.close();
                fileInputStream.close();
                Log.d(IsolatedService.TAG, "Count of detected paths " + i);
                if (i > 0) {
                    Log.d(IsolatedService.TAG, "Found atleast 1 path ");
                    z = true;
                } else {
                    z = Native.isMagiskPresentNative();
                }
                Log.d(IsolatedService.TAG, "Found Magisk in Mount " + z);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return z;
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}