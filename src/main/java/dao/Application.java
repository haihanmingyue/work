package dao;

import Util.MybatisUtil;

import java.util.List;

public class Application {
    private int id;
    private String applicationDate;//偷懒用string
    private String auditDate;
    //类别ID
    private int applicationType;
    //审核状态
    private int applicationStatus;
    private String employeeID;
    private String employedID;
    private String startDate;
    private String endDate;
    private int origindutyID;
    private int finishdutyID;
    //申请原因
    private String applicationReason;
    private String auditor;


    public int getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(int applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatusName() {
        String name = null;
        switch (getApplicationStatus()){
            case 1:
                name = "通过";
                break;
            case 0:
                name = "未通过";
                break;
            case 3:
                name = "等待审核";
                break;
            case 4:
                name = "已过期";
                break;
        }
        return name;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplyTypename() {
        String name = null;
        switch (getApplicationType()){
            case 1:
                name = "请假";
                break;
            case 0:
                name = "换班";
                break;
        }
        return name;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getOrigindutyID() {
        return origindutyID;
    }

    public void setOrigindutyID(int origindutyID) {
        this.origindutyID = origindutyID;
    }

    public int getFinishdutyID() {
        return finishdutyID;
    }

    public void setFinishdutyID(int finishdutyID) {

        this.finishdutyID = finishdutyID;
    }

    public int setdutyID(String dutyname) {
        int ori = 0;
        switch (dutyname){
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

    public String getStartDutyName() {
        return dutyName(getOrigindutyID());
    }

    public String getEndDutyName() {
        return dutyName(getFinishdutyID());
    }

    public String dutyName(int i){
        String dutyname = null;
        switch (i){
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
                dutyname = "排药";
                break;
            case 10005:
                dutyname = "电话";
                break;
            case 10006:
                dutyname = "临时";
                break;
            case 10007:
                dutyname = "休息";
                break;
            case 10008:
                dutyname = "夜班";
                break;
            case 10009:
                dutyname = "核对";
                break;
            case 10010:
                dutyname = "长1";
                break;
            case 10011:
                dutyname = "长2";
                break;
            case 10012:
                dutyname = "长3";
                break;
            case 10013:
                dutyname = "晚班";
                break;
        }
        return dutyname;
    }


    public String getApplicationReason() {
        return applicationReason;
    }

    public void setApplicationReason(String applicationReason) {
        this.applicationReason = applicationReason;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }


    public String getEmployedID() {
        return employedID;
    }

    public void setEmployedID(String employedID) {
        this.employedID = employedID;
    }

    public String getEmployeeName() {
        return NAME(getEmployeeID());
    }

    public String getEmployeedName() {
        return NAME(getEmployedID());
    }

    //通过ID获取名字
    private String NAME(String ID){
       UserMessage userMessage =  MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).selectUserMessage(ID);
       MybatisUtil.closeSqlSession();
       return userMessage.getName();
    }

    //select
    public static List<Application> AllApplication(){
        List<Application> list = MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).getApplyList();
        MybatisUtil.closeSqlSession();
        return list;
    }

    public static Application selectByDate(String s,String date){
        Application application = null;
        try{
            application =  MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).selectByDate(s,date);
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        return application;
    }

    public static List<Application> AllApplication(String date){
        List<Application> list = MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).SelectByDate(date);
        MybatisUtil.closeSqlSession();
        return list;
    }

    //update
    public static boolean updateStatus(Application application){
        boolean flag ;
        try{
            MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).updateStatus(application);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    //add
    public static boolean insertHuanBanApply(Application application){
        boolean flag= false;
        try {
            MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).insertHuanBanApply(application);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag =true;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return flag;
    }


}
