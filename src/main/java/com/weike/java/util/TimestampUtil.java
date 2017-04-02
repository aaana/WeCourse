package com.weike.java.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tina on 4/3/17.
 */
public class TimestampUtil {
    public static int compareTimestamp(Timestamp t1, Timestamp t2) {
        return t1.before(t2) ? 1 : t2.before(t1) ? -1 : 0;
    }


    public static Timestamp addTimestemp(Timestamp t, int min) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, min);

        return new Timestamp(c.getTime().getTime());
    }


}
