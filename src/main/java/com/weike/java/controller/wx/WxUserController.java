package com.weike.java.controller.wx;

import com.weike.java.entity.User;
import com.weike.java.entity.wx.WxUser;
import com.weike.java.service.wx.WxUserService;
import com.weike.java.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tina on 3/12/17.
 */
@RestController
@RequestMapping("/wx")
public class WxUserController {
    @Autowired
    private WxUserService wxUserService;

    // 检测该微信号是否已绑定
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public Map<String,Object> checkAccountExist(HttpServletRequest request) throws Exception {
        String wechat_id = request.getParameter("wechat_id");

        User u = wxUserService.checkAccount(wechat_id);

        Map<String,Object> map = new HashMap<String,Object>();
        if (u != null) {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = new HashMap<String,Object>();
            subject.put("id", u.getId());
            subject.put("email", u.getEmail());
            String sub = jwtUtil.generalSubject(subject);
            String accessToken = jwtUtil.createJWT(sub, Long.valueOf(-1));

            map.put("user", u);
            map.put("token", accessToken);
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        return map;
    }

    // 首次登录上传账号密码
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Map<String,Object> login(HttpServletRequest request) throws Exception {
        String email = request.getParameter("userEmail");
        String password = request.getParameter("password");

        User u = wxUserService.login(email, password);

        Map<String,Object> map = new HashMap<String,Object>();
        if (u != null) {
            JwtUtil jwtUtil = new JwtUtil();
            Map<String,Object> subject = new HashMap<String,Object>();
            subject.put("id", u.getId());
            subject.put("email", email);
            String sub = jwtUtil.generalSubject(subject);
            String accessToken = jwtUtil.createJWT(sub, Long.valueOf(-1));

            map.put("user", u);
            map.put("token", accessToken);
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        return map;
    }

    //绑定账号
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public Map<String,Object> setStudentId(HttpServletRequest request) throws Exception {
        String wechat_id = request.getParameter("wechat_id");
        Integer student_id = Integer.parseInt(request.getParameter("student_id"));
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
                u = wxUserService.newAccount(u, wechat_id, student_id);

                map.put("user", u);
                map.put("result", "success");
            } else {
                map.put("result", "fail");
            }
        }

        return map;
    }
}
