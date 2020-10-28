package dao;


import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ScheduleDao {


    List<Schedule> getAllSchedule();

    /**
     * 搜索全部,通过月份
     * */
    List<Schedule> getScheduleList(String date);

    List<Schedule> getScheduleListByKey(String key);

    /**
     * username + date 具体到月份
     * */
     List<Schedule> selectByUsername(@Param("employeeID") String username, @Param("date")String date);

    /**
     * username + date 具体到天数
     * */
     Schedule PersonSelectByDate(@Param("employeeID") String username, @Param("date")String date);

    int updateScheduleIDManDate(Schedule schedule);

    /**
     * 插入班次
     * */
    int insertSchedule(Schedule schedule);

    /**
     * 批量插入班次
     * */
    void insertScheduleList(@Param("SchList")List<Schedule> schedules);


    /**
     * 换班用
     * */
    void updateScheduleDayAndDuty(Schedule schedule);

    /**
     * 请假用
     * */
    int updateScheduleDuty(Schedule schedule);

    /**
     * 修改某个班次的人
     * */
    int updateScheduleMan(Schedule schedule);

    /**
     * 删除
     * */
    boolean deleteSchedule(Schedule schedule);
}
