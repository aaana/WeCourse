package com.weike.java.entity;

import java.sql.Timestamp;

/**
 * Created by tina on 2/23/17.
 */
public class NoticeCell extends Notice {
    private String sender_name;
    private String sender_avatar;
    // favorite
    private String weike_title;
    // comment
    private int comment_weike_id;
    private String comment_content;
    private String comment_target;

    public NoticeCell(Notice notice) {
        super(notice.getSender_id(), notice.getReceiver_id(),
                notice.getNotice_type(), notice.getNotice_time(),
                notice.getTrigger_id(), notice.getTarget_id(), notice.isHasread());
        this.setId(notice.getId());
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_avatar() {
        return sender_avatar;
    }

    public void setSender_avatar(String sender_avatar) {
        this.sender_avatar = sender_avatar;
    }

    public String getWeike_title() {
        return weike_title;
    }

    public void setWeike_title(String weike_title) {
        this.weike_title = weike_title;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_target() {
        return comment_target;
    }

    public int getComment_weike_id() {
        return comment_weike_id;
    }

    public void setComment_weike_id(int comment_weike_id) {
        this.comment_weike_id = comment_weike_id;
    }

    public void setComment_target(String comment_target) {
        this.comment_target = comment_target;
    }
}
