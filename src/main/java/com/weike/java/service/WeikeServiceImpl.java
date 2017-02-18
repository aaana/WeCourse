package com.weike.java.service;

import com.weike.java.DAO.FileDAO;
import com.weike.java.DAO.WeikeDAO;
import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tina on 2/16/17.
 */
@Service("WeikeService")
@Transactional
public class WeikeServiceImpl implements WeikeService {
    @Autowired
    private WeikeDAO weikeDAO;

    public void saveWeike(Weike weike) {
        weikeDAO.save(weike);
    }

    public void updateWeike(Weike weike) {
        weikeDAO.update(weike);
    }

    public void removeWeike(Weike weike) {
        weikeDAO.remove(weike);
    }

    public WeikeCell findWeikeByWeikeId(int id) {
        return weikeDAO.findWeikeByWeikeId(id);
    }

    public List<WeikeCell> findAllWeike() {
        return weikeDAO.findAllWeike();
    }

    public List<WeikeCell> findWeikesWithUserId(int id) {
        return weikeDAO.findWeikesWithUserId(id);
    }

    public List<WeikeCell> findWeikeWithQueryString(String string) {
        return weikeDAO.findWeikeWithQueryString(string);
    }
}
