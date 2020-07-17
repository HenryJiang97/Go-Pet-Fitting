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
        this.targetCaloriesLoss = calculateTargetCaloriesLoss(weight, targetWeight);
        this.activityIds = new ArrayList<>();
        this.totalExerciseTime = 0;
        this.completedWeeks = 0;
        this.petId = UUID.randomUUID();
        this.coins = 0;
        this.checkInDays = 0;
    }

    // Getters
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
}