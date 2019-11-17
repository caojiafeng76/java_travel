/**
 * @ClassName: FavoriteServiceImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 15:21 2019-07-13
 * @Version: 1.0
 **/

package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        return favoriteDao.isFavorite(Integer.parseInt(rid), uid) != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
