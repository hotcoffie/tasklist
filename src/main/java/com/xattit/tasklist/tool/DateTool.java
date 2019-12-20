package com.xattit.tasklist.tool;

import java.sql.Date;
import java.util.Calendar;

/**
 * Description 日期特殊工具
 *
 * @author xieyu
 * @version v1.0.0
 * Date: 2019/12/18 15:48
 */
public class DateTool {

    /**
     * 获取Date所在周的周一，这里认为周一是一周的第一天
     *
     * @param date
     * @return
     */
    public static Date getMonDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return new Date(calendar.getTimeInMillis());
    }
}
