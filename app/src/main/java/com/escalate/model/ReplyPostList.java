package com.escalate.model;

import java.util.List;

public class ReplyPostList {
    /**
     * status : SUCCESS
     * data : [{"user_image":"http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg","username":"ambalika","message":"http://mobulous.app/escalate/public/audio_comment/96af235f30dca14.mp3","created_at":"2018-08-07 05:26:15"},{"user_image":"http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg","username":"ambalika","message":"http://mobulous.app/escalate/public/audio_comment/49c7ad8b27da754.mp3","created_at":"2018-08-07 06:14:12"}]
     * message : Audio Comment list retrieved successfully.
     * requestKey : api/commentlist/2
     */

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "ReplyPostList{" +
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
         * message : http://mobulous.app/escalate/public/audio_comment/96af235f30dca14.mp3
         * created_at : 2018-08-07 05:26:15
         */

        private String user_image;
        private String username;
        private String fullname;
        private String message;
        private String created_at;
        private String description;
        private String msg_duration;
        private boolean isPlaying = false;
        private boolean isPlayPauseEnable = false;

        /*
        *
        *  {
      "fullname": "Rajeev sharma",
      "user_image": "https:\/\/mobulous.app\/escalate\/public\/users-photos\/8d5fd031bf46837.jpg",
      "username": "rajeev12",
      "message": "https:\/\/mobulous.app\/escalate\/public\/audio_comment\/55b400158a88262.mp3",
      "created_at": "2018-08-28 07:42:29",
      "msg_duration": "00:00:05",
      "description": "cheers"
    },
        * */
        @Override
        public String toString() {
            return "DataBean{" +
                    "user_image='" + user_image + '\'' +
                    ", username='" + username + '\'' +
                    ", fullname='" + fullname + '\'' +
                    ", message='" + message + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ",description='" +description+'\''+
                    ",msg_duration='" +msg_duration+'\''+
                    '}';
        }


        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getMsg_duration() {
            return msg_duration;
        }

        public void setMsg_duration(String msg_duration) {
            this.msg_duration = msg_duration;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
