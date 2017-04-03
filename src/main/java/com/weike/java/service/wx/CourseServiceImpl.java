package com.weike.java.service.wx;

import com.weike.java.DAO.UserDAO;
import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.DAO.wx.QrcodeRecordDAO;
import com.weike.java.DAO.wx.StuCouDAO;
import com.weike.java.entity.Follow;
import com.weike.java.entity.User;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.CourseCell;
import com.weike.java.entity.wx.QrcodeRecord;
import com.weike.java.entity.wx.StuCou;
import com.weike.java.util.TimestampUtil;
import com.weike.java.util.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.weixin4j.WeixinException;

import java.io.File;
import java.sql.Timestamp;
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

    @Autowired
    private QrcodeRecordDAO qrcodeRecordDAO;

    public Course createCourse(Course course) {
        course.setId(courseDAO.save(course));
        return course;
    }

    public Course closeCourse(Course course) {
        course.setAvailable(false);
        courseDAO.updateCourseInfo(course);
        return course;
    }

    public CourseCell getCourseByCourseId(int id, int current_user_id) {
        return transCourse2CourseCell(courseDAO.findCourseById(id), current_user_id);
    }

    public Course getSimpleCourseByCourseId(int id) {
        return courseDAO.findCourseById(id);
    }

    public List<CourseCell> getCoursesByCourseName(String course_name, int current_user_id) {
        List<Course> courses = courseDAO.findCoursesByCourseName(course_name);
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        for (Course course : courses) {
            courseCells.add(transCourse2CourseCell(course, current_user_id));
        }
        return courseCells;
    }

    public List<CourseCell> getCoursesByTeacherName(String teacher_name, int current_user_id) {
        List<Course> courses = courseDAO.findCoursesByTeacherName(teacher_name);
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        for (Course course : courses) {
            courseCells.add(transCourse2CourseCell(course, current_user_id));
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
            if (course == null) {
                continue;
            }
            CourseCell courseCell = transCourse2CourseCell(course);
            courseCell.setUnread_num(stuCou.getUnread_num());
            courseCells.add(courseCell);
        }
        return courseCells;
    }

    public CourseCell joinNewCourse(int user_id, int course_id) {
        StuCou stuCou = new StuCou(user_id, course_id, 0, 0, 0);
        stuCouDAO.save(stuCou);
        CourseCell courseCell = transCourse2CourseCell(courseDAO.findCourseById(course_id));
        if (courseCell == null) {
            return null;
        }
        courseCell.setStu_num(courseCell.getStu_num() + 1);
        courseDAO.updateCourseInfo(courseCell);
        return courseCell;
    }

    public String increaseAttendance(int user_id, int course_id, String path) throws WeixinException {
        Course course = courseDAO.findCourseById(course_id);
        if (course != null && user_id == course.getUser_id()) {

            // new qrcode record
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp expireTime = TimestampUtil.addTimestemp(now, 60);
            QrcodeRecord qrcodeRecord = new QrcodeRecord("", now, expireTime, course_id);
            int qid = qrcodeRecordDAO.save(qrcodeRecord);

            // generate qrcode and save
            String fileName = "course" + course_id + "_qrcode" + qid + ".jpg";
            String filePath = path + File.separator + fileName;
            WeixinUtil weixinUtil = new WeixinUtil();
            boolean result = weixinUtil.generateQRCode("pages/courses", filePath);

            if (result) {
                // database update

                qrcodeRecord.setSrc(fileName);
                qrcodeRecordDAO.updateSrc(qrcodeRecord);

                course.setAttendance_num(course.getAttendance_num() + 1);
                course.setQrcode_id(qid);
                courseDAO.updateCourseInfo(course);
                return fileName;
            }
        }
        return null;
    }

    public int attendCourse(int user_id, int course_id) {
        // check last qrcode record
        Course course = courseDAO.findCourseById(course_id);
        int qid = course.getQrcode_id();
        QrcodeRecord q = qrcodeRecordDAO.findQrcodeRecordById(qid);

        StuCou stuCou = stuCouDAO.findAllStuCouWithCourseIdAndUserId(course_id, user_id);
        int useQId = stuCou.getUse_qrcode_id();
        QrcodeRecord useQ = qrcodeRecordDAO.findQrcodeRecordById(useQId);

        if (useQId == qid) {
            // 已签到过
            return 1;
        } else {
            Timestamp qExpireTime = q.getExpire_time();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            if (TimestampUtil.compareTimestamp(qExpireTime, now) != 1) {
                stuCou.setAttendance(stuCou.getAttendance() + 1);
                stuCou.setUse_qrcode_id(qid);
                stuCouDAO.updateAttendance(stuCou);
                return 0;
            }
            // 过期
            return 2;
        }
    }

    public CourseCell transCourse2CourseCell(Course course) {
        if (course == null) {
            return null;
        }
        CourseCell courseCell = new CourseCell(course);
        User user = userDAO.findUserWithId(course.getUser_id());
        courseCell.setTeacher_name(user.getName());
        courseCell.setHasJoined(true);
        courseCell.setUnread_num(0);
        return  courseCell;
    }

    public CourseCell transCourse2CourseCell(Course course, int currentUser_id) {
        if (course == null) {
            return null;
        }
        CourseCell courseCell = new CourseCell(course);
        User user = userDAO.findUserWithId(course.getUser_id());
        courseCell.setTeacher_name(user.getName());
        StuCou stuCou = stuCouDAO.findAllStuCouWithCourseIdAndUserId(courseCell.getId(), currentUser_id);
        if (stuCou == null) {
            courseCell.setHasJoined(false);
            courseCell.setUnread_num(0);
        } else {
            courseCell.setHasJoined(true);
            courseCell.setUnread_num(stuCou.getUnread_num());
        }
        return  courseCell;
    }

    public CourseCell transCourse2CourseCell(Course course, String teacher_name) {
        if (course == null) {
            return null;
        }
        CourseCell courseCell = new CourseCell(course);
        courseCell.setTeacher_name(teacher_name);
        courseCell.setHasJoined(true);
        return  courseCell;
    }

}
