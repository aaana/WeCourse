package com.weike.java.DAO;

import com.weike.java.entity.Favorite;
import com.weike.java.entity.Follow;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tina on 2/21/17.
 */
@Repository
public class FollowDAOImpl implements FollowDAO {
    @Autowired
    private SessionFactory sessionFactory;


    public int save(Follow follow) {
        return (Integer) sessionFactory.getCurrentSession().save(follow);
    }

    public void remove(Follow follow) {
        sessionFactory.getCurrentSession().createQuery("delete Follow where id=?").setParameter(0, follow.getId()).executeUpdate();
    }

    public Boolean update(Follow follow) {
        String hql = "update Follow f set f.follower_id = ?, f.following_id = ?, f.follow_time = ?, f.valid = ? where f.id = ?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter(0, follow.getFollower_id());
        query.setParameter(1, follow.getFollowing_id());
        query.setParameter(2, follow.getFollow_time());
        query.setParameter(3, follow.getValid());
        query.setParameter(4, follow.getId());

        return (query.executeUpdate() > 0);
    }

    public Follow checkRecord(int follower_id, int following_id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Follow where follower_id=? and following_id=?");
        query.setParameter(0, follower_id);
        query.setParameter(1, following_id);
        return (Follow) query.uniqueResult();
    }

    public List<Follow> findAllFollowsWithUserId(int user_id) {
        return sessionFactory.getCurrentSession().createQuery("from Follow where follower_id=?").setParameter(0, user_id).list();
    }

    public int findFollowNumWithUserId(int user_id) {
        return findAllFollowsWithUserId(user_id).size();
    }

    public List<Integer> findCommonFollowings(int user1, int user2) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT a.following_id FROM Follow as a, Follow as b WHERE a.valid = true and b.valid = true and a.follower_id = ? AND b.follower_id = ? AND a.following_id = b.following_id");
        query.setParameter(0, user1);
        query.setParameter(1, user2);
        return (List<Integer>) query.list();
    }
}
