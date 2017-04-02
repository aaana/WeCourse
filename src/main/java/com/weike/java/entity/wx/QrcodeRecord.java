package com.weike.java.entity.wx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by tina on 4/2/17.
 */
@Entity
@Table
public class QrcodeRecord {
    @Id
    @GeneratedValue
    private int id;

    private String src;
    private Timestamp create_time;
    private Timestamp expire_time;
    private int course_id;

    public QrcodeRecord() {
    }

    public QrcodeRecord(String src, Timestamp create_time, Timestamp expire_time, int course_id) {
        this.src = src;
        this.create_time = create_time;
        this.expire_time = expire_time;
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public Timestamp getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(Timestamp expire_time) {
        this.expire_time = expire_time;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }
}
