package com.weike.java.DAO;

import com.weike.java.entity.Notice;

import java.util.List;

/**
 * Created by tina on 2/20/17.
 */
public interface NoticeDAO {
    public int save(Notice notice);
    public Boolean updateReadStatus(int notice_id);
    public List<Notice> findAllNoticeWithReceiverId(int receiver_id);
    public List<Notice> findAllUnreadNoticeWithReceiverId(int receiver_id);
    public List<Notice> findAllNoticeWithReceiverIdAndType(int receiver_id, int type);
    public List<Notice> findAllUnreadNoticeWithReceiverIdAndType(int receiver_id, int type);
}
