package com.weike.java.service;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;

import java.util.List;

/**
 * Created by tina on 2/16/17.
 */
public interface WeikeService {
    public void saveWeike(Weike weike);
    public void updateWeike(Weike weike);
    public void removeWeike(Weike weike);

    public WeikeCell findWeikeByWeikeId(int id);
    public WeikeCell findWeikeByWeikeId(int id, int currentUserId);
    public List<WeikeCell> findAllWeike();
    public List<WeikeCell> findAllWeike(int currentUserId);
    public List<WeikeCell> findWeikesWithUserId(int id);
    public List<WeikeCell> findWeikesWithUserId(int id, int currentUserId);
    public List<WeikeCell> findFavoriteWeikesWithUserId(int id);
    public List<WeikeCell> findFavoriteWeikesWithUserId(int id, int currentUserId);
    public List<WeikeCell> findWeikeFromStartNum(int startNum);
    public List<WeikeCell> findWeikeFromStartNum(int startNum, int currentUserId);
    public Boolean haveMoreWeike(int startNum);

    // weike operation
    public Boolean weikeGetFavorited(int weikeId);
    public Boolean weikeGetUnfavorited(int weikeId);
    public Boolean weikeGetWatched(int weikeId);
    public Boolean weikeGetCommented(int weikeId);

    // hot weike
    public List<WeikeCell> findHotWeikesWithUserId(int id);
    public List<WeikeCell> findHotWeikesWithUserId(int id, int currentUserId);
    public List<WeikeCell> findMayLikeWeikesWithUserId(int userId);

    // search
    // field: 1 -- title & descrption ; 2 -- author_name ; 3 -- subject
    public List<WeikeCell> searchWeike(int field, String searchString);
    public List<WeikeCell> searchWeike(int field, String searchString, int currentUserId);
}
