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

    public WeikeCell findWeikeByWeikeId(int id);
    public List<WeikeCell> findAllWeike();
    public List<WeikeCell> findWeikesWithUserId(int id);
    public List<WeikeCell> findWeikeWithQueryString(String string);
}
