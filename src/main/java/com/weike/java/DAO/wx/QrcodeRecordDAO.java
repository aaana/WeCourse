package com.weike.java.DAO.wx;

import com.weike.java.entity.wx.Account;
import com.weike.java.entity.wx.QrcodeRecord;

/**
 * Created by tina on 4/2/17.
 */
public interface QrcodeRecordDAO {
    public int save(QrcodeRecord q);
    public boolean updateExpireTime(QrcodeRecord q);
    public boolean updateSrc(QrcodeRecord q);

    public QrcodeRecord findQrcodeRecordById(int id);

}
