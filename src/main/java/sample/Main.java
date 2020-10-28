package sample;

import Util.DragUtil;
import Util.Method;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class Main extends Application {

    private final Method method = new Method();

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
           AnchorPane root = FXMLLoader.load(getClass().getResource("../fxml/Main.fxml"));



            HBox top = (HBox) root.lookup("#top");

            primaryStage.setResizable(false);//不可拉伸

//          primaryStage.setTitle("药房智能排班系统");//标题
//          primaryStage.getIcons().add(new Image("images/logo.jpg"));//标题栏图标

            primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
            primaryStage.setScene(new Scene(root, 300, 370));//尺寸


            JFXTextField username = (JFXTextField) root.lookup("#username");
            JFXPasswordField password = (JFXPasswordField) root.lookup("#password") ;
            JFXCheckBox jzmm = (JFXCheckBox) root.lookup("#jzmm");


            //读取user.properties中的数据
            Properties prop = new Properties();
            try {
                if (new File("user.properties").exists()) {
                    InputStream in = new BufferedInputStream(new FileInputStream("user.properties"));
                    prop.load(in);
                    for (String key : prop.stringPropertyNames()) {
                        if(prop.getProperty("flag").equals("1")){
                            username.setText(prop.getProperty("loginName"));
                            password.setText(prop.getProperty("passWord"));
                            jzmm.setSelected(true);

                        }else {
                            username.setText(prop.getProperty("loginName")); //只写入上次使用过的账号
                            password.setText("");
                            jzmm.setSelected(false);
                        }
                    }
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JFXButton biaoti = (JFXButton) root.lookup("#biaoti");
            biaoti.setDisable(true);

            //拖动窗口
            DragUtil.addDragListener(primaryStage, top);
            primaryStage.show();


            primaryStage.setX(method.getWidth()/2 - 150);  //窗口尺寸的一半
            primaryStage.setY(method.getHeight()/2 - 200);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);

    }


}
