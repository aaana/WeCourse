package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.WxNotice;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Repository
public class WxNoticeDAOImpl implements WxNoticeDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(WxNotice wxNotice) {
        return (Integer) sessionFactory.getCurrentSession().save(wxNotice);
    }

    public List<WxNotice> findAllNoticeWithCourseId(int course_id) {
        return (List<WxNotice>) sessionFactory.getCurrentSession().createQuery("from WxNotice where course_id  = ?  order by id desc ").setParameter(0, course_id).list();
    }
}
