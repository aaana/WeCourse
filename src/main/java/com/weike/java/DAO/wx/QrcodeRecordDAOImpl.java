package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.Account;
import com.weike.java.entity.wx.QrcodeRecord;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by tina on 4/2/17.
 */
@Repository
public class QrcodeRecordDAOImpl implements QrcodeRecordDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(QrcodeRecord q) {
        return (Integer) sessionFactory.getCurrentSession().save(q);
    }

    public boolean updateExpireTime(QrcodeRecord q) {
        String hql = "update QrcodeRecord q set q.expire_time = ? where q.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, q.getExpire_time());
        query.setParameter(1, q.getId());

        return (query.executeUpdate() > 0);
    }

    public boolean updateSrc(QrcodeRecord q) {
        String hql = "update QrcodeRecord q set q.src = ? where q.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, q.getSrc());
        query.setParameter(1, q.getId());

        return (query.executeUpdate() > 0);
    }

    public QrcodeRecord findQrcodeRecordById(int id) {
        return (QrcodeRecord) sessionFactory.getCurrentSession().createQuery("from QrcodeRecord where id=?").setParameter(0, id).uniqueResult();
    }
}
