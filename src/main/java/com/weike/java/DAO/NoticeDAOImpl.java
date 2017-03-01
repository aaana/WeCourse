package com.weike.java.DAO;

import com.weike.java.entity.Notice;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by tina on 2/20/17.
 */
@Repository
public class NoticeDAOImpl implements NoticeDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(Notice notice) {
        return (Integer) sessionFactory.getCurrentSession().save(notice);
    }

    public Boolean updateReadStatus(int notice_id) {
        String hql = "update Notice n set n.hasread = ? where n.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, true);
        query.setParameter(1, notice_id);

        return (query.executeUpdate() > 0);
    }

    public List<Notice> findAllNoticeWithReceiverId(int receiver_id) {
        return (List<Notice>) sessionFactory.getCurrentSession().createQuery("from Notice where receiver_id=? order by id desc").setParameter(0, receiver_id).list();
    }

    public List<Notice> findAllUnreadNoticeWithReceiverId(int receiver_id) {
        return (List<Notice>) sessionFactory.getCurrentSession().createQuery("from Notice where receiver_id=? and hasread=false order by id desc").setParameter(0, receiver_id).list();
    }

    public List<Notice> findAllNoticeWithReceiverIdAndType(int receiver_id, int type) {
        return (List<Notice>) sessionFactory.getCurrentSession().createQuery("from Notice where receiver_id=? and notice_type=? order by id desc").setParameter(0, receiver_id).setParameter(1, type).list();
    }

    public List<Notice> findAllUnreadNoticeWithReceiverIdAndType(int receiver_id, int type) {
        return (List<Notice>) sessionFactory.getCurrentSession().createQuery("from Notice where receiver_id=? and notice_type=? and hasread=false order by id desc").setParameter(0, receiver_id).setParameter(1, type).list();
    }
}
