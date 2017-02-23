package com.weike.java.DAO;

import com.weike.java.entity.Follow;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tina on 2/21/17.
 */
public interface FollowDAO {
    public int save(Follow follow);
    public void remove(Follow follow);
    public Boolean update(Follow follow);
    public Follow checkRecord(int follower_id, int following_id);
    public List<Follow> findAllFollowsWithUserId(int user_id);
    public int findFollowNumWithUserId(int user_id);
    public List<Integer> findCommonFollowings(int user1, int user2);
}
