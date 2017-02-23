package com.weike.java.DAO;

import com.weike.java.entity.Favorite;

import java.util.List;

/**
 * Created by tina on 2/19/17.
 */
public interface FavoriteDAO {
    public int save(Favorite favorite);
    public void remove(Favorite favorite);
    public Boolean update(Favorite favorite);
    public Favorite checkRecord(int weike_id, int user_id);

    public List<Favorite> findAllFavoritesWithUserId(int user_id);
    public int findFavoriteNumWithUserId(int user_id);
}
