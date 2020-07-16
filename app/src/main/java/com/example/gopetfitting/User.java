package com.example.gopetfitting;

import java.util.UUID;

public class User {

    // Basic login info
    private UUID userId;
//    private UUID imageId;
    private String name;
    private String email;

    // Personal info
    private int age;
    private Sex sex;
    private Double height;
    private Double weight;
    private Double targetWeight;
//    private int targetCaloriesLoss;

    // Intake
    private int lostCalories;
    private int caloriesIntake;

    // Activity
    private UUID activityId;
    private Time exerciseTime;

    // Pet
    private UUID petId;
    private int coins;
    private int checkInDays;


    public User(String name, String email, int age, Sex sex, Double height, Double weight, Double targetWeight) {
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
        this.activityId = UUID.randomUUID();
        this.exerciseTime = new Time(0, 0, 0);
        this.petId = UUID.randomUUID();
        this.coins = 0;
        this.checkInDays = 0;
    }
}
