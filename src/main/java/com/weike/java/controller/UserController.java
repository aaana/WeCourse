package com.weike.java.controller;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.User;
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

        User user = new User(name, email, password, type, "", "", "avatar01.png");
        int id = userService.signup(user);
        user.setId(id);

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        return "redirect:/";
    }

    @RequestMapping("/loginUser")
    public @ResponseBody Map<String,Object> login(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userService.login(email, password);
        Map<String,Object> map = new HashMap<String,Object>();
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
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
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        return "editInfoPage";
    }

    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(Model model, HttpServletRequest request) throws IOException {

        HttpSession session = request.getSession();
        User user = null;
        if(session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String school = request.getParameter("school");
        String introduction = request.getParameter("introduction");
        user.setEmail(email);
        user.setSchool(school);
        user.setIntroduction(introduction);

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
                        user.setAvatar(fileName);
                    }
                }
            }
        }

        userService.updateUserInfo(user);
        if(session.getAttribute("user") != null) {
            user = userService.findUserById(user.getId());
            session.setAttribute("user", user);
            model.addAttribute("user", user);
        }
        return "redirect:/user/" + user.getId();
    }

}
