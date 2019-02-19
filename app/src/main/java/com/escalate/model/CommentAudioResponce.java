package com.escalate.model;

import java.util.List;

public class CommentAudioResponce {
    /**
     * status : SUCCESS
     * data : [{"user_image":"http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg","username":"ambalika","message":"","created_at":"2018-08-07 06:19:48"},{"user_image":"http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg","username":"ambalika","message":"http://mobulous.app/escalate/public/audio_comment/9f13e9d7fb53a58.mp3","created_at":"2018-08-07 06:20:08"}]
     * message : Audio Comment list retrieved successfully.
     * requestKey : api/commentOnAudio
     */

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "CommentAudioResponce{" +
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
        /**
         * user_image : http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg
         * username : ambalika
         * message :
         * created_at : 2018-08-07 06:19:48
         */

        private String user_image;
        private String username;
        private String message;
        private String created_at;

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_image='" + user_image + '\'' +
                    ", username='" + username + '\'' +
                    ", message='" + message + '\'' +
                    ", created_at='" + created_at + '\'' +
                    '}';
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
    }
}
