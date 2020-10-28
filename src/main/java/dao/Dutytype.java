package dao;

import Util.MybatisUtil;

import java.util.List;

public class Dutytype implements Comparable<Dutytype>{

    private int id;
    private String dutyname;
    public int flag=0;

    public int getId() {
        return id;
    }

    public String getDutyname() {
        return dutyname;
    }

    public void setDutyname(String dutyname) {
        this.dutyname = dutyname;
    }

    public void setId(int id) {
        this.id = id;
    }



    //select
    public static Dutytype getDutyID(String dutyname){
        Dutytype dutytype = null;
        try{
            dutytype = MybatisUtil.getSqlSession().getMapper(DutytypeDao.class).getDutyID(dutyname);
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        return dutytype;
    }

    public static List<Dutytype> getAllDutytype(){
        List<Dutytype> list = null;
        try{
            list = MybatisUtil.getSqlSession().getMapper(DutytypeDao.class).getAllDutytype();
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int compareTo(Dutytype o) {
        return Integer.compare(this.id, o.id);
    }
}
