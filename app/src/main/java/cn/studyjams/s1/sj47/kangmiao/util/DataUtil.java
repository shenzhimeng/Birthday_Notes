package cn.studyjams.s1.sj47.kangmiao.util;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Calendar;

/**
 * date util
 * 日期工具
 * Created by Godream on 16/4/28 上午10:22.
 */
public class DataUtil {

    /**
     * format birthday
     */
    public static String formatBirthday(Context context, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
        );
    }

    /**
     * format birthday with no year
     */
    public static String formatBirthdayNoYear(Context context, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        return DateUtils.formatDateTime(context,
                calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_NO_YEAR
                        | DateUtils.FORMAT_ABBREV_MONTH
        );
    }

    /**
     * calculate lef days
     *
     * @param month birthday month
     * @param day   birthday days
     * @return lef days
     */
    public static int calculateLeftDays(int month, int day) {
        Calendar startCalendar = Calendar.getInstance();
        int startYear = startCalendar.get(Calendar.YEAR);
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, month);
        endCalendar.set(Calendar.DAY_OF_MONTH, day);
        endCalendar.set(Calendar.HOUR_OF_DAY, 0);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        endCalendar.set(Calendar.MILLISECOND, 0);

        long times = endCalendar.getTime().getTime() - startCalendar.getTime().getTime();
        if (times < 0) {
            endCalendar.set(Calendar.YEAR, startYear + 1);
        }

        long days = (endCalendar.getTime().getTime() - startCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24);
        return (int) days;
    }
}
