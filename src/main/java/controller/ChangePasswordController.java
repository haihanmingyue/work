package controller;

import dao.UserLoad;
import Util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.AdminIndex;
import sample.Main;
import sample.UserIndex;

import java.net.URL;
import java.util.ResourceBundle;


public  class ChangePasswordController<event>implements Initializable {

    private static Logger logger = Logger.getLogger(ChangePasswordController.class);
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton quxiao;
    @FXML
    private JFXPasswordField password1;
    @FXML
    private JFXPasswordField password2;
    @FXML
    private JFXButton queren;
    @FXML
    private JFXButton sx;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        username.setText(StageHashMap.USERS.get("userName"));
        //调用hand
        Method.hand(sx);
        Tooltip.install(sx, new Tooltip("最小化"));


    }

    //窗口缩小
    public void sxAction(ActionEvent event) {
        Stage stage = (Stage) sx.getScene().getWindow();
        stage.setIconified(true);
    }


    public void quxiao(ActionEvent event) throws Exception {
        int admin = StageHashMap.Int.get("admin");
        Stage primaryStage = (Stage)quxiao.getScene().getWindow();
        primaryStage.hide();
        if(admin==1){
            //打开管理员界面
            AdminIndex open = new AdminIndex();
            open.start(new Stage());
        }else {
            //打开用户界面
            UserIndex open = new UserIndex();
            open.start(new Stage());
        }
    }
    public void queren(ActionEvent event) {
      String name = username.getText().trim();
      String pass = password.getText().trim();
      String pass1 = password1.getText().trim();
      String pass2 = password2.getText().trim();
      try{

          UserLoad userLoad = UserLoad.selectByUsername(name);

          String str = MD5Util.convertMD5(userLoad.getPassword());

          if(!pass.equals(str)){
              new DialogBuilder(queren).setTitle("提示").setMessage("原密码错误").setPositiveBtn("确定",null).create(250,50);
          }
          else if(!pass1.equals(pass2)){
              new DialogBuilder(queren).setTitle("提示").setMessage("两次新密码不一致").setPositiveBtn("确定",null).create(250,50);
          }else{
              change(name,pass1);
              Stage primaryStage = (Stage)queren.getScene().getWindow();
              primaryStage.hide();
              Main open = new Main();
              open.start(new Stage());
          }
      }catch (Exception e){
          e.printStackTrace();
      }



    }
    private void change(String name,String pass){
        UserLoad userLoad = new UserLoad();
        userLoad.setUsername(name);
        userLoad.setPassword(MD5Util.convertMD5(pass));
        if (UserLoad.updatePassword(userLoad)) {
            new DialogBuilder(queren).setTitle("提示").setMessage("修改成功").setPositiveBtn("确定", null).create(250, 50);
            logger.info("用户" + name + "修改了密码");
        } else {
            new DialogBuilder(queren).setTitle("提示").setMessage("修改失败").setPositiveBtn("确定", null).create(250, 50);
        }
    }


}
