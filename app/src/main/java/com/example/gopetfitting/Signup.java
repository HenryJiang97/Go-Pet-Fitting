package com.example.gopetfitting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Signup extends AppCompatActivity {
    private static final String TAG = "Sign Up Activity";

    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    private EditText nameET;
    private EditText emailET;
    private EditText passwordET;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // Initialize variables
        auth = FirebaseAuth.getInstance();

        nameET = findViewById(R.id.name_signup);
        emailET = findViewById(R.id.email_signup);
        passwordET = findViewById(R.id.password_signup);
        button = findViewById(R.id.signup_signup);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // Button listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(emailET.getText().toString(), passwordET.getText().toString());

            }
        });
    }

    // Method to create new user in Firebase Authentication
    private void register(final String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d(TAG, "createUser:success");
                            firebaseUser = auth.getCurrentUser();
                            Log.d(TAG, "User: " + firebaseUser.getEmail());
                            Toast.makeText(Signup.this, "Signup successfully", Toast.LENGTH_SHORT).show();

                            System.out.println("User id: " + firebaseUser.getUid());
                            System.out.println("User email: " + firebaseUser.getEmail());


                            // Go to personal info page
                            Intent intent = new Intent(getBaseContext(), PersonalInfo.class);
                            intent.putExtra("name", nameET.getText().toString());
                            intent.putExtra("email", email);
                            intent.putExtra("uid", firebaseUser.getUid());
                            startActivity(intent);

                            //

                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUser:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
