package com.BugBazaar.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

public class RASPSettings extends AppCompatActivity {

    private Switch switch1, switch2, switch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raspsettings);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("App protection settings");

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        // Set listeners for each switch
        switch1.setOnCheckedChangeListener(new SwitchListener());
        switch2.setOnCheckedChangeListener(new SwitchListener());
        switch3.setOnCheckedChangeListener(new SwitchListener());

        // Get SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("SwitchStatePrefs", Context.MODE_PRIVATE);
        // Set the initial state of each switch based on the stored values
        switch1.setChecked(sharedPreferences.getBoolean("switch1_state", false));
        switch2.setChecked(sharedPreferences.getBoolean("switch2_state", false));
        switch3.setChecked(sharedPreferences.getBoolean("switch3_state", false));
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Store the state of switch1 in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switch1_state", isChecked);
                editor.apply();
                restartApplication();

                // If switch1 is turned on, turn off other switches
                if (isChecked) {
                    switch2.setChecked(false);
                    switch3.setChecked(false);

                }
            }
        });
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Store the state of switch1 in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switch2_state", isChecked);
                editor.apply();
                restartApplication();
                // If switch1 is turned on, turn off other switches
                if (isChecked) {
                    switch1.setChecked(false);
                    switch3.setChecked(false);
                }
            }
        });
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Store the state of switch1 in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("switch3_state", isChecked);
                editor.apply();
                restartApplication();
                // If switch1 is turned on, turn off other switches
                if (isChecked) {
                    switch1.setChecked(false);
                    switch2.setChecked(false);
                }
            }
        });


    }

    private class SwitchListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // Check which switch was changed
            Switch switchView = (Switch) buttonView;

            // Turn off other switches
            if (isChecked) {
                if (switchView == switch1) {
                    switch2.setChecked(false);
                    switch3.setChecked(false);
                    showToast("EASY PROTECTION IS ON!!!");


                } else if (switchView == switch2) {
                    switch1.setChecked(false);
                    switch3.setChecked(false);
                    showToast("Medium PROTECTION IS ON!!!");
                } else if (switchView == switch3) {
                    switch1.setChecked(false);
                    switch2.setChecked(false);
                    showToast("Advanced PROTECTION IS ON!!!");
                }
            }
        }
    }

    private void restartApplication() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
finishAffinity();    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }


}
