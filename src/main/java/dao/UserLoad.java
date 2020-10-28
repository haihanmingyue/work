package dao;


import Util.DateUtil;
import Util.MD5Util;
import Util.MybatisUtil;

import java.sql.Timestamp;
import java.util.List;


public class UserLoad {
    private Integer id;
    private String username;
    private String password;
    private Integer admin;
    private Timestamp registrationdate;
    private String telphone;
    private String email;


    public Timestamp getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Timestamp registrationdate) {
        this.registrationdate = registrationdate;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //select
    public static List<UserLoad> AllUserLoad(){
        List<UserLoad> userLoad =null;
        try{
            userLoad = MybatisUtil.getSqlSession().getMapper(UserDao.class).getUserList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MybatisUtil.closeSqlSession();
        }
        return userLoad;
    }

    public static List<UserLoad> selectLikeUsername(String username){
        List<UserLoad> userLoad =null;
        try{
            userLoad = MybatisUtil.getSqlSession().getMapper(UserDao.class).selectLikeUsername(username);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MybatisUtil.closeSqlSession();
        }
        return userLoad;
    }

    public static UserLoad selectByUsername(String username){
        UserLoad userLoad = null;
        try{
            userLoad = MybatisUtil.getSqlSession().getMapper(UserDao.class).selectByUsername(username);
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return userLoad;
    }

    //add
    public static void addUser(String s1, String s2){
        UserLoad userLoad = new UserLoad();
        userLoad.setUsername(s1);
        userLoad.setPassword(MD5Util.convertMD5(s2));
        userLoad.setRegistrationdate(DateUtil.getNowDate());
        userLoad.setAdmin(0);
        userLoad.setTelphone("");
        try {
            MybatisUtil.getSqlSession().getMapper(UserDao.class).insertUserLoad(userLoad);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            MybatisUtil.getSqlSession().commit();//一定要加这个，才是真正提交事务
            MybatisUtil.closeSqlSession();
        }


    }
    public static void insertUserLoad(UserLoad userLoad){

        try{
            MybatisUtil.getSqlSession().getMapper(UserDao.class).insertUserLoad(userLoad);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
    }

    //delete
    public static boolean deleteUserLoad(String username){
        boolean flag;
        flag= MybatisUtil.getSqlSession().getMapper(UserDao.class).deleteUserLoad(username);
        MybatisUtil.getSqlSession().commit();
        MybatisUtil.closeSqlSession();
        return flag;
    }

    //update
    public static boolean updatePassword(UserLoad userLoad){
        boolean flag = false;
        try {
            MybatisUtil.getSqlSession().getMapper(UserDao.class).updatePassword(userLoad);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    public static void updateUserLoad(UserLoad userLoad){

        try{
            MybatisUtil.getSqlSession().getMapper(UserDao.class).updateUserLoad(userLoad);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();

        }
    }


    public static boolean updateothers(UserLoad userLoad){
        boolean flag = false;
        try{
            MybatisUtil.getSqlSession().getMapper(UserDao.class).updateothers(userLoad);
            MybatisUtil.getSqlSession().commit();
            MybatisUtil.closeSqlSession();
            flag = true;
        }catch (Exception e){
            System.out.println("失败");
            e.printStackTrace();
        }
        return flag;
    }

}
