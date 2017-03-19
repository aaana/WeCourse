package com.weike.java.service.wx;

import com.weike.java.DAO.wx.WxQuestionDAO;
import com.weike.java.entity.wx.WxQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Service("wxQuestionService")
@Transactional
public class WxQuestionServiceImpl implements WxQuestionService {
    @Autowired
    private WxQuestionDAO wxQuestionDAO;

    public WxQuestion createQuestion(WxQuestion wxQuestion) {
        int id = wxQuestionDAO.save(wxQuestion);
        wxQuestion.setId(id);
        return wxQuestion;
    }

    public List<WxQuestion> getAllQuestionWithCourseId(int course_id) {
        return wxQuestionDAO.findAllQuestionWithCourseId(course_id);
    }

    public List<WxQuestion> getAllQuestionWithFirstQuestionId(int wxQuestion_id) {
        return wxQuestionDAO.findAllQuestionWithFirstQuestionId(wxQuestion_id);
    }
}
