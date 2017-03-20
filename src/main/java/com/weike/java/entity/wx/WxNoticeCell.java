package com.weike.java.entity.wx;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by tina on 3/20/17.
 */
public class WxNoticeCell extends WxNotice {
    private String publish_time_string;

    public WxNoticeCell() {
    }

    public WxNoticeCell(WxNotice wxNotice) {
        super(wxNotice.getContent(), wxNotice.getPublish_time(), wxNotice.getCourse_id());
        this.setId(wxNotice.getId());

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
}
