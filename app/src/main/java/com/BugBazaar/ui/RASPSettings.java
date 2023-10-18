package com.BugBazaar.ui;

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
        toolbarTitle.setText("RASP Settings");

        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        // Set listeners for each switch
        switch1.setOnCheckedChangeListener(new SwitchListener());
        switch2.setOnCheckedChangeListener(new SwitchListener());
        switch3.setOnCheckedChangeListener(new SwitchListener());
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
                    showToast("Switch 1 is turned on!");
                } else if (switchView == switch2) {
                    switch1.setChecked(false);
                    switch3.setChecked(false);
                    showToast("Switch 2 is turned on!");
                } else if (switchView == switch3) {
                    switch1.setChecked(false);
                    switch2.setChecked(false);
                    showToast("Switch 3 is turned on!");
                }
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
