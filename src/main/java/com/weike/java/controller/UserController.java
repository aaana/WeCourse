package com.weike.java.controller;

import com.weike.java.entity.*;
import com.weike.java.service.FollowService;
import com.weike.java.service.NoticeService;
import com.weike.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 2/17/17.
 */
@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/checkEmailUsed")
    public @ResponseBody Map<String,Object> checkEmailUsed(HttpServletRequest request) {
        String email = request.getParameter("email");
        Boolean used = userService.checkEmailUsed(email);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("used", used);
        return map;
    }

    @RequestMapping("/signupUser")
    public String signup(HttpServletRequest request) {
        String email = request.getParameter("email");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        int type = Integer.parseInt(request.getParameter("usertype"));

        User user = new User(name, email, password, type, "", "", "");
        int id = userService.signup(user);
        UserCell userCell = userService.findUserById(id);

        HttpSession session = request.getSession();
        session.setAttribute("user", userCell);
        return "redirect:/";
    }

    @RequestMapping("/loginUser")
    public @ResponseBody Map<String,Object> login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserCell userCell = userService.login(email, password);
        Map<String,Object> map = new HashMap<String,Object>();
        if (userCell != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userCell);
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        return map;
    }

    @RequestMapping("/editUserInfo")
    public String gotoEditPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null) {
            UserCell userCell = (UserCell) session.getAttribute("user");
            userCell = userService.findUserById(userCell.getId());

            model.addAttribute("user", userCell);
        }
        return "editInfoPage";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("user") != null) {
            session.setAttribute("user", null);
        }
        return "redirect:/";
    }

    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(Model model, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        UserCell userCell = null;
        if(session.getAttribute("user") != null) {
            userCell = (UserCell) session.getAttribute("user");
        }
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String school = request.getParameter("school");
        String introduction = request.getParameter("introduction");
        userCell.setName(name);
        userCell.setEmail(email);
        userCell.setSchool(school);
        userCell.setIntroduction(introduction);

        String fileName = "";
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                String fieldName = iter.next();
                MultipartFile file = multiRequest.getFile(fieldName);
                if (fieldName.equals("avatar") && file != null) {
                    fileName = file.getOriginalFilename();
                    if (fileName.trim() != "") {
                        String directoryPath = request.getSession().getServletContext().getRealPath("/resource/img");
                        File directory = new File(directoryPath);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }
                        File uploadedFile = new File(directoryPath + File.separator + fileName);
                        if (!uploadedFile.exists()) {
                            uploadedFile.createNewFile();
                        }
                        file.transferTo(uploadedFile);
                        userCell.setAvatar(fileName);
                    }
                }
            }
        }

        userService.updateUserInfo(userCell);
        if(session.getAttribute("user") != null) {
            userCell = userService.findUserById(userCell.getId());
            session.setAttribute("user", userCell);
            model.addAttribute("user", userCell);
        }
        return "redirect:/user/" + userCell.getId();
    }

    @RequestMapping("/follow")
    public @ResponseBody
    Map<String,Object> follow(HttpServletRequest request) {
        int following_id = Integer.parseInt(request.getParameter("user_id"));
        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();

        if(session.getAttribute("user") != null) {
            map.put("isLogged", true);
            UserCell userCell = (UserCell) session.getAttribute("user");

            // 新增follow
            Follow follow = followService.follow(userCell.getId(), following_id);

            // 新增notice
            Notice notice = new Notice(userCell.getId(), following_id, 2, new Timestamp(System.currentTimeMillis()), follow.getId(), following_id, false);
            noticeService.saveNotice(notice);

            map.put("result", true);

        } else {
            map.put("isLogged", false);
        }
        return map;
    }

    @RequestMapping("/unfollow")
    public @ResponseBody
    Map<String,Object> unfollow(HttpServletRequest request) {
        int following_id = Integer.parseInt(request.getParameter("user_id"));
        HttpSession session = request.getSession();
        Map<String,Object> map = new HashMap<String,Object>();

        if(session.getAttribute("user") != null) {
            map.put("isLogged", true);
            UserCell userCell = (UserCell) session.getAttribute("user");
            Follow follow = followService.unfollow(userCell.getId(), following_id);
            map.put("result", true);

        } else {
            map.put("isLogged", false);
        }
        return map;
    }
}
