package com.example.gopetfitting;

import java.util.ArrayList;
import java.util.List;
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
    private double height;
    private double weight;
    private double targetWeight;
//    private int targetCaloriesLoss;

    // Intake
    private int lostCalories;
    private int caloriesIntake;

    // Activity
    private List<UUID> activityIds;
    private Time exerciseTime;

    // Pet
    private UUID petId;
    private int coins;
    private int checkInDays;


    public User(String name, String email, int age, Sex sex, double height, double weight, double targetWeight) {
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
        this.activityIds = new ArrayList<>();
        this.exerciseTime = new Time(0, 0, 0);
        this.petId = UUID.randomUUID();
        this.coins = 0;
        this.checkInDays = 0;
    }


}
