package com.escalate.model;

import java.util.List;

import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;

public class PostListResponce {

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "PostListResponce{" +
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

        private String user_id;
        private String user_image;
        private String fullname;
        private String username;
        private String audio_url;
        private String description;
        private String tag_list;
        private String topic_name;
        private String duration;
        private String post_id;
        private String like_flag;
        private String like_count;
        private String reply_count;
        private String follow_flag;
        private boolean isPlaying = false;
        private boolean isPlayPauseEnable = false;
        private boolean isplayPauseEnableRecent =false;

        /*
        *
        * {
    "user_id": "9",
    "user_image": "https:\/\/mobulous.app\/escalate\/public\/users-photos\/b6bf048fd59a07e.jpg",
    "fullname": "Rajeev sharma",
    "username": "rajeev12",
    "audio_url": "https:\/\/mobulous.app\/escalate\/public\/audio-upload\/31972c9f76b2d40.mp3",
    "description": "#abcs dhejs #gg1-#a hsjs",
    "tag_list": "#abcs,#gg1-#a",
    "topic_name": "Ash Test",
    "duration": "00:00:20",
    "post_id": "7",
    "topic_id": "5",
    "like_flag": "0",
    "like_count": "1",
    "reply_count": "2"
  },
        * */
//
//        private OnProgressListener progressListenerGlobal;
//        private rm.com.audiowave.AudioWaveView waveViewGlobal;

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='"+user_id+'\''+
                    "user_image='" + user_image + '\'' +
                    ", fullname='" + fullname + '\'' +
                    ", username='" + username + '\'' +
                    ", audio_url='" + audio_url + '\'' +
                    ", description='" + description + '\'' +
                    ", tag_list='" + tag_list + '\'' +
                    ", topic_name='" + topic_name + '\'' +
                    ", duration='" + duration + '\'' +
                    ", post_id='" + post_id + '\'' +
                    ", like_flag='" + like_flag + '\'' +
                    ", like_count='" + like_count + '\'' +
                    ", reply_count='" + reply_count + '\'' +
                            ", follow_flag='" + follow_flag + '\'' +
                    '}';
        }


//        public OnProgressListener getProgressListenerGlobal() {
//            return progressListenerGlobal;
//        }
//
//        public void setProgressListenerGlobal(OnProgressListener progressListenerGlobal) {
//            this.progressListenerGlobal = progressListenerGlobal;
//        }
//
//        public AudioWaveView getWaveViewGlobal() {
//            return waveViewGlobal;
//        }
//
//        public void setWaveViewGlobal(AudioWaveView waveViewGlobal) {
//            this.waveViewGlobal = waveViewGlobal;
//        }


        public boolean isIsplayPauseEnableRecent() {
            return isplayPauseEnableRecent;
        }

        public void setIsplayPauseEnableRecent(boolean isplayPauseEnableRecent) {
            this.isplayPauseEnableRecent = isplayPauseEnableRecent;
        }

        public boolean isPlayPauseEnable() {
            return isPlayPauseEnable;
        }

        public void setPlayPauseEnable(boolean playPauseEnable) {
            isPlayPauseEnable = playPauseEnable;
        }

        public String getTag_list() {
            return tag_list;
        }

        public void setTag_list(String tag_list) {
            this.tag_list = tag_list;
        }

        public boolean isPlaying() {
            return isPlaying;
        }

        public void setPlaying(boolean playing) {
            isPlaying = playing;
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

        public String getAudio_url() {
            return audio_url;
        }

        public void setAudio_url(String audio_url) {
            this.audio_url = audio_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPost_id() {
            return post_id;
        }

        public void setPost_id(String post_id) {
            this.post_id = post_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getLike_flag() {
            return like_flag;
        }

        public void setLike_flag(String like_flag) {
            this.like_flag = like_flag;
        }

        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getReply_count() {
            return reply_count;
        }

        public void setReply_count(String reply_count) {
            this.reply_count = reply_count;
        }

        public String getFollow_flag() {
            return follow_flag;
        }

        public void setFollow_flag(String follow_flag) {
            this.follow_flag = follow_flag;
        }
    }
}
