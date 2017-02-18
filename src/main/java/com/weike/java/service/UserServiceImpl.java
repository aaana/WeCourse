package com.weike.java.service;

import com.weike.java.DAO.UserDAO;
import com.weike.java.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;

    public int signup(User u) {
        return userDao.save(u);
    }

    public User login(String email, String password) {
        return userDao.findUserWithEmailAndPw(email, password);
    }

    public User findUserById(int id) {
        return userDao.findUserWithId(id);
    }

    public Boolean checkEmailUsed(String email) {
        List<User> users= userDao.findUserWithEmail(email);
        return users.size() == 0 ? false : true;
    }

    public void updateUserInfo(User u) {
        userDao.update(u);
    }
}
