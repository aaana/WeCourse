package com.weike.java.entity.wx;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tina on 3/18/17.
 */
public class CourseCell extends Course {
    private String create_time_string;
    private String update_time_string;
    private String teacher_name;
    private Boolean hasJoined;
    private int unread_num;

    public CourseCell() {
    }

    public CourseCell(Course course) {
        super(course.getUser_id(), course.getCourse_name(), course.getCreate_time(), course.getUpdate_time(), course.getStu_num(), course.getAttendance_num(), course.getAvailable(), course.getQrcode_id());

        this.setId(course.getId());

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            this.create_time_string = sdf.format(this.getCreate_time());
            this.update_time_string = sdf.format(this.getUpdate_time());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCreate_time_string() {
        return create_time_string;
    }

    public void setCreate_time_string(String create_time_string) {
        this.create_time_string = create_time_string;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public Boolean getHasJoined() {
        return hasJoined;
    }

    public void setHasJoined(Boolean hasJoined) {
        this.hasJoined = hasJoined;
    }

    public int getUnread_num() {
        return unread_num;
    }

    public void setUnread_num(int unread_num) {
        this.unread_num = unread_num;
    }

    public String getUpdate_time_string() {
        return update_time_string;
    }

    public void setUpdate_time_string(String update_time_string) {
        this.update_time_string = update_time_string;
    }
}
