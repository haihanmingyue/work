package Util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Timestamp getNowDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        Date date1 = null;
        try {
            date1 = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        long longTime = date1.getTime();
        return new Timestamp(longTime);
    }

    public static Timestamp strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try{
            date1 = formatter.parse(strDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        assert date1 != null;
        long longTime = date1.getTime();
        return new Timestamp(longTime);
    }

    public static Date STRToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try{
            date1 = formatter.parse(strDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date1;
    }

    /**
     * yyyy-MM-dd
     * */
    public static String getNowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }

    /**
     * yyyy-MM
     * */
    public static String NowTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        return formatter.format(new Date());
    }

    public static String DateToString(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    public static Date StringToDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.parse(date);
    }

    /**
     * 一个月有多少天
     * */
    public static int maxDay(String date){
        Date dd = DateUtil.STRToDate(date);
        Calendar c = Calendar.getInstance();
        c.setTime(dd);
        return c.getActualMaximum(Calendar.DATE);
    }

    /**
     * 两个日期相差多少天
     * */
    public static long TwoDateSum(String s1,String s2) throws Exception{
        long betweenDate = 0;
        if(!s1.equals("") && !s2.equals("")) {
            //设置转换的日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //开始时间
            Date startDate = sdf.parse(s1);
            //结束时间
            Date endDate = sdf.parse(s2);
            //得到相差的天数 betweenDate
            betweenDate = (endDate.getTime() - startDate.getTime()) / (60 * 60 * 24 * 1000);
        }
        return betweenDate;
    }


    public static String[] DateToStrings(String date){
        return date.split("-");
        }

}
