package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tina on 2/16/17.
 */
@Entity
@Table
public class Weike implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private String title;
    private String subject;
    private int user_id;
    private String description;
    private Timestamp post_date;
    private int file_id;
    private int thumbnail_id;
    private int view_num;
    private int star_num;
    private int comment_num;

    public Weike() {
    }

    public Weike(String title, String subject, int user_id, String description, Timestamp post_date, int file_id, int thumbnail_id, int view_num, int star_num, int comment_num) {
        this.title = title;
        this.subject = subject;
        this.user_id = user_id;
        this.description = description;
        this.post_date = post_date;
        this.file_id = file_id;
        this.thumbnail_id = thumbnail_id;
        this.view_num = view_num;
        this.star_num = star_num;
        this.comment_num = comment_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getPost_date() {
        return post_date;
    }

    public void setPost_date(Timestamp post_date) {
        this.post_date = post_date;
    }

    public int getFile_id() {
        return file_id;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
    }

    public int getThumbnail_id() {
        return thumbnail_id;
    }

    public void setThumbnail_id(int thumbnail_id) {
        this.thumbnail_id = thumbnail_id;
    }


    public int getView_num() {
        return view_num;
    }

    public void setView_num(int view_num) {
        this.view_num = view_num;
    }

    public int getStar_num() {
        return star_num;
    }

    public void setStar_num(int star_num) {
        this.star_num = star_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }
}
