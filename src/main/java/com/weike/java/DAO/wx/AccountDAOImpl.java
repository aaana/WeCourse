package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.Account;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 3/14/17.
 */
@Repository
public class AccountDAOImpl implements AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(Account a) {
        return (Integer) sessionFactory.getCurrentSession().save(a);
    }

    public Account checkAccount(String wechat_id) {
        return (Account) sessionFactory.getCurrentSession().createQuery("from Account where wechat_id=?").setParameter(0, wechat_id).uniqueResult();
    }
}
