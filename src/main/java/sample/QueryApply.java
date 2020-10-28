package sample;

import Util.DragUtil;
import Util.DrawUtil;
import Util.Method;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class QueryApply extends Application {

    private Method method =new Method();



    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            VBox root = FXMLLoader.load(getClass().getResource("../fxml/QueryApply.fxml"));
            HBox top = (HBox) root.lookup("#top");
            primaryStage.setResizable(false);//不可拉伸
            primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
            primaryStage.setScene(new Scene(root, 1024, 720));//尺寸


            //拖动窗口
            DragUtil.addDragListener(primaryStage,top);

            primaryStage.show();
            primaryStage.setX(method.getWidth()/2 - 512);
            primaryStage.setY(method.getHeight()/2 - 360);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
