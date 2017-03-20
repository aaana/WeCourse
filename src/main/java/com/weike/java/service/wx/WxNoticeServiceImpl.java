package com.weike.java.service.wx;

import com.weike.java.DAO.wx.WxNoticeDAO;
import com.weike.java.entity.wx.WxNotice;
import com.weike.java.entity.wx.WxNoticeCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Service("wxNoticeService")
@Transactional
public class WxNoticeServiceImpl implements WxNoticeService {

    @Autowired
    private WxNoticeDAO wxNoticeDAO;

    public WxNoticeCell createNotice(WxNotice wxNotice) {
        int id = wxNoticeDAO.save(wxNotice);
        wxNotice.setId(id);
        return new WxNoticeCell(wxNotice);
    }

    public List<WxNoticeCell> getAllNoticesWithCourseId(int course_id) {
        List<WxNotice> wxNotices = wxNoticeDAO.findAllNoticeWithCourseId(course_id);
        List<WxNoticeCell> wxNoticeCells = new LinkedList<WxNoticeCell>();
        for (WxNotice wxNotice : wxNotices) {
            wxNoticeCells.add(new WxNoticeCell(wxNotice));
        }
        return wxNoticeCells;
    }
}
