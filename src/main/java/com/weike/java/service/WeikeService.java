package com.weike.java.service;

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

    public List<WeikeCell> findAllWeike();
    public List<WeikeCell> findAllWeike(int currentUserId);
    public List<WeikeCell> findWeikesWithUserId(int id);
    public List<WeikeCell> findWeikesWithUserId(int id, int currentUserId);
    public List<WeikeCell> findFavoriteWeikesWithUserId(int id);
    public List<WeikeCell> findFavoriteWeikesWithUserId(int id, int currentUserId);

    public WeikeCell findWeikeByWeikeId(int id);
    public WeikeCell findWeikeByWeikeId(int id, int currentUserId);
    public List<WeikeCell> findWeikeWithQueryString(String string);

    public Boolean weikeGetFavorited(int weikeId);
    public Boolean weikeGetUnfavorited(int weikeId);
    public Boolean weikeGetWatched(int weikeId);
    public Boolean weikeGetCommented(int weikeId);


}
