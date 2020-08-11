package com.example.gopetfitting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalInfo extends AppCompatActivity {
    private static final String TAG = "PersonalInfo activity";

    // Firebase realtime database
    private FirebaseFirestore database;

    private EditText ageET;
    private Spinner sexSpinner;
    private EditText heightET;
    private EditText weightET;
    private EditText targetWeightET;
    private EditText petNameET;
    private Button submitButton;
    private Spinner petTypeSpinner;

    private String uid;
    private String name;
    private String email;
    private int age;
    private Sex sex;
    private double height;
    private double weight;
    private double targetWeight;
    private String petName;
    private PetType petType;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        // Configure firebase
        database = FirebaseFirestore.getInstance();

        // Configure spinner
        sexSpinner = findViewById(R.id.sex_personal_info);
        List<String> sexes = new ArrayList<>(Arrays.asList("MALE", "FEMALE", "OTHER"));
        ArrayAdapter adapterSex = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                sexes);
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapterSex);

        petTypeSpinner = findViewById(R.id.pet_type_personal_info);
        List<String> petTypes = new ArrayList<>(Arrays.asList("CAT", "DOG"));
        ArrayAdapter adapterPetType = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                petTypes);
        adapterPetType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petTypeSpinner.setAdapter(adapterPetType);


        // Initialize variables
        ageET = findViewById(R.id.age_personal_info);
        heightET = findViewById(R.id.height_personal_info);
        weightET = findViewById(R.id.weight_personal_info);
        targetWeightET = findViewById(R.id.target_weight_personal_info);
        petNameET = findViewById(R.id.petname_personal_info);
        submitButton = findViewById(R.id.submit_personal_info);

        uid = getIntent().getStringExtra("uid");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
    }

    @Override
    protected void onStart() {
        super.onStart();

        user = new User(uid, name, email);

        // Respond to spinner selection
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentSex = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(PersonalInfo.this, "Sex: " + currentSex, Toast.LENGTH_SHORT).show();
                sex = currentSex.equals("MALE") ? Sex.MALE : (currentSex.equals("FEMALE") ? Sex.FEMALE : Sex.OTHER);
                user.setSex(sex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        petTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentPetType = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(PersonalInfo.this, "Pet Type: " + currentPetType, Toast.LENGTH_SHORT).show();
                petType = currentPetType.equals("CAT") ? PetType.CAT : PetType.DOG;
                user.setPetType(petType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Respond to submit button clicked
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get personal info
                try {
                    age = Integer.valueOf(ageET.getText().toString());
                    height = Double.valueOf(heightET.getText().toString());
                    weight = Double.valueOf(weightET.getText().toString());
                    targetWeight = Double.valueOf(targetWeightET.getText().toString());
                    petName = petNameET.getText().toString();


                    // Set new user personal info
                    user.setAge(age);
                    user.setHeight(height);
                    user.setWeight(weight);
                    user.setTargetWeight(targetWeight);
                    user.setPetName(petName);

                    // Add to database
                    database.collection("users")
                            .document(user.getUserId())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");

                                    // TODO: Parse user data to profile
                                    Intent intent = new Intent(getBaseContext(), UserPageActivity.class);
                                    intent.putExtra("user", user);
                                    startActivity(intent);



                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PersonalInfo.this, "Submission failure", Toast.LENGTH_SHORT).show();
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });

                } catch (Exception e) {
                    Log.w(TAG, "createUser:failure", e);
                    Toast.makeText(PersonalInfo.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    // Get Map format user info
    private Map<String, Object> getUserinfo() {
        Map<String, Object> map = new HashMap<>();

        map.put("id", user.getUserId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("age", user.getAge());
        map.put("sex", user.getSex());
        map.put("height", user.getHeight());
        map.put("weight", user.getWeight());
        map.put("target_weight", user.getTargetWeight());
        map.put("target_calories_loss", user.getTargetCaloriesLoss());
        map.put("lost_calories", user.getLostCalories());
        map.put("calories_intake", user.getCaloriesIntake());
        map.put("total_exercise_time", user.getTotalExerciseTime());
        map.put("completed_weeks", user.getCompletedWeeks());
        map.put("pet_id", user.getPetId());
        map.put("coins", user.getCoins());
        map.put("check_in_days", user.getCheckInDays());

        return map;
    }
}
