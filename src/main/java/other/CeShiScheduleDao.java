package other;


import java.util.List;
/**
 * 测试用
 * */

public interface CeShiScheduleDao {


    public List<CeShiSchedule> getAllSchedule();

    int insertSchedule(CeShiSchedule ceShiSchedule);


}
