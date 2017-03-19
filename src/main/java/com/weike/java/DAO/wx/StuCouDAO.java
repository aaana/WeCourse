package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.StuCou;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface StuCouDAO {
    public int save(StuCou stuCou);
    public StuCou findAllStuCouWithId(int id);
    public List<StuCou> findAllStuCouWithCourseId(int course_id);
    public List<StuCou> findAllStuCouWithUserId(int user_id);
    public Boolean updateAttendance(int id);
    public Boolean updateAttendance(int user_id, int course_id);

}
