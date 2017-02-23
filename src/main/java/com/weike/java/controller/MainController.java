package com.weike.java.controller;

import com.weike.java.entity.UploadFile;
import com.weike.java.entity.User;
import com.weike.java.service.FileService;
import com.weike.java.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/upload")
    public String upload(){
        return "publish";
    }

    @RequestMapping("/reminder")
    public String gotoReminderPage(){
        return "reviewPage";
    }

//    @RequestMapping("/json")
//    @ResponseBody
//    public List<User> json(){
//      return userService.getAllUsernames();
//    }

}
