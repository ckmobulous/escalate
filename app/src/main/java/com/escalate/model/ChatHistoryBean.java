package com.escalate.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatHistoryBean {

    @SerializedName("room_name")
    private String room_name;

    @SerializedName("room_image")
    private String room_image;

    @SerializedName("msg_thread")
    private ArrayList<ChatDataBean> chatDataHistoryList;

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_image() {
        return room_image;
    }

    public void setRoom_image(String room_image) {
        this.room_image = room_image;
    }

    public ArrayList<ChatDataBean> getChatDataHistoryList() {
        return chatDataHistoryList;
    }

    public void setChatDataHistoryList(ArrayList<ChatDataBean> chatDataHistoryList) {
        this.chatDataHistoryList = chatDataHistoryList;
    }
}
