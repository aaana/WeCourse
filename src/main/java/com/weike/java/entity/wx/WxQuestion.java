package com.weike.java.entity.wx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tina on 3/18/17.
 */
@Entity
@Table
public class WxQuestion {
    @Id
    @GeneratedValue
    private int id;

    private int publisher_id;
    private String content;
    private int parent_id;
    private int course_id;

    public WxQuestion() {
    }

    public WxQuestion(int publisher_id, String content, int parent_id, int course_id) {
        this.publisher_id = publisher_id;
        this.content = content;
        this.parent_id = parent_id;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
