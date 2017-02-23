package com.weike.java.entity;

import java.sql.Timestamp;

/**
 * Created by tina on 2/17/17.
 */
public class WeikeCell extends Weike {
    private String user_name;
    private String user_avatar;
    private String thumbnail_url;
    private String thumbnail_size;
    private int file_type;
    private String file_url;
    private boolean starred;

    public WeikeCell(Weike weike) {
        super(weike.getTitle(), weike.getSubject(), weike.getUser_id(), weike.getDescription(), weike.getPost_date(),
            weike.getFile_id(), weike.getThumbnail_id(), weike.getView_num(), weike.getStar_num(), weike.getComment_num());
        this.setId(weike.getId());
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

    public String getUser_avatar() {
        return user_avatar;
    }

    public String getThumbnail_size() {
        return thumbnail_size;
    }

    public void setThumbnail_size(String thumbnail_size) {
        this.thumbnail_size = thumbnail_size;
    }

    public void setUser_avatar(String user_avatar) {

        this.user_avatar = user_avatar;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
}
