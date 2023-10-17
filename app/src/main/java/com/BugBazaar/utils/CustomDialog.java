package com.BugBazaar.utils;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.BugBazaar.ui.Signin;

public class CustomDialog {
    public static void showCustomDialog(Context context, String title, String message, PendingIntent pendingIntent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent  intent  =new Intent(context, Signin.class);

                        context.startActivity(intent);


                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
