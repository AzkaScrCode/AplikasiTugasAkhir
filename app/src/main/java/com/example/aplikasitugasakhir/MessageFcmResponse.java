package com.example.aplikasitugasakhir;

import com.google.gson.annotations.SerializedName;

public class MessageFcmResponse {

    @SerializedName("message_id")
    private String messageID;

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }
}
