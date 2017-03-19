package com.weike.java.service.wx;

import com.weike.java.DAO.UserDAO;
import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.DAO.wx.StuCouDAO;
import com.weike.java.entity.Follow;
import com.weike.java.entity.User;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.CourseCell;
import com.weike.java.entity.wx.StuCou;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 3/18/17.
 */
@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private StuCouDAO stuCouDAO;

    public Course createCourse(Course course) {
        course.setId(courseDAO.save(course));
        return course;
    }

    public Course closeCourse(Course course) {
        course.setAvailable(false);
        courseDAO.updateCourseInfo(course);
        return course;
    }

    public CourseCell getCourseByCourseId(int id) {
        return transCourse2CourseCell(courseDAO.findCourseById(id));
    }

    public List<CourseCell> getCoursesByCourseName(String course_name) {
        List<Course> courses = courseDAO.findCoursesByCourseName(course_name);
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        for (Course course : courses) {
            courseCells.add(transCourse2CourseCell(course));
        }
        return courseCells;
    }

    public List<CourseCell> getCoursesByTeacherName(String teacher_name) {
        List<Course> courses = courseDAO.findCoursesByTeacherName(teacher_name);
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        for (Course course : courses) {
            courseCells.add(transCourse2CourseCell(course));
        }
        return courseCells;
    }

    public List<CourseCell> getCoursesByTeacherId(int teacher_id) {
        List<Course> courses = courseDAO.findCoursesByUserId(teacher_id);
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        for (Course course : courses) {
            courseCells.add(transCourse2CourseCell(course));
        }
        return courseCells;
    }

    public List<CourseCell> getCoursesByStuId(int user_id) {
        List<StuCou> stuCous = stuCouDAO.findAllStuCouWithUserId(user_id);
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        for (StuCou stuCou : stuCous) {
            Course course = courseDAO.findCourseById(stuCou.getCourse_id());
            courseCells.add(transCourse2CourseCell(course));
        }
        return courseCells;
    }

    public CourseCell joinNewCourse(int user_id, int course_id) {
        StuCou stuCou = new StuCou(user_id, course_id, 0);
        stuCouDAO.save(stuCou);
        CourseCell courseCell = transCourse2CourseCell(courseDAO.findCourseById(course_id));
        courseCell.setStu_num(courseCell.getStu_num() + 1);
        courseDAO.updateCourseInfo(courseCell);
        return courseCell;
    }

    public Boolean increaseAttendance(int user_id, int course_id) {
        Course course = courseDAO.findCourseById(course_id);
        if (user_id == course.getUser_id()) {
            course.setAttendance_num(course.getAttendance_num() + 1);
            courseDAO.updateCourseInfo(course);
            return true;
        }
        return false;
    }

    public Boolean attendCourse(int user_id, int course_id) {
        return stuCouDAO.updateAttendance(user_id, course_id);
    }

    public CourseCell transCourse2CourseCell(Course course) {
        CourseCell courseCell = new CourseCell(course);
        User user = userDAO.findUserWithId(course.getUser_id());
        courseCell.setTeacher_name(user.getName());
        return  courseCell;
    }

    public CourseCell transCourse2CourseCell(Course course, String teacher_name) {
        CourseCell courseCell = new CourseCell(course);
        courseCell.setTeacher_name(teacher_name);
        return  courseCell;
    }

}
