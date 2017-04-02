package com.weike.java.service.wx;

import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.DAO.wx.StuCouDAO;
import com.weike.java.DAO.wx.WxNoticeDAO;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.StuCou;
import com.weike.java.entity.wx.WxNotice;
import com.weike.java.entity.wx.WxNoticeCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    @Autowired
    private StuCouDAO stuCouDAO;

    @Autowired
    private CourseDAO courseDAO;

    public WxNoticeCell createNotice(WxNotice wxNotice) {
        int id = wxNoticeDAO.save(wxNotice);
        wxNotice.setId(id);

        //更新课程的update_time
        Course course = courseDAO.findCourseById(wxNotice.getCourse_id());
        course.setUpdate_time(new Timestamp(System.currentTimeMillis()));
        courseDAO.updateCourseInfo(course);

        // 每个学生-课程的unread_num加一
        List<StuCou> stuCous = stuCouDAO.findAllStuCouWithCourseId(wxNotice.getCourse_id());
        for (StuCou stuCou : stuCous) {
            stuCouDAO.updateUnreadNum(stuCou, stuCou.getUnread_num() + 1);
        }

        return new WxNoticeCell(wxNotice);
    }

    public List<WxNoticeCell> getAllNoticesWithCourseId(int course_id, int current_user_id) {
        List<WxNotice> wxNotices = wxNoticeDAO.findAllNoticeWithCourseId(course_id);
        List<WxNoticeCell> wxNoticeCells = new LinkedList<WxNoticeCell>();
        for (WxNotice wxNotice : wxNotices) {
            wxNoticeCells.add(new WxNoticeCell(wxNotice));
        }

        StuCou stuCou = stuCouDAO.findAllStuCouWithCourseIdAndUserId(course_id, current_user_id);
        if (stuCou != null) {
            //身份为学生
            stuCouDAO.updateUnreadNum(stuCou, 0);
        }


        return wxNoticeCells;
    }
}
