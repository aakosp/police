package com.psb.entity;

/**
 * Created by zl on 2015/2/5.
 */
public class Opinion {

    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String NOT_REPLY = "NOT_REPLY";

    private int id;
    private int user_id;
    private String type;
    private String title;
    private String picture;
    private String content;
    private String reply;
    private String time;
    private User user;
    private Reply reply_result;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Reply getReply_result() {
        return reply_result;
    }

    public void setReply_result(Reply reply_result) {
        this.reply_result = reply_result;
    }

    public class Reply{
        private int id;
        private int opinion_id;
        private String reply_content;
        private int user_id;
        private String reply_time;
        private String reply_police_name;
        private String reply_police_phone;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOpinion_id() {
            return opinion_id;
        }

        public void setOpinion_id(int opinion_id) {
            this.opinion_id = opinion_id;
        }

        public String getReply_content() {
            return reply_content;
        }

        public void setReply_content(String reply_content) {
            this.reply_content = reply_content;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getReply_police_name() {
            return reply_police_name;
        }

        public void setReply_police_name(String reply_police_name) {
            this.reply_police_name = reply_police_name;
        }

        public String getReply_police_phone() {
            return reply_police_phone;
        }

        public void setReply_police_phone(String reply_police_phone) {
            this.reply_police_phone = reply_police_phone;
        }
    }
}
