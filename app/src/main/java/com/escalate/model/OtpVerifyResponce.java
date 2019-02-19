package com.escalate.model;

public class OtpVerifyResponce {
    /**
     * status : SUCCESS
     * data : {"user_id":"33"}
     * message : Otp verify successfully
     * requestKey : api/verifyemailotp
     */

    private String status;
    private DataBean data;
    private String message;
    private String requestKey;

    @Override
    public String toString() {
        return "OtpVerifyResponce{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                ", requestKey='" + requestKey + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
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

    public static class DataBean {
        /**
         * user_id : 33
         */

        private String user_id;

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    '}';
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }
    }
}
