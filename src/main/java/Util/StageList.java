package Util;

import dao.Dutytype;
import dao.Schedule;

import java.util.ArrayList;
import java.util.List;

public class StageList {

    public static List<Schedule> ZaoBan = new ArrayList<>();
    public static List<Schedule> XiaoZao = new ArrayList<>();
    public static List<Schedule> shenfang = new ArrayList<>();
    public static List<Schedule> hedui = new ArrayList<>();
    public static List<Schedule> paiyao = new ArrayList<>();
    public static List<Schedule> wanban = new ArrayList<>();
    public static List<Schedule> long1 = new ArrayList<>();
    public static List<Schedule> long2 = new ArrayList<>();
    public static List<Schedule> long3 = new ArrayList<>();
    public static List<Schedule> linshi = new ArrayList<>();
    public static List<Schedule> yeban = new ArrayList<>();
    public static List<Schedule> dianhua = new ArrayList<>();

    public static List<String> 早班= new ArrayList<>();
    public static List<String> 小早= new ArrayList<>();
    public static List<String> 审方= new ArrayList<>();
    public static List<String> 核对= new ArrayList<>();
    public static List<String> 排药= new ArrayList<>();
    public static List<String> 晚班= new ArrayList<>();
    public static List<String> 长1= new ArrayList<>();
    public static List<String>长2= new ArrayList<>();
    public static List<String>长3= new ArrayList<>();
    public static List<String>临时= new ArrayList<>();
    public static List<String>夜班= new ArrayList<>();
    public static List<String>电话= new ArrayList<>();

    public static List<Dutytype> workday = new ArrayList<>();
    public static List<Dutytype> weekend = new ArrayList<>();

    public static void clearAll(){
        StageList.ZaoBan.clear();StageList.XiaoZao.clear();StageList.shenfang.clear();
        StageList.hedui.clear();StageList.paiyao.clear();StageList.dianhua.clear();
        StageList.linshi.clear();StageList.long1.clear();StageList.long2.clear();
        StageList.long3.clear();StageList.wanban.clear();StageList.yeban.clear();
        StageList.早班.clear();StageList.小早.clear();StageList.核对.clear();
        StageList.排药.clear();StageList.晚班.clear();StageList.审方.clear();
        StageList.长1.clear(); StageList.长2.clear(); StageList.长3.clear();
        StageList. 临时.clear();StageList.夜班.clear();StageList.电话.clear();

    }

}
