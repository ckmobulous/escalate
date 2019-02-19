package com.escalate.adapter;

import java.util.List;

public class ChatHistoryModel {

    public ChatHistoryModel() {
    }

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;


    @Override
    public String toString() {
        return "HistoryResponce{" +
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

        private String wowid;
        private String sender_id;
        private String image;
        private String fullname;
        private String fftime;

        @Override
        public String toString() {
            return "DataBean{" +
                    ", wowid='" + wowid + '\'' +
                    "sender_id='" + sender_id + '\'' +
                    ", image='" + image + '\'' +
                    ", fullname='" + fullname + '\'' +
                    ", fftime='" + fftime + '\'' +
                    '}';
        }


        public String getWowid() {
            return wowid;
        }

        public void setWowid(String wowid) {
            this.wowid = wowid;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getFftime() {
            return fftime;
        }

        public void setFftime(String fftime) {
            this.fftime = fftime;
        }
    }
}
