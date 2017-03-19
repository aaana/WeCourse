package com.weike.java.service.wx;

import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import com.weike.java.entity.wx.WxQuestion;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
public interface WxQuestionService {
    // 创建Question
    public WxQuestion createQuestion(WxQuestion wxQuestion);

    // 获取WxQuestion
    public List<WxQuestion> getAllQuestionWithCourseId(int course_id);
    public List<WxQuestion> getAllQuestionWithFirstQuestionId(int wxQuestion_id);
}
