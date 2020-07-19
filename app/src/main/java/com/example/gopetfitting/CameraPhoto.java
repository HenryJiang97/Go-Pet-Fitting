//package com.example.gopetfitting;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import static com.example.gopetfitting.User.REQUEST_IMAGE_CAPTURE;
//
//public class CameraPhoto extends AppCompatActivity {
//    private static final int CAMERA_REQUEST = 1888;
//    private ImageView imageView;
//    private static final int MY_CAMERA_PERMISSION_CODE = 100;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.user);
//        this.imageView = (ImageView) this.findViewById(R.id.userImage);
//        TextView changePhoto = (TextView) this.findViewById(R.id.changePhoto);
//        changePhoto.setOnClickListener(new View.OnClickListener() {
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
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data); // add super call????
//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//        }
//    }
//}
