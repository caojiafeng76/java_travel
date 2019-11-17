/**
 * @ClassName: CategoryDaoImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 12:39 2019-07-10
 * @Version: 1.0
 **/

package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        return template.query("SELECT * FROM tab_category;", new BeanPropertyRowMapper<>(Category.class));
    }
}
