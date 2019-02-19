package com.escalate.model;

public class LoginResponse {
    /**
     * status : SUCCESS
     * data : {"fullname":"ram pratap","phone":"9140405192","username":"ram1259","image":"https://mobulous.app/fametales/public/img/user_signup.png","is_admin":"0","bio":"","admin_status":"1","created_at":"2018-07-20 14:32:04","updated_at":"2018-07-20 14:32:04","user_id":"5","token":"WeZnILnFIPt8MnDEjb50BSVe8"}
     * message : User login successfully.
     * requestKey : api/login
     */

    private String status;
    private DataBean data;
    private String message;
    private String requestKey;

    @Override
    public String toString() {
        return "LoginResponse{" +
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
         * fullname : ram pratap
         * phone : 9140405192
         * username : ram1259
         * image : https://mobulous.app/fametales/public/img/user_signup.png
         * is_admin : 0
         * bio :
         * admin_status : 1
         * created_at : 2018-07-20 14:32:04
         * updated_at : 2018-07-20 14:32:04
         * user_id : 5
         * token : WeZnILnFIPt8MnDEjb50BSVe8
         */

        private String fullname;
        private String phone;
        private String username;
        private String image;
        private String is_admin;
        private String bio;
        private String admin_status;
        private String created_at;
        private String updated_at;
        private String user_id;
        private String token;
        private String email;
        private String notification_flag;

        @Override
        public String toString() {
            return "DataBean{" +
                    "fullname='" + fullname + '\'' +
                    ", phone='" + phone + '\'' +
                    ", username='" + username + '\'' +
                    ", image='" + image + '\'' +
                    ", is_admin='" + is_admin + '\'' +
                    ", bio='" + bio + '\'' +
                    ", admin_status='" + admin_status + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", token='" + token + '\'' +
                    ", email='" + email + '\'' +
                    ", notification_flag='" + notification_flag + '\'' +
                    '}';
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

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNotification_flag() {
            return notification_flag;
        }

        public void setNotification_flag(String notification_flag) {
            this.notification_flag = notification_flag;
        }
    }
}
