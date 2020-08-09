package com.example.gopetfitting.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.sql.Timestamp;
import java.util.UUID;

@IgnoreExtraProperties
public class Pet {

    //    original
    private UUID petId;
    public String username;
    public int growthScore;

    //    updated
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

    //    added
    private int have_food;
    private int have_water;
    private int have_vaccination;
    private int have_toys;
    private int coins;

    public Pet(){
        // Default constructor required for calls to DataSnapshot.getValue(Pet.class)
        this.have_food = 13;
        this.have_water = 40;
        this.have_toys = 8;
        this.have_vaccination = 3;
        this.coins = 80;
        this.growthScore = 392;
    }

    public Pet(String username, int growthScore){
        this.petId = UUID.randomUUID();
        this.username = username;
        this.growthScore = growthScore;
    }

    //    original
    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID userId) {
        this.petId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGrowthScore() {
        return growthScore;
    }

    public void setGrowthScore(int growthScore) {
        this.growthScore = growthScore;
    }

    //    updated
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

    //    added
    public int getHave_food() {
        return have_food;
    }

    public void setHave_food(int have_food) {
        this.have_food = have_food;
    }

    public int getHave_water() {
        return have_water;
    }

    public void setHave_water(int have_water) {
        this.have_water = have_water;
    }

    public int getHave_vaccination() {
        return have_vaccination;
    }

    public void setHave_vaccination(int have_vaccination) {
        this.have_vaccination = have_vaccination;
    }

    public int getHave_toys() {
        return have_toys;
    }

    public void setHave_toys(int have_toys) {
        this.have_toys = have_toys;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}

