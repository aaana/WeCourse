package com.weike.java.service.wx;

import com.weike.java.DAO.wx.WxQuestionDAO;
import com.weike.java.entity.wx.WxQuestion;
import com.weike.java.entity.wx.WxQuestionCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 3/19/17.
 */
@Service("wxQuestionService")
@Transactional
public class WxQuestionServiceImpl implements WxQuestionService {
    @Autowired
    private WxQuestionDAO wxQuestionDAO;

    public WxQuestionCell createQuestion(WxQuestion wxQuestion) {
        int id = wxQuestionDAO.save(wxQuestion);
        wxQuestion.setId(id);
        return new WxQuestionCell(wxQuestion);
    }

    public WxQuestionCell getQuestionWithId(int id) {
        return new WxQuestionCell(wxQuestionDAO.findQuestionWithId(id));
    }

    public List<WxQuestionCell> getAllQuestionWithCourseId(int course_id) {
        List<WxQuestion> wxQuestions = wxQuestionDAO.findAllQuestionWithCourseId(course_id);
        return transWxQUestion2QxQuestionCell(wxQuestions);
    }

    public List<WxQuestionCell> getAllQuestionWithFirstQuestionId(int wxQuestion_id) {
        List<WxQuestion> wxQuestions = wxQuestionDAO.findAllQuestionWithFirstQuestionId(wxQuestion_id);
        return transWxQUestion2QxQuestionCell(wxQuestions);
    }

    public List<WxQuestionCell> transWxQUestion2QxQuestionCell(List<WxQuestion> wxQuestions) {
        List<WxQuestionCell> wxQuestionCells = new LinkedList<WxQuestionCell>();
        for (WxQuestion wxQuestion : wxQuestions) {
            wxQuestionCells.add(new WxQuestionCell(wxQuestion));
        }
        return wxQuestionCells;
    }
}
