package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.StuCou;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface StuCouDAO {
    // 新增
    public int save(StuCou stuCou);

    // 查询
    public StuCou findAllStuCouWithId(int id);
    public StuCou findAllStuCouWithCourseIdAndUserId(int course_id, int user_id);
    public List<StuCou> findAllStuCouWithCourseId(int course_id);
    public List<StuCou> findAllStuCouWithUserId(int user_id);

    // 更新
    public Boolean updateAttendance(StuCou stuCou);
    public Boolean updateAttendance(int user_id, int course_id);
    public Boolean updateUnreadNum(StuCou stuCou, int unread_num);

}
