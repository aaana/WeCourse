package com.weike.java.service;

import com.sun.tools.corba.se.idl.constExpr.Not;
import com.weike.java.entity.Notice;
import com.weike.java.entity.NoticeCell;

import java.util.List;

/**
 * Created by tina on 2/20/17.
 */
public interface NoticeService {
    public int saveNotice(Notice notice);
    public Boolean readNotce(int notice_id);

    public List<NoticeCell> getNoticeWithUserId(int user_id);
    public List<NoticeCell> getUnreadNoticeWithUserId(int user_id);
    public List<NoticeCell> getNoticeWithUserIdAndType(int user_id, int notice_type);
    public List<NoticeCell> getUnreadNoticeWithUserIdAndType(int user_id, int notice_type);
    public int getUnreadNoticeNum(int user_id);
    public List<Integer> getUnreadNoticeNumList(int user_id);

}
