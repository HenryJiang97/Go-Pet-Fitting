package com.example.gopetfitting.models;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PostDTO {
    private String senderId;
    private String senderName;
    private String text;

    @ServerTimestamp
    private Date dateSent;

    public PostDTO(){}

    public PostDTO(String senderId, String senderName, String text) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}