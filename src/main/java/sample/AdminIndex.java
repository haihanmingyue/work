package sample;

import Util.DragUtil;
import Util.DrawUtil;
import Util.Method;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class AdminIndex extends Application {

    private Method method =new Method();



    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            VBox root = FXMLLoader.load(getClass().getResource("../fxml/AdminIndex.fxml"));
            HBox top = (HBox) root.lookup("#top");
            primaryStage.initStyle(StageStyle.UNDECORATED);//没有标题栏
            primaryStage.setScene(new Scene(root, 1280, 768));//尺寸

            //拖动窗口
            DragUtil.addDragListener(primaryStage,top);
            //拉伸窗口
            DrawUtil.addDrawFunc(primaryStage, root,1243,691);
            primaryStage.show();
            primaryStage.setX(method.getWidth()/2 - 640);
            primaryStage.setY(method.getHeight()/2 - 384);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);

    }
}
