package com.example.gopetfitting;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.gopetfitting.models.Pet;

import java.util.ArrayList;
import java.util.Collections;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "LeaderboardActivity";

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Pet> list;
    LeaderboardAdapter adapter;
    private FirebaseFirestore mFirestore;
    TextView passPercent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Pet>();
//        passPercent = (TextView) findViewById(R.id.pass_percent);

        reference = FirebaseDatabase.getInstance().getReference().child("pets");
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Log.e(TAG, "reference = " + reference);
        Log.e(TAG, "mAuth.getCurrentUser().getUid(); = " + mAuth.getCurrentUser().getUid());


//        reference.orderByChild("growthScore").limitToLast(10).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
//                    Pet p = dataSnapshot1.getValue(Pet.class);
//                    list.add(p);
//                    Log.e(TAG, "list item = " + p.username);
//                    Log.e(TAG, "list = " + list);
//                }
//                Collections.reverse(list);
//                adapter = new LeaderboardAdapter(LeaderboardActivity.this, list);
//                recyclerView.setAdapter(adapter);
//                Log.e(TAG, "adapter = " + adapter);
//                Log.e(TAG, "recyclerView = " + recyclerView);
//                Log.e(TAG, "adapter.getItemCount = " + adapter.getItemCount());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(LeaderboardActivity.this,"Something is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });

        mFirestore.collection("pets")
                .orderBy("growthScore")
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if(e != null){
                            System.err.println("Listen failed:" + e);
                            return;
                        }else{
                            ArrayList<Pet> p = (ArrayList<Pet>) snapshots.toObjects(Pet.class);
                            Log.e(TAG, "cities = " + p);
                            Collections.reverse(p);
                            adapter = new LeaderboardAdapter(LeaderboardActivity.this, p);
                            recyclerView.setAdapter(adapter);
                            Log.e(TAG, "adapter = " + adapter);
                            Log.e(TAG, "recyclerView = " + recyclerView);
                            Log.e(TAG, "adapter.getItemCount = " + adapter.getItemCount());

                        }
                    }
                });

    }


}