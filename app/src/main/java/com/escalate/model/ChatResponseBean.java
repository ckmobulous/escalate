package com.escalate.model;

import com.google.gson.annotations.SerializedName;

public class ChatResponseBean {

    @SerializedName("status")
    private String status;

    @SerializedName("status_code")
    private String status_code;

    @SerializedName("message")
    private String message;

    @SerializedName("requestKey")
    private String requestKey;

    @SerializedName("response")
    private ChatDataBean chatSendResponse;

    @SerializedName("data")
    private ChatHistoryBean chatHistoryResponse;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestKey() {
        return requestKey;
    }

    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }

    public ChatDataBean getChatSendResponse() {
        return chatSendResponse;
    }

    public void setChatSendResponse(ChatDataBean chatSendResponse) {
        this.chatSendResponse = chatSendResponse;
    }

    public ChatHistoryBean getChatHistoryResponse() {
        return chatHistoryResponse;
    }

    public void setChatHistoryResponse(ChatHistoryBean chatHistoryResponse) {
        this.chatHistoryResponse = chatHistoryResponse;
    }
}