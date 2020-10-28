package other;

public class BanCi implements Comparable<BanCi>{
    String dutyname;
    int id;
    int sum;
    int workNeedNumber;//工作日需要人数
    int weekNeedNumber;//周末需要人数

    @Override
    public int compareTo(BanCi o) {
        return Integer.compare(this.id, o.id);
    }
}
