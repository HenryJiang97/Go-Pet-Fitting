package com.example.gopetfitting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gopetfitting.models.PostDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.MessagesHolder> {
    static int MY_MESSAGE = 1;
    static int OTHER_MESSAGES = 2;

    String currentUserId;

    List<PostDTO> messages;
    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy, hh:mm:ss a");

    MomentsAdapter(String currentUserId){
        this.currentUserId = currentUserId;
        messages = new ArrayList<>();
    }

    @NonNull
    @Override
    public MessagesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatBubble;

        if(viewType == MY_MESSAGE){
            chatBubble = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.post_bubble_me, parent, false);
        }else{
            chatBubble = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.post_bubble_other_users, parent, false);
        }

        return new MessagesHolder(chatBubble);
    }

    @Override
    public int getItemViewType(int position) {

        if(messages.get(position).getSenderId().equals(currentUserId)){
            return MY_MESSAGE;
        }else{
            return OTHER_MESSAGES;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessagesHolder holder, int position) {
        holder.bindView(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setData(List<PostDTO> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    class MessagesHolder extends RecyclerView.ViewHolder {
        TextView mMessageTextView;
        TextView mDateSentTextView;
        TextView mSendernameTextView;
//        TextView mSendernameTextView_1;

        public MessagesHolder(@NonNull View itemView) {
            super(itemView);
            mMessageTextView = itemView.findViewById(R.id.message_tv);
            mDateSentTextView = itemView.findViewById(R.id.dateSent_tv);
            mSendernameTextView = itemView.findViewById(R.id.sender_name_tv);
//            mSendernameTextView_1 = itemView.findViewById(R.id.sender_name_tv_1);
        }

        public void bindView(PostDTO message) {
            mSendernameTextView.setText(message.getSenderName());
//            mSendernameTextView_1.setText("test");
            mMessageTextView.setText(message.getText());
            if(message.getDateSent() != null) {
                mDateSentTextView.setText(format.format(message.getDateSent()));
            }else{
                mDateSentTextView.setText(format.format(new Date()));
            }
        }
    }
}