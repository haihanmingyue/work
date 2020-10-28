package dao;

import Util.MybatisUtil;

import java.sql.Timestamp;
import java.util.List;

public class UserMessage {
    private int id;
    private String username;
    private String name;
    private int age;
    private int sex;
    private String minzu;
    private String qqnumber;
    private String weixin;
    private String idcard;
    private Timestamp birthday;
    private String departname;
    private int departID;
    //部门ID 后续修改用，不要直接用部门名字
    private String touxiangurl;
    //排班的时候控制选中的颜色
    public int flag=0;


    public UserMessage(){

    }
    public String getXingbie(){
        if( getSex()==1){
            return "男";
        }else {
            return "女";
        }
    }

    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }


    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getQqnumber() {

        return qqnumber;
    }

    public void setQqnumber(String qqnumber) {
        this.qqnumber = qqnumber;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public int getId() {return id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getTouxiangurl() {
        return touxiangurl;
    }

    public void setTouxiangurl(String touxiangurl) {
        this.touxiangurl = touxiangurl;
    }

    //select
    public static List<UserMessage> AllUserMessageList(){
        List<UserMessage> userMessages = MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).getUserMessageList();
        MybatisUtil.closeSqlSession();
        return userMessages;
    }

    /**
     * 通过key值搜索 存入List, key可以是 username， name，departname（现在系统部门是药房所以没用）
     *
     */
    public static List<UserMessage> AllUserMessageListByKey(String string){
        List<UserMessage> userMessages = MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).selectByKey(string);
        MybatisUtil.closeSqlSession();
        return userMessages;
    }

    public static UserMessage selectUserMessage(String username){
        UserMessage userMessage = null;
        try {
            userMessage = MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).selectUserMessage(username);
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return userMessage;
    }

    public static UserMessage selectUserMessageByName(String name){
        UserMessage userMessage = null;
        try{
            userMessage = MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).selectUserMessageByName(name);
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return userMessage;
    }

    //update
    public static boolean updateUserMessage(UserMessage userMessage){
        boolean flag = false;
        try{
            MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).updateUserMessage(userMessage);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean deleteUserMessage(UserMessage userMessage){
        boolean flag = false;
        try{
            MybatisUtil.getSqlSession().getMapper(UserMessageDao.class).deleteUserMessage(userMessage.getUsername());
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }


}
