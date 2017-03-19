package com.weike.java.controller.wx;

import com.weike.java.entity.User;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.CourseCell;
import com.weike.java.entity.wx.WxUser;
import com.weike.java.service.wx.CourseService;
import com.weike.java.service.wx.WxUserService;
import com.weike.java.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 3/19/17.
 */
@RestController
@RequestMapping("/wx")
public class WxCourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private WxUserService wxUserService;

    // 获取全部/搜索 课程
    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public Map<String,Object> findCourses(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String keyWord = request.getParameter("keyWord");
        String searchField = request.getParameter("searchField");

        Map<String,Object> map = new HashMap<String, Object>();
        if (token == null) {
            map.put("result", "fail");
        } else {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = jwtUtil.translateSubject(jwtUtil.parseJWT(token));

            if (subject.get("id") != null ) {
                int id = Integer.parseInt(subject.get("id").toString());
                WxUser u = wxUserService.findUserById(id);
                List<CourseCell> courseCells;
                // 只有学生可以搜索
                if (u.getType() == 1 && keyWord != null && keyWord != "" && searchField != null && searchField != "") {
                    courseCells = searchCourses(u, keyWord, searchField);
                } else {
                    courseCells = findAllCourses(u);
                }
                map.put("user", u);
                map.put("courses", courseCells);
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }
        return map;
    }

    // 获取某个 课程
    @RequestMapping(value = "/course/{course_id}", method = RequestMethod.GET)
    public Map<String,Object> findCertainCourse(@PathVariable String course_id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        Map<String,Object> map = new HashMap<String, Object>();
        if (token == null) {
            map.put("result", "fail");
        } else {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = jwtUtil.translateSubject(jwtUtil.parseJWT(token));

            if (subject.get("id") != null ) {
                int id = Integer.parseInt(subject.get("id").toString());
                WxUser u = wxUserService.findUserById(id);

                CourseCell courseCell = courseService.getCourseByCourseId(Integer.parseInt(course_id));

                map.put("user", u);
                map.put("course", courseCell);
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }
        return map;
    }

    private List<CourseCell> findAllCourses(WxUser u) {
        List<CourseCell> courseCells;
        if (u.getType() == 0) {
            courseCells = courseService.getCoursesByTeacherId(u.getId());
        } else {
            courseCells = courseService.getCoursesByStuId(u.getId());
        }
        return courseCells;
    }

    private List<CourseCell> searchCourses(WxUser u, String keyWord, String searchField) {
        List<CourseCell> courseCells = new LinkedList<CourseCell>();
        if (searchField.equals("id")) {
            int key = Integer.parseInt(keyWord);
            CourseCell course = courseService.getCourseByCourseId(key);
            courseCells.add(course);
        } else {
            courseCells = courseService.getCoursesByCourseName(keyWord);
        }
        return courseCells;
    }

    // 新建课程
    @RequestMapping(value = "/course", method = RequestMethod.POST)
    public Map<String,Object> createCourse(HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String course_name = request.getParameter("courseName");

        Map<String,Object> map = new HashMap<String, Object>();
        if (token == null) {
            map.put("result", "fail");
        } else {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = jwtUtil.translateSubject(jwtUtil.parseJWT(token));

            if (subject.get("id") != null ) {
                int id = Integer.parseInt(subject.get("id").toString());
                WxUser u = wxUserService.findUserById(id);

                Course course = new Course(u.getId(), course_name,  new Timestamp(System.currentTimeMillis()), 0, 0, true);
                CourseCell courseCell = courseService.transCourse2CourseCell(courseService.createCourse(course), u.getName());

                map.put("user", u);
                map.put("course", courseCell);
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }
        return map;
    }

    // 关闭课程
    @RequestMapping(value = "/course/{course_id}/deletion", method = RequestMethod.POST)
    public Map<String,Object> deleteCourse(@PathVariable String course_id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        Map<String,Object> map = new HashMap<String, Object>();
        if (token == null) {
            map.put("result", "fail");
        } else {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = jwtUtil.translateSubject(jwtUtil.parseJWT(token));

            if (subject.get("id") == null) {
                map.put("result", "fail");
            } else {
                int id = Integer.parseInt(subject.get("id").toString());

                CourseCell course = courseService.getCourseByCourseId(Integer.parseInt(course_id));
                if (course.getUser_id() == id) {
                    courseService.closeCourse(course);
                    map.put("result", "success");
                } else {
                    map.put("result", "fail");
                }
            }
        }
        return map;
    }

    // 参加课程
    @RequestMapping(value = "/course/{course_id}/joining", method = RequestMethod.POST)
    public Map<String,Object> joinCourse(@PathVariable String course_id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        Map<String,Object> map = new HashMap<String, Object>();
        if (token == null) {
            map.put("result", "fail");
        } else {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = jwtUtil.translateSubject(jwtUtil.parseJWT(token));

            if (subject.get("id") == null) {
                map.put("result", "fail");
            } else {
                int id = Integer.parseInt(subject.get("id").toString());
                int courseId = Integer.parseInt(course_id);
                CourseCell courseCell = courseService.joinNewCourse(id, courseId);
                map.put("course", courseCell);
                map.put("result", "success");
            }
        }
        return map;
    }

    // 签到课程
    @RequestMapping(value = "/course/{course_id}/attendance", method = RequestMethod.POST)
    public Map<String,Object> attendCourse(@PathVariable String course_id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");

        Map<String,Object> map = new HashMap<String, Object>();
        if (token == null) {
            map.put("result", "fail");
        } else {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = jwtUtil.translateSubject(jwtUtil.parseJWT(token));

            if (subject.get("id") == null) {
                map.put("result", "fail");
            } else {
                int id = Integer.parseInt(subject.get("id").toString());
                int courseId = Integer.parseInt(course_id);
                User user = wxUserService.findUserById(id);

                if (user.getType() == 0 && courseService.increaseAttendance(id, courseId)) {
                    map.put("result", "success");
                } else if (courseService.attendCourse(id, courseId)) {
                    map.put("result", "success");
                } else {
                    map.put("result", "fail");
                }
            }
        }
        return map;
    }
}
