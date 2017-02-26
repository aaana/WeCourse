package com.weike.java.controller;

import com.weike.java.entity.*;
import com.weike.java.service.NoticeService;
import com.weike.java.service.UserService;
import com.weike.java.service.WeikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 2/17/17.
 */

@Controller
@RequestMapping("/user")
public class PersonalPageController {
    @Autowired
    private WeikeService weikeService;

    @Autowired
    private UserService userService;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/hotWeikes")
    public @ResponseBody
    Map<String,Object> getHotWeikes(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        Map<String,Object> map = new HashMap<String,Object>();

        HttpSession session = request.getSession();
        List<WeikeCell> weikeCells = null;
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            weikeCells = weikeService.findHotWeikesWithUserId(userId, userCell.getId());
        } else {
            weikeCells = weikeService.findHotWeikesWithUserId(userId);
        }
        map.put("hotWeikes", weikeCells);
        return map;
    }
    @RequestMapping("/commonFollowings")
    public @ResponseBody
    Map<String,Object> getCommonFollowings(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        Map<String,Object> map = new HashMap<String,Object>();

        HttpSession session = request.getSession();
        List<UserCell> commonFollowings = null;
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            if (userId == userCell.getId()) {
                map.put("showCF", false);
            } else {
                map.put("showCF", true);
                commonFollowings = userService.findCommonFollowings(userId, userCell.getId());
            }
        } else {
            map.put("showCF", false);
        }
        map.put("commonFollowings", commonFollowings);
        return map;
    }

    @RequestMapping("/{user_id}")
    public String gotoPersonalPage(@PathVariable String user_id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<WeikeCell> weikeCells = null;
        User visiting = null;
        List<UserCell> followings = null;
        List<WeikeCell> favorites = null;
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            userCell = userService.findUserById(userCell.getId());
            session.setAttribute("user", userCell);
            model.addAttribute("user", userCell);
            model.addAttribute("messageNum", noticeService.getUnreadNoticeNumList(userCell.getId()));


            weikeCells = weikeService.findWeikesWithUserId(Integer.parseInt(user_id), userCell.getId());
            visiting = userService.findUserById(Integer.parseInt(user_id), userCell.getId());
            followings = userService.findFollowedUserWithUserid(Integer.parseInt(user_id), userCell.getId());
            favorites = weikeService.findFavoriteWeikesWithUserId(Integer.parseInt(user_id), userCell.getId());
        } else {
            weikeCells = weikeService.findWeikesWithUserId(Integer.parseInt(user_id));
            visiting = userService.findUserById(Integer.parseInt(user_id));
            followings = userService.findFollowedUserWithUserid(Integer.parseInt(user_id));
            favorites = weikeService.findFavoriteWeikesWithUserId(Integer.parseInt(user_id));
        }

        model.addAttribute("visiting", visiting);
        model.addAttribute("weikeCells", weikeCells);
        model.addAttribute("followings", followings);
        model.addAttribute("favorites", favorites);

        int page_num = request.getParameter("page_num") == null? 1 : Integer.parseInt(request.getParameter("page_num"));
        model.addAttribute("pageNum", page_num);
        return "personalPage";
    }
}
