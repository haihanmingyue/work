package other;

public class Man implements Comparable<Man>{
    public String username;
    public String name;
    int day;
    int dutyID;
    int number;
    String week;
    float WorkTime;
    int times ;
    int replyTimes;
     String work = "";
     int noWork;
     int Times; //总次数
     int YeBanTimes;//夜班次数
     int Times2;
     int zaoban;
     int xiaozao;
     int shenfang;
     int hedui;
     int paiyao;
     int dianhua;
     int linshi;
     int chang1;
     int chang2;
     int chang3;
     int wanban;
     int zaobantimes;
     int xiaozaotimes;
     int shenfangtimes;
     int heduitimes;
     int paiyaotimes;
     int dianhuatimes;
     int linshitimes;
     int chang1times;
     int chang2times;
     int chang3times;
     int wanbantimes;



    public Man(){

    }

    public Man(String username ,String name){
        this.username = username;
        this.name = name;
    }

    @Override
    public String toString() {
        return "姓名:"+name+"随机到的点数："+number;
    }

    public static void ToZero(Man man){
        man.day = 0;
        man.dutyID = 0;
        man.number= 0;
        man.WorkTime= 0;
        man.times = 0;
        man.replyTimes= 0;
        man.noWork= 0;
        man.Times= 0; //总次数
        man.YeBanTimes= 0;//夜班次数
        man.Times2= 0;
        man.zaoban= 0;
        man.xiaozao= 0;
        man.shenfang= 0;
        man.hedui= 0;
        man.paiyao= 0;
        man.dianhua= 0;
        man.linshi= 0;
        man.chang1= 0;
        man.chang2= 0;
        man.chang3= 0;
        man.wanban= 0;
        man.zaobantimes= 0;
        man.xiaozaotimes= 0;
        man.shenfangtimes= 0;
        man.heduitimes= 0;
        man.paiyaotimes= 0;
        man.dianhuatimes= 0;
        man.linshitimes= 0;
        man.chang1times= 0;
        man.chang2times= 0;
        man.chang3times= 0;
        man.wanbantimes= 0;
    }

    String toName() {
        return "姓名:"+name;
    }

    @Override
    public int compareTo(Man o) {
//        在JDK7版本以上，Comparator要满足自反性，传递性，对称性
//        if(this.number > o.number){
//            return 1;
//        }else{
//            return -1;
//        }
       // 改成这个 return this.number > o.number ? 1 : (this.number == o.number ? 0 : -1);

        return Integer.compare(this.number, o.number); //改成这个
    }


}
