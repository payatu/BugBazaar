package com.BugBazaar.ui.components;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.BugBazaar.R;
import com.BugBazaar.ui.NavigationDrawer_Dashboard;

public class ved extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ved);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("BR");
    }

    //Code to handle backbutton
    public void onBackButtonClick(View view) {
       Intent intent=new Intent(getApplicationContext(), NavigationDrawer_Dashboard.class);
       startActivity(intent);
    }
}