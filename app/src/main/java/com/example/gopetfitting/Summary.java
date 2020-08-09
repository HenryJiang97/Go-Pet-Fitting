package com.example.gopetfitting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Summary extends AppCompatActivity {

    private TextView exerciseTimeTV;
    private TextView stepsTV;
    private TextView distanceTV;
    private TextView caloriesTV;
    private TextView speedTV;
    private Button returnBT;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_summary);

        exerciseTimeTV = findViewById(R.id.exercise_time_summary);
        stepsTV = findViewById(R.id.steps_summary);
        distanceTV = findViewById(R.id.distance_summary);
        caloriesTV = findViewById(R.id.burned_calories_summary);
        speedTV = findViewById(R.id.speed_summary);
        returnBT = findViewById(R.id.return_button_summary);
    }

    @Override
    protected void onStart() {
        super.onStart();

        activity = (Activity)getIntent().getSerializableExtra("activity");
        exerciseTimeTV.setText(String.valueOf(activity.getExerciseTime()) + " s");
        stepsTV.setText(String.valueOf(activity.getSteps()) + " steps");
        distanceTV.setText(String.valueOf(activity.getDistance()) + " m");
        caloriesTV.setText(String.valueOf(activity.getBurnedCalories()) + " kcal");
        speedTV.setText(String.format("%1$,.2f", activity.getSpeed()) + " m/s");

        returnBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserPageActivity.class);
                intent.putExtra("activity", activity);
                startActivity(intent);
            }
        });
    }

    public void toProfile(View view) {
//        Intent intent = new Intent(getBaseContext(), UserPageActivity.class);
//        intent.putExtra("", calories);
//        startActivity(intent);
    }
}
