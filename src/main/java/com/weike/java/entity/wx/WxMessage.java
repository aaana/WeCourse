package com.weike.java.entity.wx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by tina on 3/27/17.
 */
@Entity
@Table
public class WxMessage {
    @Id
    @GeneratedValue
    private int id;

    private int sender_id;
    private int receiver_id;
    private int message_type;
    // 1:老师收到当有人在他的课中匿名提问 trigger_id -- question_id ; target_id --
    // 2:提问者收到当有人在他的提问下回答 trigger_id -- question_id ; target_id --
    // 3:发言者收到当有人回复他的回答 trigger_id -- question_id ; target_id --
    private Timestamp init_time;
    private int trigger_id;
    private boolean hasread;

    public WxMessage() {
    }

    public WxMessage(int sender_id, int receiver_id, int message_type, Timestamp init_time, int trigger_id, boolean hasread) {
        this.sender_id = sender_id;
        this.receiver_id = receiver_id;
        this.message_type = message_type;
        this.init_time = init_time;
        this.trigger_id = trigger_id;
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

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public Timestamp getInit_time() {
        return init_time;
    }

    public void setInit_time(Timestamp init_time) {
        this.init_time = init_time;
    }

    public int getTrigger_id() {
        return trigger_id;
    }

    public void setTrigger_id(int trigger_id) {
        this.trigger_id = trigger_id;
    }

    public boolean isHasread() {
        return hasread;
    }

    public void setHasread(boolean hasread) {
        this.hasread = hasread;
    }
}
