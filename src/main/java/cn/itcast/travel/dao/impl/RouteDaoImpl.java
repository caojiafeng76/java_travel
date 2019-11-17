/**
 * @ClassName: RouteDaoImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 16:17 2019-07-10
 * @Version: 1.0
 **/

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findTotalCount(int cid, String rname) {
        String sql = "SELECT count(*) FROM tab_route WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        //定义参数集合
        List<Object> params = new ArrayList<>();
        if (cid != 0) {
            sb.append(" AND cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() != 0) {
            sb.append(" AND rname LIKE ?");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        String sql = "SELECT * FROM tab_route WHERE 1=1";
        StringBuilder sb = new StringBuilder(sql);
        //定义参数集合
        List<Object> params = new ArrayList<>();
        if (cid != 0) {
            sb.append(" AND cid = ?");
            params.add(cid);
        }
        if (rname != null && rname.length() != 0) {
            sb.append(" AND rname LIKE ?");
            params.add("%" + rname + "%");
        }
        sb.append(" LIMIT ?, ?");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    @Override
    public Route findByRid(int rid) {
        String sql = "SELECT * FROM tab_route WHERE rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
