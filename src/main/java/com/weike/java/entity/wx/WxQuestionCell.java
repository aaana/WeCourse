package com.weike.java.entity.wx;

import com.weike.java.DAO.wx.WxQuestionDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tina on 3/20/17.
 */
public class WxQuestionCell extends WxQuestion {
    private String publish_time_string;
    private int answer_num;
    private String publisher_name;

    public List<WxQuestionCell> wxQuestionCells = new LinkedList<WxQuestionCell>();

    public WxQuestionCell() {
    }

    public WxQuestionCell(WxQuestion wxQuestion) {
        super(wxQuestion.getPublisher_id(), wxQuestion.getContent(), wxQuestion.getPublish_time(), wxQuestion.getParent_id(),
                wxQuestion.getGrandparent_id(), wxQuestion.getCourse_id());
        this.setId(wxQuestion.getId());

        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            this.publish_time_string = sdf.format(this.getPublish_time());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPublish_time_string() {
        return publish_time_string;
    }

    public void setPublish_time_string(String publish_time_string) {
        this.publish_time_string = publish_time_string;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public int getAnswer_num() {
        return answer_num;
    }

    public void setAnswer_num(int answer_num) {
        this.answer_num = answer_num;
    }
}
