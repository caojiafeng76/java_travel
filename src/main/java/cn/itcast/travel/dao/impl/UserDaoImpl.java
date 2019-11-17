/**
 * @ClassName: UserDaoImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 14:10 2019-07-08
 * @Version: 1.0
 **/

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /*
     *@Author Administrator
     *@Description 根据用户名查询用户信息
     *@Date 15:15 2019-07-08
     *@Param userName 用户名
     *@return 用户对象
     **/

    @Override
    public User findByUserName(String userName) {
        String sql = "SELECT * FROM tab_user WHERE name = ?;";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), userName);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * @return void
     * @Author Administrator
     * @Description 保存注册用户信息
     * @Date 16:40 2019-07-09
     * @Param user 注册对象
     **/

    @Override
    public void saveUser(User user) {
        String sql =
                "INSERT INTO tab_user (username, password, name, birthday, sex, telephone, email, status, code)";
        sql +=
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode());
    }

    /**
     * @return cn.itcast.travel.domain.User
     * @Author Administrator
     * @Description 根据激活码查询用户
     * @Date 16:41 2019-07-09
     * @Param [code]
     **/

    @Override
    public User findByCode(String code) {
        String sql =
                "SELECT uid, username, password, name, birthday, sex, telephone, email, status, code FROM tab_user WHERE code = ?;";
        try {
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
            return user;
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * @return void
     * @Author Administrator
     * @Description 修改用户激活状态
     * @Date 16:41 2019-07-09
     * @Param [user]
     **/

    @Override
    public void updateStatus(User user) {
        String sql =
                "UPDATE tab_user SET status = 'Y' WHERE uid = ?;";
        template.update(sql, user.getUid());
    }

    /**
     * @return cn.itcast.travel.domain.User
     * @Author Administrator
     * @Description 根据用户名和密码查询用户
     * @Date 16:42 2019-07-09
     * @Param [loginUser]
     **/

    @Override
    public User findByUserNameAndPwd(User loginUser) {
        User user = null;
        String sql =
                "SELECT * FROM tab_user WHERE username = ? AND password = ?;";
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), loginUser.getUsername(), loginUser.getPassword());

        } catch (DataAccessException e) {

        }
        return user;
    }
}
