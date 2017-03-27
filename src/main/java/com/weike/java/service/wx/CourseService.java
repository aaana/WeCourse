package com.weike.java.service.wx;

import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.CourseCell;
import com.weike.java.entity.wx.WxUser;

import java.util.List;

/**
 * Created by tina on 3/18/17.
 */
public interface CourseService {
    // 新建\关闭课程
    public Course createCourse(Course course);
    public Course closeCourse(Course course);

    // 检索课程
    public CourseCell getCourseByCourseId(int id, int current_user_id);
    public Course getSimpleCourseByCourseId(int id);
    public List<CourseCell> getCoursesByCourseName(String course_name, int current_user_id);
    public List<CourseCell> getCoursesByTeacherName(String teacher_name, int current_user_id);

    // 获取课程列表
    public List<CourseCell> getCoursesByTeacherId(int teacher_id);
    public List<CourseCell> getCoursesByStuId(int user_id);

    // 新同学加入课程
    public CourseCell joinNewCourse(int user_id, int course_id);

    // 点到
    public Boolean increaseAttendance(int user_id, int course_id);
    // 签到
    public Boolean attendCourse(int user_id, int course_id);

    public CourseCell transCourse2CourseCell(Course course);
    public CourseCell transCourse2CourseCell(Course course, String teacher_name);
}
