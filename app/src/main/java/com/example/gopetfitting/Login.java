package com.example.gopetfitting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Login extends AppCompatActivity {
    private static final String TAG = "Sign In Activity";

    private User user;

    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    private EditText emailET;
    private EditText passwordET;
    private Button loginBT;
    private TextView signUpTV;

    private DocumentReference df;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Initialize variables
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        emailET = findViewById(R.id.email_login);
        passwordET = findViewById(R.id.password_login);
        loginBT = findViewById(R.id.login_login);
        signUpTV = findViewById(R.id.signup_signin);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // If user has already signed in, proceed to user profile
        if (firebaseUser != null) {
            proceedToUserProfile(firebaseUser);
        }

        // Log in button clicked
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                signInUser(email, password);
                Intent intent = new Intent(getBaseContext(), UserPageActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        // Sign up button clicked
        signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Signup.class);
                startActivity(intent);
            }
        });
    }


    // TODO: Implement this method to user profile
    private void proceedToUserProfile(FirebaseUser user) {

    }

    // Method to authenticate and sign in user
    private void signInUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(Login.this, "Authentication succeeded!!!", Toast.LENGTH_SHORT).show();

                            firebaseUser = auth.getCurrentUser();
                            proceedToUserProfile(firebaseUser);


                            df = db.collection("users")
                                    .document(firebaseUser.getUid());

                            df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                            // Retrieve user object
                                            user = getUserData(document.getData());

//                                            Log.d(TAG, "user id: " + user.getUserId());
//                                            Log.d(TAG, "user name: " + user.getName());
//                                            Log.d(TAG, "user age: " + user.getAge());


                                            // TODO: Parse data to profile activity
                                            Intent intent = new Intent(getBaseContext(), UserPageActivity.class);
                                            intent.putExtra("user", user);
                                            startActivity(intent);


                                        } else {
                                            Log.d(TAG, "No such document");
                                        }
                                    } else {
                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });


                        } else {
                            // Sign in fail
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private User getUserData(Map<String, Object> map) {

        String uid = String.valueOf(map.get("userId"));
        String name = String.valueOf(map.get("name"));
        String email = String.valueOf(map.get("email"));
        int age = Integer.parseInt(String.valueOf(map.get("age")));
        Sex sex = String.valueOf(map.get("sex")).equals("MALE") ? Sex.MALE : (String.valueOf(map.get("sex")).equals("FEMALE") ? Sex.FEMALE : Sex.OTHER);
        double height = Double.parseDouble(String.valueOf(map.get("height")));
        double weight = Double.parseDouble(String.valueOf(map.get("weight")));
        double targetWeight = Double.parseDouble(String.valueOf(map.get("targetWeight")));
        int targetCaloriesLoss = Integer.parseInt(String.valueOf(map.get("targetCaloriesLoss")));
        int lostCalories = Integer.parseInt(String.valueOf(map.get("lostCalories")));
        int caloriesIntake = Integer.parseInt(String.valueOf(map.get("caloriesIntake")));
        long totalExerciseTime = Long.parseLong(String.valueOf(map.get("totalExerciseTime")));
        int completedWeeks = Integer.parseInt(String.valueOf(map.get("completedWeeks")));
        String activityId = String.valueOf(map.get("activityId"));

        String petId = String.valueOf(map.get("petId"));
        String petName = String.valueOf(map.get("petName"));
        PetType petType = String.valueOf(map.get("petType")).equals("CAT") ? PetType.CAT : PetType.DOG;
        int coins = Integer.parseInt(String.valueOf(map.get("coins")));
        int checkInDays = Integer.parseInt(String.valueOf(map.get("checkInDays")));
        int score = Integer.parseInt(String.valueOf(map.get("score")));


//        System.out.println(uid);
//        System.out.println(name);
//        System.out.println(email);
//        System.out.println(age);
//        System.out.println(sex);
//        System.out.println(height);
//        System.out.println(weight);
//        System.out.println(targetWeight);
//        System.out.println(targetCaloriesLoss);
//        System.out.println(lostCalories);
//        System.out.println(caloriesIntake);
//        System.out.println(totalExerciseTime);
//        System.out.println(completedWeeks);
//        System.out.println(petId);
//        System.out.println(petName);
//        System.out.println(petType);
//        System.out.println(coins);
//        System.out.println(checkInDays);
//        System.out.println(score);
//        System.out.println(activityId);

        User user = new User(uid, name, email, age, sex, height, weight, targetWeight, targetCaloriesLoss, petName, petType);

        user.setScore(score);
        user.setCompletedWeeks(completedWeeks);
        user.setActivityId(activityId);
        user.setCheckInDays(checkInDays);
        user.setCoins(coins);
        user.setPetId(petId);
        user.setTotalExerciseTime(totalExerciseTime);
        user.setCaloriesIntake(caloriesIntake);
        user.setLostCalories(lostCalories);


        return user;
    }

}
