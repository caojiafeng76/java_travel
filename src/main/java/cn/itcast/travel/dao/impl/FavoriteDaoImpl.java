/**
 * @ClassName: FavoriteDaoImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 14:52 2019-07-13
 * @Version: 1.0
 **/

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT count(*) FROM tab_favorite WHERE rid = ?;";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public Favorite isFavorite(int rid, int uid) {
        String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ?;";
        try {
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "INSERT INTO tab_favorite VALUES (?, ?, ?);";
        template.update(sql, rid, new Date(), uid);
    }
}
