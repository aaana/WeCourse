package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.awt.image.TileObserver;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tina on 2/19/17.
 */
@Entity
@Table
public class Favorite implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private int weike_id;
    private int user_id;
    private Timestamp favorite_time;
    private Boolean valid;

    public Favorite() {
    }

    public Favorite(int weike_id, int user_id, Timestamp favorite_time, Boolean valid) {
        this.weike_id = weike_id;
        this.user_id = user_id;
        this.favorite_time = favorite_time;
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeike_id() {
        return weike_id;
    }

    public void setWeike_id(int weike_id) {
        this.weike_id = weike_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Timestamp getFavorite_time() {
        return favorite_time;
    }

    public void setFavorite_time(Timestamp favorite_time) {
        this.favorite_time = favorite_time;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
