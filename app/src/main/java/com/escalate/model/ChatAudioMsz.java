package com.escalate.model;

public class ChatAudioMsz {

    public ChatAudioMsz() {
    }


    private String audioUrl;
    private String mssgDuration;
    private String receiverId;
    private String senderId;
    private String type;

    private boolean isPlayPauseEnable = false;
    private boolean isPlaying = false;
    private boolean isplayPauseEnableRecent =false;


    public String getMssgDuration() {
        return mssgDuration;
    }

    public void setMssgDuration(String mssgDuration) {
        this.mssgDuration = mssgDuration;
    }


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }



    public String getDuration() {
        return mssgDuration;
    }

    public void setDuration(String duration) {
        this.mssgDuration = duration;
    }

    public boolean isPlayPauseEnable() {
        return isPlayPauseEnable;
    }

    public void setPlayPauseEnable(boolean playPauseEnable) {
        isPlayPauseEnable = playPauseEnable;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public boolean isIsplayPauseEnableRecent() {
        return isplayPauseEnableRecent;
    }

    public void setIsplayPauseEnableRecent(boolean isplayPauseEnableRecent) {
        this.isplayPauseEnableRecent = isplayPauseEnableRecent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
