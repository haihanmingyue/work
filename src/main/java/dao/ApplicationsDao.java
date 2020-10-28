package dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ApplicationsDao {

    //搜索全部
    public List<Application> getApplyList();

    //管理员通过日期搜索
    public List<Application> SelectByDate(String date);

    //普通用户通过日期搜索，查看详细
    public Application selectByDate(@Param("employeeID") String username, @Param("applicationDate")String date);

    //用过username搜索
    public List<Application> selectByUsername(String username);

    //用过username and date 这个月的所有排班
    public List<Application> PersonSelectByKey(@Param("employeeID") String username, @Param("applicationDate")String date);

    //通过用户名删除
    boolean deleteSQ(@Param("employeeID") String username, @Param("applicationDate")String date);

    //插入数据
    int insertHuanBanApply(Application application);

    //更新审核状态
    int updateStatus(Application application);
}
