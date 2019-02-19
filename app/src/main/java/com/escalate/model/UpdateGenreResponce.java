package com.escalate.model;

public class UpdateGenreResponce {
    /**
     * status : SUCCESS
     * data : {"id":5,"fullname":"ram pratap","phone":"9140405192","username":"ram1259","image":"https://mobulous.app/fametales/public/img/user_signup.png","bio":"","topic_id":"4","admin_status":"1","created_at":"2018-07-20 14:32:04","updated_at":"2018-07-20 14:58:29"}
     * message : Topic updated successfully.
     * requestKey : api/update_topic
     */

    private String status;
    private DataBean data;
    private String message;
    private String requestKey;

    @Override
    public String toString() {
        return "UpdateGenreResponce{" +
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
         * id : 5
         * fullname : ram pratap
         * phone : 9140405192
         * username : ram1259
         * image : https://mobulous.app/fametales/public/img/user_signup.png
         * bio :
         * topic_id : 4
         * admin_status : 1
         * created_at : 2018-07-20 14:32:04
         * updated_at : 2018-07-20 14:58:29
         */

        private int id;
        private String fullname;
        private String phone;
        private String username;
        private String image;
        private String bio;
        private String topic_id;
        private String admin_status;
        private String created_at;
        private String updated_at;

        @Override
        public String toString() {
            return "DataBean{" +
                    "id=" + id +
                    ", fullname='" + fullname + '\'' +
                    ", phone='" + phone + '\'' +
                    ", username='" + username + '\'' +
                    ", image='" + image + '\'' +
                    ", bio='" + bio + '\'' +
                    ", topic_id='" + topic_id + '\'' +
                    ", admin_status='" + admin_status + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFullname() {
            return fullname;
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
    }
}
