package com.example.gopetfitting;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class UserPageActivity extends AppCompatActivity {
    private User user;
    private Pet pet;
    //private ImageView outdoor;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private Button petHome;
    private EditText age;
    private EditText weight;
    private EditText height;
    private EditText target;
    private EditText weeks;
    private TextView loseTotal;
    private TextView finished;
    private TextView todo;
    private com.example.gopetfitting.Activity exercise;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Spinner spinner;
    private List<String> photoChoice;
    private ArrayAdapter arrAdapter;
    private String pictureFilePath = null;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private String photoFrom = "change photo";
    private TextView coins;
    private TextView name;
    private boolean hasCheck;
    int duration = Toast.LENGTH_SHORT;
    CharSequence text;
    Toast toast;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        user =(User)getIntent().getSerializableExtra("user");
        name = (TextView) findViewById(R.id.textView25);
        if(user == null) {
            name.setText("alice");
            age = (EditText)findViewById(R.id.age);
            age.setText("22");
            height = (EditText) findViewById(R.id.height);
            height.setText("170");
            weight = (EditText) findViewById(R.id.weight);
            weight.setText("120");
            target = (EditText) findViewById(R.id.target);
            target.setText("100");
            weeks = (EditText) findViewById(R.id.weeks);
            weeks.setText("8");
            finished = (TextView) findViewById(R.id.textView15);
            finished.setText("146");
        }
        //Log.d("userpass: ", user.getName());
        if(user != null) {
            name.setText(user.getName());
            age = (EditText)findViewById(R.id.age);
            age.setText(String.valueOf(user.getAge()));
            Log.d("username is:", user.getName());
            height = (EditText) findViewById(R.id.height);
            height.setText(String.valueOf(user.getHeight()));
            weight = (EditText) findViewById(R.id.weight);
            weight.setText(String.valueOf(user.getWeight()));
            target = (EditText) findViewById(R.id.target);
            target.setText(String.valueOf(user.getTargetWeight()));
            weeks = (EditText) findViewById(R.id.weeks);
            weeks.setText(String.valueOf(user.getCompletedWeeks()));
            finished = (TextView) findViewById(R.id.textView15);
            finished.setText("146");
        }
        exercise = (com.example.gopetfitting.Activity)getIntent().getSerializableExtra("activity");
        //Log.d("exercise: ", String.valueOf(exercise.getBurnedCalories()));
        coins = (TextView) findViewById(R.id.textView22);
        if(exercise != null) {

            finished.setText(String.valueOf(Integer.parseInt(finished.getText().toString())+exercise.getBurnedCalories()));
            coins.setText(String.valueOf(Integer.parseInt(coins.getText().toString())+(int)(exercise.getBurnedCalories()/100)));
        }
        loseTotal = (TextView)findViewById(R.id.textView12);
        loseTotal.setText("1453");
        todo = (TextView)findViewById(R.id.textView17);
        todo.setText(String.valueOf(Integer.parseInt(loseTotal.getText().toString())-Integer.parseInt(finished.getText().toString())));
        if(user != null) {
            pet = new Pet(user.getPetName(), user.getPetType());
        }
        //Log.d("petpass: ", pet.getPetName());
        petHome = (Button)findViewById(R.id.button4);
        hasCheck = false;
        //outdoor = (ImageView) findViewById(R.id.imageView3);
        //user = new User();
        this.imageView = (ImageView) this.findViewById(R.id.userImage);
        this.spinner = (Spinner) findViewById(R.id.changePhoto);
        this.photoChoice = new ArrayList<String>() {{
            add("change photo");
            add("take photo");
            add("choose from gallery");
        }};
        arrAdapter = ArrayAdapter.createFromResource(this, R.array.photoArr, R.layout.spinner_style);
        //spinner.setAdapter(arrAdapter);
//        arrAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, photoChoice);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdapter);
        //spinner.setOnItemClickListener();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] photoChoices = getResources().getStringArray(R.array.photoArr);
                Toast.makeText(UserPageActivity.this, "You click:" + photoChoices[pos], Toast.LENGTH_SHORT).show();
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
                user.setLocalImage(pictureFile);
                System.out.println("localimage is: " + user.getLocalImage());
            }
        } else if ("choose from gallery".equals(photoFrom)) {
            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
                imageUri = data.getData();
                imageView.setImageURI(imageUri);
                user.setLocalImage(data.getData().toString());
                System.out.println("gallery localimage is: " + user.getLocalImage());
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

    public void checkin(View view) {
        if(hasCheck) return;
        text = "coin +1";
        Context context = getApplicationContext();
        toast = Toast.makeText(context, text, duration);
        toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
        toast.show();
        coins.setText(String.valueOf(Integer.parseInt(coins.getText().toString())+1));
    }
    public void toPet(View view) {
        Intent intent = new Intent(getBaseContext(), PetHomeActivity.class);
        intent.putExtra("pet", pet);
        startActivity(intent);
//        Intent intent=new Intent();
//        intent.setClass(UserPageActivity.this, PetHomeActivity.class);
//        startActivity(intent);
    }
    public void toOurdoor(View view) {
        Intent intent=new Intent();
        intent.setClass(UserPageActivity.this, Exercise.class);
        startActivity(intent);
    }
    public void toIndoor(View view) {
        Intent intent=new Intent();
        intent.setClass(UserPageActivity.this, IndoorExercise.class);
        startActivity(intent);
    }
}

