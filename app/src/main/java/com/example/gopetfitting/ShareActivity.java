//package com.example.gopetfitting;
//
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//
//import com.example.gopetfitting.models.PostDTO;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.List;
//
//public class ShareActivity extends AppCompatActivity {
//
//    private static final String TAG = "ShareActivity";
//
//    DatabaseReference reference;
//
//    private FirebaseAuth mAuth;
//    private FirebaseUser currentUser;
//    private FirebaseFirestore mFirestore;
//
//    public Pet mypet;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share);
//
//        reference = FirebaseDatabase.getInstance().getReference().child("pets");
//
//        mAuth = FirebaseAuth.getInstance();
//        mFirestore = FirebaseFirestore.getInstance();
//
//        final Pet mypet = new Pet();
//        final User myuser = new User();
//
//        Button notificationbutton = (Button) findViewById(R.id.postGrowthScoreButton);
//        notificationbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String text1 = "My pet has " + String.valueOf(mypet.getGrowthScore()) + " points";
//
//                PostDTO message1 = new PostDTO(currentUser.getUid(), currentUser.getDisplayName(), text1);
//
//                mFirestore.collection("messages")
//                        .add(message1)
//                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                if(!task.isSuccessful()){
//                                    Toast.makeText(ShareActivity.this, "Message sending failed", Toast.LENGTH_SHORT).show();
//                                }else{
//
//                                }
//                            }
//                        });
//
//                String text2 = "I have " + String.valueOf(myuser.getCoins()) + " coins";
//
//                PostDTO message2 = new PostDTO(currentUser.getUid(), currentUser.getDisplayName(), text2);
//
//                mFirestore.collection("messages")
//                        .add(message2)
//                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                if(!task.isSuccessful()){
//                                    Toast.makeText(ShareActivity.this, "Message sending failed", Toast.LENGTH_SHORT).show();
//                                }else{
//
//                                }
//                            }
//                        });
//
//                String text3 = "I have lost " + String.valueOf(myuser.getLostCalories()) + " Calories";
//
//                PostDTO message3 = new PostDTO(currentUser.getUid(), currentUser.getDisplayName(), text3);
//
//                mFirestore.collection("messages")
//                        .add(message3)
//                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                            @Override
//                            public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                if(!task.isSuccessful()){
//                                    Toast.makeText(ShareActivity.this, "Message sending failed", Toast.LENGTH_SHORT).show();
//                                }else{
//
//                                }
//                            }
//                        });
//
////                //get firebase user
////                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////
////                //get reference
////                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
////                Log.e(TAG, "user.getUid() = " + user.getUid());
//
////                ref.addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////                        User user = dataSnapshot.getValue(User.class);
////                        Log.e(TAG, "user = " + user);
////
////                        String text = "I have " + String.valueOf(user.getCoins()) + " coins";
////
////                        PostDTO message = new PostDTO(currentUser.getUid(), currentUser.getDisplayName(), text);
////
////                        mFirestore.collection("messages")
////                                .add(message)
////                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
////                                    @Override
////                                    public void onComplete(@NonNull Task<DocumentReference> task) {
////
////                                        if(!task.isSuccessful()){
////                                            Toast.makeText(ShareActivity.this, "Message sending failed", Toast.LENGTH_SHORT).show();
////                                        }else{
////
////                                        }
////                                    }
////                                });
////                    }
////
////                    @Override
////                    public void onCancelled(DatabaseError databaseError) {
////
////                    }
////                });
//
//
//                Toast.makeText(ShareActivity.this, "Your status is shared",
//                        Toast.LENGTH_SHORT).show();
//
//                Intent momentsActivityIntent = new Intent(ShareActivity.this, MomentsActivity.class);
//                startActivity(momentsActivityIntent);
//
//                sendNotification();
//            }
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//
//            Intent signinScreenIntent = new Intent(this, SigninActivity.class);
//            startActivity(signinScreenIntent);
//            finish();
//        }else{
//            if(currentUser.getDisplayName() == null || currentUser.getDisplayName().isEmpty()){
//                Intent signinScreenIntent = new Intent(this, ProfileSetupActivity.class);
//                startActivity(signinScreenIntent);
//            }else{
//                startListeningForMessages();
//            }
//        }
//    }
//
//    private void startListeningForMessages() {
//        mFirestore.collection("messages")
//                .orderBy("dateSent")
//                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot snapshots,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if(e != null){
//                            //an error has occured
//                            Log.d("TAG", "error");
//                        }else{
//                            List<PostDTO> messages = snapshots.toObjects(PostDTO.class);
//
//                        }
//                    }
//                });
//    }
//
//    public void sendNotification(){
//        // Prepare intent which is triggered if the
//        // notification is selected
//        Intent intent = new Intent(this, MomentsActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);
//
//        PendingIntent momentsIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(),
//                new Intent(this, MomentsActivity.class), 0);
//
//
//        // Build notification
//        String channelId = getString(R.string.channel_id);
//
////        Notification noti = new Notification.Builder(this)   DEPRECATED
//        Notification noti = new NotificationCompat.Builder(this,channelId)
//
//                .setContentTitle("New post from Moments")
//                .setContentText("").setSmallIcon(R.drawable.foo)
//
//                .addAction(R.drawable.foo, "Moments", momentsIntent).setContentIntent(pIntent).build();
////                .addAction(R.drawable.icon, "More", pIntent)
////              .addAction(R.drawable.icon, "And more", pIntent).build();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        // hide the notification after its selected
//        noti.flags |= Notification.FLAG_AUTO_CANCEL ;
//
//        notificationManager.notify(0, noti);
//    }
//}