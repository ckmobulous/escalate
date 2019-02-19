package com.escalate.model;

import java.util.List;

public class NotificationModel {

    public NotificationModel() {
    }

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;


    @Override
    public String toString() {
        return "NotificationResponce{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", requestKey='" + requestKey + '\'' +
                ", data=" + data +
                '}';
    }

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String msg;
        private String image;
        private String user_id;
        private String time;
        private String status;

        @Override
        public String toString() {
            return "DataBean{" +
                    ", msg='" + msg + '\'' +
                    "image='" + image + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", time='" + time + '\'' +
                            ", status='" + status + '\'' +
                    '}';
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
