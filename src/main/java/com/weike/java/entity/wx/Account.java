package com.weike.java.entity.wx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tina on 3/14/17.
 */
@Entity
@Table
public class Account {
    @Id
    @GeneratedValue
    private int id;
    private String wechat_id;
    private int wecourse_id;
    private int student_id;

    public Account() {
    }

    public Account(String wechat_id, int wecourse_id, int student_id) {
        this.wechat_id = wechat_id;
        this.wecourse_id = wecourse_id;
        this.student_id = student_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public int getWecourse_id() {
        return wecourse_id;
    }

    public void setWecourse_id(int wecourse_id) {
        this.wecourse_id = wecourse_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}