package com.example.gopetfitting;

import java.sql.Timestamp;
import java.util.UUID;

public class Activity {

    private UUID activityId;
    private Timestamp startTime;
    private boolean ifCompleted;
    private int steps;
    private double distance;
    private int burnedCalories;
    private double speed;

    public Activity() {
        this.activityId = UUID.randomUUID();
        this.ifCompleted = false;
        this.steps = 0;
        this.distance = 0.0;
        this.burnedCalories = 0;
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.speed = 0.0;
    }

    // Getters
    public UUID getActivityId() {
        return activityId;
    }

    public boolean getIfCompleted() {
        return ifCompleted;
    }

    public int getSteps() {
        return steps;
    }

    public double getDistance() {
        return distance;
    }

    public int getBurnedCalories() {
        return burnedCalories;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public double getSpeed() {
        return speed;
    }
}
