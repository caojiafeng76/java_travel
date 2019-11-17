/**
 * @ClassName: PageBean
 * @Description: 分页对象
 * @Author: Administrator
 * @Date: 15:57 2019-07-10
 * @Version: 1.0
 **/

package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalCount;//总记录数
    private int totalPage;//总页数
    private int currentPage;//当前页
    private int pageSize;//每页显示的记录数
    private List<T> list;//每页显示的内容

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
