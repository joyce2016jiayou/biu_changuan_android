package com.noplugins.keepfit.android.util.data;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateHelper {
    public static final String ENG_DATE_FROMAT = "EEE, d MMM yyyy HH:mm:ss z";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY = "yyyy";
    public static final String MM = "MM";
    public static final String DD = "dd";

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 根据long毫秒数，获得时分秒
     **/
    public static String getDateFormatByLong(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static String Ruzhutime(String timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        String dateStr = sdf.format(new Date(Long.parseLong(timeStamp)));
        return dateStr;
    }

    /**
     * @param
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— 格式化日期对象
     */
    public static Date date2date(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        String str = sdf.format(date);
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * @param
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— 时间对象转换成字符串
     */
    public static String date2string(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * 通过时间获得文件名
     *
     * @param date
     * @return
     */
    public static String getFileNameByDate(Date date) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        strDate = sdf.format(date);
        return strDate;
    }

    /**
     * 毫秒转化
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

        return  strSecond;
    }

    /**
     * @param
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— sql时间对象转换成字符串
     */
    public static String timestamp2string(Timestamp timestamp) {
        String strDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        strDate = sdf.format(timestamp);
        return strDate;
    }

    /**
     * @param dateString
     * @return
     * @作者 王建明
     * @创建日期 2012-7-13
     * @创建时间
     * @描述 —— 字符串转换成时间对象
     */
    public static Date string2date(String dateString) {
        Date formateDate = null;
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        try {
            formateDate = format.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return formateDate;
    }

    public static String getMyTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String gettime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * @param date
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— Date类型转换为Timestamp类型
     */
    public static Timestamp date2timestamp(Date date) {
        if (date == null)
            return null;
        return new Timestamp(date.getTime());
    }

    /**
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— 获得当前年份
     */
    public static String getNowYear() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY);
        return sdf.format(new Date());
    }

    /**
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— 获得当前月份
     */
    public static String getNowMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat(MM);
        return sdf.format(new Date());
    }

    /**
     * @return
     * @作者
     * @创建日期
     * @创建时间
     * @描述 —— 获得当前日期中的日
     */
    public static String getNowDay() {
        SimpleDateFormat sdf = new SimpleDateFormat(DD);
        return sdf.format(new Date());
    }

    public static String get_current_date(){
        String year = DateHelper.getNowYear();
        String month = DateHelper.getNowMonth();
        String date = DateHelper.getNowDay();

        return year+"-"+month+"-"+date;
    }

    public static String get_date(int year,int month,int date){
        String year1 = year+"";

        String month1="";
        if(month<=9){
            month1 = "0"+month;
        }else{
            month1 = ""+month;
        }
        String current_date="";
        if(date<=9){
            current_date = "0"+date;
        }else{
            current_date = ""+date;
        }

        return year1+"-"+month1+"-"+current_date;
    }

    public static String get_date2(int month,int date){

        String month1="";
        if(month<=9){
            month1 = "0"+month;
        }else{
            month1 = ""+month;
        }
        String current_date="";
        if(date<=9){
            current_date = "0"+date;
        }else{
            current_date = ""+date;
        }

        return month1+"/"+current_date;
    }

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    private final static long year = 12 * month;// 年

    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String getTimeFormatText(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > year) {
            r = (diff / year);
            return r + "年前";
        }
        if (diff > month) {
            r = (diff / month);
            return r + "个月前";
        }
        if (diff > day) {
            r = (diff / day);
            return r + "天前";
        }
        if (diff > hour) {
            r = (diff / hour);
            return r + "个小时前";
        }
        if (diff > minute) {
            r = (diff / minute);
            return r + "分钟前";
        }
        return "刚刚";
    }



    /**
     * 描述：根据时间返回几天前或几分钟的描述.
     *
     * @param strDate the str date
     * @return the string
     */
    public static String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String formatDateStr2Desc(String strDate, String outFormat) {

        DateFormat df = new SimpleDateFormat(dateFormatYMDHMS);
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c2.setTime(df.parse(strDate));
            c1.setTime(new Date());
            int d = getOffectDay(c1.getTimeInMillis(), c2.getTimeInMillis());
            if (d == 0) {
                int h = getOffectHour(c1.getTimeInMillis(),
                        c2.getTimeInMillis());
                if (h > 0) {
                    return h + "小时前";
                } else if (h < 0) {
                    return Math.abs(h) + "小时后";
                } else if (h == 0) {
                    int m = getOffectMinutes(c1.getTimeInMillis(),
                            c2.getTimeInMillis());
                    if (m > 0) {
                        return m + "分钟前";
                    } else if (m < 0) {
                        return Math.abs(m) + "分钟后";
                    } else {
                        return "刚刚";
                    }
                }
            } else if (d > 0) {
                if (d == 1) {
                    return "昨天";
                } else if (d == 2) {
                    return "前天";
                } else if (d < 30) {
                    return d + "天前";
                } else {
                    return getOldDate(d);
                }
            } else if (d < 0) {
                if (d == -1) {
                    return "明天";
                } else if (d == -2) {
                    return "后天";
                }
                return Math.abs(d) + "天后";
            }

            String out = getStringByFormat(strDate, outFormat);
            if (!TextUtils.isEmpty(out)) {
                return out;
            }
        } catch (Exception e) {
        }

        return strDate;
    }

    /**
     * 描述：计算两个日期所差的天数.
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的天数
     */
    public static int getOffectDay(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        // 先判断是否同年
        int y1 = calendar1.get(Calendar.YEAR);
        int y2 = calendar2.get(Calendar.YEAR);
        int d1 = calendar1.get(Calendar.DAY_OF_YEAR);
        int d2 = calendar2.get(Calendar.DAY_OF_YEAR);
        int maxDays = 0;
        int day = 0;
        if (y1 - y2 > 0) {
            maxDays = calendar2.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 + maxDays;
        } else if (y1 - y2 < 0) {
            maxDays = calendar1.getActualMaximum(Calendar.DAY_OF_YEAR);
            day = d1 - d2 - maxDays;
        } else {
            day = d1 - d2;
        }
        return day;
    }

    /**
     * 描述：计算两个日期所差的小时数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的小时数
     */
    public static int getOffectHour(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int h1 = calendar1.get(Calendar.HOUR_OF_DAY);
        int h2 = calendar2.get(Calendar.HOUR_OF_DAY);
        int h = 0;
        int day = getOffectDay(date1, date2);
        h = h1 - h2 + day * 24;
        return h;
    }

    /**
     * 描述：计算两个日期所差的分钟数.
     *
     * @param date1 第一个时间的毫秒表示
     * @param date2 第二个时间的毫秒表示
     * @return int 所差的分钟数
     */
    public static int getOffectMinutes(long date1, long date2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(date1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(date2);
        int m1 = calendar1.get(Calendar.MINUTE);
        int m2 = calendar2.get(Calendar.MINUTE);
        int h = getOffectHour(date1, date2);
        int m = 0;
        m = m1 - m2 + h * 60;
        return m;
    }

    /**
     * 描述：获取指定日期时间的字符串,用于导出想要的格式.
     *
     * @param strDate String形式的日期时间，必须为yyyy-MM-dd HH:mm:ss格式
     * @param format  输出格式化字符串，如："yyyy-MM-dd HH:mm:ss"
     * @return String 转换后的String类型的日期时间
     */
    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
                    "MM-dd HH:mm");
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    /**
     * 获取前n天日期、后n天日期
     * @param distanceDay 前几天 如获取前7天日期则传-7即可；如果后7天则传7
     * @return
     */
    public static String getOldDate(int distanceDay) {
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + distanceDay);
        Date endDate = null;
        try {
            endDate = dft.parse(dft.format(date.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dft.format(endDate);
    }

    /**
     * 格式化日期字符串
     *
     * @param currentTime
     * @return
     */
    public static String formatString(String currentTime) {
        DateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return format.format(currentTime);
    }

    /* 时间戳转日期
     * @param ms
     * @return
     */
    public static Date transForDate(Long ms) {
        if (ms == null) {
            ms = (long) 0;
        }
        long msl = (long) ms ;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date temp = null;
        if (ms != null) {
            try {
                String str = sdf.format(msl);
                temp = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return temp;
    }


    /**
     * 获取类似以  2019.09.09 12：00 - 15：00
     * @param create_date_l
     * @param end_date_l
     * @return
     */
    public static String get_Date_str(long create_date_l,long end_date_l){
        Date create_date = DateHelper.transForDate(create_date_l);
        Date end_date = DateHelper.transForDate(end_date_l);
        String start_hour = "";
        if (create_date.getHours() <= 9) {
            start_hour = "0" + create_date.getHours();
        } else {
            start_hour = "" + create_date.getHours();
        }
        String start_minute = "";
        if (create_date.getMinutes() <= 9) {
            start_minute = "0" + create_date.getMinutes();
        } else {
            start_minute = "" + create_date.getMinutes();
        }

        String end_hour = "";
        if (end_date.getHours() <= 9) {
            end_hour = "0" + end_date.getHours();
        } else {
            end_hour = "" + end_date.getHours();
        }
        String end_minute = "";
        if (end_date.getMinutes() <= 9) {
            end_minute = "0" + end_date.getMinutes();
        } else {
            end_minute = "" + end_date.getMinutes();
        }

        return (create_date.getYear() + 1900) + "." + (create_date.getMonth() + 1) + "." + create_date.getDate()
                + " " + start_hour + ":" + start_minute
                + "-" + end_hour + ":" + end_minute;
    }
    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     * @return
     */
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }


    /**
     * 根据时间戳获取年份
     *
     * @param start_time
     * @return
     */
    public static String get_year(long start_time) {
        Date date = transForDate(start_time);
        String year = (date.getYear() + 1900) + "";
        return year;
    }

    /**
     * 根据时间戳获取月份
     *
     * @param start_time
     * @return
     */
    public static String get_month(long start_time) {
        Date date = transForDate(start_time);
        String month = "";
        if ((date.getMonth() + 1) <= 9) {
            month = "0" + (date.getMonth() + 1);
        } else {
            month = "" + (date.getMonth() + 1);
        }
        return month;
    }
    /**
     * 根据时间戳获取日期
     *
     * @param start_time
     * @return
     */
    public static String get_date(long start_time) {
        Date date = transForDate(start_time);
        String dates = "";
        if (date.getDate() <= 9) {
            dates = "0" + date.getDate();
        } else {
            dates = "" + date.getDate();
        }
        return dates;
    }
    /**
     * 根据时间戳获取小时
     * @param start_time
     * @return
     */
    public static int get_hour(long start_time) {
        Date date = transForDate(start_time);
        int hour = date.getHours();
        return hour;
    }

    /**
     * 根据时间戳获取分钟
     * @param start_time
     * @return
     */
    public static int get_minite(long start_time) {
        Date date = transForDate(start_time);
        int hour = date.getMinutes();
        return hour;
    }


    public static String timeDay(long start_time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTimeInMillis(start_time);
        return simple1.format(calendar.getTimeInMillis());
    }

    public static String timeHourAndMinite(long start_time){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simple1 = new SimpleDateFormat("HH:mm");
        calendar.setTimeInMillis(start_time);
        return simple1.format(calendar.getTimeInMillis());
    }
}
