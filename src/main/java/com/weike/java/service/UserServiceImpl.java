package com.weike.java.service;

import com.weike.java.DAO.FavoriteDAO;
import com.weike.java.DAO.FollowDAO;
import com.weike.java.DAO.UserDAO;
import com.weike.java.DAO.WeikeDAO;
import com.weike.java.entity.Follow;
import com.weike.java.entity.User;
import com.weike.java.entity.UserCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private FavoriteDAO favoriteDAO;
    @Autowired
    private FollowDAO followDAO;
    @Autowired
    private WeikeDAO weikeDAO;



    public int signup(User u) {
        return userDao.save(u);
    }

    public UserCell login(String email, String password) {
        UserCell userCell = transUser2UserCell(userDao.findUserWithEmailAndPw(email, password));
        return userCell;
    }

    public UserCell findUserById(int id) {
        return transUser2UserCell(userDao.findUserWithId(id));
    }

    public UserCell findUserById(int id, int currentUserId) {
        return transUser2UserCell(userDao.findUserWithId(id), currentUserId);
    }

    public List<UserCell> findFollowedUserWithUserid(int userId) {
        List<Follow> follows = followDAO.findAllFollowsWithUserId(userId);
        List<UserCell> userCells = new LinkedList<UserCell>();
        for (Follow follow : follows) {
            User user = userDao.findUserWithId(follow.getFollowing_id());
            userCells.add(transUser2UserCell(user));
        }
        return userCells;
    }

    public List<UserCell> findFollowedUserWithUserid(int userId, int currentUserId) {
        List<Follow> follows = followDAO.findAllFollowsWithUserId(userId);
        List<UserCell> userCells = new LinkedList<UserCell>();
        for (Follow follow : follows) {
            User user = userDao.findUserWithId(follow.getFollowing_id());
            userCells.add(transUser2UserCell(user, currentUserId));
        }
        return userCells;
    }

    public List<UserCell> findCommonFollowings(int userId, int currentUserId) {
        List<Integer> followingIds = followDAO.findCommonFollowings(userId, currentUserId);
        List<UserCell> userCells = new LinkedList<UserCell>();
        for (int id : followingIds) {
            userCells.add(findUserById(id));
        }
        return userCells;
    }

    public Boolean checkEmailUsed(String email) {
        List<User> users= userDao.findUserWithEmail(email);
        return users.size() == 0 ? false : true;
    }

    public void updateUserInfo(User u) {
        userDao.update(u);
    }

    public UserCell transUser2UserCell(User user) {
        UserCell userCell = new UserCell(user);
        userCell.setFavorite_num(favoriteDAO.findFavoriteNumWithUserId(user.getId()));
        userCell.setFollowing_num(followDAO.findFollowNumWithUserId(user.getId()));
        userCell.setWeike_num(weikeDAO.findWeikeNumWithUserId(user.getId()));
        return userCell;
    }
    public UserCell transUser2UserCell(User user, int currentUserId) {
        UserCell userCell = transUser2UserCell(user);
        Follow follow = followDAO.checkRecord(currentUserId, user.getId());
        userCell.setHasfollowed(follow != null && follow.getValid());
        return userCell;
    }
}
