package controller;


import dao.UserLoad;
import Util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 找回密码
 * */

public  class RetrievePasswordController<event>  implements Initializable {
    private static Logger logger = Logger.getLogger(RetrievePasswordController.class);
    @FXML
    JFXTextField username,email,YanZhengMa;
    @FXML
    JFXButton send,sure,no,gb,sx;
    @FXML
    JFXPasswordField newpassword;


    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void send(ActionEvent event) {
        UserLoad userLoad = UserLoad.selectByUsername(username.getText().trim());
        if(userLoad != null){
           if(email.getText().trim().equals("") || !email.getText().trim().equals(userLoad.getEmail())){
               new DialogBuilder(send).setTitle("提示").setMessage("邮箱错误").setPositiveBtn("确定",null).create(250,50);
           }else{
               StageHashMap.YanZCode.clear();
               String s = AuthCodeUtils.getRandom(4);
               StageHashMap.YanZCode.put("YZM",s);
               SendEmail.sendMessages(email.getText().trim());
               System.out.println(s);
           }
        }
    }

    public void sure(ActionEvent event) {

        if(!username.getText().trim().equals("") && !email.getText().trim().equals("") &&
                !YanZhengMa.getText().trim().equals("") && !newpassword.getText().trim().equals("")){
            UserLoad userLoad = UserLoad.selectByUsername(username.getText().trim());
            if(userLoad!=null){
                if(!userLoad.getEmail().equals(email.getText().trim())){
                    new DialogBuilder(sure).setTitle("提示").setMessage("邮箱错误").setPositiveBtn("确定",null).create(250,50);
                }
                else if(!StageHashMap.YanZCode.get("YZM").equals(YanZhengMa.getText().trim())){
                    new DialogBuilder(sure).setTitle("提示").setMessage("验证码错误").setPositiveBtn("确定",null).create(250,50);
                }else{
                    UserLoad userLoad1 = new UserLoad();
                    userLoad1.setUsername(username.getText().trim());
                    userLoad1.setPassword(MD5Util.convertMD5(newpassword.getText().trim()));
                    if(UserLoad.updatePassword(userLoad1)){
                        new DialogBuilder(sure).setTitle("提示").setMessage("修改成功").setPositiveBtn("确定",null).create(250,50);
                    }else {
                        new DialogBuilder(sure).setTitle("提示").setMessage("ERROR").setPositiveBtn("确定",null).create(250,50);
                    }
                }
            }else {
                new DialogBuilder(sure).setTitle("提示").setMessage("账号不存在").setPositiveBtn("确定",null).create(250,50);
            }
        }else{
            new DialogBuilder(sure).setTitle("提示").setMessage("不许为空").setPositiveBtn("确定",null).create(250,50);
        }
    }

    public void closeAction(ActionEvent event) {

        StageHashMap.YanZCode.clear();
        Platform.exit();
    }

    public void sxAction(ActionEvent event) {
        Stage stage = (Stage)sx.getScene().getWindow();
        stage.setIconified(true);
    }

    //返回
    public void no(ActionEvent event) {
        Main open = new Main();
        try {
            open.start(new Stage());
            Stage primaryStage = (Stage)no.getScene().getWindow();
            primaryStage.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
