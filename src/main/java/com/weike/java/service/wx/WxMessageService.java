package com.weike.java.service.wx;

import com.weike.java.entity.wx.WxMessage;
import com.weike.java.entity.wx.WxMessageCell;

import java.util.List;

/**
 * Created by tina on 3/27/17.
 */
public interface WxMessageService {
    public int saveNotice(WxMessage wxMessage);
    public Boolean readMessage(int message_id);

    public WxMessage getSimpleMessageWithId(int message_id);
    public List<WxMessageCell> getMessageWithUserId(int user_id);
    public List<WxMessageCell> getUnreadMessageWithUserId(int user_id);
    public List<WxMessageCell> getMessageWithUserIdAndType(int user_id, int notice_type);
    public List<WxMessageCell> getUnreadMessageWithUserIdAndType(int user_id, int notice_type);
    public int getUnreadMessageNum(int user_id);
}
