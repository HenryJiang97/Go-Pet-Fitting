package com.example.gopetfitting;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class User {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    // Basic login info
    private Timestamp createTime;
    private UUID userId;
    private UUID imageId;
    private String name;
    private String email;

    // Personal info
    private int age;
    private Sex sex;
    private double height;
    private double weight;
    private double targetWeight;
    private int completeWeeks;
    private int targetCaloriesLoss;

    // Intake
    private int lostCalories;
    private int caloriesIntake;

    // Activity
    private List<UUID> activityIds;
    private long totalExerciseTime;
    private int completedWeeks;

    // Pet
    private UUID petId;
    private int coins;
    private int checkInDays;
    // 0 < beatRank < 1
    private double beatRank;
    // for pets
    private int haveFood;
    private int haveWater;
    private int haveVaccination;
    private int haveToys;

    public User(String name, String email, int age, Sex sex, double height, double weight, double targetWeight, boolean create) {
        if(create) this.createTime =new Timestamp(System.currentTimeMillis());
        System.out.printf("\ncreateTime is: %s\n", this.createTime.toString());
        this.userId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.lostCalories = 0;
        this.caloriesIntake = 0;
        this.targetCaloriesLoss = calculateTargetCaloriesLoss(weight, targetWeight);
        this.activityIds = new ArrayList<>();
        this.totalExerciseTime = 0;
        this.completedWeeks = 0;
        this.petId = UUID.randomUUID();
        this.coins = 0;
        this.checkInDays = 0;
    }

    // Getters
    public Timestamp getCreateTime() {
        return createTime;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Sex getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public List<UUID> getActivityIds() {
        return activityIds;
    }

    public UUID getPetId() {
        return petId;
    }

    public int getLostCalories() {
        return lostCalories;
    }

    public int getCoins() {
        return coins;
    }

    public long getTotalExerciseTime() {
        return totalExerciseTime;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public int getCaloriesIntake() {
        return caloriesIntake;
    }

    public int getCheckInDays() {
        return checkInDays;
    }

    public int getCompletedWeeks() {
        return completedWeeks;
    }

    public int getTargetCaloriesLoss() {
        return targetCaloriesLoss;
    }


    // Methods

    // TODO: Implement the method to calculate target calories loss for the user
    private int calculateTargetCaloriesLoss(double _weight, double _targetWeight) {
        return 0;
    }

//    private void dispatchTakePictureIntent() {
//        PackageManager packageManager = getPackageManager();
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }
}
