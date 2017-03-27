package com.weike.java.controller.wx;

import com.weike.java.entity.wx.WxQuestion;
import com.weike.java.entity.wx.WxQuestionCell;
import com.weike.java.service.wx.WxMessageService;
import com.weike.java.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tina on 3/27/17.
 */
@RestController
@RequestMapping("/wx")
public class WxMessageController {
    @Autowired
    private WxMessageService wxMessageService;

    //获取消息列表
    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public Map<String,Object> getAllMessages(HttpServletRequest request) throws Exception {
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


                map.put("messages", null);
                map.put("result", "success");
            }
        }
        return map;
    }

    //获取未读消息列表
    @RequestMapping(value = "/unreadMessages", method = RequestMethod.GET)
    public Map<String,Object> getAllUnreadMessages(HttpServletRequest request) throws Exception {
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


                map.put("messages", null);
                map.put("result", "success");
            }
        }
        return map;
    }
}
