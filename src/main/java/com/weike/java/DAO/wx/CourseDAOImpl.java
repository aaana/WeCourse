package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.Course;
import org.hibernate.Query;
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
        return (Integer) sessionFactory.getCurrentSession().save(course);
    }

    public Course findCourseById(int id) {
        return (Course) sessionFactory.getCurrentSession().createQuery("from Course where id=? and available = true").setParameter(0, id).uniqueResult();

    }

    public List<Course> findCoursesByCourseName(String course_name) {
        return (List<Course>) sessionFactory.getCurrentSession().createQuery("from Course where course_name like ? and available = true").setParameter(0, "%" + course_name + "%").list();
    }

    public List<Course> findCoursesByTeacherName(String teacher_name) {
        return (List<Course>) sessionFactory.getCurrentSession().createQuery("SELECT Course.* FROM Course, User WHERE Course.user_id = User.id and User.name LIKE ? and Course.available = true").setParameter(0, "%" + teacher_name + "%").list();
    }

    public List<Course> findCoursesByUserId(int user_id) {
        return (List<Course>) sessionFactory.getCurrentSession().createQuery("FROM Course WHERE user_id = ? and available = true").setParameter(0, user_id).list();
    }

    public Boolean updateCourseInfo(Course course) {
        String hql = "update Course c set c.stu_num = ?, c.attendance_num=?, c.available=? where c.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, course.getStu_num());
        query.setParameter(1, course.getAttendance_num());
        query.setParameter(2, course.getAvailable());
        query.setParameter(3, course.getId());

        return (query.executeUpdate() > 0);
    }
}
