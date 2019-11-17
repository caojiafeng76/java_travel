package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    int findCountByRid(int rid);

    Favorite isFavorite(int rid, int uid);

    void add(int rid, int uid);
}
