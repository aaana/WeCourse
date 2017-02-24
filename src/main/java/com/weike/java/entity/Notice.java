package com.weike.java.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by tina on 2/20/17.
 */
@Entity
@Table
public class Notice implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    private int sender_id;
    private int receiver_id;
    private int notice_type;
    // 1:Favorite trigger_id -- favorite_id ; target_id -- weike_id
    // 2:Follow trigger_id -- follow_id ; target_id -- user_id
    // 3:Comment trigger_id -- comment_id ; target_id -- comment_id (parent)
    // 4:Comment(reply) trigger_id -- comment_id ; target_id -- comment_id
    private Timestamp notice_time;
    private int trigger_id;
    private int target_id;
    private boolean hasread;

    public Notice() {
    }

    public Notice(int sender_id, int receiver_id, int notice_type, Timestamp notice_time, int trigger_id, int target_id, boolean hasread) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.notice_type = notice_type;
        this.notice_time = notice_time;
        this.trigger_id = trigger_id;
        this.target_id = target_id;
        this.hasread = hasread;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public int getNotice_type() {
        return notice_type;
    }

    public void setNotice_type(int notice_type) {
        this.notice_type = notice_type;
    }

    public int getTrigger_id() {
        return trigger_id;
    }

    public void setTrigger_id(int trigger_id) {
        this.trigger_id = trigger_id;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public boolean isHasread() {
        return hasread;
    }

    public void setHasread(boolean hasread) {
        this.hasread = hasread;
    }

    public Timestamp getNotice_time() {
        return notice_time;
    }

    public void setNotice_time(Timestamp notice_time) {
        this.notice_time = notice_time;
    }
}
