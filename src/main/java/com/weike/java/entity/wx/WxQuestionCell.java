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
    private int level_num;
    private String target_user_name;
    private int target_level_num;

    // public List<WxQuestionCell> wxQuestionCells = new LinkedList<WxQuestionCell>();

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

    public int getLevel_num() {
        return level_num;
    }

    public void setLevel_num(int level_num) {
        this.level_num = level_num;
    }

    public String getTarget_user_name() {
        return target_user_name;
    }

    public void setTarget_user_name(String target_user_name) {
        this.target_user_name = target_user_name;
    }

    public int getTarget_level_num() {
        return target_level_num;
    }

    public void setTarget_level_num(int target_level_num) {
        this.target_level_num = target_level_num;
    }
}

