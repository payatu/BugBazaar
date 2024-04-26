package com.BugBazaar.ui.ContactsPack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.BugBazaar.R;
import com.BugBazaar.ui.BaseActivity;
import com.BugBazaar.ui.Fragments.QRCodeFragment;

/* loaded from: classes.dex */
public class ReferUs extends BaseActivity {
    Button btnSendEmail;
    EditText edtEmailId;
    FragmentManager fragmentManager;
    Button openContactButton;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.BugBazaar.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_refer_us);
        ((TextView) findViewById(R.id.toolbarTitle)).setText("Refer-Us");
        this.btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
        this.edtEmailId = (EditText) findViewById(R.id.edtEmailId);
        this.openContactButton = (Button) findViewById(R.id.openContactButton);
        this.fragmentManager = getSupportFragmentManager();
        String stringExtra = getIntent().getStringExtra("fragName");
        Log.d("fragName", "Received fragName: " + stringExtra);
        if (stringExtra != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.qrfragment_placeholder, Fragment.instantiate(this, stringExtra, null)).commit();
        } else {
            loadFragment(new QRCodeFragment());
        }
    }

    public void openEmailApp(View view) {
        String obj = this.edtEmailId.getText().toString();
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("message/rfc822");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{obj});
        intent.putExtra("android.intent.extra.SUBJECT", "Invitation to join Bugbazaar!!");
        intent.putExtra("android.intent.extra.TEXT", "Hey there, I'm using BugBazaar for all of my bug needs. \n \nCheck out our new application and you will never have to go back to any other shopping app. \n\nBugBazaar!! for all your vulnerabilty needs!!.");
        startActivity(Intent.createChooser(intent, "Email via: "));
        this.edtEmailId.setText("");
    }

    public void openSelectContacts(View view) {
        startActivity(new Intent(this, SelectContacts.class));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.qrfragment_placeholder, fragment).commit();
    }

    public void onBackButtonClick(View view) {
        onBackPressed();
    }
}