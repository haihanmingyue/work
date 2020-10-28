package controller;


import dao.*;
import Util.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public  class AdminQueryApplyController<event>extends AdminIndexController implements Initializable {

    private static Logger logger = Logger.getLogger(AdminQueryApplyController.class);


    @FXML
    VBox HuanBan,QingJia;
    @FXML
    TextArea Qreason,reason;
    @FXML
    TextField Qapplyname,Qtime2,Qtime1,Qsum,Qtime3,QshenheZT,QshenheName,Qtime4,name3;
    @FXML
    TextField applyname,applyname2,time2,time1,banci1,banci2,time3,shenheZT,shenheName,time4,name2,name1;
    @FXML
    JFXButton QJBUTONGGUO,QJTAONGGUO,HBBUTONGGUO,HBTAONGGUO;

    private String temp;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        temp = StageHashMap.USERMessage.get("Type");
        System.out.print(flag);
        if(temp.equals("换班")){
            HuanBan.setManaged(true);
            HuanBan.setVisible(true);
            QingJia.setVisible(false);
            QingJia.setManaged(false);
            HuanBanApplyXiangXi(StageHashMap.USERMessage.get("SHUSER"),StageHashMap.USERMessage.get("DATE"));
            if( StageHashMap.USERMessage.get("STATUS").equals("通过") || StageHashMap.USERMessage.get("STATUS").equals("未通过")){
                HBBUTONGGUO.setVisible(false);
                HBTAONGGUO.setManaged(false);

            }
        }

        if(temp.equals("请假")){
            HuanBan.setManaged(false);
            HuanBan.setVisible(false);
            QingJia.setVisible(true);
            QingJia.setManaged(true);
            XiuJiaApplyXiangXi(StageHashMap.USERMessage.get("SHUSER"),StageHashMap.USERMessage.get("DATE"));
            if( StageHashMap.USERMessage.get("STATUS").equals("通过") || StageHashMap.USERMessage.get("STATUS").equals("未通过")){
                QJBUTONGGUO.setVisible(false);
                QJTAONGGUO.setManaged(false);
            }
        }
    }


    //窗口关闭
    @Override
    public void closeAction(ActionEvent event) {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        primaryStage.hide();
    }

    public void HuanBanApplyXiangXi(String s,String date){
        try{
            Application application = Application.selectByDate(s,date);
            applyname.setText(application.getEmployeeID());
            name1.setText(application.getEmployeeName());
            applyname2.setText(application.getEmployedID());
            name2.setText(application.getEmployeedName());
            time1.setText(application.getStartDate());
            time2.setText(application.getEndDate());
            banci1.setText(application.getStartDutyName());
            banci2.setText(application.getEndDutyName());
            time3.setText(application.getApplicationDate());
            shenheZT.setText(application.getApplicationStatusName());
            shenheName.setText(application.getAuditor());
            time4.setText(application.getAuditDate());
            reason.setText(application.getApplicationReason());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void XiuJiaApplyXiangXi(String s,String date){
        try{
            Application application = Application.selectByDate(s,date);
            Qapplyname.setText(application.getEmployeeID());
            name3.setText(application.getEmployeeName());
            Qtime1.setText(application.getStartDate());
            Qtime2.setText(application.getEndDate());
            Qsum.setText(String.valueOf(DateUtil.TwoDateSum(application.getStartDate(),application.getEndDate())));
            Qtime3.setText(application.getApplicationDate());
            QshenheZT.setText(application.getApplicationStatusName());
            QshenheName.setText(application.getAuditor());
            Qtime4.setText(application.getAuditDate());
            Qreason.setText(application.getApplicationReason());

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void HBBUTONGGUO(ActionEvent event) {
        new DialogBuilder(HBBUTONGGUO).setTitle("提示").setMessage("确定不通过吗？").setNegativeBtn("取消").setPositiveBtn("确定",this::BuTongyi,"-fx-background-color: #ffffff").create(300,100);
    }

    public void HBTAONGGUO(ActionEvent event) {
        new DialogBuilder(HBTAONGGUO).setTitle("提示").setMessage("确定通过吗？").setNegativeBtn("取消").setPositiveBtn("确定",this::Tongyi,"-fx-background-color: #ffffff").create(300,100);
    }

    public void QJBUTONGGUO(ActionEvent event) {
        new DialogBuilder(QJBUTONGGUO).setTitle("提示").setMessage("确定不通过吗？").setNegativeBtn("取消").setPositiveBtn("确定",this::BuTongyi,"-fx-background-color: #ffffff").create(300,100);
    }

    public void QJTAONGGUO(ActionEvent event) {
        new DialogBuilder(QJTAONGGUO).setTitle("提示").setMessage("确定通过吗？").setNegativeBtn("取消").setPositiveBtn("确定",this::Tongyi,"-fx-background-color: #ffffff").create(300,100);
    }
    private void Tongyi(){

        UserMessage userMessage = UserMessage.selectUserMessage(StageHashMap.USERS.get("userName"));
        Application application = new Application();
        application.setId(StageHashMap.USERMessageInt.get("ID"));
        application.setAuditor(userMessage.getName());
        application.setAuditDate(DateUtil.getNowTime());
        application.setApplicationStatus(1);

        try{
            if(temp.equals("换班")){

                H(applyname.getText().trim(),time2.getText().trim(),banci2.getText().trim(),
                 applyname2.getText().trim(), time1.getText().trim(),banci1.getText().trim());
                Application.updateStatus(application);
                HuanBanApplyXiangXi(applyname.getText().trim(),time3.getText().trim());
            }else if(temp.equals("请假")){
                int day = Integer.parseInt(Qsum.getText().trim());
                Q(Qapplyname.getText().trim(),applyname2.getText().trim(),day);
                Application.updateStatus(application);
                XiuJiaApplyXiangXi(Qapplyname.getText().trim(),Qtime3.getText().trim());
            }
            QJBUTONGGUO.setVisible(false);
            QJTAONGGUO.setVisible(false);
            HBTAONGGUO.setVisible(false);
            HBBUTONGGUO.setVisible(false);
            logger.info("管理员:"+StageHashMap.USERS.get("userName")+"("+userMessage.getName()+")"+"同意了用户"+applyname.getText().trim()+"的申请");
            new DialogBuilder(gb).setTitle("提示").setMessage("执行完毕").setNegativeBtn("取消").setPositiveBtn("确定","#ffffff").create(300,100);

        }catch (Exception e){

            new DialogBuilder(gb).setTitle("提示").setMessage("执行失败").setNegativeBtn("取消").setPositiveBtn("确定","#ffffff").create(400,100);
            e.printStackTrace();
        }

    }

    private void BuTongyi(){

        UserMessage userMessage = UserMessage.selectUserMessage(StageHashMap.USERS.get("userName"));
        Application application = new Application();
        application.setId(StageHashMap.USERMessageInt.get("ID"));
        application.setAuditor(userMessage.getName());
        application.setAuditDate(DateUtil.getNowTime());
        application.setApplicationStatus(0);

        if(Application.updateStatus(application)){
            new DialogBuilder(gb).setTitle("提示").setMessage("执行完毕").setNegativeBtn("取消").setPositiveBtn("确定","#ffffff").create(300,100);
            QJBUTONGGUO.setVisible(false);
            QJTAONGGUO.setVisible(false);
            HBTAONGGUO.setVisible(false);
            HBBUTONGGUO.setVisible(false);
            logger.info("管理员:"+StageHashMap.USERS.get("userName")+"("+userMessage.getName()+")"+"拒绝了用户"+applyname2.getText().trim()+"的申请");
        }else {
            new DialogBuilder(gb).setTitle("提示").setMessage("执行失败").setNegativeBtn("取消").setPositiveBtn("确定","#ffffff").create(300,100);
        }
    }

    //审核请假时候执行的数据库代码（ 日期 ，请假人 ，请假天数）
    private static void Q(String date,String username,int dayNumber){

        String[]s2 = date.split("-");
        int maxDate = DateUtil.maxDay(date);
        System.out.println(maxDate);
        int year = Integer.parseInt(s2[0]);
        int month = Integer.parseInt(s2[1]);
        int day = Integer.parseInt(s2[2]);
        String months ;
        String days ;
        for(int i =0;i<dayNumber;i++){
            months = month+"";
            days = day+"";
            if(month<10){
                months = "0"+month;
            }
            if(day<10){
                days = "0"+day;
            }
            String s1 = year+"-"+months+"-"+days;

            Schedule schedule;
            schedule  = Schedule.PersonSelectByDate(username,s1);
            day++;
            if(day>maxDate){
                month++;
                day = 1;
            }
            if(month>12){
                year++;
                month = 1;
            }

            if(schedule!=null ){
                if(schedule.getDutyID()!=10014){
                    Schedule schedule1  = new Schedule();
                    schedule1.setDutyID(10014);
                    schedule1.setEmployeeID(username);
                    schedule1.setDate(s1);
                    Schedule.updateScheduleDuty(schedule1);
                }else {
                    continue;
                }

            }
            if(schedule == null){
                Schedule schedule1  = new Schedule();
                schedule1.setDutyID(10014);
                schedule1.setEmployeeID(username);
                schedule1.setDate(s1);
                Schedule.insertSchedule(schedule1);
            }



        }
    }

    //换班时执行的数据库代码（员工用户名1，提起，班次ID，被换员工用户名2，被换日期，被换班次ID）
    private static void H(String EmployeeID, String date, String ID1, String EmployeedID, String date2, String ID2){

        Schedule schedule1 = Schedule.PersonSelectByDate(EmployeeID,date2);

        if(schedule1 != null) {
            int id1 = schedule1.getId();
            Schedule s1 = new Schedule();
            s1.setDate(date);
            s1.setId(id1);
            int DutyId1 = s1.dutyID(ID1);
            s1.setDutyID(DutyId1);
            Schedule.updateScheduleDayAndDuty(s1);
        }
        Schedule schedule2 = Schedule.PersonSelectByDate(EmployeedID,date);
        if(schedule2 != null){
            int id2 = schedule2.getId();
            Schedule s2 = new Schedule();
            s2.setDate(date2);
            s2.setId(id2);
            int DutyId2 = s2.dutyID(ID2);
            s2.setDutyID(DutyId2);
            Schedule.updateScheduleDayAndDuty(s2);
        }


    }

}
