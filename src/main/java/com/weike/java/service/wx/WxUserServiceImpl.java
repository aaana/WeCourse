package com.weike.java.service.wx;

import com.weike.java.DAO.UserDAO;
import com.weike.java.DAO.wx.AccountDAO;
import com.weike.java.entity.Follow;
import com.weike.java.entity.User;
import com.weike.java.entity.UserCell;
import com.weike.java.entity.wx.Account;
import com.weike.java.entity.wx.WxUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tina on 3/14/17.
 */
@Service("wxUserService")
@Transactional
public class WxUserServiceImpl implements WxUserService {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserDAO userDao;

    public User login(String email, String password) {
        return userDao.findUserWithEmailAndPw(email, password);
    }

    public WxUser newAccount(User user, String wechat_id, int student_id) {
        Account account = new Account(wechat_id, user.getId(), student_id);
        accountDAO.save(account);
        return transUser2WxUser(user, student_id);
    }

    public WxUser checkAccount(String wechat_id) {
        Account account = accountDAO.checkAccount(wechat_id);
        if (account == null) {
            return null;
        }
        User user = userDao.findUserWithId(account.getWecourse_id());
        return transUser2WxUser(user, account.getStudent_id());
    }

    public WxUser findUserById(int id) {
        return transUser2WxUser(userDao.findUserWithId(id));
    }

    public User findSimpleUserById(int id) {
        return userDao.findUserWithId(id);
    }

    public WxUser transUser2WxUser(User user) {
        if (user ==  null) {
            return null;
        }
        WxUser wxUser = new WxUser(user);
        return wxUser;
    }
    public WxUser transUser2WxUser(User user, int student_id) {
        if (user ==  null) {
            return null;
        }
        WxUser wxUser = new WxUser(user);
        wxUser.setStudent_id(student_id);
        return wxUser;
    }
}
