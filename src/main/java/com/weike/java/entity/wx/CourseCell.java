package com.weike.java.entity.wx;

/**
 * Created by tina on 3/18/17.
 */
public class CourseCell extends Course {
    private String teacher_name;

    public CourseCell() {
    }

    public CourseCell(Course course) {
        super(course.getUser_id(), course.getCourse_name(), course.getCreate_time(), course.getStu_num(), course.getAttendance_num(), course.getAvailable());
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
}
