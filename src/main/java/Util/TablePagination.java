package Util;


import javafx.collections.FXCollections;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableView;


public class TablePagination<T> {
    private Page<T> page;
    private TableView<T> tableView;
    private Pagination pagination;
    private int i;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Page<T> getPage() {
        return page;
    }

    public TableView<T> getTableView() {
        return tableView;
    }

    public Pagination getTablePagination() {
        return pagination;
    }

    /**
     * getter
     **/
    public TablePagination(Page<T> page, TableView<T> tableView) {
        this.page = page;
        this.tableView = tableView;
        pagination = new Pagination();
        pagination.setStyle("-fx-background-color: #f5f5f5");
//      pagination = new Pagination(page.getTotalPage(),0);
        pagination.pageCountProperty().bindBidirectional(page.totalPageProperty());
        updatePagination();
    }


    public void updatePagination() {
        pagination.setPageFactory(pageIndex -> {
            tableView.setItems(FXCollections.observableList(page.getCurrentPageDataList(pageIndex)));
             StageHashMap.Int.put("pageindex",pageIndex);//页码放进HashMap
             return tableView;
        });
    }


}