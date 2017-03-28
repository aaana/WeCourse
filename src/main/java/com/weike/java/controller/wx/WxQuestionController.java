package com.weike.java.controller.wx;

import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.entity.wx.*;
import com.weike.java.service.wx.WxMessageService;
import com.weike.java.service.wx.WxQuestionService;
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
 * Created by tina on 3/20/17.
 */
@RestController
@RequestMapping("/wx")
public class WxQuestionController {
    @Autowired
    private WxQuestionService wxQuestionService;

    @Autowired
    private WxMessageService wxMessageService;

    @Autowired
    private CourseDAO courseDAO;

    // 新建提问
    @RequestMapping(value = "/course/{course_id}/question", method = RequestMethod.POST)
    public Map<String,Object> newQuestion(@PathVariable String course_id, HttpServletRequest request) throws Exception {
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
                WxQuestion wxQuestion = new WxQuestion(id, content, new Timestamp(System.currentTimeMillis()), -1, -1, Integer.parseInt(course_id));
                WxQuestionCell wxQuestionCell = wxQuestionService.createQuestion(wxQuestion);
                map.put("question", wxQuestionCell);

                // 当前课程教师收到一条消息
                Course course = courseDAO.findCourseById(Integer.parseInt(course_id));
                WxMessage wxMessage = new WxMessage(id, course.getUser_id(), 1, new Timestamp(System.currentTimeMillis()), wxQuestionCell.getId(), false);
                wxMessageService.saveNotice(wxMessage);

                map.put("result", "success");
            }
        }
        return map;
    }

    // 获取某节课的所有提问
    @RequestMapping(value = "/course/{course_id}/question", method = RequestMethod.GET)
    public Map<String,Object> getAllQuestions(@PathVariable String course_id, HttpServletRequest request) throws Exception {
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
                List<WxQuestionCell> wxQuestionCells = wxQuestionService.getAllQuestionWithCourseId(Integer.parseInt(course_id));
                map.put("questions", wxQuestionCells);
                map.put("result", "success");
            }
        }
        return map;
    }

    // 新建提问的回答
    @RequestMapping(value = "/course/{course_id}/question/{question_id}", method = RequestMethod.POST)
    public Map<String,Object> newAnswer2Question(@PathVariable String course_id, @PathVariable String question_id, HttpServletRequest request) throws Exception {
        String token = request.getHeader("Authorization");
        String content = request.getParameter("content");
        String parent_id = request.getParameter("parent_id");

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

                WxQuestion wxQuestion = new WxQuestion(id, content, new Timestamp(System.currentTimeMillis()), Integer.parseInt(parent_id), Integer.parseInt(question_id), Integer.parseInt(course_id));
                WxQuestionCell wxQuestionCell = wxQuestionService.createQuestion(wxQuestion);
                map.put("question", wxQuestionCell);

                // 当前问题提问者收到一条消息
                wxQuestion = wxQuestionService.getSimpleQuestionWithId(Integer.parseInt(question_id));
                WxMessage wxMessage = new WxMessage(id, wxQuestion.getPublisher_id(), 2, new Timestamp(System.currentTimeMillis()), wxQuestionCell.getId(), false);
                wxMessageService.saveNotice(wxMessage);

                // 当前回复对象收到一条消息
                if (!parent_id.equals("-1")) {
                    WxQuestion wxQuestion2 = wxQuestionService.getSimpleQuestionWithId(Integer.parseInt(parent_id));
                    if (id != wxQuestion.getPublisher_id() && wxQuestion.getPublisher_id() != wxQuestion2.getPublisher_id()) {
                        wxMessage = new WxMessage(id, wxQuestion.getPublisher_id(), 3, new Timestamp(System.currentTimeMillis()), wxQuestionCell.getId(), false);
                        wxMessageService.saveNotice(wxMessage);
                    }
                }

                map.put("result", "success");
            }
        }
        return map;
    }


    // 获取所有某问题的回答
    @RequestMapping(value = "/course/{course_id}/question/{question_id}", method = RequestMethod.GET)
    public Map<String,Object> getAllAnswers2Question(@PathVariable String course_id, @PathVariable String question_id, HttpServletRequest request) throws Exception {
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
                WxQuestionCell wxQuestionCell = wxQuestionService.getQuestionWithId(Integer.parseInt(question_id));
                List<WxQuestionCell> wxAnswerCells = wxQuestionService.getAllQuestionWithFirstQuestionId(Integer.parseInt(question_id), id);
                map.put("question", wxQuestionCell);
                map.put("answers", wxAnswerCells);
                map.put("result", "success");
            }
        }
        return map;
    }

}
