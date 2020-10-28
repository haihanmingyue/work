package dao;



import Util.MybatisUtil;
import java.util.List;

public class Schedule {
    private int id;
    private String employeeID;
    private String employeeName;
    private int dutyID;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public int getDutyID() {
        return dutyID;
    }

    public void setDutyID(int dutyID) {
        this.dutyID = dutyID;
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

    public String getDutyName() {

        return dutyName();
    }

    public String dutyName(){
        String dutyname = null;
        switch (getDutyID()){
            case 10001:
                dutyname = "早班";
                break;
            case 10002:
                dutyname = "小早";
                break;
            case 10003:
                dutyname = "审方";
                break;
            case 10004:
                dutyname = "核对";
                break;
            case 10005:
                dutyname = "排药";
                break;
            case 10006:
                dutyname = "临时";
                break;
            case 10007:
                dutyname = "电话";
                break;
            case 10008:
                dutyname = "长1";
                break;
            case 10009:
                dutyname = "长2";
                break;
            case 10010:
                dutyname = "长3";
                break;
            case 10011:
                dutyname = "晚班";
                break;
            case 10012:
                dutyname = "请假";
                break;
            case 10013:
                dutyname = "休息";
                break;
            case 10014:
                dutyname = "夜班";
        }
        return dutyname;
    }


    /**
     * 搜索全部
     * */
    public static List<Schedule> getAllSchedule(){
        List<Schedule> list = null;
        try{
            list = MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).getAllSchedule();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        MybatisUtil.closeSqlSession();
        return list;
    }
    /**
     * 搜索全部,通过月份
     * */
    public static List<Schedule> Schedule(String date){
        List<Schedule> list = null;
        try{
            list = MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).getScheduleList(date);
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        MybatisUtil.closeSqlSession();
        return list;
    }


    public static List<Schedule> getScheduleListByKey(String key){
        List<Schedule> list = null;
        try{
            list = MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).getScheduleListByKey(key);
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取对应ID的姓名
     * */
    public String getEmployeeName() {
        String username = getEmployeeID();
        String name = "";
        UserMessage userMessage = MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).selectUserMessage(username);
        MybatisUtil.closeSqlSession();
        if(userMessage!=null){
            name = userMessage.getName();
        }
        return name;
    }

    /**
     * 个人通过日期搜索
     * */
    public static Schedule PersonSelectByDate(String username,String date){
        Schedule schedule = null;
        try{
            schedule =  MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).PersonSelectByDate(username,date);
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        return schedule;
    }

    /**
     * 更新班次
     * */
    public  static void updateScheduleDuty(Schedule schedule){
        try {
            MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).updateScheduleDuty(schedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
        }
    }

    public  static void updateScheduleMan(Schedule schedule){
        try {
            MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).updateScheduleMan(schedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
        }
    }


    /**
     * 插入数据
     * */
    public static boolean insertSchedule(Schedule schedule){
        try {
            MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).insertSchedule(schedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            return false;
        }
    }

    public  static void updateScheduleDayAndDuty(Schedule schedule){
        try{
            MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).updateScheduleDayAndDuty(schedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
        }
    }

    public  static List<Schedule> selectByUsername(String username, String date ){
        List<Schedule> list = null;
        try{
            list = MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).selectByUsername(username, date);
            MybatisUtil.closeSqlSession();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
        }
        return list;
    }

    public static boolean deleteSchedule(Schedule schedule){
        boolean flag = false;
        try{
            MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).deleteSchedule(schedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean updateScheduleIDManDate(Schedule schedule){
        boolean flag = false;
        try{
            MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).updateScheduleIDManDate(schedule);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return flag;
    }



}
