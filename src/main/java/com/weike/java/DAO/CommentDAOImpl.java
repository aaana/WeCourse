package com.weike.java.DAO;

import com.weike.java.entity.Comment;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 2/23/17.
 */
@Repository
public class CommentDAOImpl implements CommentDAO {
    @Autowired
    private SessionFactory sessionFactory;


    public int save(Comment comment) {
        return (Integer) sessionFactory.getCurrentSession().save(comment);
    }

    public List<Comment> findAllCommentWithWeikeId(int weikeId) {
        return sessionFactory.getCurrentSession().createQuery("from Comment where weike_id=?").setParameter(0, weikeId).list();

    }

    public int getCommentNumWithWeikeId(int weikeId) {
        return findAllCommentWithWeikeId(weikeId).size();
    }

    public Comment findCommentWithId(int id) {
        return (Comment) sessionFactory.getCurrentSession().createQuery("from Comment where id=?").setParameter(0, id).uniqueResult();
    }
}
