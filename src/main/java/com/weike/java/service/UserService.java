package com.weike.java.service;

import com.weike.java.entity.User;
import com.weike.java.entity.UserCell;

import java.util.List;

public interface UserService {
    public int signup(User u);
    public UserCell login(String email, String password);
    public UserCell findUserById(int id);
    public UserCell findUserById(int id, int currentUserId);
    public List<UserCell> findFollowedUserWithUserid(int userId);
    public List<UserCell> findFollowedUserWithUserid(int userId, int currentUserId);
    public List<UserCell> findCommonFollowings(int userId, int currentUserId);
    public Boolean checkEmailUsed(String email);
    public void updateUserInfo(User u);
}
