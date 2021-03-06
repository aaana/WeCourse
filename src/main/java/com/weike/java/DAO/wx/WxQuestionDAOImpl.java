package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.WxNotice;
import com.weike.java.entity.wx.WxQuestion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Repository
public class WxQuestionDAOImpl implements WxQuestionDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(WxQuestion wxQuestion) {
        return (Integer) sessionFactory.getCurrentSession().save(wxQuestion);
    }

    public WxQuestion findQuestionWithId(int id) {
        return (WxQuestion) sessionFactory.getCurrentSession().createQuery("from WxQuestion where id = ? ").setParameter(0, id).uniqueResult();
    }

    public List<WxQuestion> findAllQuestionWithCourseId(int course_id) {
        return (List<WxQuestion>) sessionFactory.getCurrentSession().createQuery("from WxQuestion where course_id  = ? and grandparent_id = -1 order by id desc").setParameter(0, course_id).list();
    }

    public List<WxQuestion> findAllQuestionWithFirstQuestionId(int wxQuestion_id) {
        return (List<WxQuestion>) sessionFactory.getCurrentSession().createQuery("from WxQuestion where grandparent_id = ?").setParameter(0, wxQuestion_id).list();
    }

    public List<WxQuestion> findAllQuestionWithRaiserId(int user_id) {
        return (List<WxQuestion>) sessionFactory.getCurrentSession().createQuery("select q from WxQuestion q, Course c where q.publisher_id=? and q.grandparent_id=-1 and q.course_id=c.id and c.available=true order by q.id desc").setParameter(0, user_id).list();
    }

    public int getAnswerNumWithQuestionId(int id) {
        return sessionFactory.getCurrentSession().createQuery("from WxQuestion where grandparent_id = ?").setParameter(0, id).list().size();
    }
}
