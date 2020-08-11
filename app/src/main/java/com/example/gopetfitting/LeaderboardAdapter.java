package com.example.gopetfitting;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.gopetfitting.models.Pet;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private static final String TAG = "LeaderboardAdapter";

    Context context;
    ArrayList<Pet> pets;

    public LeaderboardAdapter(Context c, ArrayList<Pet> p) {
        context = c;
        pets = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(pets.get(position).getUsername());
        holder.score.setText(String.valueOf(pets.get(position).getGrowthScore()));
        holder.rankNumber.setText(String.valueOf(position + 1));

        Log.e(TAG, "position = " + position);

        holder.onClick(position);
        Log.e(TAG, "holder.name = " + pets.get(1).getUsername());
        Log.e(TAG, "name = " + holder.name.toString());
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, score, rankNumber;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.name);
            score = (TextView) itemView.findViewById(R.id.score);
            rankNumber = (TextView) itemView.findViewById(R.id.rankNumber);
            btn = (Button) itemView.findViewById(R.id.checkDetails);
        }
        public void onClick(final int position) {
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(context,position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}