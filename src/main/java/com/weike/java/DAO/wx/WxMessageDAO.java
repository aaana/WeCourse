package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.WxMessage;

import java.util.List;

/**
 * Created by tina on 3/27/17.
 */
public interface WxMessageDAO {
    public int save(WxMessage wxMessage);
    public Boolean updateReadStatus(int message_id);
    public WxMessage findMessageWithId(int id);
    public List<WxMessage> findAllMessageWithReceiverId(int receiver_id);
    public List<WxMessage> findAllUnreadMessageWithReceiverId(int receiver_id);
}
