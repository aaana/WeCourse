package com.weike.java.service;

import com.weike.java.DAO.FavoriteDAO;
import com.weike.java.DAO.FileDAO;
import com.weike.java.DAO.WeikeDAO;
import com.weike.java.entity.Favorite;
import com.weike.java.entity.Weike;
import com.weike.java.entity.WeikeCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 2/16/17.
 */
@Service("WeikeService")
@Transactional
public class WeikeServiceImpl implements WeikeService {
    @Autowired
    private WeikeDAO weikeDAO;

    @Autowired
    private FavoriteDAO favoriteDAO;

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

    public WeikeCell findWeikeByWeikeId(int id, int currentUserId) {
        WeikeCell weikeCell = findWeikeByWeikeId(id);
        return checkStarred(weikeCell, currentUserId);
    }

    public List<WeikeCell> findAllWeike() {
        return weikeDAO.findAllWeike();
    }

    public List<WeikeCell> findAllWeike(int currentUserId) {
        List<WeikeCell> weikeCells = findAllWeike();
        return checkStarred(weikeCells, currentUserId);
    }

    public List<WeikeCell> findWeikesWithUserId(int id) {
        return weikeDAO.findWeikesWithUserId(id);
    }

    public List<WeikeCell> findWeikesWithUserId(int id, int currentUserId) {
        List<WeikeCell> weikeCells = findWeikesWithUserId(id);
        return checkStarred(weikeCells, currentUserId);
    }

    public List<WeikeCell> findFavoriteWeikesWithUserId(int id) {
        List<Favorite> favorites = favoriteDAO.findAllFavoritesWithUserId(id);
        List<WeikeCell> weikeCells = new LinkedList<WeikeCell>();
        for (Favorite favorite : favorites) {
            WeikeCell weikeCell = weikeDAO.findWeikeByWeikeId(favorite.getWeike_id());
            weikeCells.add(weikeCell);
        }
        return weikeCells;
    }

    public List<WeikeCell> findFavoriteWeikesWithUserId(int id, int currentUserId) {
        List<WeikeCell> weikeCells = findFavoriteWeikesWithUserId(id);
        weikeCells = checkStarred(weikeCells, currentUserId);
        return weikeCells;
    }

    public Boolean haveMoreWeike(int startNum) {
        return weikeDAO.haveMoreWeike(startNum);
    }

    public List<WeikeCell> findWeikeFromStartNum(int startNum) {
        return weikeDAO.findWeikeFromStartNum(startNum);
    }

    public List<WeikeCell> findWeikeFromStartNum(int startNum, int currentUserId) {
        List<WeikeCell> weikeCells = weikeDAO.findWeikeFromStartNum(startNum);
        return checkStarred(weikeCells, currentUserId);
    }

    public Boolean weikeGetFavorited(int weikeId) {
        Weike weike = weikeDAO.findWeikeByWeikeId(weikeId);
        weike.setStar_num(weike.getStar_num() + 1);
        return weikeDAO.update(weike);
    }

    public Boolean weikeGetUnfavorited(int weikeId) {
        Weike weike = weikeDAO.findWeikeByWeikeId(weikeId);
        weike.setStar_num(weike.getStar_num() - 1);
        return weikeDAO.update(weike);
    }

    public Boolean weikeGetWatched(int weikeId) {
        Weike weike = weikeDAO.findWeikeByWeikeId(weikeId);
        weike.setView_num(weike.getView_num() + 1);
        return weikeDAO.update(weike);
    }

    public Boolean weikeGetCommented(int weikeId) {
        Weike weike = weikeDAO.findWeikeByWeikeId(weikeId);
        weike.setComment_num(weike.getComment_num() + 1);
        return weikeDAO.update(weike);
    }

    public List<WeikeCell> findHotWeikesWithUserId(int userId) {
        return null;
    }

    public List<WeikeCell> findMayLikeWeikesWithUserId(int userId) {
        return null;
    }

    public List<WeikeCell> searchWeike(int field, String searchString) {
        return null;
    }

    public List<WeikeCell> searchWeike(int field, String searchString, int currentUserId) {
        List<WeikeCell> weikeCells = searchWeike(field, searchString);
        return checkStarred(weikeCells, currentUserId);
    }

    public List<WeikeCell> checkStarred(List<WeikeCell> weikeCells, int currentUserId) {
        for (WeikeCell weikeCell : weikeCells) {
            checkStarred(weikeCell, currentUserId);
        }
        return  weikeCells;
    }

    public WeikeCell checkStarred(WeikeCell weikeCell, int currentUserId) {
        Favorite favorite = favoriteDAO.checkRecord(weikeCell.getId(), currentUserId);
        if (favorite != null && favorite.getValid()) {
            weikeCell.setStarred(true);
        } else {
            weikeCell.setStarred(false);
        }
        return  weikeCell;
    }
}
