package com.weike.java.DAO.wx;

import com.weike.java.entity.User;
import com.weike.java.entity.wx.Account;

import java.util.List;

/**
 * Created by tina on 3/14/17.
 */
public interface AccountDAO {
    public int save(Account u);
    public Account checkAccount(int wechat_id);
}