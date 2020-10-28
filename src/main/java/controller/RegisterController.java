package controller;

import dao.UserLoad;
import Util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.Main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static Util.AuthCodeUtils.getRandom;


public  class RegisterController<event>implements Initializable {
    private static Logger logger = Logger.getLogger(RegisterController.class);

    private String code;
    @FXML
    private ImageView yzm;
    @FXML
    private JFXButton zhuce;
    @FXML
    private JFXTextField yzm1;
    @FXML
    public JFXButton quxiao;
    @FXML
    private JFXPasswordField password1;
    @FXML
    private JFXTextField username ;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton gb;
    @FXML
    private JFXButton sx;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        Method.hand(gb);
        Method.hand(sx);
        Method.hand(quxiao);
        Method.hand(zhuce);
        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));
        yzm.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yzm.setCursor(Cursor.HAND);
            }
        });

        Code();

    }


    public void closeAction(ActionEvent event) {
        Platform.exit();
    }

    public void sxAction(ActionEvent event) {
        Stage stage = (Stage)sx.getScene().getWindow();
        stage.setIconified(true);
    }

    //刷新验证码
    public void shuaxin(MouseEvent mouseEvent) {

        Code();
    }

    public void zhuce(ActionEvent event)  {
         String userName ;
         String passWord ;
         String passWord1 ;
         String YZM ;
         String code1;
         code1 = code.toLowerCase(); //全部变成小写，变成大写也行

        if(!username.getText().trim().equals("") && !password.getText().trim().equals("") &&
           !password1.getText().trim().equals("") && !yzm1.getText().trim().equals("")) {
            userName = username.getText().trim();
            passWord = password.getText().trim();
            passWord1 = password1.getText().trim();
            YZM = yzm1.getText().trim().toLowerCase();//把用户输入的也都变成小写获取，大写也行

            UserLoad userLoad = UserLoad.selectByUsername(userName);
            if(userLoad==null){
                if(Method.isChinese(userName)){
                    new DialogBuilder(zhuce).setTitle("提示").setMessage("用户名由英文或数字组成").setPositiveBtn("确定",null).create(250,50);
                }else if(!Method.IsPassword(passWord)){
                    new DialogBuilder(zhuce).setTitle("提示").setMessage("密码格式错误").setPositiveBtn("确定",null).create(250,50);
                }else if(!passWord1.equals(passWord)){
                    new DialogBuilder(zhuce).setTitle("提示").setMessage("两次密码不一致").setPositiveBtn("确定",null).create(250,50);
                }else if(!YZM.equals(code1)){
                    new DialogBuilder(zhuce).setTitle("提示").setMessage("验证码错误").setPositiveBtn("确定",null).create(250,50);
                }else {
                    UserLoad.addUser(userName, passWord);
                    logger.info("用户添加成功！");
                    new DialogBuilder(zhuce).setTitle("提示").setMessage("注册成功").setPositiveBtn("确定","#ffffff").create(250,50);
                    Stage primaryStage = (Stage)zhuce.getScene().getWindow();
                    primaryStage.hide();
                    Main open = new Main();
                    try {
                        open.start(new Stage());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        logger.info("界面跳转失败");
                    }
                }
            }else {
                new DialogBuilder(zhuce).setTitle("提示").setMessage("用户名已经存在").setPositiveBtn("确定",null).create(250,50);
            }
        }else{
            new DialogBuilder(zhuce).setTitle("提示").setMessage("不能为空").setPositiveBtn("确定",null).create(250,50);
        }

    }

    //生成验证码
    private void Code(){
        for (int i = 1; i < 2; i++) {
            FileOutputStream out = null;
            try {
                code = getRandom(4);
                out = new FileOutputStream("target" + i + ".png");
                AuthCodeUtils.draw(out,code);

            } catch (IOException e) {
                e.printStackTrace();
            }
            javafx.scene.image.Image image = new Image("file:target1.png");
            yzm.setImage(image);
        }
    }

    public void quxiao(ActionEvent event) {
        Main open =new Main();
        try {
            open.start(new Stage());
            logger.info("登录界面跳转成功");
            Stage primaryStage = (Stage)quxiao.getScene().getWindow();
            primaryStage.hide();
        } catch (Exception e) {
            logger.info("登录界面跳转失败");
            e.printStackTrace();
        }
    }
}
