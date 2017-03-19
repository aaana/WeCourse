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

    private int user_id;
    private int course_id;
    private int attendance;


    public StuCou() {
    }

    public StuCou(int user_id, int course_id, int attendance) {
        this.user_id = user_id;
        this.course_id = course_id;
        this.attendance = attendance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
