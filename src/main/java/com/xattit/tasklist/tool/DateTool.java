package com.xattit.tasklist.tool;

import java.sql.Date;
import java.util.Calendar;

/**
 * Description
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 15:48
 */
public class DateTool {
    public static Date getMonDayOfWeek() {
        return getMonDayOfWeek(null);
    }

    public static Date getMonDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        return new Date(calendar.getTimeInMillis());
    }
}
