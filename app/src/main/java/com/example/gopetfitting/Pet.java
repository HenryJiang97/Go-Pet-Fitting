package com.example.gopetfitting;

import java.sql.Time;
import 	java.sql.Timestamp;
import java.util.UUID;

public class Pet {
    private UUID petId;
    private String petName;
    // private Timestamp createTime;
    // pet's growth values
    private int hunger; // 0-110
    private int health; // -10~100
    private int cleanness; // -10~100
    private int happiness; // 0-100
    private Timestamp lastFoodTime;
    private Timestamp lastDrinkTime;
    private Timestamp lastVaccinationTime;
    private Timestamp lastCleanTime;
    private int growthScore;

    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCleanness() {
        return cleanness;
    }

    public void setCleanness(int cleanness) {
        this.cleanness = cleanness;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public Timestamp getLastFoodTime() {
        return lastFoodTime;
    }

    public void setLastFoodTime(Timestamp lastFoodTime) {
        this.lastFoodTime = lastFoodTime;
    }

    public Timestamp getLastDrinkTime() {
        return lastDrinkTime;
    }

    public void setLastDrinkTime(Timestamp lastDrinkTime) {
        this.lastDrinkTime = lastDrinkTime;
    }

    public Timestamp getLastVaccinationTime() {
        return lastVaccinationTime;
    }

    public void setLastVaccinationTime(Timestamp lastVaccinationTime) {
        this.lastVaccinationTime = lastVaccinationTime;
    }

    public Timestamp getLastCleanTime() {
        return lastCleanTime;
    }

    public void setLastCleanTime(Timestamp lastCleanTime) {
        this.lastCleanTime = lastCleanTime;
    }

    public int getGrowthScore() {
        return growthScore;
    }

    public void setGrowthScore(int growthScore) {
        this.growthScore = growthScore;
    }
}