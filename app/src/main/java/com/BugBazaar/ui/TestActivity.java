package com.BugBazaar.ui;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        RadioButton radioButton1 = findViewById(R.id.radioButton1);
        RadioButton radioButton2 = findViewById(R.id.radioButton2);

        // Set OnClickListener for the first RadioButton
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a toast when the first RadioButton is clicked
                Toast.makeText(TestActivity.this, "First Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener for the second RadioButton
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a toast when the second RadioButton is clicked
                Toast.makeText(TestActivity.this, "Second Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
