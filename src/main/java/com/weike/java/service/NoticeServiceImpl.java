package com.weike.java.service;

import com.weike.java.DAO.FavoriteDAO;
import com.weike.java.DAO.NoticeDAO;
import com.weike.java.entity.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tina on 2/20/17.
 */
@Service("noticeService")
@Transactional
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDAO noticeDAO;

    public int saveNotice(Notice notice) {
        return noticeDAO.save(notice);
    }

    public List<Notice> getNoticeWithUserId(int user_id) {
        return noticeDAO.findAllNoticeWithReceiverId(user_id);
    }

    public List<Notice> getUnreadNoticeWithUserId(int user_id) {
        return noticeDAO.findAllUnreadNoticeWithReceiverId(user_id);
    }
}
