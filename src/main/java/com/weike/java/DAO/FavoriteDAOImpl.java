package com.weike.java.DAO;

import com.weike.java.entity.Favorite;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 2/19/17.
 */
@Repository
public class FavoriteDAOImpl implements FavoriteDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public int save(Favorite favorite) {
        return (Integer) sessionFactory.getCurrentSession().save(favorite);
    }

    public void remove(Favorite favorite) {
        sessionFactory.getCurrentSession().createQuery("delete Favorite where id=?").setParameter(0, favorite.getId()).executeUpdate();
    }

    public Boolean update(Favorite favorite) {
        String hql = "update Favorite f set f.weike_id = ?, f.user_id = ?, f.favorite_time = ?, f.valid = ? where f.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, favorite.getWeike_id());
        query.setParameter(1, favorite.getUser_id());
        query.setParameter(2, favorite.getFavorite_time());
        query.setParameter(3, favorite.getValid());
        query.setParameter(4, favorite.getId());

        return (query.executeUpdate() > 0);
    }

    public Favorite checkRecord(int weike_id, int user_id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Favorite where weike_id=? and user_id=?");
        query.setParameter(0, weike_id);
        query.setParameter(1, user_id);
        return (Favorite) query.uniqueResult();
    }

    public List<Favorite> findAllFavoritesWithUserId(int user_id) {
        return sessionFactory.getCurrentSession().createQuery("from Favorite where user_id=?").setParameter(0, user_id).list();
    }

    public int findFavoriteNumWithUserId(int user_id) {
        return findAllFavoritesWithUserId(user_id).size();
    }
}

