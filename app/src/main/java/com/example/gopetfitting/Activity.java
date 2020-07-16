package com.example.gopetfitting;

import java.util.UUID;

public class Activity {

    private UUID activityId;
    private boolean ifCompleted;
    private int steps;
    private double distance;
    private int burnedCalories;
    private Time exerciseTime;
    private double speed;

    public Activity() {
        this.activityId = UUID.randomUUID();
        this.ifCompleted = false;
        this.steps = 0;
        this.distance = 0.0;
        this.burnedCalories = 0;
        this.exerciseTime = new Time(0, 0, 0);
        this.speed = 0.0;
    }

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

    public Time getExerciseTime() {
        return exerciseTime;
    }

    public double getSpeed() {
        return speed;
    }
}
