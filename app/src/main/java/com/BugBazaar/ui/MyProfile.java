package com.BugBazaar.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BugBazaar.R;

import org.w3c.dom.Text;


public class MyProfile extends AppCompatActivity {
    //Assigning variables to views
    private TextView txtViewName;
    private EditText editTxtName;
    private TextView txtViewEmail;
    private EditText editTxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

    }
}