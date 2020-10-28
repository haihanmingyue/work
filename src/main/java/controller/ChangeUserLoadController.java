package controller;

import dao.UserLoad;
import Util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;


public  class ChangeUserLoadController<event>implements Initializable {

    private static Logger logger = Logger.getLogger(ChangeUserLoadController.class);

    @FXML
    private Text usname1;
    @FXML
    private Text paword1;
    @FXML
    private  Text tel1;
    @FXML
    private  Text email1;
    @FXML
    private JFXTextField tel;
    @FXML
    private JFXTextField email;
    @FXML
    private Text biaoti;
    @FXML
    private  JFXButton gb;
    @FXML
    private  JFXButton sx;
    @FXML
    private  JFXButton adduser;
    @FXML
    private  JFXToggleButton admin;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    private int flag=0;
    private int Admin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Method.hand(gb);
        Method.hand(sx);
        Method.hand(adduser);
        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));

        admin.setText("普通用户");
        admin.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                admin.setCursor(Cursor.HAND);
            }
        });

        if (StageHashMap.ChangeAdmin.get("ChangeAdmin") != null && StageHashMap.ChangeUSERS.get("ChangePassword") != null && StageHashMap.ChangeUSERS.get("ChangeUsername") != null) {
            Admin = StageHashMap.ChangeAdmin.get("ChangeAdmin");
            String userName = StageHashMap.ChangeUSERS.get("ChangeUsername");
            String passWord = MD5Util.convertMD5(StageHashMap.ChangeUSERS.get("ChangePassword"));
            String TelPhone = StageHashMap.ChangeUSERS.get("ChangeTel");
            if (StageHashMap.ChangeUSERS.get("ChangeEmail") != null) {
                String emAil = StageHashMap.ChangeUSERS.get("ChangeEmail");
                email.setText(emAil);
            }
            StageHashMap.SelectTip.put("select", Admin);
            tel.setVisible(true);
            email.setVisible(true);
            tel1.setVisible(true);
            email1.setVisible(true);
            username.setEditable(false);
            username.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    new DialogBuilder(adduser).setTitle("提示").setMessage("无法编辑！").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);

                }
            });
            adduser.setText("确认修改");
            biaoti.setText("修改账号");
            if (Admin == 1) {
                admin.setSelected(true);
                admin.setText("   管理员");
                flag = 1;   //正常执行，但文字不变，待解决
            } else {
                admin.setSelected(false);
                admin.setText("普通用户");
                flag = 0;
            }
            username.setText(userName);
            password.setText(passWord);
            tel.setText(TelPhone);
            StageHashMap.ChangeUSERS.clear();
            StageHashMap.ChangeAdmin.clear();
        } else {
            tel.setVisible(false);
            email.setVisible(false);
            tel1.setVisible(false);
            email1.setVisible(false);
            username.setTranslateY(50);
            password.setTranslateY(80);
            usname1.setFont(new Font(22));
            paword1.setFont(new Font(22));
            paword1.setTranslateY(50);
            usname1.setTranslateY(30);
            admin.setTranslateY(-50);
            adduser.setTranslateY(-30);
        }

    }


    public void sxAction(ActionEvent event) {
        Stage stage = (Stage)sx.getScene().getWindow();
        stage.setIconified(true);
    }

    public void closeAction(ActionEvent event) {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        StageHashMap.SelectTip.clear();
        primaryStage.hide();
    }


    public void adduser(ActionEvent event) {
        String userName = username.getText().trim();


        if(StageHashMap.SelectTip.get("select")!=null){
            new DialogBuilder(adduser).setTitle("提示").setMessage("确定修改"+userName+"吗？").setNegativeBtn("取消").setPositiveBtn("确定",this::change,"-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);

        }else{
            new DialogBuilder(adduser).setTitle("提示").setMessage("确定添加"+userName+"吗？").setNegativeBtn("取消").setPositiveBtn("确定",this::add,"-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);
      }

    }

    private void change() {

        String userName = username.getText().trim();
        String passWord = password.getText().trim();
        String telphone = tel.getText().trim();
        String emaill;
        if(!email.getText().trim().equals("")){
           emaill = email.getText().trim();
        }else{
            emaill = "";
        }
        if(!emaill.equals("")&&!Method.isEmail(emaill)){
            new DialogBuilder(adduser).setTitle("提示").setMessage("请输入正确的邮箱！").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);

        }else if(!Method.IsTel(telphone)){
            new DialogBuilder(adduser).setTitle("提示").setMessage("请输入正确的手机号！").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);
        }else if(!Method.IsPassword(passWord)){
            new DialogBuilder(adduser).setTitle("提示").setMessage("8-16位").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 200);
        }
        else {
            ChangeUser(userName, passWord, flag, telphone, emaill);
            logger.info("管理员" + StageHashMap.USERS.get("userName") + "修改了用户" + userName);
            new DialogBuilder(adduser).setTitle("提示").setMessage("修改成功").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").setNegativeBtn("取消").create(300, 100);
        }
     }


    private void add() {
        String userName = username.getText().trim();
        String passWord = password.getText().trim();
        if(Method.isChinese(userName)){
            new DialogBuilder(adduser).setTitle("提示").setMessage("账号由数字英文组成").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);
        }else if(!Method.IsPassword(passWord)){
            new DialogBuilder(adduser).setTitle("提示").setMessage("必须包含大写英文\n数字,8-16位！").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").create(300, 100);
        }else {
            AddUser(userName, passWord, flag);
            logger.info("管理员" + StageHashMap.USERS.get("userName") + "添加了用户" + userName);
            new DialogBuilder(adduser).setTitle("提示").setMessage("添加成功").setPositiveBtn("确定", null, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").setNegativeBtn("取消").create(300, 100);
        }
    }
    //添加账号
    private static void AddUser(String userName,String passWord,int flag){
        UserLoad userLoad = new UserLoad();
        userLoad.setUsername(userName);
        userLoad.setPassword(MD5Util.convertMD5(passWord));
        userLoad.setTelphone("");
        userLoad.setAdmin(flag);
        userLoad.setRegistrationdate(DateUtil.getNowDate());
        UserLoad.insertUserLoad(userLoad);
    }
    //修改
    private static void ChangeUser(String userName, String passWord, int flag, String tel, String email){
        UserLoad userLoad = new UserLoad();
        userLoad.setUsername(userName);
        userLoad.setPassword(MD5Util.convertMD5(passWord));
        userLoad.setTelphone(tel);
        userLoad.setAdmin(flag);
        userLoad.setTelphone(tel);
        if(!email.equals("")){
            userLoad.setEmail(email);
        }else {
            userLoad.setEmail("");
        }
       UserLoad.updateUserLoad(userLoad);
    }

    public void admin(ActionEvent event) {
        if(flag==0){
            admin.setText("   管理员");
            flag+=1;
        }else {
            admin.setText("普通用户");
            flag-=1;
        }
    }
}

