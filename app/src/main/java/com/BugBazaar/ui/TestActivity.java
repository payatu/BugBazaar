package com.BugBazaar.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;

public class TestActivity extends AppCompatActivity {

    private TextView editableTextView;
    private ImageView pencilIcon;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        editableTextView = findViewById(R.id.editableTextView);
        pencilIcon = findViewById(R.id.pencilIcon);

        pencilIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make the TextView editable when the pencil icon is clicked
                editableTextView.setFocusable(true);
                editableTextView.setFocusableInTouchMode(true);
                editableTextView.requestFocus();

                // Show the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.showSoftInput(editableTextView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        editableTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Hide the keyboard when the EditText loses focus
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editableTextView.getWindowToken(), 0);
                }
            }
        });
    }
}
