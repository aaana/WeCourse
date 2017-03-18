package com.weike.java.service.wx;

import com.weike.java.entity.User;
import com.weike.java.entity.UserCell;
import com.weike.java.entity.wx.WxUser;

/**
 * Created by tina on 3/14/17.
 */
public interface WxUserService {
    public User login(String email, String password);
    public WxUser newAccount(User user, int wechat_id, int student_id);
    public WxUser checkAccount(int wechat_id);
    public WxUser findUserById(int id);
}
