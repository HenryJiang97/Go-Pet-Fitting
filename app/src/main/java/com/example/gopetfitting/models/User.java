package com.example.gopetfitting.models;

import java.sql.Timestamp;
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    // Basic login info
    private Timestamp createTime;
    private UUID imageId;
    private String localImage; // the location path where user store image

    // Personal info
    private int completeWeeks;

    // 0 < beatRank < 1
    private double beatRank;
    // for pets
    private int haveFood;
    private int haveWater;
    private int haveVaccination;
    private int haveToys;

    public int growthScore;
    public String username;


    public User(String name, String email, int age, Sex sex, double height, double weight, double targetWeight) {
        this.userId = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.targetWeight = targetWeight;
        this.lostCalories = 23;
        this.caloriesIntake = 0;
        this.targetCaloriesLoss = calculateTargetCaloriesLoss(weight, targetWeight);
        this.activityIds = new ArrayList<>();
        this.totalExerciseTime = 0;
        this.completedWeeks = 0;
        this.petId = UUID.randomUUID();
        this.coins = 55;
        this.checkInDays = 0;
    }

    public User() {
        this.coins = 55;
        this.createTime = new Timestamp(System.currentTimeMillis());
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

    public String getLocalImage() {
        return this.localImage;
    }

    // Methods

    // TODO: Implement the method to calculate target calories loss for the user
    private int calculateTargetCaloriesLoss(double _weight, double _targetWeight) {
        return 0;
    }

    public void setImageId(UUID imageId) {
        this.imageId = imageId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public void setCompleteWeeks(int completeWeeks) {
        this.completeWeeks = completeWeeks;
    }

    public void setTargetCaloriesLoss(int targetCaloriesLoss) {
        this.targetCaloriesLoss = targetCaloriesLoss;
    }

    public void setLostCalories(int lostCalories) {
        this.lostCalories = lostCalories;
    }

    public void setCaloriesIntake(int caloriesIntake) {
        this.caloriesIntake = caloriesIntake;
    }

    public void setActivityIds(List<UUID> activityIds) {
        this.activityIds = activityIds;
    }

    public void setTotalExerciseTime(long totalExerciseTime) {
        this.totalExerciseTime = totalExerciseTime;
    }

    public void setCompletedWeeks(int completedWeeks) {
        this.completedWeeks = completedWeeks;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setCheckInDays(int checkInDays) {
        this.checkInDays = checkInDays;
    }

    public void setBeatRank(double beatRank) {
        this.beatRank = beatRank;
    }

    public void setHaveFood(int haveFood) {
        this.haveFood = haveFood;
    }

    public void setHaveWater(int haveWater) {
        this.haveWater = haveWater;
    }

    public void setHaveVaccination(int haveVaccination) {
        this.haveVaccination = haveVaccination;
    }

    public void setHaveToys(int haveToys) {
        this.haveToys = haveToys;
    }

    public void setLocalImage(String loc) {
        this.localImage = loc;
    }

    public int getGrowthScore() {
        return growthScore;
    }

    public void setGrowthScore(int growthScore) {
        this.growthScore = growthScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}