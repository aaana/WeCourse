package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tina on 2/23/17.
 */
@Entity
@Table
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private int publisher_id;
    private int weike_id;
    private Timestamp publish_time;
    private String content;
    private int parent_id;

    public Comment() {
    }

    public Comment(int publisher_id, int weike_id, Timestamp publish_time, String content, int parent_id) {
        this.publisher_id = publisher_id;
        this.weike_id = weike_id;
        this.publish_time = publish_time;
        this.content = content;
        this.parent_id = parent_id;
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

    public int getWeike_id() {
        return weike_id;
    }

    public void setWeike_id(int weike_id) {
        this.weike_id = weike_id;
    }

    public Timestamp getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(Timestamp publish_time) {
        this.publish_time = publish_time;
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
}
