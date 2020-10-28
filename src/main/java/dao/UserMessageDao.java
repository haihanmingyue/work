package dao;

import java.util.List;

public interface UserMessageDao {

    //用过username搜索
    public UserMessage selectUserMessage(String username);

    //用过name搜索,有重名的话就不能用了
    public UserMessage selectUserMessageByName(String name);

    //搜索全部
    public List<UserMessage> getUserMessageList();

    //通过key搜索
    public List<UserMessage> selectByKey(String key);

    //通过用户名删除
    boolean deleteUserMessage(String username);

    //插入数据
    int insertUserMessage(UserMessage userMessage);

    //更新数据
    int updateUserMessage(UserMessage userMessage);

//  int updateUserTelANDEmail(UserMessage userMessage);

    //更新数据
    int updateUserTouX(String username);
}
