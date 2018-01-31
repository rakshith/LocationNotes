package com.justforchangesake.locationnotes.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sraksh on 2/1/2015.
 */
public class DateManupulateUtils {

    private static final String simpleDateFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String dateFormate = "yyyy-MM-dd";

    public static String getFormatDateTime(Date newDate){
        String dateTimeStr = null;
        dateTimeStr = new SimpleDateFormat(simpleDateFormat).format(newDate);
        return dateTimeStr;
    }

    public static String[] getDayMonthArray(String newDate) {
        String[] dayMonth = new String[3];

        if (newDate != null){
        try {
            Date date = new SimpleDateFormat(simpleDateFormat).parse(newDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            dayMonth[1] = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
            dayMonth[0] = String.valueOf(calendar.get(Calendar.DATE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        }

        return dayMonth;
    }

    public static LocalDate getDateWithouTime(){
        return new LocalDate();
    }

    public static LocalTime getTimeWithoutDate(){
        return new LocalTime();
    }

    public static DateTime getFullTimeandDateWithTimeZone(){
        return new DateTime();
    }

    public static LocalDateTime getFullDateWithTime(){
        return new LocalDateTime();
    }

    public static LocalDateTime calcNextSunday(LocalDateTime d){
        return d.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withDayOfWeek(DateTimeConstants.SUNDAY);
    }

}
