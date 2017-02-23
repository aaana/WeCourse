package com.weike.java.entity;

import java.sql.Timestamp;

/**
 * Created by tina on 2/23/17.
 */
public class CommentCell extends Comment {
    private String publisher_name;
    private String publisher_avatar;

    public CommentCell() {
    }

    public CommentCell(Comment comment) {
        super(comment.getPublisher_id(), comment.getWeike_id(), comment.getPublish_time(), comment.getContent(), comment.getParent_id());
        this.setId(comment.getId());
    }

    public CommentCell(Comment comment, String publisher_name, String publisher_avatar) {
        super(comment.getPublisher_id(), comment.getWeike_id(), comment.getPublish_time(), comment.getContent(), comment.getParent_id());
        this.setId(comment.getId());
        this.publisher_name = publisher_name;
        this.publisher_avatar = publisher_avatar;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getPublisher_avatar() {
        return publisher_avatar;
    }

    public void setPublisher_avatar(String publisher_avatar) {
        this.publisher_avatar = publisher_avatar;
    }
}
