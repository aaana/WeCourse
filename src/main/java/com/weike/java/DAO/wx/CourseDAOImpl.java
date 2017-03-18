package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.Course;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 3/18/17.
 */
@Repository
public class CourseDAOImpl implements CourseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(Course course) {
        return 0;
    }

    public Course findCourseById(int id) {
        return null;
    }

    public List<Course> findCoursesByCourseName(String course_name) {
        return null;
    }

    public List<Course> findCoursesByTeacherName(String teacher_name) {
        return null;
    }

    public Boolean updateCourseInfo(Course course) {
        return null;
    }
}
