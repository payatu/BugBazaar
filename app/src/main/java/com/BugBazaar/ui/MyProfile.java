package com.BugBazaar.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.BugBazaar.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

        public class MyProfile extends AppCompatActivity {

            private static final int SELECT_PHOTO_REQUEST = 1;
            private ImageView imageView;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_myprofile);

                imageView = findViewById(R.id.imageView);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectPhotoFromGallery();
                    }
                });



                // Load and display the image from internal storage
                loadAndDisplayImage();
            }

            private void selectPhotoFromGallery() {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO_REQUEST);
            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == SELECT_PHOTO_REQUEST && resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        try {
                            Uri selectedImageUri = data.getData();
                            InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                            Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                            imageView.setImageBitmap(imageBitmap);

                            // Save the image to internal storage
                            saveImageToInternalStorage(imageBitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
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
        }



