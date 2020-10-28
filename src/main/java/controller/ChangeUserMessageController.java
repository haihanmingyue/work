package controller;

import dao.UserDao;
import dao.UserLoad;
import dao.UserMessage;
import dao.UserMessageDao;
import Util.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;


public  class ChangeUserMessageController<event>extends UserIndexController implements Initializable {

    private static Logger logger = Logger.getLogger(ChangeUserMessageController.class);



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        chaxun();
        UnEditable();

    }

    @Override
    protected void Touxiang(String path){

        javafx.scene.image.Image image = new javafx.scene.image.Image("file:"+path);
        touxiang.setImage(image);
        //阴影
        touxiang.setEffect(new DropShadow(10, Color.GRAY));

    }

    @Override
    protected void Touxiang1(String path){
        javafx.scene.image.Image image = new javafx.scene.image.Image("file:"+path);
        touxiang.setImage(image);

        touxiang.setFitHeight(163);
        touxiang.setFitWidth(129);
    }

     @Override
     void chaxun(){
        String username = StageHashMap.USERMessage.get("UserMessage");
        UserMessage userMessage = MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).selectUserMessage(username);
        MybatisUtil.closeSqlSession();
        UserLoad userLoad = MybatisUtil.getSqlSession().getMapper(UserDao.class).selectByUsername(username);
        MybatisUtil.closeSqlSession();
        if(userMessage==null){
            flag = false;
            System.out.println(flag);
        }else {
            flag = true;
            name.setText(userMessage.getName());
            if (userMessage.getSex() == 1) {
                man.setSelected(true);
            } else {
                woman.setSelected(true);
            }
            age.setText(String.valueOf(userMessage.getAge()));
            Date d1 = userMessage.getBirthday();
            SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
            String str = sd1.format(d1);
            String[] str2;
            str2 = str.split("-");
            birthday.setValue(LocalDate.of(Integer.parseInt(str2[0]), Integer.parseInt(str2[1]), Integer.parseInt(str2[2])));
            minzu.setText(userMessage.getMinzu());
            tel.setText(userLoad.getTelphone());
            qqnumber.setText(String.valueOf(userMessage.getQqnumber()));
            weixin.setText(userMessage.getWeixin());
            email.setText(userLoad.getEmail());
            idcard.setText(userMessage.getIdcard());
            if(userMessage.getTouxiangurl()!=null){
                TouXUrl = userMessage.getTouxiangurl();
            }
            System.out.println(userMessage.getTouxiangurl());
        }
        Touxiang(TouXUrl);

    }

    @Override
    public void closeAction(ActionEvent event) {
        new DialogBuilder(gb).setTitle("提示").setMessage("确定关闭吗？").setPositiveBtn("确定", this::close, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").setNegativeBtn("取消", null, "-fx-background-color: #8A8A8A;-fx-text-fill: #ffffff").create(500, 200);
    }

    @Override
    //更新信息
    protected void updateUserMessage(){
        if(name.getText().trim().equals("")  || idcard.getText().trim().equals("") || birthday.getValue()==null || minzu.getText().trim().equals("") || tel.getText().trim().equals("")){
            new DialogBuilder(querenxiugai).setTitle("提示").setMessage("必填项不能为空").setPositiveBtn("确定", null).create(500, 100);
        }else {
            UserMessage userMessage = new UserMessage();
            UserLoad userLoad = new UserLoad();

                if (man.isSelected()) {
                    userMessage.setSex(1);
                } else if (woman.isSelected()) {
                    userMessage.setSex(0);
                }
                userMessage.setName(name.getText().trim());
                userMessage.setIdcard(idcard.getText().trim());
                userMessage.setWeixin(weixin.getText().trim());
                userMessage.setMinzu(minzu.getText().trim());
                userMessage.setQqnumber(qqnumber.getText().trim());
                userMessage.setUsername(StageHashMap.USERMessage.get("UserMessage"));
                String string = String.valueOf(birthday.getValue());
                userMessage.setBirthday(DateUtil.strToDate(string));
                if(ImageDate!=null){
                    try {
                        querenshangchuan();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                userMessage.setTouxiangurl(TouXUrl);
                userMessage.setAge(Integer.parseInt(age.getText().trim()));
                userLoad.setEmail(email.getText().trim());
                userLoad.setTelphone(tel.getText().trim());
                userLoad.setUsername(StageHashMap.USERMessage.get("UserMessage"));

                if(UserMessage.updateUserMessage(userMessage) && UserLoad.updateothers(userLoad)){
                    new DialogBuilder(querenxiugai).setTitle("提示").setMessage("修改成功").setPositiveBtn("确定", null).create(500, 100);
                    UnXianShiAnNiu();
                    XianShiXiuGai();
                    UnEditable();
                    chaxun();
                }else{
                    new DialogBuilder(querenxiugai).setTitle("提示").setMessage("修改失败").setPositiveBtn(
                            "确定", null).create(500, 100);
                    logger.info("用户" + StageHashMap.USERS.get("userName") + "修改"+StageHashMap.USERMessage.get("UserMessage")+"的信息失败");
                }
        }
    }

    @Override
    public void close() {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        Platform.setImplicitExit(true);
        logger.info("用户"+StageHashMap.USERS.get("userName")+"关闭了用户"+StageHashMap.USERMessage.get("UserMessage")+"的个人详细信息界面");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.hide();
            }
        });

    }

}

