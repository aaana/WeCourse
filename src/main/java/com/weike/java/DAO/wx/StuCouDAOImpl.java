package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.StuCou;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Repository
public class StuCouDAOImpl implements StuCouDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(StuCou stuCou) {
        return (Integer) sessionFactory.getCurrentSession().save(stuCou);
    }


    public StuCou findAllStuCouWithId(int id) {
        return (StuCou) sessionFactory.getCurrentSession().createQuery("from StuCou where id=?").setParameter(0, id).uniqueResult();
    }

    public List<StuCou> findAllStuCouWithCourseId(int course_id) {
        return (List<StuCou>) sessionFactory.getCurrentSession().createQuery("from StuCou where course_id = ? order by id desc ").setParameter(0, course_id).list();
    }

    public List<StuCou> findAllStuCouWithUserId(int user_id) {
        return (List<StuCou>) sessionFactory.getCurrentSession().createQuery("from StuCou where user_id  = ? order by id desc ").setParameter(0, user_id).list();
    }

    public Boolean updateAttendance(int id) {
        String hql = "update StuCou s set s.attendance = s.attendance + 1 where s.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, id);

        return (query.executeUpdate() > 0);
    }

    public Boolean updateAttendance(int user_id, int course_id) {
        String hql = "update StuCou s set s.attendance = s.attendance + 1 where s.user_id = ? and s.course_id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, user_id);
        query.setParameter(1, course_id);

        return (query.executeUpdate() > 0);
    }
}
