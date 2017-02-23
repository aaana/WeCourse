package com.weike.java.service;

import com.weike.java.DAO.FavoriteDAO;
import com.weike.java.entity.Favorite;
import com.weike.java.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

/**
 * Created by tina on 2/20/17.
 */
@Service("favoriteService")
@Transactional
public class FavoriteServiceImpl implements FavoriteService{

    @Autowired
    private FavoriteDAO favoriteDAO;

    public Favorite favorite(int weike_id, int user_id) {
        Favorite favorite = favoriteDAO.checkRecord(weike_id, user_id);

        if (favorite == null ) {
            favorite = new Favorite(weike_id, user_id, new Timestamp(System.currentTimeMillis()), true);
            favoriteDAO.save(favorite);
        } else {
            favorite.setValid(true);
            favoriteDAO.update(favorite);
        }
        return favorite;
    }

    public Favorite unfavorite(int weike_id, int user_id) {
        Favorite favorite = favoriteDAO.checkRecord(weike_id, user_id);
        if (favorite == null ) {
            favorite = new Favorite(weike_id, user_id, new Timestamp(System.currentTimeMillis()), false);
            favoriteDAO.save(favorite);
        } else {
            favorite.setValid(false);
            favoriteDAO.update(favorite);
        }
        return favorite;
    }
}
