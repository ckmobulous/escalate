package com.escalate.model;

import java.util.List;

public class EmailResponce {
    /**
     * status : SUCCESS
     * data : []
     * message : User mail sent successfully
     * requestKey : api/forgotPassword
     */

    private String status;
    private String message;
    private String requestKey;
    private List<?> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
