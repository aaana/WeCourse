package com.weike.java.service;

import com.weike.java.entity.Comment;
import com.weike.java.entity.CommentCell;

import java.util.List;

/**
 * Created by tina on 2/23/17.
 */
public interface CommentService {
    public int newComment(Comment comment);
    public Comment getSimpleCommentWithId(int id);
    public List<CommentCell> getAllCommentsWithWeikeId(int weikeId);
    public CommentCell transComment2CommentCell(Comment comment);
    public int getCommentNumWithWeikeId(int weikeId);

}
