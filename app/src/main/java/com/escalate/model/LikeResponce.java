package com.escalate.model;

public class LikeResponce {
    /**
     * status : SUCCESS
     * data : {"status":"success","likeflag":"1"}
     * message : Post liked successfully
     * requestKey : api/likepost
     */

    private String status;
    private DataBean data;
    private String message;
    private String requestKey;

    @Override
    public String toString() {
        return "LikeResponce{" +
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
         * status : success
         * likeflag : 1
         */

        private String status;
        private String likeflag;

        @Override
        public String toString() {
            return "DataBean{" +
                    "status='" + status + '\'' +
                    ", likeflag='" + likeflag + '\'' +
                    '}';
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLikeflag() {
            return likeflag;
        }

        public void setLikeflag(String likeflag) {
            this.likeflag = likeflag;
        }
    }
}
