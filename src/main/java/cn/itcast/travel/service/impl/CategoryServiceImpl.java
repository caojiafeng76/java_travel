/**
 * @ClassName: CategoryServiceImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 12:43 2019-07-10
 * @Version: 1.0
 **/

package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private Jedis jedis = JedisUtil.getJedis();

    @Override
    public List<Category> findAll() {
        List<Category> list = null;
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if (categorys == null || categorys.size() == 0) {
            list = categoryDao.findAll();
            for (int i = 0; i < list.size(); i++) {
                jedis.zadd("category", list.get(i).getCid(), list.get(i).getCname());
            }
        } else {
            list = new ArrayList<>();
            for (Tuple tuple : categorys) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                list.add(category);
            }
        }
        return list;
    }
}
