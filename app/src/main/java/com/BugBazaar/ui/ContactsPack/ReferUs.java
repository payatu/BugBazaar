package com.BugBazaar.ui.ContactsPack;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.BugBazaar.ui.BaseActivity;
import com.BugBazaar.ui.Fragments.QRCodeFragment;

public class ReferUs extends BaseActivity {

    Button btnSendEmail;
    Button openContactButton;
    EditText edtEmailId;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_us);
        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Refer-Us");

        btnSendEmail=findViewById(R.id.btnSendEmail);
        edtEmailId=findViewById(R.id.edtEmailId);
        openContactButton=findViewById(R.id.openContactButton);
       fragmentManager = getSupportFragmentManager(); // Initialize fragmentManager

        // Load ReferUSFragment inside qrfragment layout
        String fragName = getIntent().getStringExtra("fragName");
        Log.d("fragName", "Received fragName: " + fragName);


        if (fragName != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.qrfragment_placeholder, Fragment.instantiate(this, fragName, null))
                    .commit();
        } else {
            // Load default fragment if fragName is null
            loadFragment(new QRCodeFragment());
        }
    }

    public void openEmailApp(View view){
        String emailSubject="Invitation to join Bugbazaar!!";
        String emailContent="Hey there, I'm using BugBazaar for all of my bug needs. \n " +
                "\nCheck out our new application and you will never have to go back to any other shopping app. " +
                "\n\nBugBazaar!! for all your vulnerabilty needs!!.";
        String emailAddress= edtEmailId.getText().toString();
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

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.qrfragment_placeholder, fragment)
                .commit();
    }

    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }
}
