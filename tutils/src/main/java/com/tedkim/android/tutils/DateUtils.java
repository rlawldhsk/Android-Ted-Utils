package com.tedkim.android.tutils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Class collection of date
 * Created by Ted
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    /**
     * 현재 날짜를 기준으로 지난 일 주일간의 날짜 반환
     */
    public static List<String> getLastSevenDays() {
        String format = "M/d";

        List<String> listDates = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        for (int i = 6; i > -1; i--) {
            long lastDay = i * 24 * 60 * 60 * 1000;

            Date newDate = new Date(System.currentTimeMillis() - lastDay);
            String result = dateFormat.format(newDate);

            listDates.add(result);
            log.d(TAG, "getLastWeekDates : result = " + result);
        }
        return listDates;
    }


    /**
     * 데이터 포맷으로 날짜 반환
     */
    public static String getDateWithFormat(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date dateResult = null;
        try {
            dateResult = format.parse(date);
            log.d(TAG, "- dateResult = " + dateResult);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        if (dateResult != null) {
            long dataMillis = dateResult.getTime();

            String resultFormat = "yyyyMMdd";

            SimpleDateFormat dateFormat = new SimpleDateFormat(resultFormat);
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            Date newDate = new Date(dataMillis);

            return dateFormat.format(newDate);
        }
        return null;
    }

    public static String getSocialTime(String date) {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            Date dateResult = format.parse(date);
            return format.format(dateResult);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLast7daysOr7weeksDate(boolean isLast7days) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        Date today = new Date(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);

        if (isLast7days) {
            calendar.add(Calendar.DAY_OF_YEAR, -6);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, -6 * 7);
        }

        Date newDate = calendar.getTime();

        return format.format(newDate);
    }

    /**
     * 원하는 포맷 형태로 오늘 날짜 반환
     */
    public static String getTodayWithFormat(String resultFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(resultFormat);
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String get7WeeksAgoFirstDate() {
        //특정 날짜가 속한 주의 시작 날짜 반환 : 월요일
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, -7 * 6);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date mondayDate = calendar.getTime();
        return format.format(mondayDate);
    }

    /**
     * Get current week last date
     * @return date
     */
    public static String getCurrentWeekLastDate() {
        //이번주의 마지막 날짜 반환 : 일요일
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_WEEK, 7); //일주일의 시작이 일요일이므로 다음주 일요일 날짜로 셋팅
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        Date mondayDate = calendar.getTime();
        return format.format(mondayDate);
    }

    /**
     * Get timezone offset
     */
    public static String getCurrentTimezoneOffset() {
        Calendar mCalendar = new GregorianCalendar();
        TimeZone mTimeZone = mCalendar.getTimeZone();
        int mGMTOffset = -mTimeZone.getRawOffset();
        return String.valueOf(TimeUnit.MINUTES.convert(mGMTOffset, TimeUnit.MILLISECONDS));
    }

    /**
     * Get unix time to date
     * SimpleDateFormat("yyyy MM dd hh mm")
     *
     * @param date 날짜
     * @return unix time
     */
    @SuppressLint("SimpleDateFormat")
    public static long getUnixTimeFromDate(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd hh mm");
            return formatter.parse(date).getTime() /1000;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Get date to unix time
     *
     * @param millisecond time
     * @return unix time
     */
    public static Date getDateFromUnixTime(long millisecond) {
        String check = String.valueOf(millisecond);
        if (check.length() < 13) {
            return new Date(millisecond * 1000);
        } else {
            return new Date(millisecond);
        }
    }

    /**
     * Get current month
     *
     * @return string date
     */
    public static String getCurrentMonth() {
        return getDate(System.currentTimeMillis(), "M");
    }

    /**
     * Get date from millisecond
     *
     * @param millisecond millisecond
     * @return string date
     */
    public static String getDateFromMillisecond(long millisecond) {
//        yyyy년 MM월 dd일 E요일
        return getDate(millisecond, "yyyy.MM.dd");
    }

    /**
     * Get time from millisecond
     *
     * @param millisecond millisecond
     * @return string time
     */
    public static String getTimeFromMillisecond(long millisecond) {
        return getDate(millisecond, "H시 mm분");
    }

    /**
     * Get date from millisecond
     *
     * @param millisecond  millisecond
     * @param formatString string format
     * @return string date
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDate(long millisecond, String formatString) {
        Date date = DateUtils.getDateFromUnixTime(millisecond);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date);
    }
}
