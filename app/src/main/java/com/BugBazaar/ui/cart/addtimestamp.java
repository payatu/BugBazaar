package com.BugBazaar.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;


public class addtimestamp {

    private static Context context;
    public  static  void saveCartStartTime(View.OnClickListener onClickListener, String productName) {
        // Save the timestamp in SharedPreferences when an item is added to the cart
        SharedPreferences sharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        long currentTimeMillis = System.currentTimeMillis();
        editor.putString("productname", String.valueOf((productName)));
//        editor.putLong("cartStartTime", 232323);
        editor.apply();
    }


}
