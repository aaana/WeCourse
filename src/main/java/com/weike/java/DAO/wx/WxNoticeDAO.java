package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.WxNotice;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface WxNoticeDAO {
    public int save(WxNotice wxNotice);
    public List<WxNotice> findAllNoticeWithCourseId(int course_id);

}
