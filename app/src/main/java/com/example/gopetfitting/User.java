package com.example.gopetfitting;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private static String TAG = "ADD TO DATABASE";

    // Basic login info
    private String userId;
//    private UUID imageId;
    private String name;
    private String email;

    // Personal info
    private int age;
    private Sex sex;
    private double height;
    private double weight;
    private double targetWeight;
    private int targetCaloriesLoss;

    // Intake
    private int lostCalories;
    private int caloriesIntake;

    // Activity
    private long totalExerciseTime;
    private int completedWeeks;
    private String activityId;

    // Pet
    private String petId;
    private String petName;
    private PetType petType;
    private int coins;
    private int checkInDays;

    // Score
    private int score;
    // photo
    private String localImage; // the location path where user store image

    public User(String uid, String name, String email) {
        this.userId = uid;
        this.name = name;
        this.email = email;
        this.lostCalories = 0;
        this.caloriesIntake = 0;
        this.targetCaloriesLoss = calculateTargetCaloriesLoss();
        this.totalExerciseTime = 0;
        this.completedWeeks = 0;
        this.petId = String.valueOf(UUID.randomUUID());
        this.coins = 0;
        this.checkInDays = 0;
        this.score = 0;
        this.activityId = "";
    }
    public User() {
    }

    public User(String uid, String name, String email,
                int age, Sex sex, double height, double weight, double targetWeight, int targetCaloriesLoss, String petName, PetType petType) {
        this.userId = uid;
        this.name = name;
        this.email = email;
        this.lostCalories = 0;
        this.caloriesIntake = 0;
        this.targetCaloriesLoss = calculateTargetCaloriesLoss();
        this.totalExerciseTime = 0;
        this.completedWeeks = 0;
        this.petId = String.valueOf(UUID.randomUUID());
        this.coins = 0;
        this.checkInDays = 0;
        this.score = 0;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.targetCaloriesLoss = targetCaloriesLoss;
        this.petName = petName;
        this.petType = petType;
        this.activityId = "";
    }

    // Setters
    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCompletedWeeks(int completedWeeks) {
        this.completedWeeks = completedWeeks;
    }

    public void setCaloriesIntake(int caloriesIntake) {
        this.caloriesIntake = caloriesIntake;
    }

    public void setCheckInDays(int checkInDays) {
        this.checkInDays = checkInDays;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setTotalExerciseTime(long totalExerciseTime) {
        this.totalExerciseTime = totalExerciseTime;
    }

    public void setLostCalories(int lostCalories) {
        this.lostCalories = lostCalories;
    }

    public void addScore(int delta) {
        this.score += delta;
    }


    // Getters
    public String getUserId() {
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


    public String getPetId() {
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

    public String getPetName() {
        return petName;
    }

    public PetType getPetType() {
        return petType;
    }

    public int getScore() {
        return score;
    }


    // Methods

    // TODO: Implement the method to calculate target calories loss for the user
    private int calculateTargetCaloriesLoss() {
        return 0;
    }


    // Add user to database
    public void addToDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(this)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding activity", e);
                    }
                });
    }

    public String getLocalImage() {
        return this.localImage;
    }
    public void setLocalImage(String loc) {
        this.localImage = loc;
    }
}
