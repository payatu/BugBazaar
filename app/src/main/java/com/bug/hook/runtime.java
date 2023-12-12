package com.bug.hook;

import android.content.ContentResolver;
import android.icu.text.SymbolTable;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class runtime {


    public static  boolean  isFridaServerRunning() {
        String processName = "frida-server";
        try {
            String command = "find / -type f -name '*frida*' 2>/dev/null";

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("frida")) {
                    return true;
                }
            }
            process.waitFor();
        } catch (IOException e) {
            Log.d("Exception: ", String.valueOf(e));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    public static  boolean areFridaFilesPresent() {
        String[] fridaFileNames = { "frida.so", "frida-gadget.so" }; // Add more as needed
        for (String fileName : fridaFileNames) {
            File file = new File("/data/local/tmp/", fileName); // Or other relevant directories
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }
    public static boolean isFridaDetectedinmounts() {
        try {
            File procMountsFile = new File("/proc/mounts");
            BufferedReader br = new BufferedReader(new FileReader(procMountsFile));
            String line;

            // Search for known Frida mount points
            while ((line = br.readLine()) != null) {
                if (line.contains("/frida")) {
                    return true;
                }
            }

            br.close();
        } catch (IOException e) {
            // Handle exceptions if necessary
        }
        return false;
    }

    boolean isDebugged() {
        return Debug.isDebuggerConnected();
    }

    public static boolean areDeveloperOptionsEnabled(ContentResolver contentResolver) {
        int developerOptions = Settings.Global.getInt(contentResolver, Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0);
        return developerOptions == 1;
    }


    public static boolean isFridaDetectedfile() {
        // Check /proc/mounts for "frida"
        if (checkFridaInContent(readFileContent("/proc/mounts"))) {
            return true;
        }

        // Check /system/bin for "frida"
        if (checkFridaInContent(readFileContent("/system/bin/frida-server"))) {
            return true;
        }

        return false;
    }

    private static String readFileContent(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    content.append(line).append('\n');
                }

                br.close();
                return content.toString();
            }
        } catch (IOException e) {
            // Handle exceptions if necessary
        }

        return "";
    }

    private static boolean checkFridaInContent(String content) {
        return content.contains("frida");
    }



}
