package com.BugBazaar.ui.ContactsPack;
import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.BugBazaar.R;
import com.BugBazaar.provider.MyContactsProvider;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class SelectContacts extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ContactAdapter adapter;
    private List<Contacts> contactsList;

    // Request code for the contact permission
    private static final int CONTACTS_PERMISSION_REQUEST = 1;
    private TextView noContactsMessage;
    private TextView selectContactmsg;
    private ImageView imgNoContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);

        // Initialize the contactsList here
        contactsList = new ArrayList<>();

        selectContactmsg = findViewById(R.id.selectContactmsg);
        noContactsMessage=findViewById(R.id.noContactsMessage);
        imgNoContact=findViewById(R.id.imgNoContact);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ContactAdapter(contactsList);
        recyclerView.setAdapter(adapter);

        // Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("Select Contacts");

        // Check if permission to access contacts is granted or not.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it.
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    CONTACTS_PERMISSION_REQUEST
            );
        } else {
            // Permission granted, proceed to load data.
            loadData();
        }
    }

    // Code to handle back button
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CONTACTS_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to load data.
                loadData();
            } else {
                // Permission denied, show a message or take appropriate action.
                Log.d("Contacts", "Permission denied");
            }
        }
    }

    // Load data when permission is granted
    private void loadData() {
        // Requesting contact data using your custom content provider
        ContentResolver contentResolver = getContentResolver();
        Uri customUri = MyContactsProvider.CONTENT_URI; // Use your custom content provider's URI

        Cursor cursor = contentResolver.query(customUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // Data available, proceed to load it.
            noContactsMessage.setVisibility(View.GONE);
            imgNoContact.setVisibility(View.GONE);
            do {
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // Create a Contact object and add it to the list
                Contacts contact = new Contacts(contactName, contactNumber);
                contactsList.add(contact);
            } while (cursor.moveToNext());

            cursor.close();

            // Notify the adapter that the dataset has changed
            adapter.notifyDataSetChanged();
        } else {
            // No contacts found.
            selectContactmsg.setVisibility(View.GONE);
            noContactsMessage.setVisibility(View.VISIBLE);
            imgNoContact.setVisibility(View.VISIBLE);
        }
    }
}
