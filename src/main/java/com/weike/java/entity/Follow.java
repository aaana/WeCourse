package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by tina on 2/21/17.
 */
@Entity
@Table
public class Follow {
    @Id
    @GeneratedValue
    private int id;

    private int follower_id;
    private int following_id;
    private Timestamp follow_time;
    private Boolean valid;

    public Follow() {
    }

    public Follow(int follower_id, int following_id, Timestamp follow_time, Boolean valid) {
        this.follower_id = follower_id;
        this.following_id = following_id;
        this.follow_time = follow_time;
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(int follower_id) {
        this.follower_id = follower_id;
    }

    public int getFollowing_id() {
        return following_id;
    }

    public void setFollowing_id(int following_id) {
        this.following_id = following_id;
    }

    public Timestamp getFollow_time() {
        return follow_time;
    }

    public void setFollow_time(Timestamp follow_time) {
        this.follow_time = follow_time;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
