package com.lingzhiyi.detectfridaxp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.BugBazaar.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("detectfridaxp");



    }




}