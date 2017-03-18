package com.weike.java.entity.wx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by tina on 3/18/17.
 */
@Entity
@Table
public class WxNotice {
    @Id
    @GeneratedValue
    private int id;

    private String content;
    private Timestamp publish_time;
    private int course_id;

    public WxNotice() {
    }

    public WxNotice(String content, Timestamp publish_time, int course_id) {
        this.content = content;
        this.publish_time = publish_time;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Timestamp publish_time) {
        this.publish_time = publish_time;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
