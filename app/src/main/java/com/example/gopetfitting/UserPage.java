package com.example.gopetfitting;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static com.example.gopetfitting.User.REQUEST_IMAGE_CAPTURE;

public class UserPage extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Spinner spinner;
    private List<String> photoChoice;
    private ArrayAdapter<String> arrAdapter;
    private String pictureFilePath = null;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String photoFrom = "change photo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        this.imageView = (ImageView) this.findViewById(R.id.userImage);
        this.spinner = (Spinner) findViewById(R.id.changePhoto);
        this.photoChoice = new ArrayList<String>() {{
            add("change photo");
            add("take photo");
            add("choose from gallery");
        }};
        arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, photoChoice);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        //spinner.setOnItemClickListener();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] photoChoices = getResources().getStringArray(R.array.photoArr);
                Toast.makeText(UserPage.this, "You click:" + photoChoices[pos], Toast.LENGTH_SHORT).show();
                if ("from album".equals(photoChoices[pos])) {
                    System.out.println("from album");
                    return;
                }
                if ("take photo".equals(photoChoices[pos])) {
                    System.out.println("choose take photo");
                    photoFrom = "take photo";
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        //Logger.info("permission not allowed");
                        System.out.println("permission not allowed");
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        System.out.println("permission allowed");
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                } else if ("choose from gallery".equals(photoChoices[pos])) {
                    System.out.println("choose from gallery");
                    photoFrom = "choose from gallery";
                    openGallery();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
//        spinner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
//                } else {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                }
//            }
//        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("onRequestPermissionsResult");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        System.out.println("dispatchTakePictureIntent");
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            File imageFile = null;
            try {
                imageFile = getImageFile();
            } catch (IOException ex) {
                Toast.makeText(this,
                        "Photo can't be created, please try again",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (imageFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.gopetfitting.camera.fileprovider",
//                        imageFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult");
        super.onActivityResult(requestCode, resultCode, data); // add super call????
        if ("take photo".equals(photoFrom)) {
            // save to local storage
            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String pictureFile = "userimage" + timeStamp;

            // save picttures to local device
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = null;
            try {
                image = File.createTempFile(pictureFile, ".jpg", storageDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pictureFilePath = image.getAbsolutePath();
            System.out.println("pictureFilePath " + pictureFilePath);
            if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
                File imgFile = new File(pictureFilePath);
                System.out.println("imgFile is: " + imgFile.toString());
//            if(imgFile.exists())            {
//                imageView.setImageURI(Uri.fromFile(imgFile));
//            }
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
            }
        } else if ("choose from gallery".equals(photoFrom)) {
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
            }
        }
    }

    public File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String pictureFile = "userimage" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(pictureFile, ".jpg", storageDir);
        pictureFilePath = image.getAbsolutePath();
        System.out.println("pictureFilePath " + pictureFilePath);
        return image;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery, PICK_IMAGE);
    }
}

