package com.weike.java.service;

import com.weike.java.entity.Follow;

/**
 * Created by tina on 2/21/17.
 */
public interface FollowService {
    public Follow follow(int follower_id, int following_id);
    public Follow unfollow(int follower_id, int following_id);
}
