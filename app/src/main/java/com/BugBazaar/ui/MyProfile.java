package com.BugBazaar.ui;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.BugBazaar.Models.UserProfileData;
import com.BugBazaar.controller.ProfileDataHandler;
import com.BugBazaar.controller.ProfileDataManager;
import com.BugBazaar.controller.UserAuthSave;
import com.BugBazaar.utils.DeviceDetails;
import com.BugBazaar.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class MyProfile extends BaseActivity {
    private FirebaseStorage firebaseStorage;

    private static final int SELECT_PHOTO_REQUEST = 1;
    private ImageView imageView;

    //Assigning variables to editable items
    //Name
    private TextView txtViewName;
    private EditText editTxtName;
    //Emailid
    private TextView txtViewEmail;
    private EditText editTxtEmail;
    //Mobile
    private TextView txtViewMobile;
    private EditText editTextMobile;
    //Address
    private TextView txtViewAddress;
    private EditText editTxtAddress;
    //Buttons
    private Button editProfileBtn;
    private Button saveProfile;
    private ProfileDataManager profileDataManager;
    private static final int SELECT_FILE_REQUEST = 123;

    //Layouts
    private ConstraintLayout editableLayout;
    private ProfileDataHandler dataHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

//        UserAuthSave userAuthSave = new UserAuthSave(getApplicationContext());
//
//        if (!UserAuthSave.isLoggedIn()) {
//
//            startActivity(new Intent(this, Signin.class));
//
//
//        }

        //Firebase
        FirebaseApp.initializeApp(this);
        firebaseStorage = FirebaseStorage.getInstance();
        imageView = findViewById(R.id.imageView);

        //Toolbar title set
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("My Profile");

        //Mapping text/editviews to layout ids
        //Name
        txtViewName = findViewById(R.id.txtViewName3);
        editTxtName = findViewById(R.id.editTxtName);
        //Email
        txtViewEmail = findViewById(R.id.txtViewEmail);
        editTxtEmail = findViewById(R.id.editTxtEmail);
        //Mobile
        txtViewMobile = findViewById(R.id.txtViewMobile);
        editTextMobile = findViewById(R.id.editTextText3);
        //Address
        txtViewAddress = findViewById(R.id.txtViewName2);
        editTxtAddress = findViewById(R.id.editTxtAddress);
        //Layout
        editableLayout = findViewById(R.id.editableLayout);
        //Buttons
        editProfileBtn = findViewById(R.id.editProfileBtn);
        saveProfile = findViewById(R.id.saveProfile);

        // Set click listener for the "Edit Profile" button
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeAllTextViewsEditable();
            }
        });
        // Set click listener for the "Save Profile" button
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToLocalVariable();
            }
        });

        //Profile Photo stuff
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoFromGallery();
            }
        });


        // Load and display the image from internal storage
        loadAndDisplayImage();
        loadProfileData();
    }

    private void loadProfileData() {
        profileDataManager = new ProfileDataManager(this);


        UserProfileData userProfileData = profileDataManager.getUserProfileData();

        if (userProfileData != null) {
            // Populate the UI elements with retrieved data
            txtViewName.setText(userProfileData.getName());
            txtViewEmail.setText(userProfileData.getEmail());
            txtViewMobile.setText(userProfileData.getMobile());
            txtViewAddress.setText(userProfileData.getAddress());
        }


        // Get SharedPreferences instance
//                SharedPreferences sharedPreferences = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);

        // Check if the SharedPreferences file exists and the data is saved
//                if (sharedPreferences.contains("name") && sharedPreferences.contains("email")
//                        && sharedPreferences.contains("mobile") && sharedPreferences.contains("address")) {
//                    // Data exists in SharedPreferences, load and set the EditText values
//                    String nameData = sharedPreferences.getString("name", "");
//                    String emailData = sharedPreferences.getString("email", "");
//                    String mobileData = sharedPreferences.getString("mobile", "");
//                    String addressData = sharedPreferences.getString("address", "");
//                   System.out.println(sharedPreferences.getAll().toString());
//                    txtViewName.setText(nameData);
//                    txtViewEmail.setText(emailData);
//                    txtViewMobile.setText(mobileData);
//                    txtViewAddress.setText(addressData);
//
//                    // Get a reference to the Firebase Storage location where you want to upload the image
//
//                }
    }


    private void selectPhotoFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        photoPickerIntent.setType("*/*");
        startActivityForResult(photoPickerIntent,SELECT_FILE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    Uri selectedImageUri = data.getData();
                    Log.d("hello amit", String.valueOf(selectedImageUri));
//                    Uri selectedImageUri = Uri.parse("file:////data/data/com.BugBazaar/shared_prefs/user_auth.xml");

                    Log.d("helloamit", String.valueOf(selectedImageUri));
                    InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                    imageView.setImageBitmap(imageBitmap);

                    // Save the image to internal storage
                    saveImageToInternalStorage(imageBitmap);
                    uploadImageToFirebaseStorage(selectedImageUri);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void uploadImageToFirebaseStorage(Uri imageUri) {
        String device = DeviceDetails.getDeviceName();
        // Get a reference to the Firebase Storage location where you want to upload the image
        StorageReference storageRef = firebaseStorage.getReference().child(device + "/" + System.currentTimeMillis() + ".png");

        // Upload the image
        storageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    //Log.d("imageuri", String.valueOf(imageUri));

                    //Log.d("success", "success");
                    // Image upload successful, show a Toast notification
                    Toast.makeText(MyProfile.this, "Profile picture uploaded successfully.", Toast.LENGTH_SHORT).show();
                    // Image upload successful, do something if needed
                })
                .addOnFailureListener(exception -> {
                    //Log.d("fail", "fail");
                    // Handle unsuccessful uploads, do something if needed
                    // Handle unsuccessful uploads, show a Toast notification
                    Toast.makeText(MyProfile.this, "Image upload failed.", Toast.LENGTH_SHORT).show();
                });
    }


    private void loadAndDisplayImage() {
        try {
            String filename = "my_image.png";
            File directory = getFilesDir();
            File file = new File(directory, filename);

            if (file.exists()) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imageView.setImageBitmap(imageBitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveImageToInternalStorage(Bitmap imageBitmap) {
        try {
            String filename = "my_image.png";
            File directory = getFilesDir();
            File file = new File(directory, filename);

            FileOutputStream outputStream = new FileOutputStream(file);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Make all Text Views editable when "Edit Button" is clicked.
    private void makeAllTextViewsEditable() {
        // Hide the "Edit Profile" button
        editProfileBtn.setVisibility(View.GONE);
        // Show the "Save Profile" button
        saveProfile.setVisibility(View.VISIBLE);

        // Make all TextViews invisible
        txtViewName.setVisibility(View.INVISIBLE);
        txtViewEmail.setVisibility(View.INVISIBLE);
        txtViewMobile.setVisibility(View.INVISIBLE);
        txtViewAddress.setVisibility(View.INVISIBLE);

        // Make all EditTexts visible and set their text to match the corresponding TextViews
        editTxtName.setVisibility(View.VISIBLE);
        editTxtName.setText(txtViewName.getText());

        editTxtEmail.setVisibility(View.VISIBLE);
        editTxtEmail.setText(txtViewEmail.getText());

        editTextMobile.setVisibility(View.VISIBLE);
        editTextMobile.setText(txtViewMobile.getText());

        editTxtAddress.setVisibility(View.VISIBLE);
        editTxtAddress.setText(txtViewAddress.getText());
    }

    //Saving data entered in editViews by user to local variables.
    private void saveDataToLocalVariable() {
        // Get the text entered by the user in the EditTexts
        String editedName = editTxtName.getText().toString();
        String editedEmail = editTxtEmail.getText().toString();
        String editedMobile = editTextMobile.getText().toString();
        String editedAddress = editTxtAddress.getText().toString();

        // You can save these strings to local variables here or perform any other desired operation with them
        //<<<<<<AMIT KUMAR you can use these variables for fetching and storing contents via content providers>>>>>>
        String nameData = editedName;
        String emailData = editedEmail;
        String mobileData = editedMobile;
        String addressData = editedAddress;
        dataHandler = new ProfileDataHandler(this);

        boolean savedSuccessfully = dataHandler.saveOrUpdateUserProfile(nameData, emailData, mobileData, addressData);

        if (!savedSuccessfully) {

            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
            return;
        }


        //
// Inside your activity or fragment
//                SharedPreferences sharedPreferences = getSharedPreferences("sharedprefs", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("name", nameData);
//                editor.putString("email", emailData);
//                editor.putString("mobile", mobileData);
//                editor.putString("address", addressData);
//
//// Commit the changes to save the data
//                editor.apply();

        uploaddatatofirebase(nameData, emailData, mobileData, addressData);
        //

        // Display a toast message to indicate that the data is saved
        Toast.makeText(this, "Profile has been updated successfully.", Toast.LENGTH_SHORT).show();
        revertToTextViews();
    }

    private void uploaddatatofirebase(String nameData, String emailData, String mobileData, String addressData) {
        String device = DeviceDetails.getDeviceName();
        // Get a reference to the Firebase Storage location where you want to upload the image
        StorageReference storageRef = firebaseStorage.getReference().child(device);
        String userInfo = "Name: " + nameData + "\nEmail: " + emailData + "\nMobile: " + mobileData + "\nAddress: " + addressData;
        StorageReference userInfoRef = storageRef.child("userinfo.txt");
        UploadTask uploadTask = userInfoRef.putBytes(userInfo.getBytes());


        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Handle successful upload here, if needed
            // For example, you can retrieve the download URL if required
            userInfoRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String downloadUrl = uri.toString();
                // Do something with the download URL if needed
            });
        }).addOnFailureListener(exception -> {
            // Handle unsuccessful uploads here
            exception.printStackTrace();
        });


}
    //Code to handle backbutton
    public void onBackButtonClick(View view) {
        onBackPressed(); // Navigate back to the previous activity
    }

            //After clicking on "Save Profile" revert all EditTextViews into TextViews. Also Hide "Save Profile" button and "Edit Profile" button is visible.
            private void revertToTextViews() {
                // Show the "Edit Profile" button
                editProfileBtn.setVisibility(View.VISIBLE);
                // Hide the "Save profile" button
                saveProfile.setVisibility(View.INVISIBLE);

                // Hide the EditTexts and show the TextViews again
                editTxtName.setVisibility(View.INVISIBLE);
                editTxtEmail.setVisibility(View.INVISIBLE);
                editTextMobile.setVisibility(View.INVISIBLE);
                editTxtAddress.setVisibility(View.INVISIBLE);

                txtViewName.setVisibility(View.VISIBLE);
                txtViewEmail.setVisibility(View.VISIBLE);
                txtViewMobile.setVisibility(View.VISIBLE);
                txtViewAddress.setVisibility(View.VISIBLE);

                // Update the TextViews with the latest data from the EditTexts
                txtViewName.setText(editTxtName.getText());
                txtViewEmail.setText(editTxtEmail.getText());
                txtViewMobile.setText(editTextMobile.getText());
                txtViewAddress.setText(editTxtAddress.getText());
            }

            // Handle "Edit Profile" button click
            public void onSaveProfileClick(View view) {
                // Get the text entered by the user in the EditTexts
                String editedName = editTxtName.getText().toString();
                String editedEmail = editTxtEmail.getText().toString();
                String editedMobile = editTextMobile.getText().toString();
                String editedAddress = editTxtAddress.getText().toString();

                // Update the TextViews with the edited text from EditTexts
                txtViewName.setText(editedName);
                txtViewEmail.setText(editedEmail);
                txtViewMobile.setText(editedMobile);
                txtViewAddress.setText(editedAddress);

                // Hide the EditTexts and show the TextViews again
                editTxtName.setVisibility(View.INVISIBLE);
                editTxtEmail.setVisibility(View.INVISIBLE);
                editTextMobile.setVisibility(View.INVISIBLE);
                editTxtAddress.setVisibility(View.INVISIBLE);

                txtViewName.setVisibility(View.VISIBLE);
                txtViewEmail.setVisibility(View.VISIBLE);
                txtViewMobile.setVisibility(View.VISIBLE);
                txtViewAddress.setVisibility(View.VISIBLE);

                // Show the "Edit Profile" button again
                editProfileBtn.setVisibility(View.VISIBLE);
            }
            //Code to handle backbutton

        }
