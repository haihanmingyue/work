package sample;

import Util.DragUtil;
import Util.Method;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ChangeUserMessage extends Application {
    private Method method = new Method();

    @Override
    public void start(Stage primaryStage) {

        VBox root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/ChangeUserMessage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HBox top = (HBox) root.lookup("#top");
        primaryStage.setResizable(false);//不可拉伸
        primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
        primaryStage.setScene(new Scene(root, 1142, 720));//尺寸


        DragUtil.addDragListener(primaryStage, top);
        primaryStage.show();

        primaryStage.setX(method.getWidth()/2 - 571);  //窗口尺寸的一半
        primaryStage.setY(method.getHeight()/2 - 360);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
