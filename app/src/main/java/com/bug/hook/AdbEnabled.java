package com.bug.hook;
import bug.bazzar.profiling.yyydddy;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class AdbEnabled {
    public static boolean AdbEnabled1(Context context) {
        StringBuilder sb = new StringBuilder();
        sb.append("AdbEnabled: ");
        sb.append(Settings.Secure.getInt(context.getContentResolver(), yyydddy.n006E006E006En006E006E, 0) == 1);
        Log.i("AdbEnabled", sb.toString());
        return Settings.Global.getInt(context.getContentResolver(), yyydddy.n006E006E006En006E006E, 0) == 1;
    }
}
