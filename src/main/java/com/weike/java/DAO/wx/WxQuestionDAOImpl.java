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

    public List<WxQuestion> findAllQuestionWithCourseId(int course_id) {
        return (List<WxQuestion>) sessionFactory.getCurrentSession().createQuery("from WxQuestion where course_id  = ? and grandparent_id = -1 order by id desc ").setParameter(0, course_id).list();
    }

    public List<WxQuestion> findAllQuestionWithFirstQuestionId(int wxQuestion_id) {
        return (List<WxQuestion>) sessionFactory.getCurrentSession().createQuery("from WxQuestion where grandparent_id = ? order by id desc ").setParameter(0, wxQuestion_id).list();
    }
}
