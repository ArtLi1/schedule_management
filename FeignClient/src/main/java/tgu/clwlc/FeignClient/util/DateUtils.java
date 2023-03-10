package tgu.clwlc.FeignClient.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getMongoDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY,+8);
        return calendar.getTime();
    }

    public static Date ToDate(String str){
        try {
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date ToDate(Date date){
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }



    public static String ToString(Date date){
        return simpleDateFormat.format(date);
    }

    /**
     * 获取指定日期所在周的周一
     *
     * @param date 日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.add(Calendar.DATE, c.getFirstDayOfWeek() - c.get(Calendar.DAY_OF_WEEK) + 1);
        return c.getTime();
    }

    public static Date getFirstDayOfWeek(String date) {
        return getFirstDayOfWeek(ToDate(date));
    }

    public static Date DateAddOneDay(Date date){
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        // 把日期往后增加一天,整数  往后推,负数往前移动
        c.add(Calendar.DATE, 1);
        // 这个时间就是日期往后推一天的结果
        return c.getTime();
    }



    public static int getWeekOfDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w == 0) w = 7;
        return w;
    }

    public static int getWeekOfDate(String date){
        return getWeekOfDate(ToDate(date));
    }

    public static Date addHalfHours(Date date,int i){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, i*1800);
        Date time = c.getTime();
        return time;
    }



}
