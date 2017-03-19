package com.weike.java.service.wx;

import com.weike.java.DAO.wx.WxNoticeDAO;
import com.weike.java.entity.wx.WxNotice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Service("wxNoticeService")
@Transactional
public class WxNoticeServiceImpl implements WxNoticeService {

    @Autowired
    private WxNoticeDAO wxNoticeDAO;

    public WxNotice createNotice(WxNotice wxNotice) {
        int id = wxNoticeDAO.save(wxNotice);
        wxNotice.setId(id);
        return wxNotice;
    }

    public List<WxNotice> getAllNoticesWithCourseId(int course_id) {
        return wxNoticeDAO.findAllNoticeWithCourseId(course_id);
    }
}