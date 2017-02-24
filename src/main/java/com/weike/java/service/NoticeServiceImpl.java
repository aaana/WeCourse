package com.weike.java.service;

import com.weike.java.DAO.*;
import com.weike.java.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 2/20/17.
 */
@Service("noticeService")
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private WeikeDAO weikeDAO;
    @Autowired
    private CommentDAO commentDAO;


    public int saveNotice(Notice notice) {
        return noticeDAO.save(notice);
    }

    public Boolean readNotce(int notice_id) {
        return noticeDAO.updateReadStatus(notice_id);
    }

    public List<NoticeCell> getNoticeWithUserId(int user_id) {
        List<Notice> notices = noticeDAO.findAllNoticeWithReceiverId(user_id);
        return transComment2CommentCell(notices);
    }

    public List<NoticeCell> getUnreadNoticeWithUserId(int user_id) {
        List<Notice> notices = noticeDAO.findAllUnreadNoticeWithReceiverId(user_id);
        return transComment2CommentCell(notices);
    }

    public List<NoticeCell> getNoticeWithUserIdAndType(int user_id, int notice_type) {
        List<Notice> notices = noticeDAO.findAllNoticeWithReceiverIdAndType(user_id, notice_type);
        return transComment2CommentCell(notices);
    }

    public List<NoticeCell> getUnreadNoticeWithUserIdAndType(int user_id, int notice_type) {
        List<Notice> notices = noticeDAO.findAllUnreadNoticeWithReceiverIdAndType(user_id, notice_type);
        return transComment2CommentCell(notices);
    }

    public int getUnreadNoticeNum(int user_id) {
        return noticeDAO.findAllUnreadNoticeWithReceiverId(user_id).size();
    }

    public List<Integer> getUnreadNoticeNumList(int user_id) {
        List<Integer> list = new LinkedList<Integer>();
        list.add(noticeDAO.findAllUnreadNoticeWithReceiverId(user_id).size());
        list.add(noticeDAO.findAllUnreadNoticeWithReceiverIdAndType(user_id, 1).size());
        list.add(noticeDAO.findAllUnreadNoticeWithReceiverIdAndType(user_id, 2).size());
        list.add(noticeDAO.findAllUnreadNoticeWithReceiverIdAndType(user_id, 3).size());
        list.add(noticeDAO.findAllUnreadNoticeWithReceiverIdAndType(user_id, 4).size());
        return list;
    }

    public List<NoticeCell> transComment2CommentCell(List<Notice> notices) {
        List<NoticeCell> noticeCells = new LinkedList<NoticeCell>();
        for (Notice notice : notices) {
            NoticeCell noticeCell = new NoticeCell(notice);
            User user = userDAO.findUserWithId(notice.getSender_id());
            noticeCell.setSender_name(user.getName());
            noticeCell.setSender_avatar(user.getAvatar());

            if (notice.getNotice_type() == 1) {
                Weike weike = weikeDAO.findSimpleWeikeByWeikeId(notice.getTarget_id());
                noticeCell.setWeike_title(weike.getTitle());
            } else if (notice.getNotice_type() == 3){
                Comment comment = commentDAO.findCommentWithId(notice.getTrigger_id());
                noticeCell.setComment_content(comment.getContent());
                noticeCell.setComment_weike_id(comment.getWeike_id());
                Weike weike = weikeDAO.findSimpleWeikeByWeikeId(comment.getWeike_id());
                noticeCell.setWeike_title(weike.getTitle());
            } else if (notice.getNotice_type() == 4){
                Comment comment = commentDAO.findCommentWithId(notice.getTrigger_id());
                noticeCell.setComment_content(comment.getContent());
                noticeCell.setComment_weike_id(comment.getWeike_id());
                comment = commentDAO.findCommentWithId(notice.getTarget_id());
                noticeCell.setComment_target(comment.getContent());
            }
            noticeCells.add(noticeCell);
        }
        return noticeCells;
    }
}
