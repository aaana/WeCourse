package com.weike.java.entity.wx;

import java.sql.Timestamp;

/**
 * Created by tina on 3/27/17.
 */
public class WxMessageCell extends WxMessage {

    private String trigger_content;
    private String target_content;
    private int course_id;

    public WxMessageCell() {
    }

    public WxMessageCell(WxMessage wxMessage) {
        super(wxMessage.getSender_id(), wxMessage.getReceiver_id(), wxMessage.getMessage_type(), wxMessage.getInit_time(), wxMessage.getTrigger_id(), wxMessage.isHasread());
        this.setId(wxMessage.getId());
        trigger_content = "";
        course_id = -1;
    }

    public String getTrigger_content() {
        return trigger_content;
    }

    public void setTrigger_content(String trigger_content) {
        this.trigger_content = trigger_content;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTarget_content() {
        return target_content;
    }

    public void setTarget_content(String target_content) {
        this.target_content = target_content;
    }
}
