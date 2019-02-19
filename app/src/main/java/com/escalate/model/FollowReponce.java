package com.escalate.model;

import java.util.List;

public class FollowReponce {

    /**
     * status : SUCCESS
     * data : [{"user_id":"2","user_image":"http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg","fullname":"ambalika ghosh","username":"ambalika","bio":"http://mobulous.app/escalate/public/user_bio/61e0a346b842d24.mp3","follower_flag":"0"},{"user_id":"7","user_image":"http://mobulous.app/escalate/public/users-photos/a5f282298732ebb.jpg","fullname":"Javed Malik","username":"","bio":"","follower_flag":"0"}]
     * message : Following list retrieved successfully.
     * requestKey : api/followingList/8/9
     */

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "FollowReponce{" +
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
         * user_id : 2
         * user_image : http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg
         * fullname : ambalika ghosh
         * username : ambalika
         * bio : http://mobulous.app/escalate/public/user_bio/61e0a346b842d24.mp3
         * follower_flag : 0
         */

        private String user_id;
        private String user_image;
        private String fullname;
        private String username;
        private String bio;
        private String follower_flag;
        private boolean isPlaying = false;
        private boolean isPlayPauseEnable = false;

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", user_image='" + user_image + '\'' +
                    ", fullname='" + fullname + '\'' +
                    ", username='" + username + '\'' +
                    ", bio='" + bio + '\'' +
                    ", follower_flag='" + follower_flag + '\'' +
                    '}';
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

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_image() {
            return user_image;
        }

        public void setUser_image(String user_image) {
            this.user_image = user_image;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getFollower_flag() {
            return follower_flag;
        }

        public void setFollower_flag(String follower_flag) {
            this.follower_flag = follower_flag;
        }
    }
}
