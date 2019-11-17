/**
 * @ClassName: SellerDaoImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 13:36 2019-07-12
 * @Version: 1.0
 **/

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findBySid(int sid) {
        String sql = "SELECT * FROM tab_seller WHERE sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
    }
}
