package com.weike.java.controller;

import com.weike.java.entity.User;
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
        if(session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }

        User user = userService.findUserById(Integer.parseInt(user_id));
        model.addAttribute("visiting", user);
        List<WeikeCell> weikeCells = weikeService.findWeikesWithUserId(Integer.parseInt(user_id));
        model.addAttribute("weikeCells", weikeCells);
        return "personalPage";
    }


}
