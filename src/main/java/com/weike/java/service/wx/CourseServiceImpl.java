package com.weike.java.service.wx;

import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.CourseCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tina on 3/18/17.
 */
@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDAO courseDAO;

    public int createCourse(Course course) {
        return 0;
    }

    public Boolean closeCourse(int course_id) {
        return null;
    }

    public CourseCell getCourseByCourseId(int id) {
        return null;
    }

    public List<CourseCell> getCoursesByCourseName(String course_name) {
        return null;
    }

    public List<CourseCell> getCoursesByTeacherName(String teacher_name) {
        return null;
    }

    public CourseCell joinNewCourse(int user_id, int course_id) {
        return null;
    }

    public CourseCell transCourse2CourseCell(Course course) {
        CourseCell courseCell = new CourseCell(course);
        courseCell.setTeacher_name("");
        return  courseCell;
    }

    public CourseCell transCourse2CourseCell(Course course, String teacher_name) {
        CourseCell courseCell = new CourseCell(course);
        courseCell.setTeacher_name(teacher_name);
        return  courseCell;
    }

}
