package controller;


import dao.*;
import Util.*;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import sample.ChangePassword;
import sample.Main;
import sample.QueryApply;

import java.awt.*;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


public  class UserIndexController<event> implements Initializable {

     private static Logger logger = Logger.getLogger(UserIndexController.class);

     //申请界面
     @FXML HBox MyApply;
     @FXML  JFXButton chakan;
     @FXML  JFXButton shenqing;

     //个人申请信息列表查询界面
     @FXML  HBox queryApply;
     @FXML  JFXButton GeneralButton;

     //查询排班的界面
     @FXML
     protected ComboBox year,month;
     @FXML
     HBox MyPaiBan;
     @FXML
     GridPane Pane;

     /**
      * 分割线
      * */
     @FXML
     HBox text1;
     @FXML
     VBox person;
     @FXML
     RadioButton man,woman;
     @FXML
     javafx.scene.control.TextField name, minzu,tel,qqnumber,weixin,email,idcard,age;
     @FXML
     DatePicker birthday;
     @FXML
     JFXButton shangchuan,xiugai,ChangePassword,xjsq,quxiaoxiugai,querenxiugai;
     @FXML
     ImageView touxiang;
     @FXML
     ImageView MiNiTouXiang;
     @FXML
     JFXButton sx,fd,gb,cxgrxx,cxpbb,zx;

     private int a = 0;

     /**
      * /判断执行个人中心的确定按钮执行的是update还是insert
      * */
     protected boolean flag ;
     private int buttonColor;
     protected String TouXUrl;
     protected byte [] ImageDate;

    private Label[] labelDay;
    private List<Schedule> schedulesList = null;

    private int ButtonCancelOrRise = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //默认文本不可编辑
        UnEditable();
        //调用hand
        Method.hand(cxgrxx);
        Method.hand(cxpbb);Method.hand(zx);
        Method.hand(fd);Method.hand(gb);
        Method.hand(xjsq);Method.hand(sx);
        Method.hand(GeneralButton);
        Method.hand(ChangePassword);

        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));
        Tooltip.install(fd, new Tooltip("最大化"));
        Tooltip.install(GeneralButton, new Tooltip("菜单"));


        //放在一组，就只能选择一个
        ToggleGroup group = new ToggleGroup();
        man.setToggleGroup(group);
        woman.setToggleGroup(group);

        String url = "images/My.png";
        Method.buttonTuBiao(url,chakan,25,25);
        String url1 = "images/pick.png";
        Method.buttonTuBiao(url1,shenqing,25,25);

        String button1 = "images/button1.png";
        Method.buttonTuBiao(button1,GeneralButton,25,25);
        Buttoncancel();
        MiNiTouxiang();

    }


    /**
     * 显示日历样式排班表
     * */
    private void ShowCalendar() throws ParseException {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //默认显示系统当前年月

        String employeeID = StageHashMap.USERS.get("userName");
        System.out.println(employeeID);
        schedulesList= Schedule.selectByUsername(employeeID, DateUtil.DateToString(date));
        List<Integer> list33 = new ArrayList<>();
        for (Schedule schedule : schedulesList) {
            String[] ss2 = schedule.getDate().trim().split("-");
            //因为label从0开始循环，所以这里也要-1来对应位置
            list33.add(Integer.parseInt(ss2[2])-1);
        }
        Collections.sort(list33);

        //做一个比较器,对象里面数据种类很多 ，不能直接排序
        Comparator<Schedule> c = new Comparator<Schedule>() {
            @Override
            public int compare(Schedule S1, Schedule S2) {
                //按照“日”进行排序
                String[] ss2 = S1.getDate().trim().split("-");
                String[] ss3 = S2.getDate().trim().split("-");
                int s1 = Integer.parseInt(ss2[2])-1;
                int s2 = Integer.parseInt(ss3[2])-1;
                if(s1 > s2) {
                    return 1;  //正数表示h1比h2要大  如果h1大于h2 返回1 就是小到大 返回-1就是大到小
                }else {
                    return -1;
                }
            }
        };
        schedulesList.sort(c);
        //排序之后让两个list的顺序一致，都是从小到大
       for(Schedule schedule : schedulesList){
           System.out.println(schedule.getEmployeeID()+" "+schedule.getDate()+" "+schedule.getDutyName());
       }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        //判断每个月第一天是星期几
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        int w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        System.out.println("星期"+w);


        //天数即是label的个数
        labelDay= new Label[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];

        int x = w;
        int y = 0;
        //日期栏
        for(int i=0;i<labelDay.length;i++){
            labelDay[i] = new Label("今天是"+(i+1)+"号\n" +"\n"+
                    "   休息");
            labelDay[i].setStyle("-fx-background-color: #00cdff;-fx-alignment: center;-fx-font-family: '黑体'");
            labelDay[i].setPrefSize(150,100);
            Pane.add(labelDay[i],x-1,y);
            Pane.setHalignment(labelDay[i],HPos.CENTER);
            Pane.setValignment(labelDay[i],VPos.TOP);
            if(x==6 && !list33.contains(i)){
                labelDay[i].setStyle("-fx-background-color: #22ff67;-fx-alignment: center;-fx-font-family: '黑体'");
            }
            //x==7就切换到下一行
            if(x==7){
                labelDay[i].setStyle("-fx-background-color: #22ff67;-fx-alignment: center;-fx-font-family: '黑体'");
                x=0;
                y++;
            }
            if(list33.contains(i)){
                labelDay[i].setText("今天是"+(i+1)+"号\n"
                        +"\n"+"     "+schedulesList.get(list33.indexOf(i)).getDutyName());
                if(schedulesList.get(list33.indexOf(i)).getDutyName().equals("休息") || schedulesList.get(list33.indexOf(i)).getDutyName().equals("请假")){
                    labelDay[i].setStyle("-fx-background-color: #22ff67;-fx-alignment: center;-fx-font-family: '黑体'");

                }else{
                    labelDay[i].setStyle("-fx-background-color: #ffcd00;-fx-alignment: center;-fx-font-family: '黑体'");
                }
            }
            x++;
        }
    }
    /**
     * 调整年月后的
     * */
    protected void ShowCalendar(int years,int months) throws Exception{


        year.setPromptText(years+"");
        month.setPromptText(months+"");

        if(schedulesList!=null){
            schedulesList.clear();
        }
        Calendar calendar = Calendar.getInstance();
        String s = years+"-"+months;
        Date date = DateUtil.StringToDate(s);
        calendar.setTime(date);

        String employeeID = StageHashMap.USERS.get("userName");
        String date2 = null;
        if(months<10){
            date2  = years+"-0"+months;
        }else{
            date2 = years+"-"+months;
        }

        schedulesList= MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).selectByUsername(employeeID, date2);
        MybatisUtil.closeSqlSession();
        if(schedulesList==null){
            return;
        }
        List<Integer> list33 = new ArrayList<>();
        for (Schedule schedule : schedulesList) {
            String[] ss2 = schedule.getDate().trim().split("-");
            //因为label从0开始循环，所以这里也要-1来对应位置
            list33.add(Integer.parseInt(ss2[2])-1);
        }
        Collections.sort(list33);
        //做一个比较器,对象里面数据种类很多 ，不能直接排序
        Comparator<Schedule> c = new Comparator<Schedule>() {
            @Override
            public int compare(Schedule S1, Schedule S2) {
                //按照damage进行排序
                String[] ss2 = S1.getDate().trim().split("-");
                String[] ss3 = S2.getDate().trim().split("-");
                int s1 = Integer.parseInt(ss2[2])-1;
                int s2 = Integer.parseInt(ss3[2])-1;
                if(s1 > s2) {
                    return 1;  //正数表示h1比h2要大  如果h1大于h2 返回1 就是小到大 返回-1就是大到小
                }else {
                    return -1;
                }
            }
        };
        schedulesList.sort(c);
        //排序之后让两个list的顺序一致，都是从小到大
        for(Schedule schedule : schedulesList){
            System.out.println(schedule.getEmployeeID()+" "+schedule.getDate()+" "+schedule.getDutyName());

        }
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        //判断每个月第一天是星期几
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        int w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        System.out.println("星期"+w);
        labelDay= new Label[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        int x = w;
        int y = 0;
        //日期栏
        for(int i=0;i<labelDay.length;i++){
            labelDay[i] = new Label("今天是"+(i+1)+"号\n" +"\n"+
                    "   休息");
            labelDay[i].setStyle("-fx-background-color: #00cdff;-fx-alignment: center;-fx-font-family: '黑体'");
            labelDay[i].setPrefSize(150,100);
            Pane.add(labelDay[i],x-1,y);
            Pane.setHalignment(labelDay[i],HPos.CENTER);
            Pane.setValignment(labelDay[i],VPos.TOP);
            if(x==6 && !list33.contains(i)){
                labelDay[i].setStyle("-fx-background-color: #22ff67;-fx-alignment: center;-fx-font-family: '黑体'");
            }
            if(x==7){

                labelDay[i].setStyle("-fx-background-color: #22ff67;-fx-alignment: center;-fx-font-family: '黑体'");
                x=0;
                y++;
            }
            if(list33.contains(i)){
                labelDay[i].setText("今天是"+(i+1)+"号\n"
                        +"\n"+"     "+schedulesList.get(list33.indexOf(i)).getDutyName());
                if(schedulesList.get(list33.indexOf(i)).getDutyName().equals("休息") || schedulesList.get(list33.indexOf(i)).getDutyName().equals("请假")){
                    labelDay[i].setStyle("-fx-background-color: #22ff67;-fx-alignment: center;-fx-font-family: '黑体'");

                }else{
                    labelDay[i].setStyle("-fx-background-color: #ffcd00;-fx-alignment: center;-fx-font-family: '黑体'");
                }

            }
            x++;
        }
    }

    public void LastMonth(ActionEvent event) throws Exception{

        int years = Integer.parseInt(year.getPromptText());
        int months = Integer.parseInt(month.getPromptText());
        months = months-1;
        if(months<1 ){
            months = 1;
            new DialogBuilder(zx).setTitle("提示").setMessage("最小至1月份").setPositiveBtn("确定",null).create(500,100);
        }
        Pane.getChildren().clear();
        ShowCalendar(years,months);

    }

    public void NextMonth(ActionEvent event) throws Exception {
        int years = Integer.parseInt(year.getPromptText());
        int months = Integer.parseInt(month.getPromptText());
        months = months+1;
        if(months>12 ){
            months = 12;
            new DialogBuilder(zx).setTitle("提示").setMessage("最大至12月份").setPositiveBtn("确定",null).create(500,100);
        }
        Pane.getChildren().clear();
        ShowCalendar(years,months);
    }

    public void ChoiceYear(ActionEvent event) throws Exception {
        String mon;
        if(month.getValue() == null){
            mon = month.getPromptText();
        }else {
            mon = String.valueOf(month.getValue());
        }
        int months = Integer.valueOf(mon);
        String yea =  String.valueOf(year.getValue());
        int years = Integer.valueOf(mon);

        Pane.getChildren().clear();
        ShowCalendar(years,months);
    }

    public void ChoiceMonth(ActionEvent event) throws Exception{
        String yea;
        if(year.getValue() == null){
            yea = year.getPromptText();
        }else {
            yea = String.valueOf(year.getValue());
        }
        int years = Integer.valueOf(yea);
        String mon =  String.valueOf(month.getValue());
        int months = Integer.valueOf(mon);

        Pane.getChildren().clear();
        ShowCalendar(years,months);
    }

    /**
     * 查询排班表按钮
     * */
    public void cxpbb(ActionEvent event) {
        buttonColor = 2;
        ButtonColor(buttonColor);

        Date date = new Date();
        String s =  DateUtil.DateToString(date);
        String[]ss = s.split("-");
        int years = Integer.parseInt(ss[0]);
        int months = Integer.parseInt(ss[1]);
        year.setPromptText(years+"");
        month.setPromptText(months+"");
        for(int i=2000;i<3000;i++){
            year.getItems().add(i);
        }
        for(int i=1;i<=12;i++) {
            month.getItems().add(i);
        }
        try {
            ShowCalendar();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    /**
     * 查询按钮
     * */
    public void cxgrxx(ActionEvent event) {

        buttonColor = 1;
        ButtonColor(buttonColor);
        chaxun();
        XianShiXiuGai();
        UnXianShiAnNiu();


    }

    /**
     * 查询方法
     * */
    void chaxun(){
        String username = StageHashMap.USERS.get("userName");
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


    /**
     * 申请按钮
     * */
    public void xjsq(ActionEvent event) {
       buttonColor = 3;
       ButtonColor(buttonColor);
    }

    /**
     * 按钮颜色变化和不同的页面
     * */
    protected void ButtonColor(int buttonColor){
        switch (buttonColor){
            case 1:
                text1.setVisible(false);
                text1.setManaged(false);
                person.setManaged(true);
                person.setVisible(true);
                man.setDisable(true);
                woman.setDisable(true);
                MyPaiBan.setManaged(false);
                MyPaiBan.setVisible(false);
                MyApply.setVisible(false);
                MyApply.setManaged(false);
                cxgrxx.setStyle("-fx-background-color: white;-fx-text-fill:#171717");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill:#eeeeee");
                xjsq.setStyle("-fx-background-color: #171717;-fx-text-fill:#eeeeee");
                break;
            case 2:
                text1.setVisible(false);
                text1.setManaged(false);
                person.setManaged(false);
                person.setVisible(false);
                MyApply.setVisible(false);
                MyApply.setManaged(false);
                MyPaiBan.setManaged(true);
                MyPaiBan.setVisible(true);

                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill:#eeeeee");
                cxpbb.setStyle("-fx-background-color: white;-fx-text-fill:#171717");
                xjsq.setStyle("-fx-background-color: #171717;-fx-text-fill:#eeeeee");
                break;
            case 3:
                text1.setVisible(false);
                text1.setManaged(false);
                person.setManaged(false);
                person.setVisible(false);
                MyPaiBan.setManaged(false);
                MyPaiBan.setVisible(false);
                MyApply.setVisible(true);
                MyApply.setManaged(true);
                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill:#eeeeee");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill:#eeeeee");
                xjsq.setStyle("-fx-background-color: white;-fx-text-fill:#171717");

                break;

        }
    }

    /**
     * 注销按钮
     * */
    public void zx(ActionEvent event) {
        new DialogBuilder(zx).setTitle("提示").setMessage("确定注销登录吗？").setPositiveBtn("确定", this::zhuxiao,
                "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").setNegativeBtn("取消").create(500, 200);
    }

    /**
     * 注销的方法
     * */
    private void zhuxiao() {
        try {

            Stage primaryStage = (Stage) zx.getScene().getWindow();
            primaryStage.hide();
            logger.info("用户" + StageHashMap.USERS.get("userName") + "注销成功");
            StageHashMap.USERS.clear();
            Main open = new Main();
            open.start(new Stage());
        } catch (Exception e) {
            logger.info("用户" + StageHashMap.USERS.get("userName") + "注销失败");
            e.printStackTrace();
        }
    }

    /**
     * 窗口缩小
     * */
    public void sxAction(ActionEvent event) {
        Stage stage = (Stage) sx.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * 窗口放大
     * */
    public void fdAction(ActionEvent event) {
        if (a == 0) {
            Stage stage = (Stage) fd.getScene().getWindow();
            stage.setWidth(new Method().getWidth());
            stage.setHeight(new Method().getHeight());
            stage.setX(0);
            stage.setY(0);
            fd.setText("\uE7F3");
            a += 1;

        } else {

            Stage stage = (Stage) fd.getScene().getWindow();
            stage.setWidth(1280);
            stage.setHeight(768);
            stage.setX(new Method().getWidth() / 2 - 640);
            stage.setY(new Method().getHeight() / 2 - 384);
            a -= 1;
            fd.setText("■");
        }

    }

    /**
     * 关闭窗口按钮
     * */
    public void closeAction(ActionEvent event) {
        new DialogBuilder(gb).setTitle("提示").setMessage("确定退出吗？").setPositiveBtn("确定", this::close, "-fx-background-color: #00b5bd;-fx-text-fill: #ffffff").setNegativeBtn("取消", null, "-fx-background-color: #8A8A8A;-fx-text-fill: #ffffff").setNaturalBtn("缩小到系统托盘", this::tuopan, "-fx-background-color: #8A8A8A;-fx-text-fill: #ffffff").create(500, 200);
    }

    /**
     * 系统托盘
     * */
    private void tuopan() {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        SystemTray systemTray = SystemTray.getSystemTray();
        //托盘图标
        Image image = Toolkit.getDefaultToolkit().getImage("src/main/resources/images/smalllogo.png");
        //提示文字
        String str = "药房智能排班系统";
        //托盘右键菜单
        PopupMenu menu = new PopupMenu();
        MenuItem item1 = new MenuItem("SHOW");
        MenuItem item2 = new MenuItem("CLOSE");
        menu.add(item1);
        menu.add(item2);
        //添加托盘的图片，文字，菜单
        TrayIcon trayIcon = new TrayIcon(image,str,menu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        //为item添加点击事件
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                //primaryStage.show();  //直接用这个会启动item属于的awt线程，不是javafx，会报错 Exception in thread "AWT-EventQueue-0" java.lang.IllegalStateException: Not on FX application thread; currentThread = AWT-EventQueue
               Platform.runLater(new Runnable() {
                   @Override
                   public void run() {
                       primaryStage.show();
                   }
               });
               //移除系统图标
                systemTray.remove(trayIcon);
            }
        });

        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                logger.info("用户"+StageHashMap.USERS.get("userName")+"退出了程序");
                Platform.exit(); //直接用这个，退的更彻底
                systemTray.remove(trayIcon);
            }
        });

        Platform.setImplicitExit(false);
        primaryStage.hide();
    }

    /**
     * 查看申请
     * */
    public void chakan(ActionEvent event)throws Exception {

        StageHashMap.USERS1.put("queryapply",1);
        QueryApply open = new QueryApply();
        open.start(new Stage());
    }
    /**
     * 去申请
     * */
    public void shenqing(ActionEvent event) throws Exception{

        StageHashMap.USERS1.put("queryapply",0);
        QueryApply open = new QueryApply();
        open.start(new Stage());
    }

    //退出程序
    protected void close() {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        Platform.setImplicitExit(true);
        logger.info("用户"+StageHashMap.USERS.get("userName")+"退出了程序");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                primaryStage.hide();
            }
        });
        Platform.exit();
    }


    public void ChangePassword(ActionEvent event) throws Exception {
        Stage primaryStage = (Stage)ChangePassword.getScene().getWindow();
        primaryStage.hide();
        sample.ChangePassword open = new ChangePassword();
        open.start(new Stage());
    }


    //返回首页
    public void fanhui(ActionEvent event) {
        person.setManaged(false);
        person.setVisible(false);
        text1.setVisible(true);
        text1.setManaged(true);
        UnXianShiAnNiu();
        cxgrxx.setStyle("-fx-background-color: #171717");
        cxpbb.setStyle("-fx-background-color: #171717");
        xjsq.setStyle("-fx-background-color: #171717");


    }


    //显示年龄
    public void birthday(ActionEvent event) {
//        DataPicker 对象
//        public DatePicker begin = new DatePicker();
//
//        获取 DatePicker 对象选择的日期
//        String dateBegin = begin.getValue().toString();
//
//        也可以设置日期
//        begin.setValue(LocalDate.of(2018,09,25));

        Date d1 = new Date();
        String str3 = null;
        int nl;
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy");
        String str1 = sd1.format(d1);
        if(birthday.getValue()==null){
        //获取date pick的日期
            age.setText("0");
        }else {
            String str2 = String.valueOf(birthday.getValue());
            try {
                //转换成date数据
                Date d2 = sd1.parse(str2);
                //在转换成只剩yyyy(年)
                str3 = sd1.format(d2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert str3 != null;
            nl = (Integer.parseInt(str1) - Integer.parseInt(str3));
            age.setText(String.valueOf(nl));
        }
    }


    //修改个人信息
    public void xiugai(ActionEvent event) {
        //启动文本编辑
        Editable();
        UnXianShiXiuGai();
        XianShiAnNiu();
    }

    @FXML
    protected void quxiaoxiugai(ActionEvent event) {
        UnXianShiAnNiu();
        XianShiXiuGai();
        UnEditable();
        chaxun();
    }

    public void querenxiugai(ActionEvent event) {
        System.out.println(flag);
        if(flag){
            new DialogBuilder(querenxiugai).setTitle("提示").setMessage("确定修改吗？").setNegativeBtn("取消").setPositiveBtn(
                    "确定",this::updateUserMessage,null).create(500,100);
        }else {
            new DialogBuilder(querenxiugai).setTitle("提示").setMessage("确定添加吗？").setNegativeBtn("取消").setPositiveBtn(
                    "确定",this::insertUserMessage,null).create(500,100);
        }
    }

    //默认文本都不可编辑
    protected void UnEditable(){
        man.setDisable(true);
        woman.setDisable(true);
        age.setEditable(false);
        name.setEditable(false);
        birthday.setEditable(false);
        minzu.setEditable(false);
        tel.setEditable(false);
        qqnumber.setEditable(false);
        weixin.setEditable(false);
        email.setEditable(false);
        idcard.setEditable(false);
    }

    //启用编辑文本
    protected void Editable(){
        man.setDisable(false);
        woman.setDisable(false);
        name.setEditable(true);
        birthday.setEditable(true);
        minzu.setEditable(true);
        tel.setEditable(true);
        qqnumber.setEditable(true);
        weixin.setEditable(true);
        email.setEditable(true);
        idcard.setEditable(true);
    }

    //不显示 修改个人信息按钮
    protected void UnXianShiXiuGai(){
        xiugai.setVisible(false);
        xiugai.setManaged(false);
    }

    void XianShiXiuGai(){
        xiugai.setVisible(true);
        xiugai.setManaged(true);
    }

    //不显示“取消 确认 等”按钮
    void UnXianShiAnNiu(){
        quxiaoxiugai.setManaged(false);
        quxiaoxiugai.setVisible(false);
        querenxiugai.setVisible(false);
        querenxiugai.setManaged(false);
        shangchuan.setManaged(false);
        shangchuan.setVisible(false);
    }

    //显示按钮
    protected void XianShiAnNiu(){
        quxiaoxiugai.setManaged(true);
        quxiaoxiugai.setVisible(true);
        querenxiugai.setVisible(true);
        querenxiugai.setManaged(true);
        shangchuan.setManaged(true);
        shangchuan.setVisible(true);
    }

    //上传头像
    @FXML
    protected void shangchuan(ActionEvent event) throws Exception{
        String path =  chooseFolder();
        if(path!=null){
        File  file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        ImageDate = new byte[(int)file.length()];
        fis.read(ImageDate);
        fis.close();
        System.out.println(ImageDate.length);
           if(ImageDate.length>=10485760){
            System.out.println("图片请小于10MB");
           }else{
            Touxiang1(file.getPath());
           }
        }

    }

    protected void querenshangchuan()throws Exception{
        //将读取到的图片以用户的用户名保存
        File file2 = new File("images/"+StageHashMap.USERS.get("userName")+".jpg");
        if(!file2.exists()){
            file2.getParentFile().mkdirs();
            file2.createNewFile();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        out.write(ImageDate);
        out.close();
        System.out.println(file2.getPath());
        TouXUrl = file2.getPath();
    }

    /**
     * 选择图片
     * */
    private String chooseFolder() {
        String path = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择图片");
        fileChooser.getExtensionFilters().addAll(
                //限制打开jpg png类图片
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        Stage stage = (Stage)shangchuan.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null){
            path =  file.getPath();
            System.out.println(path);
        }else{
            System.out.println("什么都没有");
        }
        return path;
    }

    /**
     * 显示头像
     * */
    protected void Touxiang(String path){

        javafx.scene.image.Image image = new javafx.scene.image.Image("file:"+path);
        touxiang.setImage(image);
        MiNiTouXiang.setImage(image);
        //阴影
        touxiang.setEffect(new DropShadow(10,Color.GRAY));

    }

    /**
     * 小头像
     * */
    protected void MiNiTouxiang(){
        String username = StageHashMap.USERS.get("userName");
        UserMessage userMessage = UserMessage.selectUserMessage(username);
        javafx.scene.image.Image image = new javafx.scene.image.Image("file:"+userMessage.getTouxiangurl());
        MiNiTouXiang.setImage(image);
    }

    /**
     * 头像预览
     * */
    protected void Touxiang1(String path){
        javafx.scene.image.Image image = new javafx.scene.image.Image("file:"+path);
        touxiang.setImage(image);
        MiNiTouXiang.setImage(image);
        MiNiTouXiang.setFitHeight(50);
        MiNiTouXiang.setFitWidth(50);
        touxiang.setFitHeight(163);
        touxiang.setFitWidth(129);
    }

    //插入信息
    private void insertUserMessage(){
        if(name.getText().trim().equals("") ||(!man.isSelected() && !woman.isSelected()) || idcard.getText().trim().equals("") || birthday.getValue()==null
    || minzu.getText().trim().equals("") ||tel.getText().trim().equals("")){
            new DialogBuilder(querenxiugai).setTitle("提示").setMessage("必填项不能为空").setPositiveBtn(
                    "确定", null).create(500, 100);
        }else {
            if(!Method.minzu(minzu.getText().trim())){
                new DialogBuilder(querenxiugai).setTitle("提示").setMessage("请输入正确的中华人民共和国的民族").setPositiveBtn(
                        "确定", null).create(500, 100);
            }else {
                UserMessage userMessage = new UserMessage();
                try {

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
                    if (ImageDate != null) {
                        querenshangchuan();
                    }
                    //由于本系统为药房排班，暂时默认为药房
                    userMessage.setDepartname("药房");
                    userMessage.setUsername(StageHashMap.USERS.get("userName"));
                    String string = String.valueOf(birthday.getValue());
                    userMessage.setBirthday(DateUtil.strToDate(string));
                    userMessage.setTouxiangurl(TouXUrl);
                    userMessage.setAge(Integer.parseInt(age.getText().trim()));

                    MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).insertUserMessage(userMessage);
                    MybatisUtil.getSqlSession().commit();
                    MybatisUtil.closeSqlSession();

                    UserLoad userLoad = new UserLoad();
                    userLoad.setEmail(email.getText().trim());
                    userLoad.setTelphone(tel.getText().trim());
                    userLoad.setUsername(StageHashMap.USERS.get("userName"));
                    UserLoad.updateothers(userLoad);
//                    MybatisUtil.getSqlSession().getMapper(UserDao.class).updateothers(userLoad);
//                    MybatisUtil.getSqlSession().commit();
//                    MybatisUtil.closeSqlSession();

                    new DialogBuilder(querenxiugai).setTitle("提示").setMessage("修改成功").setPositiveBtn(
                            "确定", null).create(500, 100);
                    UnXianShiAnNiu();
                    XianShiXiuGai();
                    UnEditable();
                    chaxun();

                } catch (Exception e) {

                    logger.info("用户" + StageHashMap.USERS.get("userName") + "修改信息失败");
                    e.printStackTrace();
                }
            }
        }
    }

    //更新信息
    protected void updateUserMessage(){
        if(name.getText().trim().equals("")  || idcard.getText().trim().equals("") || birthday.getValue()==null
                || minzu.getText().trim().equals("") || tel.getText().trim().equals("")){
            new DialogBuilder(querenxiugai).setTitle("提示").setMessage("必填项不能为空").setPositiveBtn(
                    "确定", null).create(500, 100);
        } else {
            if(!Method.minzu(minzu.getText().trim())){
                new DialogBuilder(querenxiugai).setTitle("提示").setMessage("请输入正确的中华人民共和国的民族").setPositiveBtn(
                        "确定", null).create(500, 100);
            }else if(!Method.IsTel(tel.getText().trim())){
                new DialogBuilder(querenxiugai).setTitle("提示").setMessage("请输入正确的手机号码").setPositiveBtn(
                        "确定", null).create(500, 100);
            }
            else{
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
                    userMessage.setUsername(StageHashMap.USERS.get("userName"));
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
                    userLoad.setUsername(StageHashMap.USERS.get("userName") );

                    if(UserLoad.updateothers(userLoad) && UserMessage.updateUserMessage(userMessage)){
                        new DialogBuilder(querenxiugai).setTitle("提示").setMessage("修改成功").setPositiveBtn(
                                "确定", null).create(500, 100);
                        UnXianShiAnNiu();
                        XianShiXiuGai();
                        UnEditable();
                        chaxun();
                    }else{
                        new DialogBuilder(querenxiugai).setTitle("提示").setMessage("修改失败").setPositiveBtn(
                                "确定", null).create(500, 100);
                        logger.info("用户" + StageHashMap.USERS.get("userName") + "修改信息失败");
                    }

            }
        }

    }

    /**
     * 按钮消失
     * */
    protected void Buttoncancel(){
        cxgrxx.setVisible(false);
        cxgrxx.setManaged(false);
        cxpbb.setVisible(false);
        cxpbb.setManaged(false);
        zx.setVisible(false);
        zx.setManaged(false);
        xjsq.setVisible(false);
        xjsq.setManaged(false);
        ChangePassword.setManaged(false);
        ChangePassword.setVisible(false);
    }
    /**
     * 按钮出现
     * */
    protected void ButtonRise(){
        cxgrxx.setVisible(true);
        cxgrxx.setManaged(true);
        cxpbb.setVisible(true);
        cxpbb.setManaged(true);
        zx.setVisible(true);
        zx.setManaged(true);
        xjsq.setVisible(true);
        xjsq.setManaged(true);
        ChangePassword.setVisible(true);
        ChangePassword.setManaged(true);

    }

    /**
     * 菜单按钮事件
     * */
    @FXML
    protected void GeneralButton(ActionEvent event) {
        if(ButtonCancelOrRise==0){
           ButtonRise();
            ButtonCancelOrRise++;
            String button2 = "images/button2.png";
            Method.buttonTuBiao(button2,GeneralButton,25,25);
        }else{
            Buttoncancel();
            ButtonCancelOrRise--;
            String button1 = "images/button1.png";
            Method.buttonTuBiao(button1,GeneralButton,25,25);
        }

    }
}
