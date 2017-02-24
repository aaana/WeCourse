package com.weike.java.service;

import com.weike.java.DAO.CommentDAO;
import com.weike.java.DAO.UserDAO;
import com.weike.java.entity.Comment;
import com.weike.java.entity.CommentCell;
import com.weike.java.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 2/23/17.
 */
@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private UserDAO userDAO;


    public int newComment(Comment comment) {
        return commentDAO.save(comment);
    }

    public Comment getSimpleCommentWithId(int id) {
        return commentDAO.findCommentWithId(id);
    }

    public List<CommentCell> getAllCommentsWithWeikeId(int weikeId) {
        List<Comment> comments = commentDAO.findAllCommentWithWeikeId(weikeId);
        List<CommentCell> commentCells = new LinkedList<CommentCell>();
        for (Comment comment : comments) {
            commentCells.add(transComment2CommentCell(comment));
        }
        return commentCells;
    }

    public CommentCell transComment2CommentCell(Comment comment) {
        User user = userDAO.findUserWithId(comment.getPublisher_id());
        return new CommentCell(comment, user.getName(), user.getAvatar());
    }

    public int getCommentNumWithWeikeId(int weikeId) {
        return commentDAO.getCommentNumWithWeikeId(weikeId);
    }
}
