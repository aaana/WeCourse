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
        StuCou stuCouTest = findAllStuCouWithCourseIdAndUserId(stuCou.getCourse_id(), stuCou.getUser_id());
        if (stuCouTest == null) {
            return (Integer) sessionFactory.getCurrentSession().save(stuCou);
        } else {
            return stuCouTest.getId();
        }
    }


    public StuCou findAllStuCouWithId(int id) {
        return (StuCou) sessionFactory.getCurrentSession().createQuery("from StuCou where id=?").setParameter(0, id).uniqueResult();
    }

    public StuCou findAllStuCouWithCourseIdAndUserId(int course_id, int user_id) {
        return (StuCou) sessionFactory.getCurrentSession().createQuery("from StuCou where course_id=? and user_id = ?")
                .setParameter(0, course_id).setParameter(1, user_id).uniqueResult();
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

    public Boolean updateUnreadNum(StuCou stuCou, int unread_num) {
        String hql = "update StuCou s set s.unread_num = ? where s.user_id = ? and s.course_id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, unread_num);
        query.setParameter(1, stuCou.getUser_id());
        query.setParameter(2, stuCou.getCourse_id());

        return (query.executeUpdate() > 0);
    }
}
