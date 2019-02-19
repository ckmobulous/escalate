package com.escalate.model;

import java.util.List;

public class GenerResponce {
    /**
     * status : SUCCESS
     * data : [{"topic_id":"4","name":"songs"},{"topic_id":"2","name":"Comedy"}]
     * message : Genre list retrieved successfully.
     * requestKey : api/genre_list
     */

    private String status;
    private String message;
    private String requestKey;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "GenerResponce{" +
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
         * topic_id : 4
         * name : songs
         */

        private String topic_id;
        private String name;
        private String status;
        private String icon;
        private boolean isSelected;

        @Override
        public String toString() {
            return "DataBean{" +
                    "topic_id='" + topic_id + '\'' +
                    ", name='" + name + '\'' +
                    ", status='" + status + '\'' +
                            ", icon='" + icon + '\'' +
                    '}';
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }


        public String getTopic_id() {
            return topic_id;
        }

        public void setTopic_id(String topic_id) {
            this.topic_id = topic_id;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
