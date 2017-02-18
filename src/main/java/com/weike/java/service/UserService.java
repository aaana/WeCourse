package com.weike.java.service;

import com.weike.java.entity.User;

import java.util.List;

public interface UserService {
    public int signup(User u);
    public User login(String email, String password);
    public User findUserById(int id);
    public Boolean checkEmailUsed(String email);
    public void updateUserInfo(User u);
}
