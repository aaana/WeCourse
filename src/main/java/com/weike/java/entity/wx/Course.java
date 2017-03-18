package com.weike.java.entity.wx;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by tina on 3/18/17.
 */
@Entity
@Table
public class Course {
    @Id
    @GeneratedValue
    private int id;
    private int account_id;
    private String course_name;
    private Timestamp create_time;
    private int stu_num;
    private int attendance_num;
    private Boolean available;

    public Course() {
    }

    public Course(int account_id, String course_name, Timestamp create_time, int stu_num, int attendance_num, Boolean available) {
        this.account_id = account_id;
        this.course_name = course_name;
        this.create_time = create_time;
        this.stu_num = stu_num;
        this.attendance_num = attendance_num;
        this.available = available;
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

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getStu_num() {
        return stu_num;
    }

    public void setStu_num(int stu_num) {
        this.stu_num = stu_num;
    }

    public int getAttendance_num() {
        return attendance_num;
    }

    public void setAttendance_num(int attendance_num) {
        this.attendance_num = attendance_num;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
