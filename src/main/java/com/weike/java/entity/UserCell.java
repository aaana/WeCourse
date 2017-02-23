package com.weike.java.entity;

/**
 * Created by tina on 2/21/17.
 */
public class UserCell extends User {
    private int following_num;
    private int weike_num;
    private int favorite_num;
    private Boolean hasfollowed;

    public UserCell() {}

    public UserCell(User user) {
        super(user.getName(), user.getEmail(), user.getPassword(), user.getType(), user.getSchool(), user.getIntroduction(), user.getAvatar());
        this.setId(user.getId());
    }

    public int getFollowing_num() {
        return following_num;
    }

    public void setFollowing_num(int following_num) {
        this.following_num = following_num;
    }

    public int getWeike_num() {
        return weike_num;
    }

    public void setWeike_num(int weike_num) {
        this.weike_num = weike_num;
    }

    public int getFavorite_num() {
        return favorite_num;
    }

    public void setFavorite_num(int favorite_num) {
        this.favorite_num = favorite_num;
    }

    public Boolean getHasfollowed() {
        return hasfollowed;
    }

    public void setHasfollowed(Boolean hasfollowed) {
        this.hasfollowed = hasfollowed;
    }
}
