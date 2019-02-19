package com.escalate.model;

import java.util.List;

public class ReplyCommentResponse {

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "ReplyCommentResponse{" +
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

        private String fullname;
        private String user_image;
        private String username;
        private String message;
        private String created_at;
        private String msg_duration;
        private String description;


        @Override
        public String toString() {
            return "DataBean{" +
                    "fullname='" + fullname + '\'' +
                    ", user_image='" + user_image + '\'' +
                    ", username='" + username + '\'' +
                    ", message='" + message + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", msg_duration='" + msg_duration + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getMsg_duration() {
            return msg_duration;
        }

        public void setMsg_duration(String msg_duration) {
            this.msg_duration = msg_duration;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
