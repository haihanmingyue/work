package Util;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import java.util.List;


/**
 * 调用Page 和TablePagination
 * 在设置好的border里面set进表格
 * */
public class ShowBorderTable<T> {

    private List<T> list;

    /**
     * 显示第一页
     * */
    public ShowBorderTable(ObservableList<T> DaoList, TableView<T> Table, BorderPane border){
        Page<T> page= new Page<>(DaoList, 30);
        TablePagination<T> table3 = new TablePagination<>(page, Table);
        border.setCenter(table3.getTablePagination());
    }

    /**
     * 显示指定页数，这里用来刷新后不改变页数使用
     * */
    public ShowBorderTable(ObservableList<T> DaoList, TableView<T> Table, BorderPane border,int x){
        Page<T> page= new Page<>(DaoList, 30);
        TablePagination<T> table3 = new TablePagination<>(page, Table);
        table3.getTablePagination().setCurrentPageIndex(x);
        border.setCenter(table3.getTablePagination());
    }

    /**
     * 无参方法
     * */
    public ShowBorderTable(){

    }

    /**
     * 显示当前页内容
     * */
    public void ShowBorderTableNowPage(ObservableList<T> DaoList, TableView<T> Table, BorderPane border){
        int x = StageHashMap.Int.get("pageindex");
        new ShowBorderTable<>(DaoList,Table,border,x);
    }

    /**
     * 显示底页内容
     * */
    public void ShowBorderTableEndPage(ObservableList<T> DaoList, TableView<T> Table, BorderPane border){
        Page<T> page= new Page<>(DaoList, 30);
        TablePagination<T> table3 = new TablePagination<>(page, Table);
        table3.getTablePagination().setCurrentPageIndex(page.getTotalPage());
        border.setCenter(table3.getTablePagination());
    }

    /**
     * 页数跳转
     * */
    public void PageChange(TextField textField, JFXButton button,ObservableList<T> DaoList, TableView<T> Table, BorderPane border){
        String s = textField.getText().trim();
        int tznumber;
        if (s.equals("") || s.equals("0")) {
            new DialogBuilder(button).setTitle("提示").setMessage("不要为空或者输入正确页数").setPositiveBtn("确定", null).create(500, 200);
        } else {
            tznumber = Integer.parseInt(s) - 1;
            Page<T> page = new Page<>(DaoList, 30);
            TablePagination<T> table3 = new TablePagination<>(page, Table);
            if (tznumber + 1 > table3.getPage().getTotalPage()) {
                new DialogBuilder(button).setTitle("提示").setMessage("不要超过总页数").setPositiveBtn("确定", null).create(500, 200);
            } else {
                table3.getTablePagination().setCurrentPageIndex(tznumber);
                border.setCenter(table3.getTablePagination());
            }
        }
    }

}
