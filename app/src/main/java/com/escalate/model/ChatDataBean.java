package com.escalate.model;

import com.google.gson.annotations.SerializedName;

public class ChatDataBean {

    @SerializedName("unique_id")
    private String unique_id;

    @SerializedName("image")
    private String user_image;

    @SerializedName("msg")
    private String audioUrl;

    @SerializedName("message_duration")
    private String message_duration;

    @SerializedName("time")
    private String message_time;

    @SerializedName("flag")
    private String user_flag;


    private boolean isPlaying = false;
    private boolean isPlayPauseEnable = false;
    private boolean isplayPauseEnableRecent =false;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getMessage_time() {
        return message_time;
    }

    public void setMessage_time(String message_time) {
        this.message_time = message_time;
    }

    public String getUser_flag() {
        return user_flag;
    }

    public void setUser_flag(String user_flag) {
        this.user_flag = user_flag;
    }

    public String getMessage_duration() {
        return message_duration;
    }

    public void setMessage_duration(String message_duration) {
        this.message_duration = message_duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isPlayPauseEnable() {
        return isPlayPauseEnable;
    }

    public void setPlayPauseEnable(boolean playPauseEnable) {
        isPlayPauseEnable = playPauseEnable;
    }

    public boolean isIsplayPauseEnableRecent() {
        return isplayPauseEnableRecent;
    }

    public void setIsplayPauseEnableRecent(boolean isplayPauseEnableRecent) {
        this.isplayPauseEnableRecent = isplayPauseEnableRecent;
    }
}
