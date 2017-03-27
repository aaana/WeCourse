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

    public List<WxMessage> findAllMessageWithReceiverId(int receiver_id) {
        return (List<WxMessage>) sessionFactory.getCurrentSession().createQuery("from WxMessage where receiver_id=? order by id desc").setParameter(0, receiver_id).list();
    }

    public List<WxMessage> findAllUnreadMessageWithReceiverId(int receiver_id) {
        return (List<WxMessage>) sessionFactory.getCurrentSession().createQuery("from WxMessage where receiver_id=? and hasread=false order by id desc").setParameter(0, receiver_id).list();
    }

    public List<WxMessage> findAllMessageWithReceiverIdAndType(int receiver_id, int type) {
        return (List<WxMessage>) sessionFactory.getCurrentSession().createQuery("from WxMessage where receiver_id=? and notice_type=? order by id desc").setParameter(0, receiver_id).setParameter(1, type).list();
    }

    public List<WxMessage> findAllUnreadMessageWithReceiverIdAndType(int receiver_id, int type) {
        return (List<WxMessage>) sessionFactory.getCurrentSession().createQuery("from WxMessage where receiver_id=? and notice_type=? and hasread = false order by id desc").setParameter(0, receiver_id).setParameter(1, type).list();
    }
}
