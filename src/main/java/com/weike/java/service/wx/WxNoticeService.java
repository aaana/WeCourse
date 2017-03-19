package com.weike.java.service.wx;

import com.weike.java.entity.wx.WxNotice;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface WxNoticeService {
    // 新建通知
    public WxNotice createNotice(WxNotice wxNotice);

    // 获得某课程的全部通知
    public List<WxNotice> getAllNoticesWithCourseId(int course_id);
}
