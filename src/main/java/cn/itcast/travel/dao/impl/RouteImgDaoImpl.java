/**
 * @ClassName: RouteImgImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 11:10 2019-07-12
 * @Version: 1.0
 **/

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
    }
}

