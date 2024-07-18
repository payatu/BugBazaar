package com.BugBazaar.Models;

import android.util.Log;

public class CredentialsLoader {
    // Obfuscated username and password
    private static final String OBFUSCATED_USERNAME_HEX = "418<;"; // Obfuscated "admin"
    private static final String OBFUSCATED_PASSWORD_HEX = "\u0017 2\u00174/44'\u0006066::9"; // Obfuscated "BugBazaarSeccool"

    // XOR key for obfuscation/deobfuscation
    private static final byte XOR_KEY = 0x55; //85

    // Function to perform XOR obfuscation
    private static String obfuscate(String str) {
        char[] obfuscatedChars = str.toCharArray();
        for (int i = 0; i < obfuscatedChars.length; i++) {
            obfuscatedChars[i] = (char) (obfuscatedChars[i] ^ XOR_KEY);
        }
        return new String(obfuscatedChars);
    }


    // Function to deobfuscate the obfuscated strings
    private static String deobfuscate(String obfuscatedString) {
        char[] obfuscatedChars = obfuscatedString.toCharArray();
        for (int i = 0; i < obfuscatedChars.length; i++) {
            obfuscatedChars[i] = (char) (obfuscatedChars[i] ^ XOR_KEY);
        }
        return new String(obfuscatedChars);
    }

    public static User getUser() {
        String username = deobfuscate(OBFUSCATED_USERNAME_HEX);
        Log.d("uname",username);
        String password = deobfuscate(OBFUSCATED_PASSWORD_HEX);
        Log.d("pass",password);

        return new User(username, password);
    }

}
