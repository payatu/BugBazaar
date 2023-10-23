package com.darvin.security;

public class Native {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static native boolean isMagiskPresentNative();

    Native() {
    }

    static {
        System.loadLibrary("native-lib");
    }
}