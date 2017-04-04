package com.weike.java.DAO.wx;

import com.weike.java.entity.Notice;
import com.weike.java.entity.wx.WxMessage;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 3/27/17.
 */
@Repository
public class WxMessageDAOImpl implements WxMessageDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(WxMessage wxMessage) {
        return (Integer) sessionFactory.getCurrentSession().save(wxMessage);
    }

    public Boolean updateReadStatus(int message_id) {
        String hql = "update WxMessage m set m.hasread = ? where m.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, true);
        query.setParameter(1, message_id);

        return (query.executeUpdate() > 0);
    }

    public WxMessage findMessageWithId(int id) {
        return (WxMessage) sessionFactory.getCurrentSession().createQuery("from WxMessage where id=? ").setParameter(0, id).uniqueResult();
    }

    public List<WxMessage> findAllMessageWithReceiverId(int receiver_id) {
        return (List<WxMessage>) sessionFactory.getCurrentSession().createQuery("SELECT m from WxMessage m, WxQuestion q, Course c where m.receiver_id=? and m.trigger_id=q.id and q.course_id=c.id and c.available=true order by m.id desc").setParameter(0, receiver_id).list();
    }

    public List<WxMessage> findAllUnreadMessageWithReceiverId(int receiver_id) {
        return (List<WxMessage>) sessionFactory.getCurrentSession().createQuery("SELECT m from WxMessage m, WxQuestion q, Course c where m.receiver_id=? and m.hasread=false and m.trigger_id=q.id and q.course_id=c.id and c.available=true order by m.id desc").setParameter(0, receiver_id).list();
    }

}
