package dao;


import java.util.List;


public interface UserDao {

    /**
     * 搜索所有用户
     * */
    public List<UserLoad> getUserList();

    /**
     * 通过用户名模糊搜索
     * */
    public List<UserLoad> selectLikeUsername(String string);
    /**
     * 通过用户名搜索
     * */
    public UserLoad selectByUsername(String username);
    /**
     * 通过权限级别搜索
     * */
    public UserLoad selectByAdmin(Integer admin);
    /**
     * 通过用户名删除
     * */
    boolean deleteUserLoad(String username);
     /**
      * 插入数据
      * */
     int insertUserLoad(UserLoad userLoad);

     /**
      * 更新数据
      * */
     int updateUserLoad(UserLoad userLoad);

     /**
      * 单独更新tel和email
      * */
     int updateothers(UserLoad userLoad);
     /**
      * 更新密码
      * */
     int updatePassword(UserLoad userLoad);

}
