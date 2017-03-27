package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.WxNotice;
import com.weike.java.entity.wx.WxQuestion;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface WxQuestionDAO {
    public int save(WxQuestion wxQuestion);
    public WxQuestion findQuestionWithId(int id);
    public List<WxQuestion> findAllQuestionWithCourseId(int course_id);
    public List<WxQuestion> findAllQuestionWithFirstQuestionId(int wxQuestion_id);
    public int getAnswerNumWithQuestionId(int id);
}
