package com.escalate.model;

public class ViewProfileResponce {


    /**
     * status : SUCCESS
     * data : {"fullname":"Jack Shukla","phone":"8445577995","username":"jackshukla","image":"https://mobulous.app/fametales/public/img/user_signup.png","is_admin":"0","bio":"","topic_id":"","admin_status":"1","created_at":"2018-07-30 06:51:50","updated_at":"2018-07-30 06:51:50","user_id":"58"}
     * message : User details retrieved successfully.
     * requestKey : api/userdetail/58
     */

    private String status;
    private DataBean data;
    private String message;
    private String requestKey;

    @Override
    public String toString() {
        return "ViewProfileResponce{" +
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
         * fullname : Jack Shukla
         * phone : 8445577995
         * username : jackshukla
         * image : https://mobulous.app/fametales/public/img/user_signup.png
         * is_admin : 0
         * bio :
         * topic_id :
         * admin_status : 1
         * created_at : 2018-07-30 06:51:50
         * updated_at : 2018-07-30 06:51:50
         * user_id : 58
         */

        private String fullname;
        private String phone;
        private String username;
        private String image;
        private String is_admin;
        private String bio;
        private String topic_id;
        private String admin_status;
        private String created_at;
        private String updated_at;
        private String user_id;
        private String email;
        private String num_of_post;
        private String number_of_followers;
        private String number_of_following;
        private String flag_follow;
        private String socialid;
        private String bio_duration;
        private String firebase_id;
        private String country_code;
        @Override
        public String toString() {
            return "DataBean{" +
                    "fullname='" + fullname + '\'' +
                    ", phone='" + phone + '\'' +
                    ", username='" + username + '\'' +
                    ", image='" + image + '\'' +
                    ", is_admin='" + is_admin + '\'' +
                    ", bio='" + bio + '\'' +
                    ", topic_id='" + topic_id + '\'' +
                    ", admin_status='" + admin_status + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", email='" + email + '\'' +
                    ", num_of_post='" + num_of_post + '\'' +
                    ", number_of_followers='" + number_of_followers + '\'' +
                    ", number_of_following='" + number_of_following + '\'' +
                    ", flag_follow='" + flag_follow + '\'' +
                    ", socialid='" + socialid + '\'' +
                    ", bio_duration='" + bio_duration + '\'' +
                    ", firebase_id='" + firebase_id + '\'' +
                    ", country_code='" + country_code + '\'' +
                    '}';
        }


        public String getBio_duration() {
            return bio_duration;
        }

        public void setBio_duration(String bio_duration) {
            this.bio_duration = bio_duration;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }


        public String getFullname() {
            return fullname;
        }

        public String getSocialid() {
            return socialid;
        }

        public void setSocialid(String socialid) {
            this.socialid = socialid;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getIs_admin() {
            return is_admin;
        }

        public void setIs_admin(String is_admin) {
            this.is_admin = is_admin;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getAdmin_status() {
            return admin_status;
        }

        public void setAdmin_status(String admin_status) {
            this.admin_status = admin_status;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getNum_of_post() {
            return num_of_post;
        }

        public void setNum_of_post(String num_of_post) {
            this.num_of_post = num_of_post;
        }

        public String getNumber_of_followers() {
            return number_of_followers;
        }

        public void setNumber_of_followers(String number_of_followers) {
            this.number_of_followers = number_of_followers;
        }

        public String getNumber_of_following() {
            return number_of_following;
        }

        public void setNumber_of_following(String number_of_following) {
            this.number_of_following = number_of_following;
        }

        public String getFlag_follow() {
            return flag_follow;
        }

        public void setFlag_follow(String flag_follow) {
            this.flag_follow = flag_follow;
        }


        public String getFirebase_id() {
            return firebase_id;
        }

        public void setFirebase_id(String firebase_id) {
            this.firebase_id = firebase_id;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }
    }


}
