package com.weike.java.DAO;

import com.weike.java.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public int save(User u) {
        return (Integer) sessionFactory.getCurrentSession().save(u);
    }

    public Boolean update(User u) {
        //sessionFactory.getCurrentSession().update(u);
        String hql = "update User u set u.name = ?,u.email=?,u.password=?,u.type=?,u.school=?,u.introduction=?,u.avatar=? where u.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, u.getName());
        query.setParameter(1, u.getEmail());
        query.setParameter(2, u.getPassword());
        query.setParameter(3, u.getType());
        query.setParameter(4, u.getSchool());
        query.setParameter(5, u.getIntroduction());
        query.setParameter(6, u.getAvatar());
        query.setParameter(7, u.getId());

        return (query.executeUpdate() > 0);
    }

    public List<User> findAll() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        return criteria.list();
    }

    public User findUserWithEmailAndPw(String email, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where email=? and password=?");
        query.setParameter(0, email);
        query.setParameter(1, password);
        return (User) query.uniqueResult();
    }

    public User findUserWithId(int id) {
        return (User) sessionFactory.getCurrentSession().createQuery("from User where id=?").setParameter(0, id).uniqueResult();
    }

    public List<User> findUserWithEmail(String email) {
        return (List<User>) sessionFactory.getCurrentSession().createQuery("from User where email=?").setParameter(0, email).list();
    }

    public List<Integer> findUserIdsWithQueryString(String string, String searchString) {
        return (List<Integer>) sessionFactory.getCurrentSession().createQuery(string).setParameter(0, "%" + searchString + "%").list();
    }
}
