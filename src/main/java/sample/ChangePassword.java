package sample;

import Util.DragUtil;
import Util.DrawUtil;
import Util.Method;
import com.jfoenix.controls.JFXButton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ChangePassword extends Application {
    private Method method = new Method();
    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            AnchorPane root = FXMLLoader.load(getClass().getResource("../fxml/ChangePassword.fxml"));
            HBox top = (HBox) root.lookup("#top");
            primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
            primaryStage.setResizable(false);//不可拉伸
            primaryStage.setScene(new Scene(root, 300, 351));//尺寸
            JFXButton biaoti = (JFXButton) root.lookup("#biaoti");
            biaoti.setDisable(true);

            //拖动窗口
            DragUtil.addDragListener(primaryStage,top);
            primaryStage.show();
            primaryStage.setX(method.getWidth()/2 - 150);  //窗口尺寸的一半
            primaryStage.setY(method.getHeight()/2 - 175.5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
