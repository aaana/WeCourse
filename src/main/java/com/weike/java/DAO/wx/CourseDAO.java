package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.Course;

import java.util.List;

/**
 * Created by tina on 3/18/17.
 */
public interface CourseDAO {
    public int save(Course course);
    public Course findCourseById(int id);
    public List<Course> findCoursesByCourseName(String course_name);
    public List<Course> findCoursesByTeacherName(String teacher_name);
    public List<Course> findCoursesByUserId(int user_id);
    public Boolean updateCourseInfo(Course course);
}
