package sample;

import Util.AuthCodeUtils;
import Util.DragUtil;
import Util.Method;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;


import static Util.AuthCodeUtils.getRandom;

public class Register extends Application {

    private Method method = new Method();


    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../fxml/Register.fxml"));
            HBox top = (HBox) root.lookup("#top");

            primaryStage.setResizable(false);

            primaryStage.setScene(new Scene(root,312,370));
            primaryStage.initStyle(StageStyle.UNDECORATED);


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
