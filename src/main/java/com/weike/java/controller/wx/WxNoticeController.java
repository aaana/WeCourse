package com.weike.java.controller.wx;

import com.weike.java.entity.wx.*;
import com.weike.java.service.wx.CourseService;
import com.weike.java.service.wx.WxNoticeService;
import com.weike.java.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 3/19/17.
 */
@RestController
@RequestMapping("/wx")
public class WxNoticeController {
    @Autowired
    private WxNoticeService wxNoticeService;

    @Autowired
    private CourseService courseService;

    // 获取通知
    @RequestMapping(value = "/course/{course_id}/notice", method = RequestMethod.GET)
    public Map<String,Object> getAllNotices(@PathVariable String course_id, HttpServletRequest request) throws Exception {
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
                List<WxNoticeCell> wxNoticeCells = wxNoticeService.getAllNoticesWithCourseId(Integer.parseInt(course_id));
                map.put("notices", wxNoticeCells);
                map.put("result", "success");
            }
        }
        return map;
    }

    // 新建通知
    @RequestMapping(value = "/course/{course_id}/notice", method = RequestMethod.POST)
    public Map<String,Object> newNotice(@PathVariable String course_id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String content = request.getParameter("content");

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
                Course c = courseService.getSimpleCourseByCourseId(Integer.parseInt(course_id));

                if (id == c.getUser_id()) {
                    WxNotice wxNotice = new WxNotice(content, new Timestamp(System.currentTimeMillis()), Integer.parseInt(course_id));
                    wxNotice = wxNoticeService.createNotice(wxNotice);
                    map.put("notice", wxNotice);
                    map.put("result", "success");
                } else {
                    map.put("result", "fail");
                }
            }
        }
        return map;
    }

}
