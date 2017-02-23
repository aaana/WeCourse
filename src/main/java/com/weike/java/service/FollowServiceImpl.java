package com.weike.java.service;

import com.weike.java.DAO.FavoriteDAO;
import com.weike.java.DAO.FollowDAO;
import com.weike.java.entity.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by tina on 2/21/17.
 */
@Service("followService")
@Transactional
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowDAO followDAO;

    public Follow follow(int follower_id, int following_id) {
        Follow follow = followDAO.checkRecord(follower_id, following_id);
        if (follow == null) {
            follow = new Follow(follower_id, following_id, new Timestamp(System.currentTimeMillis()), true);
            followDAO.save(follow);
        } else {
            follow.setValid(true);
            followDAO.update(follow);
        }
        return follow;
    }

    public Follow unfollow(int follower_id, int following_id) {
        Follow follow = followDAO.checkRecord(follower_id, following_id);
        if (follow == null) {
            follow = new Follow(follower_id, following_id, new Timestamp(System.currentTimeMillis()), false);
            followDAO.save(follow);
        } else {
            follow.setValid(false);
            followDAO.update(follow);
        }
        return follow;
    }
}
