package dao;


import java.util.List;


public interface DutytypeDao {


    public List<Dutytype> getAllDutytype();

    public Dutytype getDutyID(String dutyname);

}
