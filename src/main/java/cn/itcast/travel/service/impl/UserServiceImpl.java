/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 14:09 2019-07-08
 * @Version: 1.0
 **/

package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean regist(User user) {
        User u = userDao.findByUserName(user.getUsername());
        if (u != null) {
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        String content =
                "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";
        MailUtils.sendMail(user.getEmail(), content, "【黑马旅游网】激活邮件");
        userDao.saveUser(user);
        return true;
    }

    @Override
    public boolean active(String code) {
       User user = userDao.findByCode(code);
        if (user == null) {
            return false;
        }
        userDao.updateStatus(user);
        return true;
    }

    @Override
    public User login(User loginUser) {

        return userDao.findByUserNameAndPwd(loginUser);
    }
}
