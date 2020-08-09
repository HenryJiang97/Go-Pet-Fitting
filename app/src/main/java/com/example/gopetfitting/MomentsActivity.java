package com.example.gopetfitting;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gopetfitting.models.PostDTO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MomentsActivity extends AppCompatActivity {

    private static final String TAG = "MomentsActivity";

    DatabaseReference reference;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore mFirestore;

    MomentsAdapter mAdapter;
    RecyclerView mRecyclerView;
    Toolbar toolbar;
    TextView mDisplayNameTV;
    public ImageView mProfileIV;
    public ImageView mProfileTVBubbleMe;
    EditText mMessageET;
    ProgressBar sendingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moments);

        reference = FirebaseDatabase.getInstance().getReference().child("pets");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View inflatedView = getLayoutInflater().inflate(R.layout.post_bubble_me, null);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signinScreenIntent = new Intent(MomentsActivity.this, ProfileSetupActivity.class);
                startActivity(signinScreenIntent);
            }
        });

        mDisplayNameTV = findViewById(R.id.display_name_text);
        mProfileIV = findViewById(R.id.profile_image);
//        mProfileTVBubbleMe = (ImageView) inflatedView.findViewById(R.id.profile_image_bubble_me);
        mMessageET = findViewById(R.id.message_et);
        sendingProgress = findViewById(R.id.sending_progress);
        sendingProgress.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        mAdapter = new MomentsAdapter(mAuth.getCurrentUser().getUid());
        mRecyclerView = findViewById(R.id.chat_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.send_btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(MomentsActivity.this);
                String text = mMessageET.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    sendingProgress.setVisibility(View.VISIBLE);
                    PostDTO message = new PostDTO(currentUser.getUid(), currentUser.getDisplayName(), text);
                    sendMessage(message);
                    sendNotification();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent signinScreenIntent = new Intent(this, SigninActivity.class);
            startActivity(signinScreenIntent);
            finish();
        }else{
            if(currentUser.getDisplayName() == null || currentUser.getDisplayName().isEmpty()){

                Intent signinScreenIntent = new Intent(this, ProfileSetupActivity.class);
                startActivity(signinScreenIntent);
            }else{
                mDisplayNameTV.setText(currentUser.getDisplayName());
                Uri imageUrl = currentUser.getPhotoUrl();

                if(imageUrl != null){
                    Glide.with(this)
                            .load(imageUrl)
                            .into(mProfileIV);
                    Log.d("TAG", "imageUrl = " + imageUrl);
                    Log.d("TAG", "mProfileIV = " + mProfileIV);
                }

//                if(imageUrl != null){
//                    Log.d("TAG", "imageUrl = " + imageUrl);
//                    Log.d("TAG", "mProfileTVBubbleMe = " + mProfileTVBubbleMe);
//                    Glide.with(this)
//                            .load(imageUrl)
//                            .into(mProfileTVBubbleMe);
//                }


                startListeningForMessages();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

//        if (id == R.id.action_edit_profile) {
//            Intent signinScreenIntent = new Intent(this, ProfileSetupActivity.class);
//            startActivity(signinScreenIntent);
//            return true;
//        }else if (id == R.id.action_logout) {
//            mAuth.signOut();
//            Intent signinScreenIntent = new Intent(this, SigninActivity.class);
//            startActivity(signinScreenIntent);
//            finish();
//            return true;
  //      }

        return super.onOptionsItemSelected(item);
    }


    private void startListeningForMessages() {
        mFirestore.collection("messages")
                .orderBy("dateSent")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            System.err.println("Listen failed:" + e);
                            return;
                        }else{
                            List<PostDTO> messages = snapshots.toObjects(PostDTO.class);

                            mAdapter.setData(messages);

                            mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                        }
                    }
                });
    }

    private void sendMessage(PostDTO message){
        mFirestore.collection("messages")
                .add(message)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        sendingProgress.setVisibility(View.INVISIBLE);
                        if(!task.isSuccessful()){
                            Toast.makeText(MomentsActivity.this, "Message sending failed", Toast.LENGTH_SHORT).show();
                        }else{
                            mMessageET.setText("");
                        }
                    }
                });
    }

    public static void hideKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void sendNotification(){
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, MomentsActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        PendingIntent momentsIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(),
                new Intent(this, MomentsActivity.class), 0);


        // Build notification
        String channelId = getString(R.string.channel_id);

//        Notification noti = new Notification.Builder(this)   DEPRECATED
        Notification noti = new NotificationCompat.Builder(this,channelId)

                .setContentTitle("New post from Moments")
                .setContentText("").setSmallIcon(R.drawable.foo_background)

                .addAction(R.drawable.foo, "Moments", momentsIntent).setContentIntent(pIntent).build();
//                .addAction(R.drawable.icon, "More", pIntent)
//              .addAction(R.drawable.icon, "And more", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL ;

        notificationManager.notify(0, noti);
    }

}