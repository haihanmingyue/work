package controller;

import dao.Dutytype;
import dao.Schedule;
import dao.UserMessage;
import Util.*;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public  class AddScheduleController<event>implements Initializable {

    private static Logger logger = Logger.getLogger(AddScheduleController.class);

    @FXML  TextField name;
    @FXML TextField username;
    @FXML JFXDatePicker DatePick;
    @FXML JFXButton addSchedule;
    @FXML TextField BanCiYesOrNo;
    @FXML MenuButton ChoiceBanCi;
    @FXML TextField BanCi;
    @FXML JFXButton gb;
    @FXML JFXButton sx;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Method.hand(gb);
        Method.hand(sx);
        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));

        List<Dutytype> list = Dutytype.getAllDutytype();
        for(int i=0;i<list.size();i++){
           if(list.get(i).getId()==10007){
               list.remove(i);
               break;
           }
        }
        for(Dutytype dutytype : list){
            MenuItem item = new MenuItem(String.valueOf(dutytype.getId()));
            ChoiceBanCi.getItems().add(item);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ChoiceBanCi.setText(String.valueOf(dutytype.getId()));
                    BanCi.setText(dutytype.getDutyname());
                }
            });
        }


    }


    public void sxAction(ActionEvent event) {
        Stage stage = (Stage)sx.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeAction(ActionEvent event) {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        primaryStage.hide();
    }


    public void addSchedule(ActionEvent event) {
        if(!name.getText().trim().equals("无此人") && !BanCi.getText().trim().equals("") && BanCiYesOrNo.getText().trim().equals("可安排")){
            Schedule schedule = new Schedule();
            schedule.setEmployeeID(username.getText().trim());
            schedule.setDate(DatePick.getValue().toString());
            schedule.setDutyID(Integer.parseInt(ChoiceBanCi.getText().trim()));
            if(Schedule.insertSchedule(schedule)){
                new DialogBuilder(gb).setTitle("提示").setMessage("成功！").setPositiveBtn("确定",null).create(200,50);
                BanCiYesOrNo.setText("已有");
            }else {
                new DialogBuilder(gb).setTitle("提示").setMessage("失败！").setPositiveBtn("确定",null).create(200,50);
            }
        }else {
            new DialogBuilder(gb).setTitle("提示").setMessage("请正确填写信息！").setPositiveBtn("确定",null).create(200,50);
        }
    }

    public void DatePick(ActionEvent event) {
        String date = null;
        if(DatePick.getValue()!=null){
            date = DatePick.getValue().toString();
        }
        System.out.println(date);
        if(!username.getText().trim().equals("")){
            Schedule schedule = Schedule.PersonSelectByDate(username.getText().trim(),date);
            if(schedule!=null){
                BanCiYesOrNo.setText("已有");
            }else {
                BanCiYesOrNo.setText("可安排");
            }
        }else {
            new DialogBuilder(gb).setTitle("提示").setMessage("用户名为空！").setPositiveBtn("确定",null).create(200,50);
        }
    }

    public void username(ActionEvent event) {
        if(!username.getText().trim().equals("")){
            UserMessage userMessage = UserMessage.selectUserMessage(username.getText().trim());
            if(userMessage!=null){
                name.setText(userMessage.getName());
            }else {
                name.setText("无此人");
            }
        }
        if(DatePick.getValue()!=null){
            String date = DatePick.getValue().toString();
            Schedule schedule = Schedule.PersonSelectByDate(username.getText().trim(),date);
            if(schedule!=null){
                  BanCiYesOrNo.setText("已有");
            }else {
                BanCiYesOrNo.setText("可安排");
            }
        }
    }
}

