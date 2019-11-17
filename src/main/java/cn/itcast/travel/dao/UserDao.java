package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findByUserName(String userName);

    void saveUser(User user);

    User findByCode(String code);

    void updateStatus(User user);

    User findByUserNameAndPwd(User loginUser);
}
