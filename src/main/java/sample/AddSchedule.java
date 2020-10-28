package sample;




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
import Util.DragUtil;

import java.io.IOException;

public class AddSchedule extends Application {
    private final Method method = new Method();

    @Override
    public void start(Stage primaryStage) {

        VBox root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../fxml/AddSchedule.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HBox top = (HBox) root.lookup("#top");
        primaryStage.setResizable(false);//不可拉伸
        primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
        primaryStage.setScene(new Scene(root, 332, 440));//尺寸
        JFXButton gb = (JFXButton) root.lookup("#gb");
        JFXButton sx = (JFXButton) root.lookup("#sx");
        Tooltip.install(gb, new Tooltip("关闭"));
        Tooltip.install(sx, new Tooltip("最小化"));//鼠标悬停提示框

        DragUtil.addDragListener(primaryStage, top);
        primaryStage.show();

        primaryStage.setX(method.getWidth()/2 - 222.5);  //窗口尺寸的一半
        primaryStage.setY(method.getHeight()/2 - 283);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
