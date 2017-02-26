package com.weike.java.DAO;

import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;

import java.util.List;

/**
 * Created by tina on 2/16/17.
 */
public interface WeikeDAO {
    public int save(Weike weike);
    public Boolean update(Weike weike);
    public void remove(Weike weike);

    public Weike findSimpleWeikeByWeikeId(int id);
    public WeikeCell findWeikeByWeikeId(int id);
    public List<WeikeCell> findAllWeike();
    public List<WeikeCell> findWeikesWithUserId(int id);
    public List<WeikeCell> findHotWeikesWithUserId(int id);
    public List<WeikeCell> findWeikeFromStartNum(int startId);
    public List<WeikeCell> findWeikeWithQueryString(String string, String searchString);
    public List<WeikeCell> findWeikeWithQueryString(String string, List<Integer> searchUsers);

    public int findWeikeNumWithUserId(int id);
    public Boolean haveMoreWeike(int startId);


}
