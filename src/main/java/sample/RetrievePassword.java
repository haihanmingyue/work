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


public class RetrievePassword extends Application {

    private Method method = new Method();

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../fxml/RetrievePassword.fxml"));
            HBox top = (HBox) root.lookup("#top");

            primaryStage.setResizable(false);//不可拉伸


            primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
            primaryStage.setScene(new Scene(root, 300, 370));//尺寸


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
