package com.weike.java.DAO;

import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;

import java.util.List;

/**
 * Created by tina on 2/16/17.
 */
public interface WeikeDAO {
    public int save(Weike weike);
    public void update(Weike weike);
    public void remove(Weike weike);

    public WeikeCell findWeikeByWeikeId(int id);
    public List<WeikeCell> findAllWeike();
    public List<WeikeCell> findWeikesWithUserId(int id);
    public List<WeikeCell> findWeikeWithQueryString(String string);
}
