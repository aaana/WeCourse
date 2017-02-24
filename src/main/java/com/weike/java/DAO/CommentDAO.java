package com.weike.java.DAO;

import com.weike.java.entity.Comment;

import java.util.List;

/**
 * Created by tina on 2/23/17.
 */
public interface CommentDAO {
    public int save(Comment comment);
    public List<Comment> findAllCommentWithWeikeId(int weikeId);
    public int getCommentNumWithWeikeId(int weikeId);
    public Comment findCommentWithId(int id);
}
