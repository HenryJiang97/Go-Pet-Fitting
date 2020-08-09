package com.example.gopetfitting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.nguyenhoanglam.imagepicker.model.Config;
//import com.nguyenhoanglam.imagepicker.model.Image;
//import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;

public class ProfileSetupActivity extends AppCompatActivity {
    private final String TAG = ProfileSetupActivity.class.getSimpleName();

    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private String imagePath;

    private ImageView mProfileIV;
    private TextView mDisplayNameET;
    private ProgressBar mSavingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Profile Setup");

        mProfileIV = findViewById(R.id.profile_image);
        mDisplayNameET = findViewById(R.id.display_name_edit_text);

        mSavingProgress = findViewById(R.id.saving_progress);
        mSavingProgress.setVisibility(View.INVISIBLE);

        findViewById(R.id.save_changes_btn).setOnClickListener(saveChanges);

//        findViewById(R.id.change_profile_pic_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ImagePicker.with(ProfileSetupActivity.this)
//                        .setMultipleMode(false)
//                        .setShowCamera(true)
//                        .setSavePath("ImagePicker")
//                        .setRequestCode(100)
//                        .setKeepScreenOn(true)
//                        .start();
//            }
//        });

        Uri imageUrl = mAuth.getCurrentUser().getPhotoUrl();
        String name = mAuth.getCurrentUser().getDisplayName();

        if(imageUrl != null){
            Glide.with(this)
                    .load(imageUrl)
                    .into(mProfileIV);
        }

        if(!TextUtils.isEmpty(name)){
            mDisplayNameET.setText(name);
            mDisplayNameET.setEnabled(false);
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
//        if (requestCode == Config.RC_PICK_IMAGES && resultCode == RESULT_OK && data != null) {
//            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
//            Image image = images.get(0);
//            if(image != null){
//                imagePath = image.getPath();
//                Glide.with(this)
//                        .load(imagePath)
//                        .into(mProfileIV);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    private View.OnClickListener saveChanges = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String displayname = mDisplayNameET.getText().toString();
            if(TextUtils.isEmpty(displayname)){
                Toast.makeText(ProfileSetupActivity.this, "Please enter your display name", Toast.LENGTH_LONG).show();
                return;
            }

            mSavingProgress.setVisibility(View.VISIBLE);

            if(imagePath != null && !imagePath.isEmpty()){
                //image was selected, so upload
                mStorageRef = FirebaseStorage.getInstance().getReference();

                Uri file = Uri.fromFile(new File(imagePath));
                final StorageReference imageRef = mStorageRef.child("images/" + mAuth.getCurrentUser().getUid() + ".jpg");

                imageRef.putFile(file)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Get a URL to the uploaded content
//                                Uri downloadUrl =  taskSnapshot.getDownloadUrl();
                                imageRef.getDownloadUrl().addOnCompleteListener(
                                        new OnCompleteListener<Uri>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                if(task.isSuccessful()){
                                                    updateDetails(displayname, task.getResult().toString());
                                                }else{
                                                    mSavingProgress.setVisibility(View.INVISIBLE);

                                                    Log.w(TAG, "get upload image url:failure", task.getException());
                                                    Toast.makeText(ProfileSetupActivity.this, "Error occurred, please try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                );
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {

                                mSavingProgress.setVisibility(View.INVISIBLE);
                                Log.w(TAG, "upload image:failure", exception);
                                Toast.makeText(ProfileSetupActivity.this, "Error occurred, please try again", Toast.LENGTH_SHORT).show();

                            }
                        });
            }else{
                updateDetails(displayname, "");
            }
        }
    };

    private void updateDetails(final String displayname, String profileImageUrl) {
        UserProfileChangeRequest updateRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(displayname)
                .setPhotoUri(Uri.parse(profileImageUrl))
                .build();

        mAuth.getCurrentUser()
                .updateProfile(updateRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mSavingProgress.setVisibility(View.INVISIBLE);
                        if(task.isSuccessful()){
                            //completed

                            //get firebase user
                            FirebaseUser user = mAuth.getCurrentUser();

                            //get reference
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

                            //build child
                            ref.child("username").setValue(displayname);

                            Toast.makeText(ProfileSetupActivity.this, "Changes saved.",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ProfileSetupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}