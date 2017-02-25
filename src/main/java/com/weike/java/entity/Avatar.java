package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by tina on 2/25/17.
 */
@Entity
@Table
public class Avatar {
    @Id
    @GeneratedValue
    private int id;

    private String url;

    public Avatar() {
    }

    public Avatar(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
