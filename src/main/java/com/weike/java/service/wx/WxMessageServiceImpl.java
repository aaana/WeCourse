package com.weike.java.service.wx;

import com.weike.java.DAO.wx.CourseDAO;
import com.weike.java.DAO.wx.WxMessageDAO;
import com.weike.java.DAO.wx.WxQuestionDAO;
import com.weike.java.entity.wx.Course;
import com.weike.java.entity.wx.WxMessage;
import com.weike.java.entity.wx.WxMessageCell;
import com.weike.java.entity.wx.WxQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 3/27/17.
 */
@Service("wxMessgaeService")
@Transactional
public class WxMessageServiceImpl implements WxMessageService {

    @Autowired
    private WxMessageDAO wxMessageDAO;

    @Autowired
    private WxQuestionDAO wxQuestionDAO;

    @Autowired
    private CourseDAO courseDAO;


    public int saveNotice(WxMessage wxMessage) {
        return wxMessageDAO.save(wxMessage);
    }

    public Boolean readMessage(int message_id) {
        return wxMessageDAO.updateReadStatus(message_id);
    }

    public WxMessage getSimpleMessageWithId(int message_id) {
        return wxMessageDAO.findMessageWithId(message_id);
    }

    public List<WxMessageCell> getMessageWithUserId(int user_id) {
        List<WxMessage> wxMessages = wxMessageDAO.findAllMessageWithReceiverId(user_id);
        return transWxMessage2WxMessageCell(wxMessages);
    }

    public List<WxMessageCell> getUnreadMessageWithUserId(int user_id) {
        List<WxMessage> wxMessages = wxMessageDAO.findAllUnreadMessageWithReceiverId(user_id);
        return transWxMessage2WxMessageCell(wxMessages);
    }

    public List<WxMessageCell> getMessageWithUserIdAndType(int user_id, int message_type) {
        List<WxMessage> wxMessages = wxMessageDAO.findAllMessageWithReceiverIdAndType(user_id, message_type);
        return transWxMessage2WxMessageCell(wxMessages);
    }

    public List<WxMessageCell> getUnreadMessageWithUserIdAndType(int user_id, int message_type) {
        List<WxMessage> wxMessages = wxMessageDAO.findAllUnreadMessageWithReceiverIdAndType(user_id, message_type);
        return transWxMessage2WxMessageCell(wxMessages);
    }

    public int getUnreadMessageNum(int user_id) {
        return wxMessageDAO.findAllUnreadMessageWithReceiverId(user_id).size();
    }


    public List<WxMessageCell> transWxMessage2WxMessageCell(List<WxMessage> wxMessages) {
        List<WxMessageCell> wxMessageCells = new LinkedList<WxMessageCell>();
        for (WxMessage message : wxMessages) {
            WxMessageCell wxMessageCell = transWxMessage2WxMessageCell(message);
            wxMessageCells.add(wxMessageCell);
        }
        return wxMessageCells;
    }

    public WxMessageCell transWxMessage2WxMessageCell(WxMessage wxMessage) {
        WxMessageCell wxMessageCell = new WxMessageCell(wxMessage);

        // 1:老师收到当有人在他的课中匿名提问
        // 2:提问者收到当有人在他的提问下回答
        // 3:发言者收到当有人回复他的回答
        if (wxMessage.getMessage_type() == 1) {
            WxQuestion wxQuestion = wxQuestionDAO.findQuestionWithId(wxMessage.getTrigger_id());
            wxMessageCell.setTrigger_content(wxQuestion.getContent());
            wxMessageCell.setCourse_id(wxQuestion.getCourse_id());

            Course course = courseDAO.findCourseById(wxQuestion.getCourse_id());
            wxMessageCell.setTarget_content(course.getCourse_name());

        } else if (wxMessage.getMessage_type() == 2){
            WxQuestion wxQuestion = wxQuestionDAO.findQuestionWithId(wxMessage.getTrigger_id());
            wxMessageCell.setTrigger_content(wxQuestion.getContent());
            wxMessageCell.setCourse_id(wxQuestion.getCourse_id());

            wxQuestion = wxQuestionDAO.findQuestionWithId(wxQuestion.getGrandparent_id());
            wxMessageCell.setTarget_content(wxQuestion.getContent());

        } else if (wxMessage.getMessage_type() == 3){
            WxQuestion wxQuestion = wxQuestionDAO.findQuestionWithId(wxMessage.getTrigger_id());
            wxMessageCell.setTrigger_content(wxQuestion.getContent());
            wxMessageCell.setCourse_id(wxQuestion.getCourse_id());

            wxQuestion = wxQuestionDAO.findQuestionWithId(wxQuestion.getParent_id());
            wxMessageCell.setTarget_content(wxQuestion.getContent());
        }
        return wxMessageCell;
    }
}
