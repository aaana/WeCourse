package com.weike.java.DAO;

import com.weike.java.entity.Avatar;
import com.weike.java.entity.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 2/25/17.
 */
@Repository
public class AvatarDAOImpl implements AvatarDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Avatar> getAllAvatar() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Avatar.class);
        return criteria.list();
    }
}
