package com.weike.java.controller.wx;

import com.weike.java.entity.User;
import com.weike.java.entity.wx.CourseCell;
import com.weike.java.entity.wx.WxUser;
import com.weike.java.service.wx.CourseService;
import com.weike.java.service.wx.WxUserService;
import com.weike.java.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public Map<String,Object> checkAccountExist(HttpServletRequest request) throws Exception {
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
                List<CourseCell> courseCells;

                if (u.getType() == 0) {
                    courseCells = courseService.getCoursesByTeacherId(u.getId());
                } else {
                    courseCells = courseService.getCoursesByStuId(u.getId());
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
}
