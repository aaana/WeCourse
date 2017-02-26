package com.weike.java.DAO;

import com.weike.java.entity.User;

import java.util.List;

public interface UserDAO {
    public int save(User u);
    public Boolean update(User u);
    public List<User> findAll();

    public User findUserWithEmailAndPw(String email, String password);
    public User findUserWithId(int id);
    public List<User> findUserWithEmail(String email);
    public List<Integer> findUserIdsWithQueryString(String string, String searchString);
}
