package com.BugBazaar.ui.ContactsPack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.BugBazaar.R;
import com.BugBazaar.ui.ContactsPack.SelectContacts;

public class ReferUs extends AppCompatActivity {

    Button btnCopyLink;
    Button btnSendEmail;
    Button openContactButton;
    EditText edtEmailId;
    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_us);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Refer-Us");

        btnCopyLink=findViewById(R.id.btnCopyLink);
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        btnSendEmail=findViewById(R.id.btnSendEmail);
        edtEmailId=findViewById(R.id.edtEmailId);
        openContactButton=findViewById(R.id.openContactButton);

    }
    public void onCopyLinkClick(View view){

        String copyDeepLink = "bb://bugbazaar/dashboard";
        ClipData clipData = ClipData.newPlainText("Copied Text", copyDeepLink);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Link has been copied.", Toast.LENGTH_SHORT).show();
    }

    public void openEmailApp(View view){
        String emailSubject="Invitation to join Bugbazaar!!";
        String emailContent="Hey there, I'm using BugBazaar for all of my bug needs. \n " +
                "\nCheck out our new application and you will never have to go back to any other shopping app. " +
                "\n\nBugBazaar!! for all your vulnerabilty needs!!.";
        String emailAddress= edtEmailId.getText().toString();
        Log.d("emailId",emailAddress);
        //Creating Intent
        Intent iEMail = new Intent(Intent.ACTION_SEND);
        iEMail.setType("message/rfc822");
        iEMail.putExtra(Intent.EXTRA_EMAIL,new String[]{emailAddress});
        iEMail.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        iEMail.putExtra(Intent.EXTRA_TEXT,emailContent);

        startActivity(Intent.createChooser(iEMail, "Email via: "));
        edtEmailId.setText("");
    }
    public void openSelectContacts(View view){

        Intent intent=new Intent(this, SelectContacts.class);
        startActivity(intent);
    }


    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

}