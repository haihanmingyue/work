package other;

import dao.Schedule;
import Util.DialogBuilder;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextArea;

import java.text.SimpleDateFormat;
import java.util.*;

public class PaiBanFinish {
    private static int workday ;
    private static int weekday;

    private static int zaoban;
    private static int xiaozao;
    private static int shenfang;
    private static int hedui;
    private static int paiyao;
    private static int dianhua;
    private static int linshi;
    private static int chang1;
    private static int chang2;
    private static int chang3;
    private static int wanban;

    private static int zaobanWorkNeedNumber=0;
    private static int xiaozaoWorkNeedNumber=0;
    private static int shenfangWorkNeedNumber=0;
    private static int heduiWorkNeedNumber=0;
    private static int paiyaoWorkNeedNumber=0;
    private static int dianhuaWorkNeedNumber=0;
    private static int linshiWorkNeedNumber=0;
    private static int chang1WorkNeedNumber=0;
    private static int chang2WorkNeedNumber=0;
    private static int chang3WorkNeedNumber=0;
    private static int wanbanWorkNeedNumber=0;
    private static int zaobanWeekNeedNumber=0;
    private static int xiaozaoWeekNeedNumber=0;
    private static int shenfangWeekNeedNumber=0;
    private static int heduiWeekNeedNumber=0;
    private static int paiyaoWeekNeedNumber=0;
    private static int dianhuaWeekNeedNumber=0;
    private static int linshiWeekNeedNumber=0;
    private static int chang1WeekNeedNumber=0;
    private static int chang2WeekNeedNumber=0;
    private static int chang3WeekNeedNumber=0;
    private static int wanbanWeekNeedNumber=0;
    private static int[] number3 ;//work
    private static int[] number4 ;//week
    private static ArrayList<Man> cmanList = new ArrayList<>();
    private static ArrayList<Man> fmanList = new ArrayList<>();//存夜班移除的人
    private static ArrayList<ArrayList<Man>> dmanList = new ArrayList<>();
    private static ArrayList<Man> emanList = new ArrayList<>();
    private static int sumNumber;
    private static int monthDay;
    public static boolean temp;
    public static List<Schedule> ScheduleList = new ArrayList<>();


    public static void main(String[] args) {



        String date = "2020-04";
//        String[] mans = {"用户1","用户2","用户3","用户4","用户5","用户6","用户7","用户8","用户9","用户10","用户11","用户12"};
        String[] mans = {"用户1","用户2","用户3","用户4","用户5","用户6","用户7"};
        ArrayList<Man> manList = new ArrayList<>();
        for(String string : mans) {
            Man man = new Man();
            man.name = string;
            manList.add(man);
        }

        sort(manList);

        String[] work = {"早班","小早","核对"};
        String[] week = {"早班","临时","核对","晚班"};
        int [] number =  {2,2,1};
        int [] number2 =  {1,1,1,1};
        //2,2,1,1
        // 1,2,1,1
//        Schedule(work,week,number,number2,manList,date);


    }

    public PaiBanFinish(String[] work, String[] week, int[] number, int[]number2, ArrayList<Man> manList, String Date, JFXButton button, TextArea textArea){
        cmanList.clear();
        fmanList.clear();
        dmanList.clear();
        emanList.clear();
        if(ScheduleList!=null){
            ScheduleList.clear();
        }
        temp = Schedule(work, week, number,number2, manList, Date,button,textArea);
    }

    //方法，供外部使用
    public static boolean Schedule(String[] work, String[] week, int[] number, int[]number2, ArrayList<Man> manList, String Date,JFXButton button,TextArea textArea){
        boolean flag = false;
        int w = WorkDayAndWeekDay(Date);

        System.out.println(Date+"第一天是星期"+w);

        System.out.println("工作日"+workday);

        System.out.println("周末"+weekday);

        ArrayList<Man> bmanList = new ArrayList<>();

        WorkDaySumTimes(work,number);

        WeekDaySumTimes(week,number2);

        ArrayList<String> worklist = HeBinWork(work,week);

        WorkNeedNumber(work,week,number,number2);

        ArrayList<BanCi> WorkList= XieRuID(worklist);

        WorkNeedNumber(WorkList);

        XieRuTimes(WorkList);

        ManWorkTimes(WorkList,manList);
        ArrayList<Man> list = NightShift(manList,Date);
        
        ManAndNeedMan(number,number2,manList);
        int StartOrFinish = ManNumberToPaiBan(manList);

        //人数比需求多， 1和2 其实一样，1的暂时没想好，就先用2的
        if(StartOrFinish == 2){
            System.out.println("执行 StartOrFinish = 2");
            for(int i =0 ;i<WorkList.size();i++) {
                bmanList.clear();
                int count = w-1;
                int jjj=0;
//                timesToZero(manList);
                String workname = WorkList.get(i).dutyname;
                System.out.println("开始 "+workname+" 的排班");
                for(int j= 0;j<monthDay ;j++) {
                    String name = list.get(jjj).name;
                    DeleteNightMan(manList,name,jjj);
                    if(i>0){
                        deletell(manList,jjj);
                    }
                    if(count==5 || count==6) {
                        boolean result ;
                        result = Arrays.asList(week).contains(workname);
                        System.out.println((j+1)+" "+result);
                        if(result){
                            delete(manList, workname,bmanList);
                            if(!ManNumber(manList, number4[i]))
                                break;
                            else {
                                sort(manList);
                                ManToEnd(manList);
                                NoWorkToFirst(manList);
                                if(workname.equals("早班")){
                                    NoZaoBan(manList,list,jjj,number4[i]);
                                }
                                paiban(manList, workname,j,number4[i],count,Date);
                            }
                        }
                        if(!result){
                            count++;
                            manList.addAll(emanList);
                            manList.addAll(fmanList);
                            emanList.clear();
                            fmanList.clear();
                            jjj++;
                            if (count == 7) {
                                count = 0;
                            }
                            continue;
                        }
                    }
                    else {
                        boolean result ;
                        result = Arrays.asList(work).contains(workname);
                        if(result){
                            delete(manList, workname,bmanList);
                            if(!ManNumber(manList, number3[i]))
                                break;
                            else {
                                sort(manList);
                                ManToEnd(manList);
                                NoWorkToFirst(manList);
                                if(workname.equals("早班")){
                                    NoZaoBan(manList,list,jjj,number4[i]);
                                }
                                paiban(manList, workname,j,number3[i],count,Date);
                            }
                        }else {
                            count++;
                            manList.addAll(emanList);
                            manList.addAll(fmanList);
                            emanList.clear();
                            fmanList.clear();
                            jjj++;
                            if (count == 7) {
                                count = 0;
                            }
                            continue;
                        }
                    }
                    count++;
                    if (count == 7) {
                        count = 0;
                    }
                    System.out.println("添加被移除的 ");
                    manList.addAll(emanList);
                    manList.addAll(fmanList);
                    emanList.clear();
                    fmanList.clear();
                    jjj++;
                }
                System.out.println(workname+" 的排班结束");
                manList.addAll(cmanList);
                cmanList.clear();
                System.out.println("------------");
            }

            System.out.println("-----------");
            manList.addAll(emanList);
            manList.addAll(fmanList);
            new DialogBuilder(button).setTitle("提示").setMessage("排班结束").setPositiveBtn("确定",null).create(500,200);
            textArea.setText("");
            for(Man man : manList) {
                textArea.appendText("姓名："+man.name+" 一共上了 " +man.Times +" 个班次\n");
                System.out.print("姓名："+man.name+" 一共上了 " +man.Times +" 个班次");
                for(BanCi banCi : WorkList){
                    textArea.appendText(" "+banCi.dutyname+"上了 "+ print(man,banCi.dutyname)+"次 ");
                    System.out.print(" "+banCi.dutyname+" 上了 "+ print(man,banCi.dutyname)+"次 ");
                }
                textArea.appendText("夜班上了 "+ man.YeBanTimes+"次\n");
                System.out.print("夜班上了 "+ man.YeBanTimes);
                System.out.println();
            }
            flag = true;
        }
        if(StartOrFinish == 1){
            System.out.println("执行 StartOrFinish = 1");
            for(int i =0 ;i<WorkList.size();i++) {
                bmanList.clear();
                int count = w-1;
                int jjj=0;
//                timesToZero(manList);
                String workname = WorkList.get(i).dutyname;
                System.out.println("开始 "+workname+" 的排班");
                for(int j= 0;j<monthDay;j++) {
                    String name = list.get(jjj).name;
                    DeleteNightMan(manList,name,jjj);
                    if(i>0){
                        deletell(manList,jjj);
                    }
                    if(count==5 || count==6) {
                        boolean result ;
                        result = Arrays.asList(week).contains(workname);
                        System.out.println((j+1)+" "+result);
                        if(result){
                            delete(manList, workname,bmanList);
                            if(!ManNumber(manList, number4[i]))
                                break;
                            else {
                                sort(manList);
                                NoWorkToFirst(manList);
                                if(workname.equals("早班")){
                                    NoZaoBan(manList,list,jjj,number4[i]);
                                }
                                ManToEnd(manList);
                                paiban(manList, workname,j,number4[i],count,Date);
                            }
                        }
                        if(!result){
                            count++;
                            manList.addAll(emanList);
                            manList.addAll(fmanList);
                            emanList.clear();
                            fmanList.clear();
                            jjj++;
                            if (count == 7) {
                                count = 0;
                            }
                            continue;
                        }
                    }
                    else {
                        boolean result ;
                        result = Arrays.asList(work).contains(workname);
                        if(result){
                            delete(manList, workname,bmanList);
                            if(!ManNumber(manList, number3[i]))
                                break;
                            else {
                                sort(manList);
                                NoWorkToFirst(manList);
                                if(workname.equals("早班")){
                                    NoZaoBan(manList,list,jjj,number4[i]);
                                }
                                ManToEnd(manList);
                                paiban(manList, workname,j,number3[i],count,Date);
                            }
                        }else {
                            count++;
                            manList.addAll(emanList);
                            manList.addAll(fmanList);
                            emanList.clear();
                            fmanList.clear();
                            jjj++;
                            if (count == 7) {
                                count = 0;
                            }
                            continue;
                        }
                    }
                    count++;
                    if (count == 7) {
                        count = 0;
                    }
                    System.out.println("添加被移除的 ");
                    manList.addAll(emanList);
                    manList.addAll(fmanList);
                    emanList.clear();
                    fmanList.clear();
                    jjj++;
                }
                System.out.println(workname+" 的排班结束");
                manList.addAll(cmanList);
                cmanList.clear();
                System.out.println("------------");
            }
            System.out.println("-----------");
            manList.addAll(fmanList);
            new DialogBuilder(button).setTitle("提示").setMessage("排班结束").setPositiveBtn("确定",null).create(500,200);
            textArea.setText("");
            for(Man man : manList) {
                textArea.appendText("姓名："+man.name+" 一共上了 " +man.Times +" 个班次\n");
                System.out.print("姓名："+man.name+" 一共上了 " +man.Times +" 个班次");
                for(BanCi banCi : WorkList){
                    textArea.appendText(" "+banCi.dutyname+"上了 "+ print(man,banCi.dutyname)+"次 ");
                    System.out.print(" "+banCi.dutyname+" 上了 "+ print(man,banCi.dutyname)+"次 ");
                }
                textArea.appendText("夜班上了 "+ man.YeBanTimes+"次\n");
                System.out.print("夜班上了 "+ man.YeBanTimes);
                System.out.println();
            }
            flag = true;
        }
        //人数 和 一天最多需求人数一样时
        if(StartOrFinish == 0){
            System.out.println("执行 StartOrFinish = 0");
            for(int i =0 ;i<WorkList.size();i++) {
                int count = w-1;
                int jjj=0;
//                timesToZero(manList);
                String workname = WorkList.get(i).dutyname;
                System.out.println("开始 "+workname+" 的排班");
                for(int j= 0;j<monthDay ;j++) {
                    String name = list.get(jjj).name;
                    DeleteNightMan(manList,name,jjj);
                    if(i>0){
                        deletell(manList,jjj);
                    }
                    if(count==5 || count==6) {
                        boolean result ;
                        result = Arrays.asList(week).contains(workname);
                        System.out.println((j+1)+" "+result);
                        if(result){
                            if(!ManNumber(manList, number4[i]))
                                break;
                            else {
                                sort(manList);

                                NoWorkToFirst(manList);
                                if(workname.equals("早班")){
                                    NoZaoBan(manList,list,jjj,number4[i]);
                                }
                                ManToEnd(manList);
                                paiban(manList, workname,j,number4[i],count,Date);
                            }
                        }
                        if(!result){
                            count++;
                            jjj++;
                            manList.addAll(fmanList);
                            manList.addAll(emanList);
                            emanList.clear();
                            fmanList.clear();
                            if (count == 7) {
                                count = 0;
                            }
                            continue;
                        }
                    }
                    else {
                        boolean result ;
                        result = Arrays.asList(work).contains(workname);
                        if(result){
                            if(!ManNumber(manList, number3[i]))
                                break;
                            else {
                                sort(manList);
                                NoWorkToFirst(manList);
                                if(workname.equals("早班")){
                                    NoZaoBan(manList,list,jjj,number4[i]);
                                }
                                ManToEnd(manList);
                                paiban(manList, workname,j,number3[i],count,Date);
                            }
                        }else {
                            count++;
                            jjj++;
                            manList.addAll(fmanList);
                            manList.addAll(emanList);
                            emanList.clear();
                            fmanList.clear();
                            if (count == 7) {
                                count = 0;
                            }
                            continue;
                        }
                    }
                    count++;
                    if (count == 7) {
                        count = 0;
                    }

                    System.out.println("添加被移除的 ");
                    manList.addAll(fmanList);
                    manList.addAll(emanList);
                    emanList.clear();
                    fmanList.clear();
                    jjj++;
                }
                System.out.println(workname+" 的排班结束");

            }
            System.out.println("-----------");
            manList.addAll(fmanList);
            new DialogBuilder(button).setTitle("提示").setMessage("排班结束").setPositiveBtn("确定",null).create(500,200);
            for(Man man : manList) {
                textArea.appendText("姓名："+man.name+" 一共上了 " +man.Times +" 个班次\n");
                System.out.print("姓名："+man.name+" 一共上了 " +man.Times +" 个班次");
                for(BanCi banCi : WorkList){
                    textArea.appendText(" "+banCi.dutyname+"上了 "+ print(man,banCi.dutyname)+"次 ");
                    System.out.print(" "+banCi.dutyname+" 上了 "+ print(man,banCi.dutyname)+"次 ");
                }
                textArea.appendText("夜班上了 "+ man.YeBanTimes+"次\n");
                System.out.print("夜班上了 "+ man.YeBanTimes);
                System.out.println();
            }
            flag = true;
        }
        //人数小于 一天最多需求人数时
        if (StartOrFinish == -1){
            System.out.println("执行 StartOrFinish = -1");
            new DialogBuilder(button).setTitle("提示").setMessage("您给定的人数太少了！\n至少要有一天班次所需相同的人数,夜班默认占用一人").setPositiveBtn("确定",null).create(500,200);
            System.out.println("您给定的人数太少了，至少要有一天班次所需相同的人数");
        }

        return flag;
    }

    private static int print(Man man, String workname){
        int x = 0;
        switch (workname) {
            case "早班":
                x = man.zaobantimes;
                break;
            case "小早":
               x = man.xiaozaotimes;
                break;
            case "审方":
               x = man.shenfangtimes;
                break;
            case "核对":
                x = man.heduitimes;
                break;
            case "排药":
                x = man.paiyaotimes;
                break;
            case "电话":
                x = man.dianhuatimes;
                break;
            case "临时":
                x = man.linshitimes;
                break;
            case "长1":
                x = man.chang1times;
                break;
            case "长2":
                x = man.chang2times;
                break;
            case "长3":
                x = man.chang3times;
                break;
            case "晚班":
              x = man.wanbantimes;
                break;
        }
        return x;
    }
    
    private static void DeleteNightMan(ArrayList<Man> manList,String name,int i){
        for(int j = 0;j<manList.size();j++){
            if(manList.get(j).name.equals(name)){
                Man man = manList.get(j);
                man.times++;
                fmanList.add(man);
                System.out.println((i+1)+"号是 "+name+" 上夜班，暂时移除");
                manList.remove(man);
                System.out.println("剩余: "+manList.size());
                break;
            }
        }
    }

    private static void NoZaoBan(ArrayList<Man> manList,ArrayList<Man> list,int j,int number){
        int day;
        day = j-1;
        if(day>=0){
            for(int d=0;d<number;d++){
                if(manList.get(d).name.equals(list.get(day).name)){
                    Man man = manList.get(d);
                    manList.remove(man);
                    manList.add(man);
                    System.out.println(man.name+"前一天是夜班，将"+man.name+"移动到最后一位，不上早班");
                    NoZaoBan(manList,list,j,number);
                }else {
                    break;
                }
            }
        }
    }

    //将每个班次的人数写入 变量
    private static void WorkNeedNumber(String[] work, String[] week, int[] number, int[] number2){
        for(int i = 0;i<work.length;i++){
            switch (work[i]) {
                case "早班":
                    zaobanWorkNeedNumber = number[i];
                    break;
                case "小早":
                    xiaozaoWorkNeedNumber  = number[i];
                    break;
                case "审方":
                    shenfangWorkNeedNumber  = number[i];
                    break;
                case "核对":
                    heduiWorkNeedNumber  = number[i];
                    break;
                case "排药":
                    paiyaoWorkNeedNumber  = number[i];
                    break;
                case "电话":
                    dianhuaWorkNeedNumber  = number[i];
                    break;
                case "临时":
                    linshiWorkNeedNumber  = number[i];
                    break;
                case "长1":
                    chang1WorkNeedNumber  = number[i];
                    break;
                case "长2":
                    chang2WorkNeedNumber  = number[i];
                    break;
                case "长3":
                    chang3WorkNeedNumber  = number[i];
                    break;
                case "晚班":
                    wanbanWorkNeedNumber  = number[i];
                    break;
            }
        }
        for(int i = 0;i<week.length;i++){
            switch (week[i]) {
                case "早班":
                    zaobanWeekNeedNumber = number2[i];
                    break;
                case "小早":
                    xiaozaoWeekNeedNumber  = number2[i];
                    break;
                case "审方":
                    shenfangWeekNeedNumber  = number2[i];
                    break;
                case "核对":
                    heduiWeekNeedNumber  = number2[i];
                    break;
                case "排药":
                    paiyaoWeekNeedNumber  = number2[i];
                    break;
                case "电话":
                    dianhuaWeekNeedNumber  = number2[i];
                    break;
                case "临时":
                    linshiWeekNeedNumber  = number2[i];
                    break;
                case "长1":
                    chang1WeekNeedNumber  = number2[i];
                    break;
                case "长2":
                    chang2WeekNeedNumber  = number2[i];
                    break;
                case "长3":
                    chang3WeekNeedNumber  = number2[i];
                    break;
                case "晚班":
                    wanbanWeekNeedNumber  = number2[i];
                    break;
            }
        }
    }
    // 计算出 实际用于循环的 每个班次的人数
    private static void WorkNeedNumber(ArrayList<BanCi> WorkList){
        number3 = new int[WorkList.size()];
        number4 = new int[WorkList.size()];
        for(int i = 0;i<WorkList.size();i++){
            switch (WorkList.get(i).dutyname) {
                case "早班":
                    if(zaobanWorkNeedNumber==0){
                        number3[i] = zaobanWeekNeedNumber;
                    }else {
                        number3[i] = zaobanWorkNeedNumber;
                    }
                    break;
                case "小早":
                    if(xiaozaoWorkNeedNumber==0){
                        number3[i] = xiaozaoWeekNeedNumber;
                    }else {
                        number3[i] = xiaozaoWorkNeedNumber;
                    }
                    break;
                case "审方":
                    if(shenfangWorkNeedNumber==0){
                        number3[i] = shenfangWeekNeedNumber;
                    }else {
                        number3[i] = shenfangWorkNeedNumber;
                    }
                    break;
                case "核对":
                    if(heduiWorkNeedNumber==0){
                        number3[i] = heduiWeekNeedNumber;
                    }else {
                        number3[i] = heduiWorkNeedNumber;
                    }
                    break;
                case "排药":
                    if(paiyaoWorkNeedNumber==0){
                        number3[i] = paiyaoWeekNeedNumber;
                    }else {
                        number3[i] = paiyaoWorkNeedNumber;
                    }
                    break;
                case "电话":
                    if(dianhuaWorkNeedNumber==0){
                        number3[i] = dianhuaWeekNeedNumber;
                    }else {
                        number3[i] = dianhuaWorkNeedNumber;
                    }
                    break;
                case "临时":
                    if(linshiWorkNeedNumber==0){
                        number3[i] = linshiWeekNeedNumber;
                    }else {
                        number3[i] = linshiWorkNeedNumber;
                    }
                    break;
                case "长1":
                    if(chang1WorkNeedNumber==0){
                        number3[i] = chang1WeekNeedNumber;
                    }else {
                        number3[i] = chang1WorkNeedNumber;
                    }
                    break;
                case "长2":
                    if(chang2WorkNeedNumber==0){
                        number3[i] = chang2WeekNeedNumber;
                    }else {
                        number3[i] = chang2WorkNeedNumber;
                    }
                    break;
                case "长3":
                    if(chang3WorkNeedNumber==0){
                        number3[i] = chang3WeekNeedNumber;
                    }else {
                        number3[i] = chang3WorkNeedNumber;
                    }
                    break;
                case "晚班":
                    if(wanbanWorkNeedNumber==0){
                        number3[i] = wanbanWeekNeedNumber;
                    }else {
                        number3[i] = wanbanWorkNeedNumber;
                    }
                    break;
            }
        }
        for(int i = 0;i<WorkList.size();i++){
            switch (WorkList.get(i).dutyname) {
                case "早班":
                    if(zaobanWeekNeedNumber==0){
                        number4[i] = zaobanWorkNeedNumber;
                    }else {
                        number4[i] = zaobanWeekNeedNumber;
                    }
                    break;
                case "小早":
                    if(xiaozaoWeekNeedNumber==0){
                        number4[i] = xiaozaoWorkNeedNumber;
                    }else {
                        number4[i] = xiaozaoWeekNeedNumber;
                    }
                    break;
                case "审方":
                    if(shenfangWeekNeedNumber==0){
                        number4[i] = shenfangWorkNeedNumber;
                    }else {
                        number4[i] = shenfangWeekNeedNumber;
                    }
                    break;
                case "核对":
                    if(heduiWeekNeedNumber==0){
                        number4[i] = heduiWorkNeedNumber;
                    }else {
                        number4[i] = heduiWeekNeedNumber;
                    }
                    break;
                case "排药":
                    if(paiyaoWeekNeedNumber==0){
                        number4[i] = paiyaoWorkNeedNumber;
                    }else {
                        number4[i] = paiyaoWeekNeedNumber;
                    }
                    break;
                case "电话":
                    if(dianhuaWeekNeedNumber==0){
                        number4[i] = dianhuaWorkNeedNumber;
                    }else {
                        number4[i] = dianhuaWeekNeedNumber;
                    }
                    break;
                case "临时":
                    if(linshiWeekNeedNumber==0){
                        number4[i] = linshiWorkNeedNumber;
                    }else {
                        number4[i] = linshiWeekNeedNumber;
                    }
                    break;
                case "长1":
                    if(chang1WeekNeedNumber==0){
                        number4[i] = chang1WorkNeedNumber;
                    }else {
                        number4[i] = chang1WeekNeedNumber;
                    }
                    break;
                case "长2":
                    if(chang2WeekNeedNumber==0){
                        number4[i] = chang2WorkNeedNumber;
                    }else {
                        number4[i] = chang2WeekNeedNumber;
                    }
                    break;
                case "长3":
                    if(chang3WeekNeedNumber==0){
                        number4[i] = chang3WorkNeedNumber;
                    }else {
                        number4[i] = chang3WeekNeedNumber;
                    }
                    break;
                case "晚班":
                    if(wanbanWeekNeedNumber==0){
                        number4[i] = wanbanWorkNeedNumber;
                    }else {
                        number4[i] = wanbanWeekNeedNumber;
                    }
                    break;
            }
        }

    }

    //看一天需要的最多的人 和 进行排班的人
    private static void ManAndNeedMan(int[] number, int[]number2, ArrayList<Man> manList){
        int sum1=0,sum2=0;
       for(int v : number){
           sum1 += v;
       }
       sum1++;
       System.out.println("sum1 : " +sum1);
        for(int v : number2){
            sum2 += v;
        }
        sum2++;
        System.out.println("sum2 : " +sum2);
        sumNumber = Math.max(sum1, sum2);
    }

    // 看进行排班的人是否符合 输入的人数要求
    private static int ManNumberToPaiBan(ArrayList<Man> manList){
        int result = 0;
        if(manList.size()==sumNumber){
            result = 0;
        }
        if(manList.size()<sumNumber){
            result = -1;
        }
        if((manList.size()-sumNumber)>= 1 && manList.size()/sumNumber==1){
            result = 1;
        }
        if(manList.size()/sumNumber>=2){
            result =2;
        }

        return result;
    }

    //进行排班
    private static void paiban(ArrayList<Man> manList,String workname,int j,int x,int count,String date) {
        String[] week = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        switch (workname) {
            case "早班":
                ArrayList<Man> list = new ArrayList<>();
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).zaoban--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).zaobantimes++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    list.add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上早班");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10001);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                dmanList.add(list);
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "小早":
                    for (int c = 0; c < x; c++) {
                        //点数最小的人排
                        manList.get(c).xiaozao--;
                        manList.get(c).times++;
                        manList.get(c).Times++;
                        manList.get(c).noWork = 0;
                        manList.get(c).xiaozaotimes++;
                        manList.get(c).week = week[count];
                        Man man = new Man();
                        man.name = manList.get(c).name;
                        dmanList.get(j).add(man);
                        System.out.println((j + 1) + "号 " + manList.get(c).name + " " + manList.get(c).week + " 上小早");
                        Schedule schedule = new Schedule();
                        schedule.setDutyID(10002);
                        schedule.setEmployeeID(manList.get(c).username);
                        String s;
                        if ((j + 1) < 10) {
                            s = "0" + (j + 1);
                        } else {
                            s = "" + (j + 1);
                        }
                        schedule.setDate(date + "-" + s);
                        ScheduleList.add(schedule);
                    }
                    for (int h = x; h < manList.size(); h++) {
                        manList.get(h).times = 0;
                        manList.get(h).noWork++;
                        manList.get(h).replyTimes = 0;
                        System.out.println(manList.get(h).name + "，没有班次");

                    }
                    break;

            case "临时":
                    for (int c = 0; c < x; c++) {
                        //点数最小的人排
                        manList.get(c).linshi--;
                        manList.get(c).times++;
                        manList.get(c).Times++;
                        manList.get(c).noWork = 0;
                        manList.get(c).linshitimes++;
                        manList.get(c).week = week[count];
                        Man man = new Man();
                        man.name = manList.get(c).name;
                        dmanList.get(j).add(man);
                        System.out.println((j + 1) + "号 " + manList.get(c).name + " " + manList.get(c).week + " 上临时");
                        Schedule schedule = new Schedule();
                        schedule.setDutyID(10006);
                        schedule.setEmployeeID(manList.get(c).username);
                        String s;
                        if ((j + 1) < 10) {
                            s = "0" + (j + 1);
                        } else {
                            s = "" + (j + 1);
                        }
                        schedule.setDate(date + "-" + s);
                        ScheduleList.add(schedule);
                    }
                    for (int h = x; h < manList.size(); h++) {
                        manList.get(h).times = 0;
                        manList.get(h).noWork++;
                        System.out.println(manList.get(h).name + "，没有班次");

                    }
                    break;
            case "核对":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).hedui--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).heduitimes++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上核对");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10004);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "晚班":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).wanban--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).wanbantimes++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上晚班");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10011);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "审方":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).shenfang--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).shenfangtimes++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上晚班");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10003);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "长3":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).chang3--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).chang3times++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上长3");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10010);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "长2":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).chang2--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).chang2times++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上长2");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10009);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "长1":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).chang1--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).chang1times++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 上长1");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10008);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "电话":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).dianhua--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).dianhuatimes++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 电话");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(1007);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;
            case "排药":
                for(int c =0; c<x; c++) {
                    //点数最小的人排
                    manList.get(c).paiyao--;
                    manList.get(c).times++;
                    manList.get(c).Times++;
                    manList.get(c).noWork=0;
                    manList.get(c).paiyaotimes++;
                    manList.get(c).week= week[count];
                    Man man = new Man();
                    man.name = manList.get(c).name;
                    dmanList.get(j).add(man);
                    System.out.println((j+1)+"号 "+manList.get(c).name+" "+manList.get(c).week+" 排药");
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10005);
                    schedule.setEmployeeID(manList.get(c).username);
                    String s;
                    if((j+1)<10){
                        s = "0"+(j+1);
                    }else {
                        s = ""+(j+1);
                    }
                    schedule.setDate(date+"-"+s);
                    ScheduleList.add(schedule);
                }
                for(int h = x;h<manList.size();h++) {
                    manList.get(h).times = 0;
                    manList.get(h ).noWork++;
                    System.out.println(manList.get(h).name+"，没有班次");

                }
                break;

        }

    }

    private static void deletell(ArrayList<Man> manList,int j){
            ArrayList<Man> list = dmanList.get(j);
            for(Man man : list){
               for(int i =0;i<manList.size();i++){
                   if(man.name .equals(manList.get(i).name)){
                       Man man1 = manList.get(i);
                       emanList.add(man1);
                       System.out.println((j+1)+"号，"+manList.get(i).name+" 今天已经有班次，暂时移除");
                       manList.remove(man1);
                       System.out.println("剩余人数："+manList.size());
                       deletell(manList,j);
                   }
               }
            }
        }

    //判断现有人数
    private static boolean ManNumber(ArrayList<Man> manList,int i) {
        boolean flag = true;
        if(manList.size()<i) {
            flag = false;
            System.out.println("需要"+i+"人，人数不足，停止循环");
        }
        return flag;
    }

    //给排班的人随机点数，并排序
    private static void sort(ArrayList<Man> manList) {
        Random random = new Random();
        for(Man man : manList) {
            man.number = random.nextInt(1001);
        }
        manList.sort(Man::compareTo);
    }

    //noWork 点数最大的最后一个移动，能使他排在第一位
    private static void NoWorkToFirst(ArrayList<Man> manList) {

        Comparator<Man> comparator = Comparator.comparingInt(p -> p.noWork);//比较器
        manList.sort(comparator);
        for(int i=0;i<manList.size();i++ ) {
            if(manList.get(i).noWork+1>=2) {
                System.out.println(manList.get(i).name+"移到第一位" + manList.get(i).noWork);
                Man man;
                man = manList.get(i);
                manList.remove(manList.get(i));
                manList.add(0,man);
                System.out.println("剩余"+ manList.size()+"人");
            }
        }
    }


    //连续工作两次的 移动到最后一位
    private static void ManToEnd(ArrayList<Man> manList) {
        for(int i=0;i<manList.size();i++ ) {
            if(manList.get(i).times+1>=3) {
                System.out.println(manList.get(i).name+"已经连续两次，移到最后一位");
                Man man ;
                man = manList.get(i);
                man.times=0;
                manList.remove(manList.get(i));
                manList.add((manList.size()),man);
                System.out.println("剩余"+ manList.size()+"人");
            }
        }

    }


    //移除某个班次次数已经用完的人
    private static void delete(ArrayList<Man> manList,String workname,ArrayList<Man> bmanList) {
        switch (workname) {
            case "早班":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).zaoban-1<0) {
                        System.out.println(manList.get(i).name+"白班次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "小早":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).xiaozao-1<0) {
                        System.out.println(manList.get(i).name+"小早次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "审方":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).shenfang-1<0) {
                        System.out.println(manList.get(i).name+"审方次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "核对":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).hedui-1<0) {
                        System.out.println(manList.get(i).name+"核对次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "排药":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).paiyao-1<0) {
                        System.out.println(manList.get(i).name+"排药次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "电话":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).dianhua-1<0) {
                        System.out.println(manList.get(i).name+"电话次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "临时":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).linshi-1<0) {
                        System.out.println(manList.get(i).name+"临时次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "长1":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).chang1-1<0) {
                        System.out.println(manList.get(i).name+"长1次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "长2":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).chang2-1<0) {
                        System.out.println(manList.get(i).name+"长2已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "长3":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).chang3-1<0) {
                        System.out.println(manList.get(i).name+"长3次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
            case "晚班":
                for(int i=0;i<manList.size();i++ ) {
                    if(manList.get(i).wanban-1<0) {
                        System.out.println(manList.get(i).name+"晚班次数已满,移除");
                        Man man ;
                        man = manList.get(i);
                        bmanList.add(man);
                        cmanList.add(man);
                        manList.remove(man);
                        System.out.println("剩余"+ manList.size()+"人");
                        delete(manList, workname,bmanList);
                    }
                }
                break;
        }
    }

    //给每个班次写入刚才计算的一个月需要的人数
    private static void XieRuTimes(ArrayList<BanCi> list){
        for(int i = 0;i<list.size();i++) {
            switch (list.get(i).dutyname) {
                case "早班":
                    list.get(i).sum = zaoban;
                    break;
                case "小早":
                    list.get(i).sum = xiaozao;
                    break;
                case "审方":
                    list.get(i).sum = shenfang;
                    break;
                case "核对":
                    list.get(i).sum = hedui;
                    break;
                case "排药":
                    list.get(i).sum = paiyao;
                    break;
                case "电话":
                    list.get(i).sum = dianhua;
                    break;
                case "临时":
                    list.get(i).sum = linshi;
                    break;
                case "长1":
                    list.get(i).sum = chang1;
                    break;
                case "长2":
                    list.get(i).sum = chang2;
                    break;
                case "长3":
                    list.get(i).sum = chang3;
                    break;
                case "晚班":
                    list.get(i).sum = wanban;
                    break;
            }
        }
    }

    //每个人 上某个班次的平均次数, 多算上一次
    private static void ManWorkTimes(ArrayList<BanCi> WorkList,ArrayList<Man> manList){
        int x1;

        for(BanCi banCi : WorkList){
            switch (banCi.dutyname) {
                case "早班":
                    x1 = zaoban / manList.size();
                    for(Man man : manList) {
                        man.zaoban= x1+1;
                    }
                    break;
                case "小早":
                    x1 = xiaozao / manList.size();
                    for(Man man : manList) {
                        man.xiaozao= x1+1;
                    }
                    break;
                case "审方":
                    x1 = shenfang / manList.size();
                    for(Man man : manList) {
                        man.shenfang= x1+1;
                    }
                    break;
                case "核对":
                    x1 = hedui / manList.size();
                    for(Man man : manList) {
                        man.hedui= x1+1;
                    }
                    break;
                case "排药":
                    x1 = paiyao / manList.size();
                    for(Man man : manList) {
                        man.paiyao= x1+1;
                    }
                    break;
                case "电话":
                    x1 = dianhua / manList.size();
                    for(Man man : manList) {
                        man.dianhua= x1+1;
                    }
                    break;
                case "临时":
                    x1 = linshi / manList.size();
                    for(Man man : manList) {
                        man.linshi= x1+1;
                    }
                    break;
                case "长1":
                    x1 = chang1 / manList.size();
                    for(Man man : manList) {
                        man.chang1= x1+1;
                    }
                    break;
                case "长2":
                    x1 = chang2 / manList.size();
                    for(Man man : manList) {
                        man.chang2= x1+1;
                    }
                    break;
                case "长3":
                    x1 = chang3 / manList.size();
                    for(Man man : manList) {
                        man.chang3= x1+1;
                    }
                    break;
                case "晚班":
                    x1 = wanban / manList.size();
                    for(Man man : manList) {
                        man.wanban= x1+1;
                    }
                    break;
            }
        }


    }

    //给班次写入ID,并通过ID排序
    private static ArrayList<BanCi> XieRuID(ArrayList<String> workList){
        ArrayList<BanCi> list = new ArrayList<>();
        for(String s : workList){
            BanCi banCi = new BanCi();
            banCi.dutyname = s;
            switch (s){
                case "早班":
                    banCi.id = 10001;
                    break;
                case "小早":
                    banCi.id = 10002;
                    break;
                case "审方":
                    banCi.id = 10003;
                    break;
                case "核对":
                    banCi.id = 10004;
                    break;
                case "排药":
                    banCi.id = 10005;
                    break;
                case "电话":
                    banCi.id = 10007;
                    break;
                case "临时":
                    banCi.id = 10006;
                    break;
                case "长1":
                    banCi.id = 10008;
                    break;
                case "长2":
                    banCi.id = 10009;
                    break;
                case "长3":
                    banCi.id = 10010;
                    break;
                case "晚班":
                    banCi.id = 10011;
                    break;
            }
            list.add(banCi);
        }
        list.sort(BanCi::compareTo);
        return list;
    }

    //合并工作日和周末的班次(去掉名字一样的班次)
    private static ArrayList<String> HeBinWork(String[] work,String[] week){
        ArrayList<String> list = new ArrayList<>();
        for(String s : work){
            list.add(s);
        }
        for(String s : week){
            list.add(s);
        }
        Set<String> set = new HashSet<>();// 将list所有元素添加到set中    set集合特性会自动去重复
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    //计算某个班次 一个月 工作日一共要多少人
    private static void WorkDaySumTimes(String[] work,int[]number){
        for (int i=0;i<work.length;i++) {
            switch (work[i]) {
                case "早班":
                    zaoban+= number[i]*workday;
                    break;
                case "小早":
                    xiaozao+= number[i]*workday;
                    break;
                case "审方":
                    shenfang+= number[i]*workday;
                    break;
                case "核对":
                    hedui+= number[i]*workday;
                    break;
                case "排药":
                    paiyao+= number[i]*workday;
                    break;
                case "电话":
                    dianhua+= number[i]*workday;
                    break;
                case "临时":
                    linshi+= number[i]*workday;
                    break;
                case "长1":
                    chang1+= number[i]*workday;
                    break;
                case "长2":
                    chang2+= number[i]*workday;
                    break;
                case "长3":
                    chang3+= number[i]*workday;
                    break;
                case "晚班":
                    wanban+= number[i]*workday;
                    break;
            }
        }
    }

    //计算某个班次 一个月 周末一共要多少人
    private static void WeekDaySumTimes(String[] work,int[]number){
        for (int i=0;i<work.length;i++) {
            switch (work[i]) {
                case "早班":
                    zaoban+= number[i]*weekday;
                    break;
                case "小早":
                    xiaozao+= number[i]*weekday;
                    break;
                case "审方":
                    shenfang+= number[i]*weekday;
                    break;
                case "核对":
                    hedui+= number[i]*weekday;
                    break;
                case "排药":
                    paiyao+= number[i]*weekday;
                    break;
                case "电话":
                    dianhua+= number[i]*weekday;
                    break;
                case "临时":
                    linshi+= number[i]*weekday;
                    break;
                case "长1":
                    chang1+= number[i]*weekday;
                    break;
                case "长2":
                    chang2+= number[i]*weekday;
                    break;
                case "长3":
                    chang3+= number[i]*weekday;
                    break;
                case "晚班":
                    wanban+= number[i]*weekday;
                    break;
            }
        }
    }

    //某个月有多少个工作日，周少个周末,顺便返回某个月的第一天是星期几
    private static int WorkDayAndWeekDay(String Date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        java.util.Date date;
        int w;
        Calendar calendar1 = Calendar.getInstance();
        try{
            date  = sdf.parse(Date);
            calendar1.setTime(date);
        }catch (Exception e){
            System.out.println("error");
        }
        int sumday = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("一共"+ sumday+"天");
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        //判断每个月第一天是星期几
        w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        int x1;
        int x2;
        int x3;
        if(w==0) w=7;
        x1 = 20;
        x2 = 8;
        x3 = sumday - 28;
        switch (w) {
            case 1:
            case 3:
            case 2:
                x1 = x1 + x3;
                workday = x1;
                weekday = x2;
                break;
            case 4:
                if(sumday==31) {
                    workday = x1+2;
                    weekday = x2+1;
                }
                else if(sumday == 30) {
                    workday = x1+2;
                    weekday = x2;
                }
                else if(sumday == 29) {
                    workday = x1+1;
                    weekday = x2;
                }
                else if(sumday == 28) {
                    workday = x1;
                    weekday = x2;
                }
                break;
            case 5:
                if(sumday==31) {
                    workday = x1+1;
                    weekday = x2+2;
                }
                else if(sumday == 30) {
                    workday = x1+1;
                    weekday = x2+1;
                }
                else if(sumday == 29) {
                    workday = x1+1;
                    weekday = x2;
                }
                else if(sumday == 28) {
                    workday = x1;
                    weekday = x2;
                }
                break;
            case 6:
                if(sumday==31) {
                    workday = x1+1;
                    weekday = x2+2;
                }
                else if(sumday == 30) {
                    workday = x1;
                    weekday = x2+2;
                }
                else if(sumday == 29) {
                    workday = x1;
                    weekday = x2+1;
                }
                else if(sumday == 28) {
                    workday = x1;
                    weekday = x2;
                }
                break;
            case 7:
                if(sumday==31) {
                    workday = x1+2;
                    weekday = x2+1;
                }
                else if(sumday == 30) {
                    workday = x1+1;
                    weekday = x2+1;
                }
                else if(sumday == 29) {
                    workday = x1;
                    weekday = x2+1;
                }
                else if(sumday == 28) {
                    workday = x1;
                    weekday = x2;
                }
                break;
        }
        return w;
    }

    //判断是否会连续三天上同一个班次
    private static void JiShu(int[] number, int WhatNumber, ArrayList<Man> manList){
        for(int i = 0;i< number[WhatNumber];i++){
           if((manList.get(i).replyTimes +1)==3){
               System.out.println(manList.get(i).name+"已经连续两天上同一个班次，重新随机");
               sort(manList);
               JiShu(number,WhatNumber,manList);
           }
        }
    }

    /**
     * 夜班轮班
     * */
    private static ArrayList<Man> NightShift(ArrayList<Man> manList,String Date){

        ArrayList<Man> list ;
        list = manList;
        ArrayList<Man> endList = new ArrayList<>();
        Random random  = new Random();
        for(Man man : list){
            man.number = random.nextInt(1001);
        }
        manList.sort(Man::compareTo);
        String [] week = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date;
        Calendar calendar1 = Calendar.getInstance();
        try{
            date  = sdf.parse(Date);
            calendar1.setTime(date);

        }catch (Exception e){
            System.out.println("error");
        }
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        //判断每个月第一天是星期几
        int w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        monthDay = calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);
        int count = w-1;
        int mansLength = 0;
        for (int i =0;i<calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
            //法定工时167h  每个月
            list.get(mansLength).day = (i+1);
            list.get(mansLength).week = week[count];
            list.get(mansLength).work = "夜班";
            list.get(mansLength).YeBanTimes++;
            list.get(mansLength).Times++;
            Man man = new Man();
            man.day = (i+1);
            man.week = week[count];
            man.work = "夜班";
            man.name = list.get(mansLength).name;

            Schedule schedule = new Schedule();
            schedule.setDutyID(10014);
            schedule.setEmployeeID(manList.get(mansLength).username);
            String s;
            if((i+1)<10){
                s = "0"+(i+1);
            }else {
                s = ""+(i+1);
            }
            schedule.setDate(Date+"-"+s);
            ScheduleList.add(schedule);
            endList.add(man);
            mansLength++;
            count++;
            if (mansLength == list.size()) {
                mansLength = 0;
            }
            if (count == 7) {
                count = 0;
            }
        }
        return endList;
    }

}
