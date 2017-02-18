package com.weike.java.entity;

import java.sql.Timestamp;

/**
 * Created by tina on 2/17/17.
 */
public class WeikeCell extends Weike {
    private String user_name;
    private String thumbnail_url;
    private int file_type;
    private String file_url;

    public WeikeCell(String title, String subject, int user_id, String description, Timestamp post_date, int file_id, int thumbnail_id, int star_num, int comment_num) {
        super(title, subject, user_id, description, post_date, file_id, thumbnail_id, star_num, comment_num);
    }

    public WeikeCell(Weike weike) {
        this.setId(weike.getId());
        this.setTitle(weike.getTitle());
        this.setSubject(weike.getSubject());
        this.setUser_id(weike.getUser_id());
        this.setDescription(weike.getDescription());
        this.setPost_date(weike.getPost_date());
        this.setFile_id(weike.getFile_id());
        this.setThumbnail_id(weike.getThumbnail_id());
        this.setStar_num(weike.getStar_num());
        this.setComment_num(weike.getComment_num());
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public int getFile_type() {
        return file_type;
    }

    public void setFile_type(int file_type) {
        this.file_type = file_type;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
