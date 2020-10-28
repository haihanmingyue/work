package controller;

import dao.UserLoad;
import Util.*;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.AdminIndex;
import sample.Register;
import sample.RetrievePassword;
import sample.UserIndex;

import java.net.URL;
import java.util.ResourceBundle;


public  class MainController<event>implements Initializable {
    private static Logger logger = Logger.getLogger(MainController.class);

    @FXML
    JFXButton zhuce2;
    @FXML
    JFXCheckBox jzmm;
    @FXML
    JFXButton wjmm;
    @FXML
    JFXTextField username ;
    @FXML
    JFXPasswordField password;
    @FXML
    JFXButton dl;
    @FXML
    JFXButton gb;
    @FXML
    JFXButton sx;
    @FXML
    JFXButton zhuce;


    @Override
    public void initialize(URL location, ResourceBundle resources){

        Method.hand(dl);
        Method.hand(gb);
        Method.hand(sx);
        Method.hand(zhuce);
        Method.hand(wjmm);
        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));//鼠标悬停提示框

        jzmm.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                jzmm.setCursor(Cursor.HAND);
            }
        });
    }

    public void closeAction(ActionEvent event) {
        Platform.exit();
    }

    public void sxAction(ActionEvent event) {
        Stage stage = (Stage)sx.getScene().getWindow();
        stage.setIconified(true);
    }

    public void denglu(ActionEvent event) {
        String userName = username.getText().trim();
        String passWord = password.getText().trim();
        if(userName.equals("") || passWord.equals("")){
            new DialogBuilder(jzmm).setTitle("提示").setMessage("不能为空").setPositiveBtn("确定","#ffffff").create(250,50);
        }else {
            try {
                UserLoad userLoad = UserLoad.selectByUsername(userName);
                String str = MD5Util.convertMD5(userLoad.getPassword());
                if (userName.equals(userLoad.getUsername()) && passWord.equals(str)){
                    logger.info("用户"+userLoad.getUsername()+"登陆成功");
                    if(jzmm.isSelected()){  //根据checkbox的选中状态，判断执行的方法
                        Method.remberusers(userName,passWord);
                    }else{
                        Method.remberusers2(userName,passWord);
                    }
                    //每次登陆成功清楚上次一的数据
                    StageHashMap.USERS.clear();
                    //存入username
                    StageHashMap.USERS.put("userName", username.getText());
                    //存入admin
                    StageHashMap.Int.put("admin", userLoad.getAdmin());
                    Stage primaryStage = (Stage)dl.getScene().getWindow();
                    primaryStage.hide();
                    if(userLoad.getAdmin()==1){
                        //打开管理员界面
                        AdminIndex open = new AdminIndex();
                        open.start(new Stage());
                    }else {
                        //打开用户界面
                        UserIndex open = new UserIndex();
                        open.start(new Stage());
                    }
                }
                else if(userName.equals(userLoad.getUsername()) && !passWord.equals(str)){
                    new DialogBuilder(dl).setTitle("提示").setMessage("密码错误").setPositiveBtn("确定","#ffffff").create(250,50);
                    }
                } catch (NullPointerException e){
                new DialogBuilder(dl).setTitle("提示").setMessage("用户名不存在").setPositiveBtn("确定","#ffffff").create(250,50);
                }catch(Exception e) {
                e.printStackTrace();
                }

              }
        }


    public void wjmm(ActionEvent event) {
        RetrievePassword open = new RetrievePassword();
        try {
            open.start(new Stage());
            Stage primaryStage = (Stage)wjmm.getScene().getWindow();
            primaryStage.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void zhuce(ActionEvent event) {
        Register open = new Register();
        try {
            open.start(new Stage());
            logger.info("注册界面打开成功");
            Stage primaryStage = (Stage)zhuce.getScene().getWindow();
            primaryStage.hide();
        } catch (Exception e) {
            logger.info("注册界面打开失败");
            e.printStackTrace();
        }
    }

//
//    public void zhuce2(ActionEvent event) {
//        Desktop desktop = Desktop.getDesktop();
//        try {
//            desktop.browse(new URI("http://152.136.137.56:8080/Haihanmingyue/zhuce.jsp"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

}
