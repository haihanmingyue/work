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
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.net.URL;
import java.util.*;


public  class QueryApplyController<event>extends AdminIndexController implements Initializable {

    private static Logger logger = Logger.getLogger(QueryApplyController.class);

    public TextField SearchBox;
    public TextField tiaozhuanyeshu;
    public BorderPane ApplyBorder;
    public TableView<Application> ApplyTable;
    public TableColumn UserName,ApplyType,Date,Auditor,ApplyStatus,xiangxi,AuditDate,quxiao;
    public VBox Apply;
    public JFXDatePicker searchdate;
    public TextArea reason;

    public VBox HuanBan,QingJia;
    public TextArea Qreason;

    //去申请点击后界面的控件
    public AnchorPane QingjiaAnchro;
    public AnchorPane QingjiaAnchro2;
    public RadioButton Huanbanbutton,QingjiaButton;
    public VBox ShenQ;
    public TextField Stime,Sapplyname,Sum;
    public TextArea Sreason;
    public JFXDatePicker startDate,endDate;
    //huan ban
    public JFXDatePicker startDate1,endDate1;
    public TextField Sbanci1,Sbanci2;
    public TextField Sapplyname1;


    @FXML
    TextField Qapplyname,Qtime2,Qtime1,Qsum,Qtime3,QshenheZT,QshenheName,Qtime4;
    @FXML
    TextField applyname,applyname2,time2,time1,banci1,banci2,time3,shenheZT,shenheName,time4;


    //table列表里面的按钮
    private JFXButton button1,button2;
    private Application application1;
    private ObservableList<Application> applicationsList;
    private List<Application> applications;
    private int index3;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(StageHashMap.USERS1.get("queryapply")==0){
            ShenQ.setManaged(true);
            ShenQ.setVisible(true);
            Apply.setVisible(false);
            Apply.setManaged(false);
            Sapplyname.setText(StageHashMap.USERS.get("userName"));
            Stime.setText(DateUtil.getNowTime());

        }
        else if(StageHashMap.USERS1.get("queryapply")==1){
            ShenQ.setManaged(false);
            ShenQ.setVisible(false);
            Apply.setVisible(true);
            Apply.setManaged(true);
            showList();
        }

        //放在一组，就只能选择一个
        ToggleGroup group1 = new ToggleGroup();
        Huanbanbutton.setToggleGroup(group1);
        QingjiaButton.setToggleGroup(group1);

    }


    private void All() {
        applicationsList = FXCollections.observableArrayList();
        applicationsList.clear();
        applications = MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).selectByUsername(StageHashMap.USERS.get("userName"));
        applicationsList.addAll(applications);
        MybatisUtil.closeSqlSession();
    }

    private void KeyAll(String key,String key2) {
        applicationsList = FXCollections.observableArrayList();
        applicationsList.clear();
        applications = MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).PersonSelectByKey(key,key2);
        applicationsList.addAll(applications);
        MybatisUtil.closeSqlSession();
    }
    //写入数据到table
    private void create() {
        UserName.setCellValueFactory(new PropertyValueFactory("employeeID"));
        ApplyType.setCellValueFactory(new PropertyValueFactory("applyTypename"));//映射
        Date.setCellValueFactory(new PropertyValueFactory("applicationDate"));
        ApplyStatus.setCellValueFactory(new PropertyValueFactory("applicationStatusName"));
        Auditor.setCellValueFactory(new PropertyValueFactory("auditor"));
        AuditDate.setCellValueFactory(new PropertyValueFactory("auditDate"));
        xiangxi.setCellFactory((col) -> {
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
                                    int index = StageHashMap.Int.get("pageindex");
                                    int index1 = index * 30;
                                    int index2 = getIndex();
                                    index3 = index2 + index1;
                                    application1 = applicationsList.get(index3);
                                    Apply.setVisible(false);
                                    Apply.setManaged(false);
                                    if(application1.getApplyTypename().equals("换班")){
                                        HuanBan.setVisible(true);
                                        HuanBan.setManaged(true);
                                        HuanBanApplyXiangXi(application1.getApplicationDate());
                                    }else{
                                        QingJia.setManaged(true);
                                        QingJia.setVisible(true);
                                        XiuJiaApplyXiangXi(application1.getApplicationDate());
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
        quxiao.setCellFactory((col) -> {
                    return new TableCell<UserLoad, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            button2 = new JFXButton("取消申请");
                            button2.setStyle("-fx-background-color: #00bcff;-fx-text-fill: #ffffff");
                            button2.setOnMouseEntered(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    setCursor(Cursor.HAND);
                                }
                            });
                            button2.setOnMouseClicked((col1) -> {
                                int index = StageHashMap.Int.get("pageindex");
                                int index1 = index * 30;
                                int index2 = getIndex();
                                index3 = index2 + index1;
                                application1 = applicationsList.get(index3);
                                if(!application1.getApplicationStatusName().equals("等待审核")){
                                    new DialogBuilder(searchdate).setTitle("提示").setMessage("只等'待审核状'态才能取消申请").setPositiveBtn("确定",null).create(500,200);
                                }else{
                                    new DialogBuilder(searchdate).setTitle("提示").setMessage("确定取消申请吗").setNegativeBtn("取消").setPositiveBtn("确定",this::deleteSQ,"-fx-background-color:#87ecfa").create(500,200);
                                }
                            });
                            if (empty){
                                //如果此列为空默认不添加元素
                                setText(null);
                                setGraphic(null);
                            } else {
                                this.setGraphic(button2);
                            }
                        }
                        private void deleteSQ() {
                            if(MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).deleteSQ(StageHashMap.USERS.get("userName"),application1.getApplicationDate())){
                                logger.info("用户"+StageHashMap.USERS.get("userName")+"取消了"+application1.getApplicationDate()+"的换班/休假申请");
                                new DialogBuilder(searchdate).setTitle("提示").setMessage("取消申请成功").setPositiveBtn("确定",null).create(500,200);
                                MybatisUtil.getSqlSession().commit();
                                MybatisUtil.closeSqlSession();
                                showList();
                            }
                            else{
                                new DialogBuilder(searchdate).setTitle("提示").setMessage("取消失败").setPositiveBtn("确定",null).create(500,200);
                            }
                        }
                    };
                }
        );

        ApplyTable.setItems(applicationsList);
        //Tableview 随窗口变化变化
        ApplyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }


    //创建所有用户账号列表
    private void showList() {
        All();
        number();
        create();
    }

    //创建搜索列表
    private void sskList(JFXDatePicker jfxDatePicker) {
        System.out.println(jfxDatePicker.getValue().toString());
        String key = jfxDatePicker.getValue().toString();
        KeyAll(StageHashMap.USERS.get("userName"),key);
        number();
        create();
    }

    //设置每页显示个数,加载分页控件
    private void number() {
        Page<Application> page = new Page<>(applicationsList, 30);
        TablePagination<Application> table3 = new TablePagination<>(page, ApplyTable);
        ApplyBorder.setCenter(table3.getTablePagination());
    }

    // 用作刷新后，仍旧显示当前页面
    private void number1() {
        Page<Application> page = new Page<>(applicationsList, 30);
        TablePagination<Application> table3 = new TablePagination<>(page, ApplyTable);
        int x = StageHashMap.Int.get("pageindex");
        table3.getTablePagination().setCurrentPageIndex(x);
        ApplyBorder.setCenter(table3.getTablePagination());
    }

    //页数跳转方法
    private void PageChange(TextField textField){
        String s = textField.getText().trim();
        int tznumber;
        if (s.equals("") || s.equals("0")) {
            new DialogBuilder(gb).setTitle("提示").setMessage("不要为空或者输入正确页数").setPositiveBtn("确定", null).create(500, 200);
        } else {
            tznumber = Integer.parseInt(s) - 1;
            Page<Application> page = new Page<>(applicationsList, 30);
            TablePagination<Application> table3 = new TablePagination<>(page, ApplyTable);
            if (tznumber + 1 > table3.getPage().getTotalPage()) {
                new DialogBuilder(gb).setTitle("提示").setMessage("不要超过总页数").setPositiveBtn("确定", null).create(500, 200);
            } else {
                table3.getTablePagination().setCurrentPageIndex(tznumber);
                ApplyBorder.setCenter(table3.getTablePagination());
            }
        }
    }

    public void TiaoZhuan(ActionEvent event) {
        PageChange(tiaozhuanyeshu);
    }

    public void shouye(ActionEvent event) {
        number();
    }
    public void diye(ActionEvent event) {
        Page<Application> page = new Page<>(applicationsList, 30);
        TablePagination<Application> table3 = new TablePagination<>(page, ApplyTable);
        table3.getTablePagination().setCurrentPageIndex(page.getTotalPage());
        ApplyBorder.setCenter(table3.getTablePagination());
    }
    public void shuaxin(ActionEvent event) {
        All();
        create();
        number1();
    }
    //搜索按钮
    public void ApplySearch(ActionEvent event) {
        if (searchdate.getValue() == null) {
            showList();
        }else {
            System.out.println(searchdate.getValue().toString());
            sskList(searchdate);
        }
    }

    @Override
    public void fanhui(ActionEvent event){
        HuanBan.setManaged(false);
        HuanBan.setVisible(false);
        QingJia.setVisible(false);
        QingJia.setManaged(false);
        Apply.setVisible(true);
        Apply.setManaged(true);
    }

    //窗口关闭
    @Override
    public void closeAction(ActionEvent event) {
        Stage primaryStage = (Stage) gb.getScene().getWindow();
        primaryStage.hide();
    }

    public void HuanBanApplyXiangXi(String date){
        try{
            Application application = MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).selectByDate(StageHashMap.USERS.get("userName"),date);
            applyname.setText(application.getEmployeeID());
            applyname2.setText(application.getEmployedID());
            time1.setText(application.getStartDate());
            time2.setText(application.getEndDate());
            banci1.setText(application.getStartDutyName());
            banci2.setText(application.getEndDutyName());
            time3.setText(application.getApplicationDate());
            shenheZT.setText(application.getApplicationStatusName());
            shenheName.setText(application.getAuditor());
            time4.setText(application.getAuditDate());
            reason.setText(application.getApplicationReason());
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void XiuJiaApplyXiangXi(String date){
        try{
            Application application = MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).selectByDate(StageHashMap.USERS.get("userName"),date);
            Qapplyname.setText(application.getEmployeeID());
            Qtime1.setText(application.getStartDate());
            Qtime2.setText(application.getEndDate());
            Qsum.setText(String.valueOf(DateUtil.TwoDateSum(application.getStartDate(),application.getEndDate())));
            Qtime3.setText(application.getApplicationDate());
            QshenheZT.setText(application.getApplicationStatusName());
            QshenheName.setText(application.getAuditor());
            Qtime4.setText(application.getAuditDate());
            Qreason.setText(application.getApplicationReason());
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Huanbanbutton(ActionEvent event) {
        QingjiaAnchro2.setVisible(true);
        QingjiaAnchro2.setManaged(true);
        QingjiaAnchro.setVisible(false);
        QingjiaAnchro.setManaged(false);
    }

    public void QingjiaButton(ActionEvent event) {
        QingjiaAnchro2.setVisible(false);
        QingjiaAnchro2.setManaged(false);
        QingjiaAnchro.setVisible(true);
        QingjiaAnchro.setManaged(true);
    }

    public void TiJiao(ActionEvent event) {
        String s = Sreason.getText().trim();
        if(!Huanbanbutton.isSelected() && !QingjiaButton.isSelected()){
            new DialogBuilder(Sreason).setTitle("提示").setMessage("请选择一个申请类型").setPositiveBtn("确定",null).create(500,200);
        }
        else if(Huanbanbutton.isSelected()){
            if(startDate1.getValue()==null || endDate1.getValue()==null ){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("日期不可为空").setPositiveBtn("确定",null).create(500,200);
            }
            else if(s.length()>150){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("不要超过150个字"+"\n"+"当前字数"+s.length()).setPositiveBtn("确定",null).create(500,200);
            }
            else if(s.length()<1){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("请填写申请理由").setPositiveBtn("确定",null).create(500,200);
            }else if(Sbanci1.getText().trim().equals("无班次")){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("原班次不存在").setPositiveBtn("确定",null).create(500,200);
            }else if(Sbanci2.getText().trim().equals("无班次")){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("被换人班次不存在").setPositiveBtn("确定",null).create(500,200);
            }
            else {
                if(MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).selectByDate(StageHashMap.USERS.get("userName"),Stime.getText().trim())!=null){
                    new DialogBuilder(Sreason).setTitle("提示").setMessage(Stime.getText().trim()+"这天已经存在申请").setPositiveBtn("确定",null).create(500,200);
                }
                else{
                    Application application = new Application();
                    application.setApplicationDate(Stime.getText().trim());
                    application.setApplicationReason(Sreason.getText().trim());
                    application.setApplicationType(0);
                    application.setEmployeeID(Sapplyname.getText().trim());
                    application.setEmployedID(Sapplyname1.getText().trim());
                    application.setStartDate(String.valueOf(startDate1.getValue()));
                    application.setEndDate(String.valueOf(endDate1.getValue()));
                    application.setOrigindutyID(application.setdutyID(Sbanci1.getText().trim()));
                    application.setFinishdutyID(application.setdutyID(Sbanci2.getText().trim()));
                    application.setApplicationStatus(3);
                    try {
                        MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).insertHuanBanApply(application);
                        MybatisUtil.getSqlSession().commit();
                        new DialogBuilder(Sreason).setTitle("提示").setMessage("申请提交成功").setPositiveBtn("确定",null).create(500,200);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    MybatisUtil.closeSqlSession();
                }

            }
        }
        else if(QingjiaButton.isSelected()){
            if(startDate.getValue()==null || endDate.getValue()==null ){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("日期不可为空").setPositiveBtn("确定",null).create(500,200);
            }
            else if(s.length()>150){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("不要超过150个字"+"\n"+"当前字数"+s.length()).setPositiveBtn("确定",null).create(500,200);
            }
            else if(s.length()<1){
                new DialogBuilder(Sreason).setTitle("提示").setMessage("请填写申请理由").setPositiveBtn("确定",null).create(500,200);
            }
            else {
                if(MybatisUtil.getSqlSession().getMapper(ApplicationsDao.class).selectByDate(StageHashMap.USERS.get("userName"),Stime.getText().trim())!=null){
                    new DialogBuilder(Sreason).setTitle("提示").setMessage(Stime.getText().trim()+"这天已经存在申请").setPositiveBtn("确定",null).create(500,200);
                }
                else{
                    Application application = new Application();
                    application.setApplicationDate(Stime.getText().trim());
                    application.setApplicationReason(Sreason.getText().trim());
                    application.setApplicationType(1);
                    application.setEmployeeID(Sapplyname.getText().trim());
                    application.setStartDate(String.valueOf(startDate.getValue()));
                    application.setEndDate(String.valueOf(endDate.getValue()));
                    application.setApplicationStatus(3);
                    if(Application.insertHuanBanApply(application)){
                        new DialogBuilder(Sreason).setTitle("提示").setMessage("申请提交成功").setPositiveBtn("确定",null).create(500,200);
                    }else {
                        new DialogBuilder(Sreason).setTitle("提示").setMessage("ERROR").setPositiveBtn("确定",null).create(500,200);
                    }
                }

            }
        }

    }

    //两个datepicker选择了日期后的事件
    public void Sbanci1(ActionEvent event) {
        String s = String.valueOf(startDate1.getValue());
        Schedule schedule= Schedule.PersonSelectByDate(StageHashMap.USERS.get("userName"),s);
        if( schedule!=null ){
            Sbanci1.setText(schedule.getDutyName());
        }else{
            Sbanci1.setText("无班次");
        }



    }

    public void Sbanci2(ActionEvent event) {
        String s3 = Sapplyname1.getText().trim();
        if(!s3.equals("") && !s3.equals(StageHashMap.USERS.get("userName"))) {
            String s2 = String.valueOf(endDate1.getValue());
            Schedule schedule1 = Schedule.PersonSelectByDate(s3,s2);
            if (schedule1 != null) {
                Sbanci2.setText(schedule1.getDutyName());
            } else {
                Sbanci2.setText("无班次");
            }
        }
        if(s3.equals(StageHashMap.USERS.get("userName"))){
            new DialogBuilder(Sreason).setTitle("提示").setMessage("被换人不能是自己！").setPositiveBtn("确定",null).create(500,200);
        }

    }

    //换班里的第二个datepicker 点击时的事件，防止被换人ID不存在
    public void dateclicked(MouseEvent mouseEvent) {
        String s3 = Sapplyname1.getText().trim();
        if(s3.equals("")){
            new DialogBuilder(Sreason).setTitle("提示").setMessage("被换人请勿为空！").setPositiveBtn("确定",null).create(500,200);
        }
    }

    //请假里的第二个datepicker 选择日期后的事件
    public void enddateaction(ActionEvent event)throws Exception {
       long sum = DateUtil.TwoDateSum(String.valueOf(startDate.getValue()),String.valueOf(endDate.getValue()));
       Sum.setText(String.valueOf(sum));
    }
    //请假里的第二个datepicker 点击时的事件
    public void enddatepickclicked(MouseEvent mouseEvent) {

        if(startDate.getValue()==null){
            new DialogBuilder(Sreason).setTitle("提示").setMessage("开始时间请勿为空").setPositiveBtn("确定",null).create(500,200);
        }
    }
}
