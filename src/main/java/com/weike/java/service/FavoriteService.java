package com.weike.java.service;

import com.weike.java.entity.Favorite;

/**
 * Created by tina on 2/20/17.
 */
public interface FavoriteService {
    public Favorite favorite(int weike_id, int user_id);
    public Favorite unfavorite(int weike_id, int user_id);
}
