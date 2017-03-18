package com.weike.java.service.wx;

import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.CourseCell;
import com.weike.java.entity.wx.WxUser;

import java.util.List;

/**
 * Created by tina on 3/18/17.
 */
public interface CourseService {
    public int createCourse(Course course);
    public Boolean closeCourse(int course_id);
    public CourseCell getCourseByCourseId(int id);
    public List<CourseCell> getCoursesByCourseName(String course_name);
    public List<CourseCell> getCoursesByTeacherName(String teacher_name);

    public CourseCell joinNewCourse(int user_id, int course_id);
}
