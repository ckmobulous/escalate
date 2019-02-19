package com.escalate.model;

public class PostAudioResponce {

    /**
     * status : SUCCESS
     * data : {"user_id":"2","description":"this audio related to work","audio_url":"http://mobulous.app/escalate/public/audio-upload/c82aa03e3d7f019.mp3","duration":"3","updated_at":"2018-08-06 15:33:06","created_at":"1 second ago","topic_name":"Comedy","audio_id":"2"}
     * message : Audio uploaded successfully.
     * requestKey : api/audio
     */

    private String status;
    private DataBean data;
    private String message;
    private String requestKey;

    @Override
    public String toString() {
        return "PostAudioResponce{" +
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
         * user_id : 2
         * description : this audio related to work
         * audio_url : http://mobulous.app/escalate/public/audio-upload/c82aa03e3d7f019.mp3
         * duration : 3
         * updated_at : 2018-08-06 15:33:06
         * created_at : 1 second ago
         * topic_name : Comedy
         * audio_id : 2
         */

        private String user_id;
        private String description;
        private String audio_url;
        private String duration;
        private String updated_at;
        private String created_at;
        private String topic_name;
        private String audio_id;

        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", description='" + description + '\'' +
                    ", audio_url='" + audio_url + '\'' +
                    ", duration='" + duration + '\'' +
                    ", updated_at='" + updated_at + '\'' +
                    ", created_at='" + created_at + '\'' +
                    ", topic_name='" + topic_name + '\'' +
                    ", audio_id='" + audio_id + '\'' +
                    '}';
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAudio_url() {
            return audio_url;
        }

        public void setAudio_url(String audio_url) {
            this.audio_url = audio_url;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getTopic_name() {
            return topic_name;
        }

        public void setTopic_name(String topic_name) {
            this.topic_name = topic_name;
        }

        public String getAudio_id() {
            return audio_id;
        }

        public void setAudio_id(String audio_id) {
            this.audio_id = audio_id;
        }
    }
}
