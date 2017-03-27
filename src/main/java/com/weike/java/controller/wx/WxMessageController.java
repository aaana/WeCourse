package com.weike.java.controller.wx;

import com.weike.java.entity.wx.WxMessage;
import com.weike.java.entity.wx.WxMessageCell;
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
import java.util.List;
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
                List<WxMessageCell> wxMessageCells = wxMessageService.getMessageWithUserId(id);

                map.put("messages", wxMessageCells);
                map.put("result", "success");
            }
        }
        return map;
    }

    //阅读某条消息
    @RequestMapping(value = "/message/{message_id}", method = RequestMethod.POST)
    public Map<String,Object> changMessageState(@PathVariable String message_id, HttpServletRequest request) throws Exception {
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
                WxMessage wxMessage = wxMessageService.getSimpleMessageWithId(Integer.parseInt(message_id));
                if (id == wxMessage.getReceiver_id()) {
                    wxMessageService.readMessage(Integer.parseInt(message_id));
                    map.put("result", "success");
                } else {
                    map.put("result", "fail");
                }
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
                List<WxMessageCell> wxMessageCells = wxMessageService.getUnreadMessageWithUserId(id);

                map.put("messages", wxMessageCells);
                map.put("result", "success");
            }
        }
        return map;
    }

    //获取未读消息数量
    @RequestMapping(value = "/unreadMessages/num", method = RequestMethod.GET)
    public Map<String,Object> getUnreadMessagesNum(HttpServletRequest request) throws Exception {
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
                int num = wxMessageService.getUnreadMessageNum(id);

                map.put("unreadMessageNum", num);
                map.put("result", "success");
            }
        }
        return map;
    }
}
