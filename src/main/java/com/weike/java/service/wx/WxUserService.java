package com.weike.java.service.wx;

import com.weike.java.entity.User;
import com.weike.java.entity.UserCell;
import com.weike.java.entity.wx.WxUser;

/**
 * Created by tina on 3/14/17.
 */
public interface WxUserService {
    // 登录
    public User login(String email, String password);

    // 绑定
    public WxUser newAccount(User user, int wechat_id, int student_id);

    // 检查记录
    public WxUser checkAccount(int wechat_id);

    // 获取Account
    public WxUser findUserById(int id);
    public User findSimpleUserById(int id);
}
