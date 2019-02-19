package com.escalate.model;

import android.support.annotation.NonNull;

public class RecentSBean implements Cloneable,Comparable<RecentSBean> {


    private String common_name;

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

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
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

    public String getCommon_name() {
        return common_name;
    }

    public void setCommon_name(String common_name) {
        this.common_name = common_name;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getRecent_search_item_type() {
        return recent_search_item_type;
    }

    public void setRecent_search_item_type(int recent_search_item_type) {
        this.recent_search_item_type = recent_search_item_type;
    }

    @Override
    public RecentSBean clone() {
        RecentSBean clone;
        try {
            clone = (RecentSBean) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // won't happen
        }

        return clone;

    }


    @Override
    public int compareTo(@NonNull RecentSBean o) {

        int r;
        r = common_name.compareToIgnoreCase(o.getCommon_name());
        return r;
    }
}
