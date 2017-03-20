package com.weike.java.service.wx;

import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import com.weike.java.entity.wx.WxQuestion;
import com.weike.java.entity.wx.WxQuestionCell;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface WxQuestionService {
    // 创建Question
    public WxQuestionCell createQuestion(WxQuestion wxQuestion);

    // 获取WxQuestion
    public WxQuestionCell getQuestionWithId(int id);
    public List<WxQuestionCell> getAllQuestionWithCourseId(int course_id);
    public List<WxQuestionCell> getAllQuestionWithFirstQuestionId(int wxQuestion_id);
}
