package com.weike.java.controller;

import com.weike.java.entity.User;
import com.weike.java.entity.UserCell;
import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;
import com.weike.java.service.UserService;
import com.weike.java.service.WeikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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

    @RequestMapping("/{user_id}")
    public String upload(@PathVariable String user_id, Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<WeikeCell> weikeCells = null;
        User visiting = null;
        List<UserCell> followings = null;
        List<UserCell> commonFollowings = null;
        List<WeikeCell> favorites = null;
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            userCell = userService.findUserById(userCell.getId());

            model.addAttribute("user", userCell);
            weikeCells = weikeService.findWeikesWithUserId(Integer.parseInt(user_id), userCell.getId());
            visiting = userService.findUserById(Integer.parseInt(user_id), userCell.getId());
            followings = userService.findFollowedUserWithUserid(Integer.parseInt(user_id), userCell.getId());
            favorites = weikeService.findFavoriteWeikesWithUserId(Integer.parseInt(user_id), userCell.getId());
            commonFollowings = userService.findCommonFollowings(Integer.parseInt(user_id), userCell.getId());
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
        model.addAttribute("commonFollowings", commonFollowings);
        return "personalPage";
    }
}
