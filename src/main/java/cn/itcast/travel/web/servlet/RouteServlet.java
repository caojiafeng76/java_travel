package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        String cid_str = request.getParameter("cid");
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        int cid = 0;
        if (cid_str != null && cid_str.length() > 0 && !"null".equals(cid_str)) {
            cid = Integer.parseInt(cid_str);
        }
        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSize_str != null && pageSize_str.length() > 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            pageSize = 5;
        }
        PageBean<Route> pb = routeService.pageQuery(cid, currentPage, pageSize, rname);
        writeValue(pb, response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findOne(rid);
        writeValue(route, response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        int uid = getUid(request);
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag, response);
    }

    private int getUid(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        return user == null ? 0 : user.getUid();
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        int uid = getUid(request);
        if (uid == 0) return;
        favoriteService.add(rid,uid);
    }
}
