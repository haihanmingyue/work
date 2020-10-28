package other;


import dao.Dutytype;
import dao.Schedule;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 测试用
 * */

public class CeShi {

    public static void main(String[] args) {

    }


    public static void Scheduling(int[] workNumber,int[] number,float WorkTime,float NightWorkTime,List<Dutytype> WorkingDay,List<Dutytype>Weekend, ArrayList<Man> manList, String Date){


        ArrayList<Man> bmanList = new ArrayList<>();
        ArrayList<Man> cmanList = new ArrayList<>();
        int sum=0;
        int sum1=0;
        for(int peoplenum :workNumber){
            sum+=peoplenum;
        }
        for(int peoplenum :workNumber){
            sum1+=peoplenum;
        }
        Sort(manList);
        String [] week = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date;
        int w;
        Calendar calendar1 = Calendar.getInstance();
        try{
            date  = sdf.parse(Date);
            calendar1.setTime(date);

        }catch (Exception e){
            System.out.println("error");
        }
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        //判断每个月第一天是星期几
        w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        System.out.println(Date);
        ArrayList<Man> YBList = NightShift(NightWorkTime,manList,Date);
        ArrayList<Man> ADDYBList = new ArrayList<>();
        ArrayList<Man> dManList = new ArrayList<>();
        boolean temp = true;
        int allpeople = 0;
        //做一个比较器,对象里面数据种类很多 ，不能直接排序
        Comparator<Man> manComparator = new Comparator<Man>() {
            @Override
            public int compare(Man man1, Man man2) {
                //按照damage进行排序
                return Integer.compare(man2.noWork, man1.noWork);  //正数表示h1比h2要大  如果h1大于h2 返回1 就是小到大 返回-1就是大到小

            }
        };
        for(int i=0;i<calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {
            if(i<YBList.size()){
                System.out.println(i+1+"号是"+YBList.get(i).name+"上夜班，移除");
                YBList.get(i).Times++;
                YBList.get(i).WorkTime+=NightWorkTime;
                YBList.get(i).times+=1;
                ADDYBList.add(YBList.get(i));
                manList.remove(YBList.get(i));
            }
            //轮换回第一个人
            if(i==YBList.size()){
                allpeople = i;
                System.out.println(i+1+"号是"+YBList.get(0).name+"上夜班");
                YBList.get(0).Times++;
                YBList.get(0).WorkTime+=NightWorkTime;
                YBList.get(0).times+=1;
                ADDYBList.add(YBList.get(0));
                manList.remove(YBList.get(0));
            }
            if(i>YBList.size()){
                int newNumber = i-allpeople;
                System.out.println(i+1+"号是"+YBList.get(newNumber).name+"上夜班");
                YBList.get(newNumber).Times++;
                YBList.get(newNumber).WorkTime+=NightWorkTime;
                YBList.get(newNumber).times+=1;
                ADDYBList.add(YBList.get(newNumber));
                manList.remove(YBList.get(newNumber));
            }
            if(w==7 || w==6){
                for (int count = 0; count < sum1; count++) {
                    //法定工时167h  每个月
                    if(manList.size()>=7){
                        Algorithm(workNumber,number,WorkTime,NightWorkTime,Date,manList,bmanList,cmanList,week,WorkingDay,Weekend,w,count,i);
                    }else {
                        temp = false;
                    }
                }
            }
            if(w>=1 && w<=5){
                for (int count = 0; count < sum; count++) {
                    //法定工时167h  每个月
                    if(manList.size()>=10){
                        Algorithm(workNumber,number,WorkTime,NightWorkTime,Date,manList,bmanList,cmanList,week,WorkingDay,Weekend,w,count,i);
                    }else {
                        temp = false;
                    }
                }
            }
            if(w==7 || w==6){
                for (int count = sum1; count < manList.size(); count++) {
                    manList.get(count).noWork++;
                    manList.get(count).times=0;
                    manList.get(count).work = "休息";
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10009);
                    schedule.setEmployeeID(manList.get(count).username);
                    String s1;
                    if(i<10){
                        s1 = "0"+i;
                    }else {
                        s1 = ""+i;
                    }
                    schedule.setDate(Date+"-"+s1);
//                    if(Schedule.insertSchedule(schedule)){
//                        System.out.println("成功");
//                    }else {
//                        System.out.println("失败");
//                    }
                }
            }
            if(w>=1 && w<=5){
                for (int count = sum; count < manList.size(); count++) {
                    manList.get(count).noWork++;
                    manList.get(count).times=0;
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(10009);
                    schedule.setEmployeeID(manList.get(count).username);
                    schedule.setDate(Date);
//                    if(Schedule.insertSchedule(schedule)){
//                        System.out.println("成功");
//                    }else {
//                        System.out.println("失败");
//                    }
                }
            }

            removeDuplicate(cmanList);
            manList.addAll(cmanList);
            cmanList.clear();
            System.out.println("-------分割线------");

            if(i<YBList.size()){
                System.out.println("添加夜班被移除的："+ADDYBList.get(i).name);
                manList.add(ADDYBList.get(i));
            }
            if(i==YBList.size()){
                System.out.println("添加夜班被移除的："+ADDYBList.get(i).name);
                manList.add(ADDYBList.get(i));
            }
            if(i>YBList.size()){
                int newNumber = i-allpeople;
                System.out.println("添加夜班被移除的："+ADDYBList.get(newNumber).name);
                manList.add(ADDYBList.get(newNumber));
            }
            System.out.println("-------分割线------");
            Sort(manList);
            for(int x = 0;x<manList.size();x++){
                if((manList.get(x).noWork+1)>=2){
                    System.out.println("将"+manList.get(x).name+"插入第一位,"+ manList.get(x).noWork);
                    Man man = manList.get(x);
                    manList.remove(manList.get(x));
//                  manList.add(0,man);
                    dManList.add(man);
                }
            }
            dManList.sort(manComparator);
            manList.addAll(0,dManList);
            for(Man man : dManList){
                man.noWork = 0;
            }
            dManList.clear();


            if(!temp){
                System.out.println("人数在"+(i+1)+"号已经不足以进行排班，停止运行");
                break;
            }
            if(w==7){
                w=0;
            }
            w++;


        }
        Comparator<Man> end = (man1, man2) -> {

            return Float.compare(man1.WorkTime, man2.WorkTime);  //正数表示h1比h2要大  如果h1大于h2 返回1 就是小到大 返回-1就是大到小
        };
        System.out.println("总计："+(manList.size()+bmanList.size())+" 人");

        ArrayList<Man> endend = new ArrayList<>();
        endend.addAll(manList);
        endend.addAll(bmanList);
        endend.sort(end);
        for(Man man : endend){
            System.out.println(man.name+"上了"+man.Times+"次夜班"+","+man.Times2+"次正常班次"+",工时："+man.WorkTime);
        }
        System.out.println("执行结束");
    }

    /**
     * removeDuplicate ： 去重
     * */
    private static void removeDuplicate(ArrayList<Man> list) {
        HashSet<Man> h = new HashSet<>(list);
        list.clear();
        list.addAll(h);
    }
    /**
     * Algorithm ：算法
     * */
    private static void Algorithm(int[]number1,int[] number,float WorkTime , float NightWorkTime,String Date,ArrayList<Man> manList,ArrayList<Man> banList,ArrayList<Man> canList,String[] week,List<Dutytype> WorkingDay,List<Dutytype> Weekend,int w,int count,int i){
        //法定工时167h  每个月
        int flag = w-1;
        int sum = 0;
        for(int x :number){
            sum +=x;
        }
        int sum1 = 0;
        for(int x :number1){
            sum1 +=x;
        }
            if (time(manList.get(count))) {
                manList.get(count).week = week[flag];
                if(week[flag].equals("星期六") || week[flag].equals("星期日")){

                    for (int x = 0; x < number.length; x++) {
                        for (int k = 0; k < number[x]; k++) {
                            manList.get(count).work = Weekend.get(x).getDutyname();
                            manList.get(count).dutyID = Weekend.get(x).getId();
                            count++;
                            if((count+1)>sum){
                                count=0;
                                System.out.println();
                            }
                        }
                    }

                }else{
                    for (int x = 0; x < number1.length; x++) {
                        for (int k = 0; k < number1[x]; k++) {
                            manList.get(count).work = WorkingDay.get(x).getDutyname();
                            manList.get(count).dutyID = WorkingDay.get(x).getId();
                            count++;
                            if((count+1)>sum1){
                                count=0;
                            }
                        }
                    }
                }
                if ((manList.get(count).times + 1) == 3 ) {
                    System.out.println(week[flag]+","+manList.get(count).toName() + "," + manList.get(count).work + "," + "已连续两天都有工作,暂时移除,现有人数");
                    manList.get(count).work = "休息";
                    manList.get(count).times = 0;
                    Schedule schedule = new Schedule();
                    String s;
                    if((i+1)<10){
                       s = "0"+(i+1);
                    }else {
                        s = ""+(i+1);
                    }
                    schedule.setDate(Date+"-"+s);
                    schedule.setDutyID(10009);
                    schedule.setEmployeeID(manList.get(count).username);
//                    if(Schedule.insertSchedule(schedule)){
//                        System.out.println("数据插入成功");
//                    }else {
//                        System.out.println("数据插入失败");
//                    }
                    canList.add(manList.get(count));
                    manList.remove(count);
                    System.out.println(manList.size()+"人");
                    if (manList.size() >= 7) {
                       Algorithm(number1,number,WorkTime,NightWorkTime,Date,manList, banList, canList,week, WorkingDay,Weekend,w, count, i);
                    }else {
                        //如果人数去掉小于指定人数，就加回所有人，进行安排，直到人数足够且没有人连续三天上班
                        removeDuplicate(canList);
                        manList.addAll(canList);
                        canList.clear();
                        Algorithm(number1,number,WorkTime,NightWorkTime,Date,manList, banList, canList,week,WorkingDay,Weekend,w, count, i);
                    }
                }
                else if ((manList.get(count).times + 1)!= 3) {
                    manList.get(count).WorkTime += WorkTime;
                    manList.get(count).times++;
                    manList.get(count).noWork=0;
                    manList.get(count).Times2++;
                    String s;
                    if((i+1)<10){
                        s = "0"+(i+1);
                    }else {
                        s = ""+(i+1);
                    }
                    Schedule schedule = new Schedule();
                    schedule.setDutyID(manList.get(count).dutyID);
                    schedule.setEmployeeID(manList.get(count).username);
                    schedule.setDate(Date+"-"+s);
//                    if(Schedule.insertSchedule(schedule)){
//                        System.out.println("成功");
//                    }else {
//                        System.out.println("失败");
//                    }
                    System.out.println((i + 1) + "号," +week[flag]+","+ manList.get(count).name + "," + manList.get(count).work + "，工时：" + manList.get(count).WorkTime);
                }

            }else{
                System.out.println((i + 1) + "号," + manList.get(count).toName() + "," + manList.get(count).work + "，工时：" + manList.get(count).WorkTime + "，会超过法定工时，移除,现有人数");
                banList.add(manList.get(count));
                manList.remove(count);
                System.out.println(manList.size()+"人");
                if(w==6 || w==7){
                    if (manList.size() >= sum1) {
                        Algorithm(number1,number,WorkTime,NightWorkTime,Date,manList, banList, canList,week,WorkingDay,Weekend, w,count, i);
                    }else{
                        System.out.println("人数太少了，多加些人吧");

                    }
                }
                else if(w>=1 && w<=5){
                    if (manList.size() >= sum) {
                        Algorithm(number1,number,WorkTime,NightWorkTime,Date,manList, banList, canList,week, WorkingDay,Weekend,w, count, i);
                    }else{
                        System.out.println("人数太少了，多加些人吧");

                    }
                }

            }


    }

    /**
     * 排序
     * */
    private static void Sort(ArrayList<Man> list){
            Random random = new Random();
            for(Man man : list){
                man.number = random.nextInt(1001);
            }
            list.sort(Man::compareTo);
    }


    /**
     * 夜班轮班
     * */
    private static ArrayList<Man> NightShift(float NightWorkTime,ArrayList<Man> manList,String Date){

        ArrayList<Man> list ;
        list = manList;
        ArrayList<Man> endList = new ArrayList<>();
        Random random  = new Random();
        for(Man man : list){
            man.number = random.nextInt(1001);
        }
        manList.sort(Man::compareTo);
        for(Man man : list){
            System.out.println(man.toString());
        }
        String [] week = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date;
        int w;
        Calendar calendar1 = Calendar.getInstance();
        try{
            date  = sdf.parse(Date);
            calendar1.setTime(date);

        }catch (Exception e){
            System.out.println("error");
        }
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        //判断每个月第一天是星期几
        w = calendar1.get(Calendar.DAY_OF_WEEK)-1;
        if(w==0) w=7;
        System.out.println(w);
        int count = 0;
        int mansLength = 0;
        for (int i =0;i<calendar1.getActualMaximum(Calendar.DAY_OF_MONTH);i++) {

            //法定工时167h  每个月
            list.get(mansLength).week = week[count];
            list.get(mansLength).work = "夜班";
            Schedule schedule = new Schedule();
            schedule.setDutyID(10014);
            schedule.setEmployeeID(manList.get(count).username);
            String s;
            if((i+1)<10){
                s = "0"+(i+1);
            }else {
                s = ""+(i+1);
            }
            schedule.setDate(Date+"-"+s);
//            if(Schedule.insertSchedule(schedule)){
//                System.out.println("成功");
//            }else {
//                System.out.println("失败");
//            }
            endList.add(list.get(mansLength));
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

    /**
     * 法定工时
     * */
    private static boolean time(Man man){
        return (man.WorkTime+10 <= 167);
    }



}
