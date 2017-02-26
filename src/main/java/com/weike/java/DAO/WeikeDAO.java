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
    public List<WeikeCell> findWeikeFromStartNum(int startNum);
    public List<WeikeCell> findWeikeWithQueryString(String string, String searchString);
    public List<WeikeCell> findWeikeWithQueryString(String string, List<Integer> searchUsers);

    public List<WeikeCell> searchWeikeWithContentString(int startNum, String searchString);
    public List<WeikeCell> searchWeikeWithUserNameString(int startNum, List<Integer> searchUsers);
    public List<WeikeCell> searchWeikeWithSubjectString(int startNum, String searchString);


    public int findWeikeNumWithUserId(int id);
    public Boolean haveMoreWeike(int startId);

    public int getGap();


}
