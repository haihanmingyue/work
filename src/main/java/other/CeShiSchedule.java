package other;


import Util.MybatisUtil;

import java.util.List;


/**
 * 测试用
 * */
public class CeShiSchedule {
    private int id;
    private String username;
    private String duty;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public int dutyID(String s) {
        int ori = 0;
        switch (s){
            case "早班":
                ori = 10001;
                break;
            case "小早":
                ori = 10002;
                break;
            case "审方":
                ori = 10003;
                break;
            case "排药":
                ori = 10004;
                break;
            case "电话":
                ori = 10005;
                break;
            case "临时":
                ori = 10006;
                break;
            case "休息":
                ori = 10007;
                break;
            case "夜班":
                ori = 10008;
                break;
            case "核对":
                ori = 10009;
                break;
            case "长1":
                ori = 10010;
                break;
            case "长2":
                ori = 10011;
                break;
            case "长3":
                ori = 10012;
                break;
            case "晚班":
                ori = 10013;
                break;
        }
        return ori;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }





    /**
     * 搜索全部
     * */
    public static List<CeShiSchedule> getAllSchedule(){
        List<CeShiSchedule> list = null;
        try{
            list = MybatisUtil.getSqlSession().getMapper(CeShiScheduleDao.class).getAllSchedule();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        MybatisUtil.closeSqlSession();
        return list;
    }


    /**
     * 插入数据
     * */
    public static boolean insertSchedule(CeShiSchedule ceShiSchedule){
        try {
            MybatisUtil.getSqlSession().getMapper(CeShiScheduleDao.class).insertSchedule(ceShiSchedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            return false;
        }
    }






}
