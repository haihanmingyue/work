package controller;

import dao.*;
import Util.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.Logger;
import Util.ShowBorderTable;

import other.Man;
import other.PaiBanFinish;
import sample.AddSchedule;
import sample.AdminQueryApply;
import sample.ChangeUserLoad;
import sample.ChangeUserMessage;


import java.net.URL;
import java.util.*;


public  class AdminIndexController<event>extends UserIndexController implements Initializable {

    private static Logger logger = Logger.getLogger(AdminIndexController.class);


    @FXML AnchorPane resault;
    @FXML TextArea resulttext;
    @FXML AnchorPane change;
    @FXML AnchorPane ZhouYi;
    @FXML AnchorPane ZhouRi;
    @FXML VBox MakeSchedule;
    @FXML AnchorPane People;

    /**
     * 排班信息列表
     * */
    @FXML JFXButton TableList,CalendarView;
    @FXML VBox PaiBanONE,PaiBanTable;
    @FXML TextField ScheduleSearchText,ScheduleChangeText;
    @FXML Button ScheduleSearch,ScheduleSearchUpdate,ScheduleChange;
    @FXML BorderPane ScheduleBorder;
    @FXML TableView<Schedule> ScheduleTable;
    @FXML TableColumn ScheduleID,ScheduleUsername,ScheduleName,ScheduleDuty,ScheduleDate,ScheduleDutyName;
    @FXML ComboBox ScheduleYear,ScheduleMonth,ScheduleDay;
    @FXML TableColumn ScheduleBJ,ScheduleSC;
    private Schedule schedule;
    private ShowBorderTable<Schedule> ScheduleSBT;
    private ObservableList<Schedule> schedulesList;

    /**
     * 申请审批界面
     * */
    @FXML
    TextField TZNumber;
    @FXML
    BorderPane ApplyBorder;
    @FXML
    TableView<Application> ApplyTable;
    @FXML
    TableColumn SHUserName,SHApplyType,SHDate;
    @FXML
    TableColumn SHApplyStatus,SHAuditor,SHAuditDate,xiangqing;
    @FXML
    VBox Apply;
    @FXML
    JFXDatePicker searchdate;
    @FXML
    JFXButton shenqingshenpi;
    private Application application1;
    private ObservableList<Application> applicationsList;
    private ShowBorderTable<Application> APPlySBT;

    /**
     *这里是查询用户信息的界面
     * */
    @FXML
    VBox Usermessage;
    @FXML
    TextField ssk1,tiaozhuanyeshu;
    @FXML
    TableView<UserMessage>usermessagetable;
    @FXML
    TableColumn loadID,Name,sex,depart,phone,shanchu,xiangxi;
    @FXML
    BorderPane usermessageborder;
    private ObservableList<UserMessage> userMessageslist;
    private UserMessage userMessage1,userMessage2;
    private ShowBorderTable<UserMessage> UserMessageSBT;

    /**
     * 默认启动的界面
     * */
    @FXML
    HBox text1;

    /**
     * 判断按钮颜色的 和 显示 不同界面
     **/
    private int buttonColor= 0;

    /**
     * 查询账户信息
     * tjzh 添加账号 BJ 编辑 SC 删除 CX 查询
     * cxgrxx 查询个人信息 cxyhxx 查询用户信息
     * cxpbb 查询排班表 zzpbb 制作排班表
     * */
    private ObservableList<UserLoad> list;
    private UserLoad userLoad1,userLoad2;
    @FXML
    Button shua,ss,tz,sy,dy,tjzh;
    @FXML
    BorderPane border;
    @FXML
    TextField tznumber,ssk;
    @FXML
    VBox table1;
    @FXML
    JFXButton cx,cxgrxx,cxyhxx,cxpbb,zzpbb;
    @FXML
    TableColumn Password,Admin,Time,Tel,Email,Bj,Sc,ID,Username;
    @FXML
    TableView<UserLoad> table;
    private ShowBorderTable<UserLoad> UserLoadSBT;
    private JFXButton button1,button2;
    private int index3;

    /**
     * 查看排班
     * JFXButton[] 的个数即是每月天数
     * */
    private JFXButton[] jfxButtons;
    public AnchorPane ScheduleOfDay;
    private static boolean X ;
    //弹出的排班表信息界面用来判断是否清空控件
    private int count1 = 0;
    private int ButtonCancelOrRise = 0;
    private JFXButton[] people;
    private JFXButton[] banci;
    private JFXButton[] banc2;
    private int xxxx=0;//全选按钮1 的判断变量
    private int xxxx1=0;//全选按钮2的
    private int xxxx2=0;//全选按钮3的
    private List<UserMessage> manList;
    private List<Dutytype> list111,list222;
    private ArrayList<Dutytype> work1 = new ArrayList<>();
    private ArrayList<Dutytype> work2 = new ArrayList<>();
    private ArrayList<Man> peopleList = new ArrayList<>();
    private int[] worknumber;
    private int[] weeknumber;
    private String date;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //调用hand
        Method.hand(cxgrxx);Method.hand(cxyhxx);Method.hand(zzpbb);
        Method.hand(cxpbb);Method.hand(cx);Method.hand(zx);
        Method.hand(shenqingshenpi);Method.hand(fd);Method.hand(gb);
        Method.hand(sx);Method.hand(ss);Method.hand(shua);Method.hand(ChangePassword);
        Method.hand(sy);Method.hand(dy);Method.hand(tz);Method.hand(tjzh);
        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));
        Tooltip.install(fd, new Tooltip("最大化"));

        //放在一组，就只能选择一个
        ToggleGroup group = new ToggleGroup();
        man.setToggleGroup(group);
        woman.setToggleGroup(group);

        String button1 = "images/button1.png";
        Method.buttonTuBiao(button1,GeneralButton,25,25);
        Buttoncancel(); //默认菜单栏消失
        MiNiTouxiang();

        MakeSchedule();
    }


    /**
     * 显示日历样式的排班信息（默认系统当前年月）
     * */
    private void ShowCalendar() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        int w = calendar1.get(Calendar.DAY_OF_WEEK)-1;//判断每个月第一天是星期几
        if(w==0) w=7;
        System.out.println("星期"+w);
        //天数即是button的个数
        jfxButtons = new JFXButton[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        int x = w;
        int y = 0;
        //日期栏
        for(int i=0;i<jfxButtons.length;i++){
            jfxButtons[i] = new JFXButton("今天是"+(i+1)+"号\n"+"点击查看今日排班");
            jfxButtons[i].setStyle("-fx-background-color: #00cdff;-fx-alignment: center;-fx-font-family: '黑体';");
            jfxButtons[i].setPrefSize(150,100);
            Pane.add(jfxButtons[i],x-1,y);
            Pane.setHalignment(jfxButtons[i], HPos.CENTER);
            Pane.setValignment(jfxButtons[i], VPos.TOP);
            int finalI = i;
            jfxButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ScheduleOfDay.setVisible(true);
                    ScheduleOfDay.setManaged(true);
                    ScheduleOfDay.setTranslateX(0);
                    String years = year.getPromptText();
                    String months = month.getPromptText();
                    String days = String.valueOf(finalI+1);
                    if(count1>0){
                        ScheduleOfDay.getChildren().clear();
                    }
                    count1++;
                    if((finalI+1)<10){
                       days = "0"+days;
                    }
                    if(Integer.parseInt(months)<10){
                        months = "0"+months;
                    }
                    String sum = years+"-"+months+"-"+days;
                    SearchScheduleByDay(sum);

                }
            });
            if(x==6){
                jfxButtons[i].setStyle("-fx-background-color: #22ff67;-fx-font-family: '黑体'");
            }
            //x==7就切换到下一行
            if(x==7){
                jfxButtons[i].setStyle("-fx-background-color: #22ff67;-fx-font-family: '黑体'");
                x=0;
                y++;
            }
            x++;
        }
    }

    /**
     * 调整年月后的
     * */
    @Override
    public void ShowCalendar(int years,int months) throws Exception{
        year.setPromptText(years+"");
        month.setPromptText(months+"");
        Calendar calendar = Calendar.getInstance();
        String s = years+"-"+months;
        Date date = DateUtil.StringToDate(s);
        calendar.setTime(date);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);
        //判断每个月第一天是星期几
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        int w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        System.out.println("星期"+w);
        jfxButtons = new JFXButton[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        int x = w;
        int y = 0;
        //日期栏
        //日期栏
        for(int i=0;i<jfxButtons.length;i++){
            jfxButtons[i] = new JFXButton("今天是"+(i+1)+"号\n"+"点击查看今日排班");
            jfxButtons[i].setStyle("-fx-background-color: #00cdff;-fx-alignment: center;-fx-font-family: '黑体';");
            jfxButtons[i].setPrefSize(150,100);
            Pane.add(jfxButtons[i],x-1,y);
            Pane.setHalignment(jfxButtons[i], HPos.CENTER);
            Pane.setValignment(jfxButtons[i], VPos.TOP);
            int finalI = i;
            jfxButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ScheduleOfDay.setVisible(true);
                    ScheduleOfDay.setManaged(true);
                    ScheduleOfDay.setTranslateX(0);

                    //获取ComBox 的 年 月，日就是按钮的次序
                    String years = year.getPromptText();
                    String months = month.getPromptText();
                    String days = String.valueOf(finalI+1);
                    if(count1>0){
                        ScheduleOfDay.getChildren().clear();
                    }
                    count1++;
                    if((finalI+1)<10){
                        days = "0"+days;
                    }
                    if(Integer.parseInt(months)<10){
                        months = "0"+months;
                    }
                    String sum = years+"-"+months+"-"+days;
                    SearchScheduleByDay(sum);
                }
            });

            if(x==6){
                jfxButtons[i].setStyle("-fx-background-color: #22ff67;-fx-font-family: '黑体'");
            }
            //x==7就切换到下一行
            if(x==7){
                jfxButtons[i].setStyle("-fx-background-color: #22ff67;-fx-font-family: '黑体'");
                x=0;
                y++;
            }
            x++;
        }
    }

    /**
     * 通过日期 搜索排班
     * */
    private void SearchScheduleByDay(String date){

        JFXButton CancelButton = new JFXButton(">");
        CancelButton.setLayoutY(10);
        CancelButton.setLayoutX(14);
        CancelButton.setPrefWidth(30);
        CancelButton.setStyle("-fx-background-color: white");
        Label label = new Label("今天是");
        label.setLayoutY(15);
        label.setLayoutX(95);
        Label label1 = new Label(date);
        label1.setLayoutY(40);
        label1.setLayoutX(85);
        Label[] label2 = new Label[10];
        String[]strings = new String[]{"早班","小早","审方","核对","排药","电话","长1","长2","长3","晚班"};
        for(int i =0;i<10;i++){
            label2[i] = new Label(strings[i]);
            label2[i].setLayoutX(17);
            label2[i].setLayoutY(40+35*(i+1));
            ScheduleOfDay.getChildren().add(label2[i]);
        }
        Label label3 = new Label("夜班");
        label3.setLayoutY(455);
        label3.setLayoutX(17);
        Label label4 = new Label("临时");
        label4.setLayoutY(420);
        label4.setLayoutX(17);
        ScheduleOfDay.getChildren().addAll(CancelButton,label,label1,label3,label4);
        CancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ScheduleOfDay.setTranslateX(249);
                ScheduleOfDay.setVisible(false);
                ScheduleOfDay.setManaged(false);
            }
        });

        //清空所有数据
        StageList.clearAll();
        List<Schedule> dayList;
        //搜索某日的排班信息存入list
        dayList = Schedule.Schedule(date);
        UserMessage userMessage;

        for(Schedule schedule : dayList){
            //搜索该ID相对应的用户信息
            userMessage = UserMessage.selectUserMessage(schedule.getEmployeeID());
            if(schedule.getDutyName().equals("早班")){
                StageList.早班.add(userMessage.getName()); //存入该班次对应人的名字
                StageList.ZaoBan.add(schedule);
            }
            if(schedule.getDutyName().equals("小早")){
                StageList.小早.add(userMessage.getName());
                StageList.XiaoZao.add(schedule);
            }
            if(schedule.getDutyName().equals("审方")){
                StageList.审方.add(userMessage.getName());
                StageList.shenfang.add(schedule);
            }
            if(schedule.getDutyName().equals("核对")){
                StageList.核对.add(userMessage.getName());
                StageList.hedui.add(schedule);
            }
            if(schedule.getDutyName().equals("排药")){
                StageList.排药.add(userMessage.getName());
                StageList.paiyao.add(schedule);
            }
            if(schedule.getDutyName().equals("晚班")){
                StageList.晚班.add(userMessage.getName());
                StageList.wanban.add(schedule);
            }
            if(schedule.getDutyName().equals("临时")){
                StageList.临时.add(userMessage.getName());
                StageList.linshi.add(schedule);
            }
            if(schedule.getDutyName().equals("夜班")){
                StageList.夜班.add(userMessage.getName());
                StageList.yeban.add(schedule);
            }
            if(schedule.getDutyName().equals("电话")){
                StageList.电话.add(userMessage.getName());
                StageList.dianhua.add(schedule);
            }
            if(schedule.getDutyName().equals("长1")){
                StageList.长1.add(userMessage.getName());
                StageList.long1.add(schedule);
            }
            if(schedule.getDutyName().equals("长2")){
                StageList.长2.add(userMessage.getName());
                StageList.long2.add(schedule);
            }
            if(schedule.getDutyName().equals("长3")){
                StageList.长3.add(userMessage.getName());
                StageList.long3.add(schedule);
            }

        }

        TextField [] text = CreateTextFiled(StageList.早班,45,45,74);

        TextField [] text2 = CreateTextFiled(StageList.小早,45,45,108);

        TextField [] text3 = CreateTextFiled(StageList.审方,45,45,143);

        TextField [] text4 = CreateTextFiled(StageList.核对,45,45,176);

        TextField [] text5 = CreateTextFiled(StageList.排药,45,45,210);

        TextField [] text6 = CreateTextFiled(StageList.晚班,45,45,385);

        TextField [] text7 = CreateTextFiled(StageList.电话,45,45,245);

        TextField [] text8 = CreateTextFiled(StageList.临时,45,45,415);

        TextField [] text9 = CreateTextFiled(StageList.长1,45,45,280);

        TextField [] text10 = CreateTextFiled(StageList.长2,45,45,315);

        TextField [] text11 = CreateTextFiled(StageList.长3,45,45,350);

        TextField [] text12 = CreateTextFiled(StageList.夜班,45,45,450);


        JFXButton changeButton = new JFXButton("修改");
        changeButton.setStyle("-fx-background-color: #0f8afa;-fx-text-fill: white;");
        changeButton.setPrefWidth(150);
        changeButton.setLayoutY(600);
        changeButton.setLayoutX(50);
        ScheduleOfDay.getChildren().add(changeButton);

        changeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                while(true){
                    new DialogBuilder(gb).setTitle("提示").setMessage("是否修改？").setNegativeBtn("取消",this::Xfalse,null).setPositiveBtn("确定",this::Xtrue,null).create(300,100);
                    if(!X)
                        break;
                    ChangeSchedule(text,StageList.ZaoBan);
                    ChangeSchedule(text2,StageList.XiaoZao);
                    ChangeSchedule(text3,StageList.shenfang);
                    ChangeSchedule(text4,StageList.hedui);
                    ChangeSchedule(text5,StageList.paiyao);
                    ChangeSchedule(text6,StageList.wanban);
                    ChangeSchedule(text7,StageList.dianhua);
                    ChangeSchedule(text8,StageList.linshi);
                    ChangeSchedule(text9,StageList.long1);
                    ChangeSchedule(text10,StageList.long2);
                    ChangeSchedule(text11,StageList.long3);
                    ChangeSchedule(text12,StageList.yeban);
                    new DialogBuilder(gb).setTitle("提示").setMessage("执行结束").setNegativeBtn("取消",this::Xfalse,null).setPositiveBtn("确定",this::Xtrue,null).create(300,100);
                    break;
                }

            }

            private void Xfalse() {
                X = false;
            }

            private void Xtrue() {
                X = true;
            }
        });

    }

    /**
     * 通过名字获取ID，有重名的话就不能使用了，以后再做优化
     **/
    private String ID(String NAME){
        UserMessage userMessage = UserMessage.selectUserMessageByName(NAME);

        if(userMessage==null){
            new DialogBuilder(gb).setTitle("提示").setMessage("没有"+NAME+"这个名字的ID,该班次修改失败").setPositiveBtn("确定",null).create(400,100);
            return null;
        }else{
            return userMessage.getUsername();
        }
    }

    /**
     * 修改排班
     * */
    private void ChangeSchedule(TextField[] textFields,List<Schedule> list){
        boolean flag = true;
        Schedule s1;
        for (int i =0;i<textFields.length;i++) {
            if(!textFields[i].getText().trim().equals("")){
                s1 = new Schedule();
                System.out.println(textFields[i].getText());
                if(ID(textFields[i].getText().trim())==null){
                    flag = false;
                }else{
                    String ID = ID(textFields[i].getText().trim());
                    s1.setEmployeeID(ID);
                }
                s1.setId(list.get(i).getId());
                //flag==false就说明没有搜索到名字对应的ID，立刻退出循环
                if(!flag){
                    break;
                }
                try{
                    Schedule.updateScheduleMan(s1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     *日历界面查每日排班时生成的文本框  width： 宽度  XX： LayoutX   yy： LayoutY
     */
    private TextField[] CreateTextFiled(List<String> list,int width,int xx,int yy){
        TextField[] text = new TextField[list.size()];
        for(int x =0; x<list.size();x++){
            text[x] = new TextField();
            text[x].setText(list.get(x));
            text[x].setPrefWidth(width);
            text[x].setLayoutX(xx*(x+1));
            text[x].setLayoutY(yy);
            ScheduleOfDay.getChildren().add(text[x]);
        }
        return text;
    }



    /**
     * 用户信息界面
     * */
    public void UserMessageSs(ActionEvent event) {

        if (!ssk1.getText().trim().equals("")) {
            UserMessageSskList(ssk1);
        } else {
            UserMessageShowList();
        }
    }

    private void UserMessageAll() {
        userMessageslist = FXCollections.observableArrayList();
        userMessageslist.clear();
        userMessageslist.addAll(UserMessage.AllUserMessageList());
    }

    private void UserMessageKeyAll(String key) {
        userMessageslist = FXCollections.observableArrayList();
        userMessageslist.clear();
        userMessageslist.addAll(UserMessage.AllUserMessageListByKey(key));
    }

    /**
     * 写入数据到用户详细信息列表
     * */
    private void UserMessageCreate() {

        loadID.setCellValueFactory(new PropertyValueFactory("username"));
        loadID.setCellFactory(TextFieldTableCell.forTableColumn());
        Name.setCellValueFactory(new PropertyValueFactory("name"));//映射
        sex.setCellValueFactory(new PropertyValueFactory("xingbie"));
        depart.setCellValueFactory(new PropertyValueFactory("departname"));
        phone.setCellValueFactory(new PropertyValueFactory("weixin"));
        xiangxi.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button1 = new JFXButton("详细");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button1.setOnMouseClicked((col1) -> {
                                int index = StageHashMap.Int.get("pageindex");
                                StageHashMap.USERMessage.clear();
                                int num = index + 1;
                                System.out.println("这是第" + num + "页");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                System.out.println("第" + index2 + "个");
                                index3 = index2 + index1;
                                System.out.println("在总数中是第" + index3 + "个");
                                userMessage1 = userMessageslist.get(index3);
                                StageHashMap.USERMessage.put("UserMessage",userMessage1.getUsername());
                                 ChangeUserMessage open = new ChangeUserMessage();
                                 try {
                                     open.start(new Stage());
                                 }catch (Exception e){
                                     e.printStackTrace();
                                 }
                            });
                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                    };
                }
        );
        shanchu.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button2 = new JFXButton("删除");
                            button2.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button2.setOnMouseClicked((col1) -> {
                                int index = StageHashMap.Int.get("pageindex");
                                int num = index + 1;
                                System.out.println("这是第" + num + "页");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                System.out.println("第" + index2 + "个");
                                index3 = index2 + index1;
                                System.out.println("在总数中是第" + index3 + "个");
                                userMessage2 = userMessageslist.get(index3);
                                new DialogBuilder(zx).setTitle("提示").setMessage("确定清空用户" + userMessage2.getUsername() + "的信息吗？").setNegativeBtn("取消").setPositiveBtn("确定", this::del, "-fx-background-color: #00bcff;-fx-text-fill: #ffffff").create(500, 200);
                            });
                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button2);
                            }
                        }
                        //删除
                        private void del() {
                                if (UserMessage.deleteUserMessage(userMessage2)) {
                                    userMessageslist.remove(index3);
                                    logger.info(StageHashMap.USERS.get("userName") + "清空了账号" + userMessage2.getUsername()+"的信息");
                                    MybatisUtil.getSqlSession().commit();
                                    MybatisUtil.closeSqlSession();
                                    new DialogBuilder(zx).setTitle("提示").setMessage("删除成功").setPositiveBtn("确定", null).create(250, 50);
                                    UserMessageSBT.ShowBorderTableNowPage(userMessageslist,usermessagetable,usermessageborder);
                                } else {
                                    new DialogBuilder(zx).setTitle("提示").setMessage("删除失败").setPositiveBtn("确定", null).create(250, 50);
                                    logger.info(StageHashMap.USERS.get("userName") + "清空了用户" + userMessage2.getUsername() + "的信息失败");
                                }
                        }
                    };
                }
        );
        //usermessagetable.setItems((ObservableList<UserMessage>) userMessages);
        usermessagetable.setItems(userMessageslist);
        usermessagetable.setEditable(true);
        //TableView 随窗口变化
        usermessagetable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * 创建所有用户信息列表
     * */
    private void UserMessageShowList() {
        UserMessageAll();
        new ShowBorderTable<>(userMessageslist,usermessagetable,usermessageborder);
        UserMessageCreate();
    }
    /**
     * 创建搜索列表
     * */
    private void UserMessageSskList(TextField textField) {
        String key = textField.getText().trim();
        UserMessageKeyAll(key);
        new ShowBorderTable<>(userMessageslist,usermessagetable,usermessageborder);
        UserMessageCreate();
    }



    public void shouye(ActionEvent event) {
        new ShowBorderTable<>(userMessageslist,usermessagetable,usermessageborder);
    }

    public void diye(ActionEvent event) {
        UserMessageSBT.ShowBorderTableEndPage(userMessageslist,usermessagetable,usermessageborder);
    }

    public void TiaoZhuan(ActionEvent event) {
        UserMessageSBT.PageChange(tiaozhuanyeshu,gb,userMessageslist,usermessagetable,usermessageborder);
    }



    public void shuaxin(ActionEvent event) {
        UserMessageAll();
        UserMessageCreate();
        UserMessageSBT.ShowBorderTableNowPage(userMessageslist,usermessagetable,usermessageborder);
    }


    /**
     * 账号信息部分
     *
     * 搜索按钮事件添加
     * */
    public void ss(ActionEvent event) {
        String key = ssk.getText().trim();
        if (!key.equals("")) {
            sskList(ssk);
        } else {
            showList();
        }
    }

    private void All() {
        list = FXCollections.observableArrayList();
        list.clear();
        list.addAll(UserLoad.AllUserLoad());
    }

    private void KeyAll(String username) {
        list = FXCollections.observableArrayList();
        list.clear();
        list.addAll(UserLoad.selectLikeUsername(username));
    }

    //写入数据到table
    private void create() {

        ID.setCellValueFactory(new PropertyValueFactory("id"));
        Username.setCellValueFactory(new PropertyValueFactory("username"));//映射
        Password.setCellValueFactory(new PropertyValueFactory("password"));
        Admin.setCellValueFactory(new PropertyValueFactory("admin"));
        Time.setCellValueFactory(new PropertyValueFactory("registrationdate"));
        Tel.setCellValueFactory(new PropertyValueFactory("telphone"));
        Email.setCellValueFactory(new PropertyValueFactory("email"));

        Bj.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button1 = new JFXButton("编辑");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                               @Override
                               public void handle(MouseEvent event) {
                                   setCursor(Cursor.HAND);
                               }
                           });
                            button1.setOnMouseClicked((col1) -> {
                                if (StageHashMap.Int.get("admin") == 1) {

                                    int index = StageHashMap.Int.get("pageindex");
                                    int num = index + 1;
                                    System.out.println("这是第" + num + "页");
                                    int index1 = index * 30;
                                    int index2 = getIndex();
                                    System.out.println("第" + index2 + "个");
                                    index3 = index2 + index1;
                                    System.out.println("在总数中是第" + index3 + "个");
                                    userLoad1 = list.get(index3);
                                    StageHashMap.ChangeUSERS.put("ChangeUsername", userLoad1.getUsername());
                                    StageHashMap.ChangeUSERS.put("ChangePassword", userLoad1.getPassword());
                                    StageHashMap.ChangeUSERS.put("ChangeTel", userLoad1.getTelphone());
                                    StageHashMap.ChangeAdmin.put("ChangeAdmin", userLoad1.getAdmin());
                                    if(userLoad1.getEmail()!=null){
                                        StageHashMap.ChangeUSERS.put("ChangeEmail", userLoad1.getEmail());
                                    }
                                    ChangeUserLoad open = new ChangeUserLoad();
                                    open.start(new Stage());

                                }

                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                    };
                }
        );

        Sc.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button2 = new JFXButton("删除");
                            button2.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button2.setOnMouseClicked((col1) -> {
                                int index = StageHashMap.Int.get("pageindex");
                                int num = index + 1;
                                System.out.println("这是第" + num + "页");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                System.out.println("第" + index2 + "个");
                                index3 = index2 + index1;
                                System.out.println("在总数中是第" + index3 + "个");
                                userLoad2 = list.get(index3);
                                new DialogBuilder(zx).setTitle("提示").setMessage("确定删除用户" + userLoad2.getUsername() + "吗？").setNegativeBtn("取消").setPositiveBtn("确定", this::del, "-fx-background-color: #00bcff;-fx-text-fill: #ffffff").create(500, 200);
                            });
                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button2);
                            }
                        }

                        //删除用户账号
                        private void del() {
                            int j = userLoad2.getAdmin();
                            if (j == 1) {
                                new DialogBuilder(zx).setTitle("提示").setMessage("管理员账号无法删除").setPositiveBtn("确定", null).create(250, 50);
                            } else {
                                if (UserLoad.deleteUserLoad(userLoad2.getUsername())) {
                                    list.remove(index3);
                                    logger.info(StageHashMap.USERS.get("userName") + "删除了账号" + userLoad2.getUsername());
                                    new DialogBuilder(zx).setTitle("提示").setMessage("删除成功").setPositiveBtn("确定", null).create(250, 50);
                                    UserLoadSBT.ShowBorderTableNowPage(list,table,border);
                                } else {
                                    new DialogBuilder(zx).setTitle("提示").setMessage("删除失败").setPositiveBtn("确定", null).create(250, 50);
                                    logger.info(StageHashMap.USERS.get("userName") + "删除账号" + userLoad2.getUsername() + "失败");
                                }

                            }
                        }
                    };
                }
        );
        table.setItems(list);
        //Tableview 随窗口变化变化
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * 所有数据
     * */
    private void showList() {
        All();
        new ShowBorderTable<>(list,table,border);
        create();
    }

    /**
     * 获取搜索框输入的数据进行搜索
     * */
    private void sskList(TextField textField) {
        String key = textField.getText().trim();
        KeyAll(key);
        new ShowBorderTable<>(list,table,border);
        create();
    }

    /**
     * 跳转按钮
     * */
    public void tiaozhuan(ActionEvent event) {
        UserLoadSBT.PageChange(tznumber,gb,list,table,border);
    }


    public void index(ActionEvent event) {
        new ShowBorderTable<>(list,table,border);
    }

    public void end(ActionEvent event) {
        UserLoadSBT.ShowBorderTableEndPage(list,table,border);
    }

    /**
     * 添加账号按钮
     * */
    public void addUserLoad(ActionEvent event) {
        ChangeUserLoad open = new ChangeUserLoad();
        open.start(new Stage());
    }

    public void shua(ActionEvent event) {
        All();
        create();
        new ShowBorderTable<>(list,table,border);
    }

    /**
     * 个人信息中心
     * */
    @Override
    public void cxgrxx(ActionEvent event) {
        buttonColor = 1;
        ButtonColor(buttonColor);
        UnEditable();
        XianShiXiuGai();
        UnXianShiAnNiu();
        chaxun();
    }
    /**
     * 返回首页
     * */
    @Override
    public void fanhui(ActionEvent event) {
          UnXianShiAnNiu();
          person.setManaged(false);
          person.setVisible(false);
          text1.setVisible(true);
          text1.setManaged(true);
          cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
          cx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
    }

    /**
     * 查询账号信息
     * */
    public void cx(ActionEvent event) {
        buttonColor = 2;
        ButtonColor(buttonColor);
        UserLoadSBT = new ShowBorderTable<>();
        showList();
    }

    /**
     * 查询用户信息
     * */
    public void cxyhxx(ActionEvent event) {
       buttonColor = 3;
       ButtonColor(buttonColor);
       UserMessageSBT= new ShowBorderTable<>();
       UserMessageShowList();
    }

    /**
     * 查询排班表
     * */
    public void cxpbb(ActionEvent event) {
        buttonColor = 4;
        ButtonColor(buttonColor);
        ScheduleSBT = new ShowBorderTable<>();
        String url1 = "images/listicon.png";
        Method.buttonTuBiao(url1,TableList,25,25);
        String url2 = "images/riliicon.png";
        Method.buttonTuBiao(url2,CalendarView,25,25);
    }

    /**
     * 制作排班表
     * */
    public void zzppb(ActionEvent event) {
        buttonColor = 5;
        ButtonColor(buttonColor);
    }

   /**
    * 申请审批
    * */
    public void shenqingshenpi(ActionEvent event) {
        buttonColor = 6;
        ButtonColor(buttonColor);
        APPlySBT = new ShowBorderTable<>();
        SHShowList();
    }

    /**
     * 不同按钮的事件以及按钮颜色变化
     * */
    @Override
    public void ButtonColor(int buttonColor){
        switch (buttonColor){
            case 1:
                text1.setVisible(false);text1.setManaged(false);
                table1.setVisible(false);table1.setManaged(false);
                person.setManaged(true);person.setVisible(true);
                Apply.setVisible(false);Apply.setManaged(false);
                MyPaiBan.setVisible(false);MyPaiBan.setManaged(false);
                PaiBanTable.setManaged(false);PaiBanTable.setVisible(false);
                MakeSchedule.setVisible(false);MakeSchedule.setManaged(false);
                PaiBanONE.setVisible(false);PaiBanONE.setManaged(false);
                Usermessage.setVisible(false);Usermessage.setManaged(false);
                cxgrxx.setStyle("-fx-background-color: #eeeeee;-fx-text-fill: #171717");
                cx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxyhxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                shenqingshenpi.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                zzpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                break;
            case 2:
                text1.setVisible(false);text1.setManaged(false);
                person.setVisible(false);person.setManaged(false);
                Usermessage.setVisible(false);Usermessage.setManaged(false);
                Apply.setVisible(false);Apply.setManaged(false);
                MyPaiBan.setVisible(false);MyPaiBan.setManaged(false);
                PaiBanTable.setManaged(false);PaiBanTable.setVisible(false);
                MakeSchedule.setVisible(false);MakeSchedule.setManaged(false);
                PaiBanONE.setVisible(false);PaiBanONE.setManaged(false);
                table1.setVisible(true);table1.setManaged(true);
                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cx.setStyle("-fx-background-color: #eeeeee;-fx-text-fill: #171717");
                cxyhxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                shenqingshenpi.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                zzpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                break;
            case 3:
                table1.setManaged(false);table1.setVisible(false);
                text1.setManaged(false);text1.setVisible(false);
                person.setVisible(false);person.setManaged(false);
                MyPaiBan.setVisible(false);MyPaiBan.setManaged(false);
                Apply.setVisible(false);Apply.setManaged(false);
                PaiBanONE.setVisible(false);PaiBanONE.setManaged(false);
                PaiBanTable.setManaged(false);PaiBanTable.setVisible(false);
                MakeSchedule.setVisible(false);MakeSchedule.setManaged(false);
                Usermessage.setVisible(true);Usermessage.setManaged(true);
                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxyhxx.setStyle("-fx-background-color: #eeeeee;-fx-text-fill: #171717");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                shenqingshenpi.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                zzpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                break;
            case 4:
                table1.setManaged(false);table1.setVisible(false);
                text1.setManaged(false);text1.setVisible(false);
                person.setManaged(false);person.setVisible(false);
                Usermessage.setVisible(false);Usermessage.setManaged(false);
                Apply.setVisible(false);Apply.setManaged(false);
                MyPaiBan.setVisible(false);MyPaiBan.setManaged(false);
                PaiBanTable.setManaged(false);PaiBanTable.setVisible(false);
                MakeSchedule.setVisible(false);MakeSchedule.setManaged(false);
                PaiBanONE.setVisible(true);PaiBanONE.setManaged(true);
                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxyhxx.setStyle("-fx-background-color:#171717;-fx-text-fill: #eeeeee");
                cxpbb.setStyle("-fx-background-color: #eeeeee;-fx-text-fill: #171717");
                shenqingshenpi.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                zzpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                break;
            case 5:
                table1.setManaged(false);table1.setVisible(false);
                text1.setManaged(false);text1.setVisible(false);
                person.setManaged(false);person.setVisible(false);
                Usermessage.setVisible(false);Usermessage.setManaged(false);
                MyPaiBan.setVisible(false);MyPaiBan.setManaged(false);
                PaiBanONE.setVisible(false);PaiBanONE.setManaged(false);
                PaiBanTable.setManaged(false);PaiBanTable.setVisible(false);
                Apply.setVisible(false);Apply.setManaged(false);
                MakeSchedule.setVisible(true);MakeSchedule.setManaged(true);
                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxyhxx.setStyle("-fx-background-color:#171717;-fx-text-fill: #eeeeee");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                zzpbb.setStyle("-fx-background-color: #eeeeee;-fx-text-fill: #171717");
                shenqingshenpi.setStyle("-fx-background-color:#171717;-fx-text-fill: #eeeeee");
                break;
            case 6:
                table1.setManaged(false);table1.setVisible(false);
                text1.setManaged(false);text1.setVisible(false);
                person.setManaged(false);person.setVisible(false);
                Usermessage.setVisible(false);Usermessage.setManaged(false);
                MyPaiBan.setVisible(false);MyPaiBan.setManaged(false);
                PaiBanTable.setManaged(false);PaiBanTable.setVisible(false);
                PaiBanONE.setVisible(false);PaiBanONE.setManaged(false);
                MakeSchedule.setVisible(false);MakeSchedule.setManaged(false);
                Apply.setVisible(true);Apply.setManaged(true);
                cxgrxx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cx.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                cxyhxx.setStyle("-fx-background-color:#171717;-fx-text-fill: #eeeeee");
                cxpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                zzpbb.setStyle("-fx-background-color: #171717;-fx-text-fill: #eeeeee");
                shenqingshenpi.setStyle("-fx-background-color: #eeeeee;-fx-text-fill: #171717");
                break;
        }
    }


    /**
     * 获取所有数据存入list
     * */
    private void SHAll() {
        applicationsList = FXCollections.observableArrayList();
        applicationsList.clear();
        applicationsList.addAll(Application.AllApplication());
    }

    /**
     * 通过key值获取数据存入list
     * */
    private void SHKeyAll(String date) {
        applicationsList = FXCollections.observableArrayList();
        applicationsList.clear();
        applicationsList.addAll(Application.AllApplication(date));
    }

    /**
     * 写入数据到每列
     * */
    private void SHCreate() {
        SHUserName.setCellValueFactory(new PropertyValueFactory("employeeID"));
        SHApplyType.setCellValueFactory(new PropertyValueFactory("applyTypename"));//映射
        SHDate.setCellValueFactory(new PropertyValueFactory("applicationDate"));
        SHApplyStatus.setCellValueFactory(new PropertyValueFactory("applicationStatusName"));
        SHAuditor.setCellValueFactory(new PropertyValueFactory("auditor"));
        SHAuditDate.setCellValueFactory(new PropertyValueFactory("auditDate"));
        xiangqing.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button1 = new JFXButton("详情");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button1.setOnMouseClicked((col1) -> {
                                StageHashMap.USERMessage.clear();
                                StageHashMap.USERMessageInt.clear();
                                int index = StageHashMap.Int.get("pageindex");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                index3 = index2 + index1;
                                application1 = applicationsList.get(index3);
                                System.out.println(application1.getEmployeeID());
                                System.out.println(application1.getApplicationDate());
                                StageHashMap.USERMessageInt.put("ID",application1.getId());
                                StageHashMap.USERMessage.put("SHUSER",application1.getEmployeeID()); //审核人
                                StageHashMap.USERMessage.put("DATE",application1.getApplicationDate());
                                StageHashMap.USERMessage.put("STATUS",application1.getApplicationStatusName());
                                StageHashMap.USERMessage.put("Type",application1.getApplyTypename());
                                try {
                                    AdminQueryApply open = new AdminQueryApply();
                                    open.start(new Stage());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                    };
                }
        );

        ApplyTable.setItems(applicationsList);
        //Tableview 随窗口变化变化
        ApplyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * 所有
     * */
    private void SHShowList() {
        SHAll();
        new ShowBorderTable<>(applicationsList,ApplyTable,ApplyBorder);
        SHCreate();
    }

    /**
     * 搜索后显示的列表
     * */
    private void SHList(JFXDatePicker jfxDatePicker) {
        String key = String.valueOf(jfxDatePicker.getValue());
        SHKeyAll(key);
        new ShowBorderTable<>(applicationsList,ApplyTable,ApplyBorder);
        SHCreate();
    }

    /**
     * SHTZ = 审核跳转
     * */
    public void SHTZ(ActionEvent event) {

        APPlySBT.PageChange(TZNumber,gb,applicationsList,ApplyTable,ApplyBorder);
    }

    public void SHSXin(ActionEvent event) {
        SHAll();
        SHCreate();
        APPlySBT.ShowBorderTableNowPage(applicationsList,ApplyTable,ApplyBorder);
    }

    public void ApplySearch(ActionEvent event) {
        if (searchdate.getValue() == null) {
            SHShowList();
        }else {
            System.out.println(searchdate.getValue().toString());
            SHList(searchdate);
        }
    }

    /**
     * 审核列表 首页
     * */
    public void SHSY(ActionEvent event) {
        new ShowBorderTable<>(applicationsList,ApplyTable,ApplyBorder);
        APPlySBT.ShowBorderTableNowPage(applicationsList,ApplyTable,ApplyBorder);
    }

    /**
     * 审核列表 底页
     * */
    public void SHDY(ActionEvent event) {
        APPlySBT.ShowBorderTableEndPage(applicationsList,ApplyTable,ApplyBorder);
    }


    /**
     * 查看排班（列表形式）
     * */
    public void TableView(ActionEvent event) {
        PaiBanONE.setVisible(false);
        PaiBanONE.setManaged(false);
        PaiBanTable.setManaged(true);
        PaiBanTable.setVisible(true);
        ScheduleAll();
        ScheduleShowList();
        ScheduleCreate();

    }
    /**
     * 查看排班（日历形式）
     * */
    public void Calendar(ActionEvent event) {
        PaiBanONE.setVisible(false);
        PaiBanONE.setManaged(false);
        MyPaiBan.setManaged(true);
        MyPaiBan.setVisible(true);
        CalendarNowMonth();
    }
    /**
     * 默认显示系统当前月份
     * */
    private void CalendarNowMonth(){
        Date date = new Date();
        String s =  DateUtil.DateToString(date);
        String[]ss = s.split("-");
        int years = Integer.parseInt(ss[0]);
        int months = Integer.parseInt(ss[1]);
        year.setPromptText(years+"");
        month.setPromptText(months+"");
        ComBoxItem();
        try {
            ShowCalendar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ScheduleOfDay.setEffect(new DropShadow(10, Color.GRAY));
    }
    /**
     * ComBox 选项
     * */
    private void ComBoxItem(){
        for(int i=2000;i<3000;i++){
            year.getItems().add(i);
        }
        for(int i=1;i<=12;i++) {
            month.getItems().add(i);
        }

    }

    /**
     * 获取数据存入list
     * */
    private void ScheduleAll() {
        schedulesList = FXCollections.observableArrayList();
        schedulesList.clear();
        schedulesList.addAll(Schedule.getAllSchedule());
    }

    /**
     * 获取数据存入list
     * */
    private void ScheduleKeyAll(String key) {
        schedulesList = FXCollections.observableArrayList();
        schedulesList.clear();
        schedulesList.addAll(Schedule.getScheduleListByKey(key));
    }

    /**
     * 写入数据到每列
     * */
    private void ScheduleCreate() {



        ScheduleID.setCellValueFactory(new PropertyValueFactory("id"));

        ScheduleUsername.setCellValueFactory(new PropertyValueFactory("employeeID"));//映射
        ScheduleUsername.setCellFactory(TextFieldTableCell.forTableColumn());
        /**
         *要想编辑的数据生效，就要加这段XXX.setOnEditCommit
         */
        ScheduleUsername.setOnEditCommit(new EventHandler<CellEditEvent<Schedule, String>>() {
            @Override
            public void handle(CellEditEvent<Schedule, String> t) {
                ((Schedule) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setEmployeeID(t.getNewValue());
            }
        });

        ScheduleName.setCellValueFactory(new PropertyValueFactory("employeeName"));
        ScheduleDuty.setCellValueFactory(new PropertyValueFactory("dutyID"));

        /**
         * 与运行时相同的错误）。原因是静态回调forTableColumn仅适用TableColumn于String类型。
         *
         * 对于其他类型，您必须提供一个自定义的字符串转换器。这将解决您的问题：
         * */
        ScheduleDuty.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>(){

            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }

        }));
        ScheduleDuty.setOnEditCommit(new EventHandler<CellEditEvent<Schedule, Integer>>() {
            @Override
            public void handle(CellEditEvent<Schedule, Integer> t) {
                ((Schedule) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDutyID(t.getNewValue());
            }
        });

        ScheduleDutyName.setCellValueFactory(new PropertyValueFactory("dutyName"));

        ScheduleDate.setCellValueFactory(new PropertyValueFactory("date"));
        ScheduleDate.setCellFactory(TextFieldTableCell.forTableColumn());
        ScheduleDate.setOnEditCommit(new EventHandler<CellEditEvent<Schedule, String>>() {
            @Override
            public void handle(CellEditEvent<Schedule, String> t) {
                ((Schedule) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setDate(t.getNewValue());
            }
        });

        ScheduleBJ.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button1 = new JFXButton("修改");
                            button1.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button1.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button1.setOnMouseClicked((col1) -> {
                                StageHashMap.USERMessage.clear();
                                StageHashMap.USERMessageInt.clear();
                                int index = StageHashMap.Int.get("pageindex");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                index3 = index2 + index1;
                                schedule = schedulesList.get(index3);

                                //.getCellObservableValue(index).getValue() 获取数据

                                new DialogBuilder(zx).setTitle("提示").setMessage("确定修改吗？").setNegativeBtn("取消").setPositiveBtn("确定", this::ChangeSchedule, "-fx-background-color: #00bcff;-fx-text-fill: #ffffff").create(500, 200);

                            });

                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button1);
                            }
                        }
                        private void ChangeSchedule() {

                            int i = Integer.parseInt(ScheduleDuty.getCellObservableValue(index3).getValue().toString());
                            String s = ScheduleUsername.getCellObservableValue(index3).getValue().toString();
                            String date = ScheduleDate.getCellObservableValue(index3).getValue().toString();
                            String[] s2 = date.split("-");
                            int month = Integer.parseInt(s2[1]);
                            int day = Integer.parseInt(s2[2]);
                            String months,days;
                            if(month<10){
                                months  = "0"+month;
                            }else {
                                months = ""+month;
                            }
                            if(day<10){
                                days  = "0"+day;
                            }else {
                                days = ""+day;
                            }
                            String Date = s2[0]+"-"+months+"-"+days;
                            System.out.println(Date);
                            Schedule s1 = new Schedule();
                            s1.setId(schedule.getId());
                            s1.setDutyID(i);
                            s1.setEmployeeID(s);
                            s1.setDate(Date);

                            if(Schedule.updateScheduleIDManDate(s1)){
                                new DialogBuilder(zx).setTitle("提示").setMessage("成功").setPositiveBtn("确定",null).create(500, 200);
                            }else{
                                new DialogBuilder(zx).setTitle("提示").setMessage("失败").setPositiveBtn("确定", null).create(500, 200);
                            }

                            ScheduleShowList();

                        }
                    };
                }
        );
        ScheduleSC.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button2 = new JFXButton("删除");
                            button2.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button2.setOnMouseClicked((col1) -> {
                                int index = StageHashMap.Int.get("pageindex");
                                int num = index + 1;
                                System.out.println("这是第" + num + "页");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                System.out.println("第" + index2 + "个");
                                index3 = index2 + index1;
                                System.out.println("在总数中是第" + index3 + "个");
                                schedule = schedulesList.get(index3);
                                new DialogBuilder(zx).setTitle("提示").setMessage("确定删除吗？").setNegativeBtn("取消").setPositiveBtn("确定", this::del, "-fx-background-color: #00bcff;-fx-text-fill: #ffffff").create(500, 200);
                            });
                            if (empty) {
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button2);
                            }
                        }

                        //删除用户账号
                        private void del() {

                                if (Schedule.deleteSchedule(schedule)) {
                                    schedulesList.remove(index3);
                                    new DialogBuilder(zx).setTitle("提示").setMessage("删除成功").setPositiveBtn("确定", null).create(250, 50);
                                    ScheduleSBT.ShowBorderTableNowPage(schedulesList,ScheduleTable,ScheduleBorder);
                                } else {
                                    new DialogBuilder(zx).setTitle("提示").setMessage("删除失败").setPositiveBtn("确定", null).create(250, 50);
                                }
                        }
                    };
                }
        );

        ScheduleTable.setItems(schedulesList);
        //Tableview 随窗口变化变化
        ScheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        ScheduleTable.setEditable(true);
    }

    /**
     * 所有
     * */
    private void ScheduleShowList() {
        ScheduleAll();
        ScheduleCreate();
        new ShowBorderTable<>(schedulesList,ScheduleTable,ScheduleBorder);
    }

    /**
     * 搜索后显示的列表
     * */
    private void ScheduleList(TextField ScheduleSearchText) {
        String key = ScheduleSearchText.getText().trim();
        ScheduleKeyAll(key);
        ScheduleCreate();
        new ShowBorderTable<>(schedulesList,ScheduleTable,ScheduleBorder);
    }



    public void ScheduleSearch(ActionEvent event) {
        if (ScheduleSearchText.getText().trim().equals("")) {
            ScheduleShowList();
        }else {
            System.out.println(ScheduleSearchText.getText().trim());
            ScheduleList(ScheduleSearchText);
        }
    }

    public void ScheduleRefresh(ActionEvent event) {
        ScheduleAll();
        ScheduleCreate();
        ScheduleSBT.ShowBorderTableNowPage(schedulesList,ScheduleTable,ScheduleBorder);
    }

    public String date(){
        String date;
        String year = ScheduleYear.getPromptText();
        String month = ScheduleMonth.getPromptText();
        String day = ScheduleDay.getPromptText();
       if(Integer.parseInt(month)<10){
           month = "0"+month;
       }
       if(!day.equals("")){
           if(Integer.parseInt(day)<10){
               day = "0"+day;
           }
           date = year+"-"+month+"-"+day;
       }else {
           date = year+"-"+month;
       }

        return date;
    }

    /**
     * 页数跳转
     * */
    public void Jump(ActionEvent event) {
        ScheduleSBT.PageChange(ScheduleChangeText,gb,schedulesList,ScheduleTable,ScheduleBorder);
    }
    /**
     * 第一页
     * */
    public void ScheduleSY(ActionEvent event) {
        new ShowBorderTable<>(schedulesList,ScheduleTable,ScheduleBorder);
    }

    /**
     * 最后页
     * */
    public void ScheduleDY(ActionEvent event) {
        ScheduleSBT.ShowBorderTableEndPage(schedulesList,ScheduleTable,ScheduleBorder);
    }

    /**
     * 添加新排班
     * */
    public void AddSchedule(ActionEvent event) throws Exception{
        AddSchedule open = new AddSchedule();
        open.start(new Stage());
    }


    /**
     * 菜单按钮事件
     * */
    @Override
    public void GeneralButton(ActionEvent event) {

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
    /**
     * 重写按钮显示
     * */
    @Override
    public void ButtonRise(){
         cx.setManaged(true);
         cx.setVisible(true);
         cxgrxx.setVisible(true);
         cxgrxx.setManaged(true);
         cxpbb.setVisible(true);
         cxpbb.setManaged(true);
         zzpbb.setManaged(true);
         zzpbb.setVisible(true);
         ChangePassword.setVisible(true);
         ChangePassword.setManaged(true);
         zx.setManaged(true);
         zx.setVisible(true);
         cxyhxx.setVisible(true);
         cxyhxx.setManaged(true);
        shenqingshenpi.setVisible(true);
        shenqingshenpi.setManaged(true);
    }

    /**
     * 重写按钮消失
     * */
    @Override
    protected void Buttoncancel(){
        cx.setManaged(false);
        cx.setVisible(false);
        cxgrxx.setVisible(false);
        cxgrxx.setManaged(false);
        cxpbb.setVisible(false);
        cxpbb.setManaged(false);
        zzpbb.setManaged(false);
        zzpbb.setVisible(false);
        ChangePassword.setVisible(false);
        ChangePassword.setManaged(false);
        zx.setManaged(false);
        zx.setVisible(false);
        cxyhxx.setVisible(false);
        cxyhxx.setManaged(false);
        shenqingshenpi.setVisible(false);
        shenqingshenpi.setManaged(false);

    }

    public void QUANXUAN(ActionEvent event) {
        if(xxxx==0){
            for(int i = 0;i<people.length;i++){
                people[i].setStyle("-fx-background-color: #747474;-fx-text-fill: white");
                peopleList.add(0,new Man(manList.get(i).getUsername(),manList.get(i).getName()));
                manList.get(i).flag=1;
            }
            System.out.println(peopleList.size());
            xxxx++;
        }else {
            for(int i = 0;i<people.length;i++){
                people[i].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
                manList.get(i).flag=0;
            }
            peopleList.clear();
            System.out.println(peopleList.size());
            xxxx--;
        }

    }

    public void QUANXUAN2(ActionEvent event) {
        if(xxxx1==0){
            for(int i = 0;i<list111.size();i++){
                banci[i].setStyle("-fx-background-color: #747474;-fx-text-fill: white");
                list111.get(i).flag=1;
            }
            work1.clear();
            work1.addAll(list111);
            System.out.println(work1.size());
            xxxx1++;
        }else {
            for(int i = 0;i<list111.size();i++){
                banci[i].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
                list111.get(i).flag=0;
            }
            work1.clear();
            System.out.println(work1.size());
            xxxx1--;
        }
    }

    public void QUANXUAN3(ActionEvent event) {
        if(xxxx2==0){
            for(int i = 0;i<list222.size();i++){
                banc2[i].setStyle("-fx-background-color: #747474;-fx-text-fill: white");
                list222.get(i).flag=1;
            }
            work2.clear();
            work2.addAll(list222);
            System.out.println(work2.size());
            xxxx2++;
        }else {
            for(int i = 0;i<list222.size();i++){
                banc2[i].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
                list222.get(i).flag=0;
            }
            work2.clear();
            System.out.println(work2.size());
            xxxx2--;
        }
    }

    private  void MakeSchedule(){
        list111 = Dutytype.getAllDutytype();

        for(Dutytype dutytype : list111){
            if (dutytype.getDutyname().equals("夜班")){
                list111.remove(dutytype);
                break;
            }
        }
        for(Dutytype dutytype : list111){
            if (dutytype.getDutyname().equals("休息")){
                list111.remove(dutytype);
                break;
            }
        }
        for(Dutytype dutytype : list111){
            if (dutytype.getDutyname().equals("请假")){
                list111.remove(dutytype);
                break;
            }
        }

        ZhouYi.setStyle("-fx-background-color: #ffffff;-fx-border-color: #b4b4b4");
        banci = new JFXButton[list111.size()];
        int count1 = 0;
        int Y1 = 0;
        for(int i = 0;i<list111.size();i++){
            banci[i] = new JFXButton();
            banci[i].setText(list111.get(i).getDutyname());
            banci[i].setPrefWidth(50);
            banci[i].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
            banci[i].setPrefHeight(10);
            banci[i].setLayoutX(20+count1*60);
            banci[i].setLayoutY(Y1*40+20);
            banci[i].setFocusTraversable(false);
            ZhouYi.getChildren().add(banci[i]);
            count1++;
            if(count1==2){
                count1=0;
                Y1++;
            }
            int finalCount = i;
            banci[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(list111.get(finalCount).flag==0){
                        banci[finalCount].setStyle("-fx-background-color: #747474;-fx-text-fill: white");
                        list111.get(finalCount).flag++;
                        work1.add(list111.get(finalCount));
                        StageList.workday = work1;
                    }else{
                        banci[finalCount].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
                        list111.get(finalCount).flag--;
                        work1.remove(list111.get(finalCount));
                        StageList.workday = work1;
                    }


                }
            });
        }

        list222  = Dutytype.getAllDutytype();
        for(Dutytype dutytype : list222){
            if (dutytype.getDutyname().equals("请假")){
                list222.remove(dutytype);
                break;
            }
        }
        for(Dutytype dutytype : list222){
            if (dutytype.getDutyname().equals("夜班")){
                list222.remove(dutytype);
                break;
            }
        }
        for(Dutytype dutytype : list222){
            if (dutytype.getDutyname().equals("休息")){
                list222.remove(dutytype);
                break;
            }
        }
        ZhouRi.setStyle("-fx-background-color: #ffffff;-fx-border-color: #b4b4b4");
        banc2 = new JFXButton[list222.size()];
        int xxx=0;
        int yyy=0;
        for(int i = 0;i<list222.size();i++){
            banc2[i] = new JFXButton();
            banc2[i].setText(list222.get(i).getDutyname());
            banc2[i].setPrefWidth(50);
            banc2[i].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
            banc2[i].setPrefHeight(10);
            banc2[i].setLayoutX(20+xxx*60);
            banc2[i].setLayoutY(yyy*40+20);
            banc2[i].setFocusTraversable(false);
            ZhouRi.getChildren().add(banc2[i]);
            xxx++;
            if(xxx==2){
                xxx=0;
                yyy++;
            }
            int finalCount = i;
            banc2[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(list222.get(finalCount).flag==0){
                        banc2[finalCount].setStyle("-fx-background-color: #747474;-fx-text-fill: white");
                        list222.get(finalCount).flag++;
                        work2.add(list222.get(finalCount));


                    }else{
                        banc2[finalCount].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
                        list222.get(finalCount).flag--;
                        work2.remove(list222.get(finalCount));
                    }
                }
            });
        }



        manList = UserMessage.AllUserMessageList();
        List<Man> ManList = new ArrayList<>();
        for(UserMessage userMessage : manList){
            Man man = new Man();
            man.username = userMessage.getUsername();
            man.name = userMessage.getName();
            ManList.add(man);
        }
        People.setStyle("-fx-background-color: #ffffff;-fx-border-color: #b4b4b4");
        people = new JFXButton[manList.size()];
        int count = 0;
        int Y = 0;
        for(int i = 0;i<manList.size();i++){
            people[i] = new JFXButton();
            people[i].setText(manList.get(i).getName());
            people[i].setPrefWidth(60);
            people[i].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
            people[i].setPrefHeight(10);
            people[i].setLayoutX(30+count*70);
            people[i].setLayoutY(Y*30+20);
            people[i].setFocusTraversable(false);
            people[i].setEffect(new DropShadow(10,Color.GRAY));
            People.getChildren().add(people[i]);
            count++;
            if(count==9){
                count=0;
                Y++;
            }
            int finalCount = i;
            people[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(manList.get(finalCount).flag==0){
                        people[finalCount].setStyle("-fx-background-color: #747474;-fx-text-fill: white");
                        peopleList.add(ManList.get(finalCount));
                        manList.get(finalCount).flag++;
                    }else{
                        people[finalCount].setStyle("-fx-background-color: #2541ff;-fx-text-fill: white");
                        peopleList.remove(ManList.get(finalCount));
                        manList.get(finalCount).flag--;
                    }
                }
            });
        }

    }

    public void schedule(){

            String[] work = new String[work1.size()];
            String[] week = new String[work2.size()];
            for(int i =0;i<work1.size();i++){
                work[i] = work1.get(i).getDutyname();
            }
            for(int i =0;i<work2.size();i++){
                week[i] = work2.get(i).getDutyname();
            }
            int[] number = worknumber;
            int[] number2 = weeknumber;

            ArrayList<Man> list = new ArrayList<>();
            if(list.size()!=0){
                list.clear();
            }
            list.addAll(peopleList);
            for(Man man :list){
                Man.ToZero(man);
            }
            resulttext.setText("");

            new PaiBanFinish(work,week,number,number2,list,date,gb,resulttext);
            boolean flag = PaiBanFinish.temp;
            if(flag){
                change.setVisible(false);
                change.setManaged(false);
                resault.setManaged(true);
                resault.setVisible(true);
                new DialogBuilder(gb).setTitle("提示").setMessage("你可以通过右边的确定/取消按钮\n确定是否保存排班数据").setPositiveBtn("确定",null).create(500,200);

            }

    }

    public void make(ActionEvent event) {

        if(work1.size() !=0 && work2.size()!=0 && peopleList.size()!=0){

            work1.sort(Dutytype::compareTo);
            work2.sort(Dutytype::compareTo);
            Stage stage = new Stage();
            stage.setResizable(false);
            AnchorPane anchorPane = new AnchorPane();
            Label label = new Label("请输入每个班次的人数");
            label.setLayoutY(15);
            label.setLayoutX(80);
            Label label2 = new Label("工作日");
            label2.setLayoutY(40);
            Label label3 = new Label("周末");
            label3.setLayoutY(40);
            label3.setLayoutX(200);
            Label yearlabel = new Label("年");
            Label monthlabel = new Label("月");
            TextField yeartext = new TextField();
            TextField monthtext = new TextField();
            yeartext.setLayoutY(410);
            yeartext.setLayoutX(100);
            yeartext.setPrefWidth(50);
            monthtext.setPrefWidth(30);
            monthtext.setLayoutY(410);
            monthtext.setLayoutX(180);
            yearlabel.setLayoutY(415);
            yearlabel.setLayoutX(160);
            monthlabel.setLayoutY(415);
            monthlabel.setLayoutX(220);
            anchorPane.getChildren().addAll(label,label2,label3,yearlabel,monthlabel,yeartext,monthtext);

            Label[] labels = new Label[work1.size()];
            TextField[] textFields = new TextField[work1.size()];
            for(int i =0;i<labels.length;i++){
                labels[i] = new Label();
                labels[i].setText(work1.get(i).getDutyname()+":");
                labels[i].setLayoutY(40+(i+1)*30);
                textFields[i] = new TextField();
                textFields[i].setPromptText("人数");
                textFields[i].setLayoutX(40);
                textFields[i].setPrefWidth(50);
                textFields[i].setLayoutY(40+(i+1)*30);
                anchorPane.getChildren().add(labels[i]);
                anchorPane.getChildren().add(textFields[i]);
            }

            Label[] label2s = new Label[work2.size()];
            TextField[] textField2s = new TextField[work2.size()];
            for(int i =0;i<label2s.length;i++){
                label2s[i] = new Label();
                label2s[i].setText(work2.get(i).getDutyname()+":");
                label2s[i].setLayoutX(200);
                label2s[i].setLayoutY(40+(i+1)*30);
                textField2s[i] = new TextField();
                textField2s[i].setPromptText("人数");
                textField2s[i].setLayoutX(240);
                textField2s[i].setPrefWidth(50);
                textField2s[i].setLayoutY(40+(i+1)*30);
                anchorPane.getChildren().add(label2s[i]);
                anchorPane.getChildren().add(textField2s[i]);

            }

            JFXButton button = new JFXButton("生成排班数据");
            anchorPane.getChildren().add(button);
            button.setTranslateY(450);
            button.setTranslateX(110);
            button.setPrefWidth(100);
            button.setPrefHeight(30);
            button.setStyle("-fx-text-fill: #ffffff;-fx-background-color: #1379ff");

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                        boolean flag = true;
                        for(TextField textField : textFields){
                            if(textField.getText().trim().equals("") || !Method.isNumberic(textField.getText().trim())){
                                flag = false;
                                break;
                            }
                        }
                        for(TextField textField : textField2s){
                            if(textField.getText().trim().equals("") || !Method.isNumberic(textField.getText().trim())){
                                flag = false;
                                break;
                            }
                        }
                        if(!Method.isNumberic(yeartext.getText().trim()) || !Method.isNumberic(monthtext.getText().trim()) || yeartext.getText().trim().equals("") || monthtext.getText().trim().equals("")){
                            flag = false;
                        }

                    if(!flag){
                        new DialogBuilder(gb).setTitle("提示").setMessage("请正确填写").setPositiveBtn("确定","#ffffff").create(500,200);
                    }else {
                        worknumber = new int[work1.size()];
                        for(int work =0;work<worknumber.length;work++){
                            worknumber[work] = Integer.parseInt(textFields[work].getText().trim());
                        }
                        weeknumber = new int[work2.size()];
                        for(int week =0;week<weeknumber.length;week++){
                            weeknumber[week] = Integer.parseInt(textField2s[week].getText().trim());
                        }
                        int month = Integer.parseInt(monthtext.getText().trim());
                        String monthstring;
                        if(month<10){
                            monthstring = "0"+month;
                        }else {
                            monthstring = ""+month;
                        }
                        date = yeartext.getText().trim()+"-"+monthstring;
                        schedule();

                    }

                }
            });
            Scene s = new Scene(anchorPane,300,500);
            stage.setScene(s);
            stage.show();
        }else {
            new DialogBuilder(gb).setTitle("提示").setMessage("班次和排班人员必选选择").setPositiveBtn("确定","#ffffff").create(500,200);
        }


    }

    public void ScheduleSure(ActionEvent event) {
        List<Schedule> schedules = PaiBanFinish.ScheduleList;
        MybatisUtil.getSqlSession().getMapper(ScheduleDao.class).insertScheduleList(schedules);
        MybatisUtil.getSqlSession().commit();
        MybatisUtil.closeSqlSession();
        new DialogBuilder(gb).setTitle("提示").setMessage("保存完毕").setPositiveBtn("确定",null).create(500,200);

    }

    public void ScheduleEsc(ActionEvent event) {
        change.setVisible(true);
        change.setManaged(true);
        resault.setManaged(false);
        resault.setVisible(false);
    }
}
