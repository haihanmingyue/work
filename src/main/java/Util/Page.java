package Util;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;


/**
 * 数据分页
 * */
public class Page<T> {
    private SimpleIntegerProperty totalRecord; // 数据总数
    private SimpleIntegerProperty pageSize; //  每页多少条
    private SimpleIntegerProperty totalPage; // 总页数
    private List<T> rowDataList; // total data

    public int getTotalRecord() {
        return totalRecord.get();
    }

    public SimpleIntegerProperty totalRecordProperty() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord.set(totalRecord);
    }

    public int getPageSize() {
        return pageSize.get();
    }

    public SimpleIntegerProperty pageSizeProperty() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize.set(pageSize);
    }

    public int getTotalPage() {
        return totalPage.get();
    }

    SimpleIntegerProperty totalPageProperty() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage.set(totalPage);
    }

    public List<T> getRowDataList() {
        return rowDataList;
    }

    public void setRowDataList(List<T> rowDataList) {
        this.rowDataList = rowDataList;
    }

    /**
     * @param rowDataList
     * @param pageSize    the number of data in per page
     */
    public Page(List<T> rowDataList, int pageSize) {
        this.totalRecord = new SimpleIntegerProperty();
        this.totalPage = new SimpleIntegerProperty();
        this.rowDataList = rowDataList;
        this.pageSize = new SimpleIntegerProperty(pageSize);
        initialize();

    }

    private void initialize() {
        totalRecord.set(rowDataList.size());

        // calculate the number of total pages
        totalPage.set(
                totalRecord.get() % pageSize.get() == 0 ?
                        //为零，正好整除=页数
                        totalRecord.get() / pageSize.get() :
                        //不为零，多一页
                        totalRecord.get() / pageSize.get() + 1);

        // add listener: the number of total pages need to be change if the page size changed
        pageSize.addListener((observable, oldVal, newVal) ->
                totalPage.set(
                        totalRecord.get() % pageSize.get() == 0 ?
                                totalRecord.get() / pageSize.get() :
                                totalRecord.get() / pageSize.get() + 1)
        );
    }


    /**
     * current page number(0-based system)
     *
     * @param currentPage current page number
     * @return
     */

    List<T> getCurrentPageDataList(int currentPage) {
        int fromIndex = pageSize.get() * currentPage;
        int tmp = pageSize.get() * currentPage + pageSize.get() - 1;
        int endIndex = tmp >= totalRecord.get() ? totalRecord.get() - 1 : tmp;
        // subList(fromIndex, toIndex) -> [fromIndex, toIndex)
        return rowDataList.subList(fromIndex, endIndex + 1);
    }
}
