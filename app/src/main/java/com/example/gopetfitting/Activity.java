package com.example.gopetfitting;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Activity implements Serializable {
    private static String TAG = "ADD TO DATABASE";

    private String activityId;
    private long startTime;
    private long exerciseTime;
    private int steps;
    private double distance;
    private int burnedCalories;
    private double speed;

    public Activity(String id, long startTime, long exerciseTime, int steps, double distance, int burnedCalories) {
        this.activityId = id;
        this.steps = steps;
        this.distance = distance;
        this.burnedCalories = burnedCalories;
        this.exerciseTime = exerciseTime;
        this.startTime = startTime;
        this.speed = getSpeed();
    }

    // Getters


    public String getActivityId() {
        return activityId;
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

    public long getStartTime() {
        return startTime;
    }

    public long getExerciseTime() {
        return exerciseTime;
    }


    // Get average speed of the exercise (m / s)
    public double getSpeed() {
        return (double)distance / exerciseTime;
    }

    // Method to add this activity to database
    public void addToDB() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("activities")
                .document(activityId)
                .set(this)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding activity", e);
                    }
                });
    }
}
