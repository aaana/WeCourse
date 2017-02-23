package com.weike.java.service;

import com.sun.tools.corba.se.idl.constExpr.Not;
import com.weike.java.entity.Notice;

import java.util.List;

/**
 * Created by tina on 2/20/17.
 */
public interface NoticeService {
    public int saveNotice(Notice notice);
    public List<Notice> getNoticeWithUserId(int user_id);
    public List<Notice> getUnreadNoticeWithUserId(int user_id);
}
