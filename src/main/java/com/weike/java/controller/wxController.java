package com.weike.java.controller;

import com.weike.java.entity.UserCell;
import com.weike.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tina on 3/12/17.
 */
@RestController
@RequestMapping("/wx")
public class wxController {
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public Map<String,Object> login(HttpServletRequest request) {
        String email = request.getParameter("userEmail");
        String password = request.getParameter("password");

        UserCell userCell = userService.login(email, password);
        Map<String,Object> map = new HashMap<String,Object>();
        if (userCell != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", userCell);
            map.put("user", userCell);
            map.put("result", "success");
        } else {
            map.put("result", "fail");
        }
        return map;
    }
}
