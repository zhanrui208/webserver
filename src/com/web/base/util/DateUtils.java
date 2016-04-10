package com.web.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 类说明:
 * 
 * @author <a href="mailto:guolei_mao@kingdee.com">guolei_mao</a>
 * @version 1.0, 2011-6-1
 */
public class DateUtils {


    private static String yearmonthPattern = "yyyy-MM";

    private static String defaultDatePattern = "yyyy-MM-dd";

    private static String defaultTimePattern = "HH:mm:ss";

    private static String currentDateTimePattern = "yyyyMMddHHmmss";
    
    private static String currentDateTimePattern2 = "yyyy-MM-dd HH:mm:ss";

    public static Date addDay(Date startDate, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        cal.add(Calendar.DAY_OF_MONTH, i);
        return cal.getTime();
    }

    public static Date strToDate(String date, String format) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date currentTimeAddDay(int i) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, i);
        return cal.getTime();
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    // 获取date当天的开始时间，即天后的时间清零
    public static Date getDay(Date date) {
        Calendar c = getDayCalendar(date);
        return c.getTime();
    }

    // 获取date的小时开始时间，即小时后的时间清零
    public static Date getHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        clear(cal);
        return cal.getTime();
    }

    // 获取date下一天的开始时间
    public static Date getNextDay(Date date) {
        Calendar cal = getDayCalendar(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    private static void clear(Calendar cal) {
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);
    }

    private static Calendar getDayCalendar(Date date) {
        Calendar d = Calendar.getInstance();
        d.setTime(date);

        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(d.get(Calendar.YEAR), d.get(Calendar.MONTH), d.get(Calendar.DAY_OF_MONTH));
        return c;
    }


    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    public static String getCurYear() {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(c.YEAR));
    }

    /**
     * 获得默认的 time pattern
     */
    public static String getTimePattern() {
        return defaultTimePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    public static String getYearmonth(Date date) {
        return format(date, yearmonthPattern);
    }

    public static String getYearmonthDay(Date date) {
        return format(date, defaultDatePattern);
    }
    /**
     * 得到当前的日期和时间，如果是多个服务器，如果服务器时间不同步， 则需要使用这个方法取得同步的日期和时间


     * 
     */
    public static Date getDate() {
        Date today = new Date();
        return today;
    }

    public static long getLongDate() {
        Date today = new Date();
        return today.getTime();
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";

        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }

        return (returnValue);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        if (strDate!=null && strDate.length()>13){
            return parse(strDate, getDatePattern()+ " " +getTimePattern());
        }
        return parse(strDate, getDatePattern());
    }

    /**
     * @author lian_lin 2013-03-08
     * @param strDate 待转换的日期
     * @return 将只能日期的字符串转化Date类的.自动补充当前时间的小时,分钟,秒
     * @throws ParseException
     */
    public static Date parseJoinTime(String strDate) throws ParseException {
        Date date = null;
        if(StringUtils.isBlank(strDate)||strDate.length()!=10){
            return date;
        }
        try{
             Date nowDate = getDate();
            String strNowDate = format(nowDate,getDatePattern()+ " " +getTimePattern());
            strDate += strNowDate.substring(10);
            date = parse(strDate,  getDatePattern()+ " " +getTimePattern());
        }catch(Exception e){
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern)
            throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.parse(strDate);
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    public static Date addMonth(int n) {
        return addMonth(getDate(), n);
    }


    /**
     * @author lian_lin
     * @param date 需要增加小时的日期
     * @param n 增加小时数值
     * @return在当前日期上增加数个小时
     */
    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }

    /**
     * @author linlian 
     * @param date 需要增加秒数日期
     * @param n 增加秒数值
     * @return 增加秒数后的日期
     */
    public static Date addSecond(Date date,int n){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }

    /**
     * @author linlian 
     * @param date 需要增加分钟数日期
     * @param n 增加分钟数值
     * @return 增加分钟数后的日期
     */
    public static Date addMinute(Date date,int n){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }


   /**
    * @author linlian 
    * @param n 增加天数值
    * @return 在当前日期增加天数的日期
    */
    public static Date addDay(int n) {
        return addDay(getDate(), n);
    }

    /**
     * @author linlian  
     * @param date1 日期比较值
     * @param date2 日期比较值
     * @return 比较两个日期的大小，如果date1小于date2 返回false否则返回true.
     */
    public static boolean compare(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        // 如果date1在date2前面，表明date1比较小
        if (cal1.before(cal2)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * @author linlian  
     * @return 返回当前日期精确毫秒.
     */
    public static String getOnlyID() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMDDhhmmssSSS");
        double dblTmp;
        for (dblTmp = Math.random() * 100000D; dblTmp < 10000D; dblTmp = Math
                .random() * 100000D)
            ;
        String strRnd = String.valueOf(dblTmp).substring(0, 4);
        String s = df.format(getDate()) + strRnd;
        return s;
    }

    /**
     * @author linlian   
     * @param Date date
     * @return Date数组，[0]为第一天的日期，[1]最后一天的日期 获取给定时间所在周的第一天(Sunday)的日期和最后一天(Saturday)的日期 *
     */
    public static Date[] getWeekStartAndEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getWeekStartAndEndDate(cal);
    }

    /**
     * @author linlian   
     * @param calendar *
     * @return Date数组，[0]为第一天的日期，[1]最后一天的日期 获取给定时间所在周的第一天(Sunday)的日期和最后一天(Saturday)的日期 *
     */
    public static Date[] getWeekStartAndEndDate(Calendar calendar) {
        Date[] dates = new Date[2];
        Date firstDateOfWeek, lastDateOfWeek; // 得到当天是这周的第几天
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // 减去dayOfWeek,得到第一天的日期，因为Calendar用０－６代表一周七天，所以要减一
        calendar.add(Calendar.DAY_OF_WEEK, -(dayOfWeek - 1));
        firstDateOfWeek = calendar.getTime(); // 每周7天，加６，得到最后一天的日子
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        lastDateOfWeek = calendar.getTime();
        dates[0] = firstDateOfWeek;
        dates[1] = lastDateOfWeek;
        return dates;
    }

    /**
     * @author linlian   
     * @param calendar *
     * @return Date数组，[0]为第一天的日期，[1]最后一天的日期 获取给定时间所在月的第一天Ｆ的日期和最后一天的日期 * *
     */
    public static Date[] getMonthStartAndEndDate(Calendar calendar) {
        Date[] dates = new Date[2];
        Date firstDateOfMonth, lastDateOfMonth; // 得到当天是这月的第几天
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // 减去dayOfMonth,得到第一天的日期，因为Calendar用０代表每月的第一天，所以要减一
        calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        firstDateOfMonth = calendar.getTime(); // calendar.getActualMaximum(Calendar.DAY_OF_MONTH)得到这个月有几天
        calendar.add(Calendar.DAY_OF_MONTH, calendar
                .getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
        lastDateOfMonth = calendar.getTime();
        dates[0] = firstDateOfMonth;
        dates[1] = lastDateOfMonth;
        return dates;
    }

    /* 获取系统时间 返回 Calendar */
    public static Calendar getCurrentDate() {
        Calendar now = Calendar.getInstance();
        return now;
    }

    /* 返回时间戳类型 */
    public static Calendar getCalendar(String strDate) {
        Calendar c1 = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = df.parse(strDate);
            //System.out.println(date);
            c1 = Calendar.getInstance();
            c1.setTime(date);
            //System.out.println(c1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c1;
    }

    /**
     * @author linlian  
     * @param strDate 待指定日期
     * @return 指定日期月份有多少天
     */
    public static int getMonthCount(String strDate) {
        int day = 0;
        try {
            // String strDate = "2006-9-1";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            Date date = sdf.parse(strDate);
            calendar.setTime(date);
            int month = calendar.get(Calendar.MONTH) + 1;
            day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            //System.out.println(month + "月：" + day + "天");
        } catch (Exception ex) {
        }
        return day;
    }


    /**
     * @author linlian  
     * @param d1 
     * @param d2
     * @return 计算2个日期间的小时差值
     */
    public static int getHours(String d1, String d2) {
        return (int)Math.abs(getDoubleHours(d1,d2));
    }

    /**
     * @author linlian  
     * @param d1 
     * @param d2
     * @return 计算2个日期间的小时差值 返回float类型
     */
    public static double getDoubleHours(String d1, String d2) {
        double hour=0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date now = df.parse(d1);
            java.util.Date date = df.parse(d2);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            hour = ((double)l / (60 * 60 * 1000) - day * 24);
            day=Math.abs(day);
            if(day!=0)
            {
                hour=Math.abs(hour);
                hour=hour+24*day;
            }
        } catch (Exception e) {
        }
        return (double)Math.abs(hour);
    }

    public static String getCurrentDateTime() {
        Date date = new Date();
        return format(date, currentDateTimePattern);
    }

    public static String getCurrentDateTime2() {
        Date date = new Date();
        return format(date, currentDateTimePattern2);
    }
    
    /**
     * @author linlian  
     * @param strBeginDate
     * @return 主要用于在HQL查询,时间参数格式化
     * @throws ParseException
     */
    public static Date getBeginDate(String strBeginDate)throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strBeginDate+" 00:00:00");
    }


    /**
     * @author linlian  
     * @param strEndDate
     * @return 主要用于在HQL查询,时间参数格式化
     * @throws ParseException
     */
    public static Date getEndDate(String strEndDate)throws ParseException{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.parse(strEndDate+" 23:59:59");
    }

    /**
     * @author linlian  
     * @param beginDate
     * @return 返回指定日期的时间 :00:00:00
     * @throws ParseException
     */
    public static Date getBeginDate(Date beginDate)throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(beginDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }


    /**
     * @author linlian  
     * @param endDate
     * @return 返回指定日期的时间 :23:59:59
     * @throws ParseException
     */
    public static Date getEndDate(Date endDate)throws ParseException{
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }



    public static int getDayOfWeek(Date date){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);          
        return cal.get(java.util.Calendar.DAY_OF_WEEK)-1;
    }
    public static int getDayOfMonth(Date date){
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);          
        return cal.get(java.util.Calendar.DAY_OF_MONTH);
    }


    /**
        * @author wk
        * @param datefrom 
        * @param dateto
        * @return 返回两日期之前相差的天数
        */
      public static int betweenDays(Date datefrom,Date dateto){
            Calendar fromCalendar = clearHours(datefrom);  
            Calendar toCalendar = clearHours(dateto);
            long l = fromCalendar.getTime().getTime() - toCalendar.getTime().getTime();
         int day = (int) (l/(24 * 60 * 60 * 1000));
         return day;
      }

      /**
          * @author wk
          * @param date
          * @return calendar中只保存年月日的信息
          */
      public static Calendar clearHours(Date date){
            Calendar calendar = Calendar.getInstance();  
            calendar.setTime(date);  
            calendar.set(Calendar.HOUR_OF_DAY, 0);  
            calendar.set(Calendar.MINUTE, 0);  
            calendar.set(Calendar.SECOND, 0);  
            calendar.set(Calendar.MILLISECOND, 0); 
            return calendar;
      }

      /**
       * @author lian_lin
       * @param beginDate 开始日期
       * @param endDate 结束日期
       * @return 两个日期相隔多少个月
       */
      public static int betweenMonths(Date beginDate, Date endDate) {
        int iMonth = 0;
        try {
            Calendar beginCal = Calendar.getInstance();
            beginCal.setTime(beginDate);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            iMonth = ((endCal.get(Calendar.YEAR) - beginCal.get(Calendar.YEAR)) * 12 + endCal
                    .get(Calendar.MONTH)) - beginCal.get(Calendar.MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMonth;
    }


    /**
     * @author lian_lin
     * @param beiginDate 开始日期
     * @param endDate 结束日期
     * @return 返回两日期之前相差的天数
     */
    public static int betweenDay(Date beiginDate, Date endDate) {
        Calendar fromCalendar = clearHours(beiginDate);
        Calendar toCalendar = clearHours(endDate);
        long l = toCalendar.getTime().getTime()
                - fromCalendar.getTime().getTime();
        int day = (int) (l / (24 * 60 * 60 * 1000));
        return day;
    }


      /**
       * @author lian_lin
       * @param beiginDate 开始日期
       * @param endDate 结束日期
       * @return 两个日期相隔多少个星期
       */
      public static int beteenWeek(Date beiginDate,Date endDate){
         int CONST_WEEK = 7*24*60*60*1000;
          Calendar before = Calendar.getInstance();
          Calendar after = Calendar.getInstance();
          before.setTime(beiginDate);
          after.setTime(endDate);
          int week = before.get(Calendar.DAY_OF_WEEK);
          before.add(Calendar.DATE, -week);
          week = after.get(Calendar.DAY_OF_WEEK);
          after.add(Calendar.DATE, 7 - week);
          int iweek = (int) ((after.getTimeInMillis() - before
                  .getTimeInMillis()) / CONST_WEEK);
          iweek = iweek - 1;
           return iweek;
      }

      /**
       * @author lian_lin
       * @param date
       * @return 获取指定日期的小时数 例如"2013-08-08 21:30:44" 返回21
       */
      public static int getHourOfInt(Date date){
        Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          return cal.get(Calendar.HOUR_OF_DAY);
      }
      /**
       * @author lian_lin
       * @param date
       * @return 获取指定日期的分钟数 例如"2013-08-08 21:30:44" 返回30
       */
      public static int getMinuteOfInt(Date date){
        Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          return cal.get(Calendar.MINUTE);
      }

      /**
       * @author lian_lin
       * @param date 需要重置的时间
       * @param hour 重置小时数
       * @param min  重置分钟数
       * @return 重置后时间
       */
    public static Date resetHourAndMin(Date date, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return cal.getTime();
    }

    public static Map<Integer, String>  kdweiboWeek = null;
    static{
        kdweiboWeek = new LinkedHashMap<Integer, String>();
        kdweiboWeek.put(0, "星期天");
        kdweiboWeek.put(1, "星期一");
        kdweiboWeek.put(2, "星期二");
        kdweiboWeek.put(3, "星期三");
        kdweiboWeek.put(4, "星期四");
        kdweiboWeek.put(5, "星期五");
        kdweiboWeek.put(6, "星期六");
    }

    /**
      * @author lian_lin
     * @param no 
     * @return 返回星期中文值 ，例如 1，返回星期一
     */
    public static String getWeekLocalString(int no){
        if( no<0||no>0){
            return "";
        }
        return kdweiboWeek.get(no);
    }

    /**
     * @author lian_lin
     * @param date
     * @return 根据日期返回对应的星期值，例如 2013-08-28 返回星期三
     */
    public static String getWeekLocalString(Date date){
        int no = getDayOfWeek(date);
        return getWeekLocalString(no);
    }


}
