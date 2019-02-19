package com.escalate.model;

import android.support.annotation.NonNull;

import java.util.List;

public class TopSearchResponce {
    /**
     * status : SUCCESS
     * data : [{"user_image":"http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg","user_name":"ambalika","fullname":"ambalika ghosh","number_of_post":"2","topic_match":"2","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/img/user_signup.png","user_name":"adnaniOS","fullname":"adman Khan","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/img/user_signup.png","user_name":"jackshukla","fullname":"Jack Shukla","number_of_post":"0","topic_match":"2","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/img/user_signup.png","user_name":"javed25","fullname":"malik javed","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/img/user_signup.png","user_name":"tiwariboy","fullname":"Lallan Tiwari","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/202e44d0306dc4c.jpg","user_name":"","fullname":"Javed Malik","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/5efefd39c1927c9.jpg","user_name":"testmobulous123@gmail.com","fullname":"Mobulous Apps","number_of_post":"0","topic_match":"2","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/66a46c75a7d335f.jpg","user_name":"","fullname":"Javed Malik","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/67a949711d24d2c.jpg","user_name":"","fullname":"Fitviu App","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/725221502691ede.jpg","user_name":"mahi009","fullname":"mahipal","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/7f18af9596fa265.jpg","user_name":"","fullname":"Javed Malik","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/9affc018e2750a6.jpg","user_name":"Ash123","fullname":"Ayushman","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/a5f282298732ebb.jpg","user_name":"","fullname":"Javed Malik","number_of_post":"0","topic_match":"0","follower_flag":"1"},{"user_image":"http://mobulous.app/escalate/public/users-photos/d777b52299bbc88.jpg","user_name":"","fullname":"Javed Malikbh","number_of_post":"0","topic_match":"0","follower_flag":"0"},{"user_image":"http://mobulous.app/escalate/public/users-photos/f7e4aec6e37ec5f.jpg","user_name":"","fullname":"Fitviu App","number_of_post":"0","topic_match":"0","follower_flag":"0"}]
     * message : Top profile list retrieved successfully.
     * requestKey : api/topprofile/2
     */

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "TopSearchResponce{" +
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

    public static class DataBean /*implements Comparable<DataBean>*/ implements Cloneable {
        /**
         * user_image : http://mobulous.app/escalate/public/users-photos/90ed887c4bd7915.jpg
         * user_name : ambalika
         * fullname : ambalika ghosh
         * number_of_post : 2
         * topic_match : 2
         * follower_flag : 0
         */

        private String user_id;
        private String user_image;
        private String user_name;
        private String fullname;
        private String number_of_post;
        private String topic_match;
        private String follower_flag;
        private String topic_id;
        private String topic_name;
        private String tag_id;
        private String num_of_post;
        private String tag_name;
        private String icon;

        private int recent_search_item_type = -1;

        @Override
        public String toString() {
            return "DataBean{" +
                    ", user_id='" + user_id + '\'' +
                    "user_image='" + user_image + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", fullname='" + fullname + '\'' +
                    ", number_of_post='" + number_of_post + '\'' +
                    ", topic_match='" + topic_match + '\'' +
                    ", follower_flag='" + follower_flag + '\'' +
                    ", topic_id='" + topic_id + '\'' +
                    ", topic_name='" + topic_name + '\'' +
                    ", tag_id='" + tag_id + '\'' +
                    ", num_of_post='" + num_of_post + '\'' +
                    ", tag_name='" + tag_name + '\'' +
                    ", icon='" + icon + '\'' +
                    ", recent_search_item_type='" + recent_search_item_type + '\'' +
                    '}';
        }

        @Override
        public DataBean clone() {
            DataBean clone;
            try{
                clone = (DataBean) super.clone();

            }catch(CloneNotSupportedException e){
                throw new RuntimeException(e); // won't happen
            }

            return clone;

        }


        public int getRecent_search_item_type() {
            return recent_search_item_type;
        }

        public void setRecent_search_item_type(int recent_search_item_type) {
            this.recent_search_item_type = recent_search_item_type;
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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getNumber_of_post() {
            return number_of_post;
        }

        public void setNumber_of_post(String number_of_post) {
            this.number_of_post = number_of_post;
        }

        public String getTopic_match() {
            return topic_match;
        }

        public void setTopic_match(String topic_match) {
            this.topic_match = topic_match;
        }

        public String getFollower_flag() {
            return follower_flag;
        }

        public void setFollower_flag(String follower_flag) {
            this.follower_flag = follower_flag;
        }

        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getTag_id() {
            return tag_id;
        }

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public String getNum_of_post() {
            return num_of_post;
        }

        public void setNum_of_post(String num_of_post) {
            this.num_of_post = num_of_post;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

//        @Override
//        public int compareTo(@NonNull DataBean o) {
//
//            int r_s_type = this.recent_search_item_type;
//            int r;
//            switch (r_s_type) {
//                case 0: //ITEM_TYPE_PEOPLE
//                    if (o.getFullname() != null)
//                        r = fullname.compareToIgnoreCase(o.getFullname());
//                    else
//                        r = 0;
//                    break;
//
//                case 1://ITEM_TYPE_TOPICS
//                    if (o.getTopic_name() != null)
//                        r = topic_name.compareToIgnoreCase(o.getTopic_name());
//                    else
//                        r = 0;
//                    break;
//
//                case 2:
//                    r = 0;
//                    break;
//
//                case 3://ITEM_TYPE_TAGS
//                    if (o.getTag_name() != null)
//                        r = tag_name.compareToIgnoreCase(o.getTag_name());
//                    else
//                        r = 0;
//                    break;
//
//                default:
//                    r = 0;
//                    break;
//            }
//
//            return r;
//        }
    }
}
