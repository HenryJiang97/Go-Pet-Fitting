package com.example.gopetfitting;

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
    private static final String TAG = "Signup Activity";

    private FirebaseUser user;
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
                Toast.makeText(Signup.this, "Sign up clicked", Toast.LENGTH_SHORT).show();
                createNewUser(emailET.getText().toString(), passwordET.getText().toString());

            }
        });
    }

    // Method to create new user in Firebase Authentication
    private void createNewUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUser:success");
                            user = auth.getCurrentUser();

                            Log.d(TAG, "User: " + user.getEmail());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUser:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}
