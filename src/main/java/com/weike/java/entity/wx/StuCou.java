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
public class StuCou {

    @Id
    @GeneratedValue
    private int id;

    private int account_id;
    private int course_id;
    private int attendance;


    public StuCou() {
    }

    public StuCou(int account_id, int course_id, int attendance) {
        this.account_id = account_id;
        this.course_id = course_id;
        this.attendance = attendance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
}
