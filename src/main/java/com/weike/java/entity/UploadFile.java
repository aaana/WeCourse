package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by tina on 2/13/17.
 */

@Entity
@Table
public class UploadFile implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private int type;
    private String url;

    public UploadFile() {
    }

    public UploadFile(int type, String url) {
        this.type = type;
        this.url = url;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
