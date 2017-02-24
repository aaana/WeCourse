package com.weike.java.controller;

import com.weike.java.entity.NoticeCell;
import com.weike.java.entity.UserCell;
import com.weike.java.service.NoticeService;
import com.weike.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 2/24/17.
 */
@Controller
@RequestMapping("/")
public class ReminderPageController {

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/reminder")
    public String gotoReminderPage(Model model, HttpServletRequest request){
        int page_num = request.getParameter("page_num") == null? 0 : Integer.parseInt(request.getParameter("page_num"));
        Boolean unread = request.getParameter("unread") == null? true : Boolean.getBoolean(request.getParameter("unread"));
        model.addAttribute("pageNum", page_num);
        model.addAttribute("unread", unread);

        HttpSession session = request.getSession();
        List<NoticeCell> noticeCells = null;
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            userCell = userService.findUserById(userCell.getId());
            session.setAttribute("user", userCell);
            model.addAttribute("messageNum", noticeService.getUnreadNoticeNumList(userCell.getId()));
            model.addAttribute("user", userCell);
        }
        return "reviewPage";
    }

    @RequestMapping("/notices")
    public @ResponseBody
    Map<String,Object> getNotices(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            List<NoticeCell> noticeCells = noticeService.getNoticeWithUserId(userCell.getId());
            map.put("noticeCells", noticeCells);
        }
        return map;
    }

    @RequestMapping("/watchNotice")
    public @ResponseBody
    Map<String,Object> watchNotice(HttpServletRequest request) {
        int noticeId = Integer.parseInt(request.getParameter("noticeId"));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result", noticeService.readNotce(noticeId));
        return map;
    }
}
