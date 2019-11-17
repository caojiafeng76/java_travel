package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();
    private ResultInfo info = new ResultInfo();

    /**
     * @return void
     * @Author Administrator
     * @Description 激活
     * @Date 11:07 2019-07-10
     * @Param [request, response]
     **/

    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//获取随机生成的唯一编号
        String code = request.getParameter("code");
//        UserService service = new UserServiceImpl();
        if (code != null && code.length() != 0) {
            boolean flag = service.active(code);
            String msg = null;
            if (flag) {
                //激活成功
                msg = "激活成功，请<a href='login.html'>登录</a>";
            } else {
                //激活失败
                msg = "激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * @return void
     * @Author Administrator
     * @Description 退出
     * @Date 11:06 2019-07-10
     * @Param [request, response]
     **/

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //跳转登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        writeValue(user, response);
    }

    /**
     * @return void
     * @Author Administrator
     * @Description 登录
     * @Date 11:06 2019-07-10
     * @Param [request, response]
     **/

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (checkCode(request, response)) return;

        Map<String, String[]> map = request.getParameterMap();
        User loginUser = new User();
        try {
            BeanUtils.populate(loginUser, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        User user = service.login(loginUser);
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误!");
        }
        if (user != null && !"Y".equals(user.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("您尚未激活，请查询邮件进行激活！或联系管理员！");
        }
        if (user != null && "Y".equals(user.getStatus())) {
            info.setFlag(true);
            request.getSession().setAttribute("user", user);
        }
        writeValue(info, response);
    }

    /**
     * @return boolean
     * @Author Administrator
     * @Description 验证码
     * @Date 11:12 2019-07-10
     * @Param [request, response]
     **/

    private boolean checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//防止复用
        if (checkcode_server == null || checkcode_server.length() == 0 || !checkcode_server.equalsIgnoreCase(check)) {
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            writeValue(info, response);
            return true;
        }
        return false;
    }

    /**
     * @return void
     * @Author Administrator
     * @Description 注册
     * @Date 11:05 2019-07-10
     * @Param [request, response]
     **/

    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码
        if (checkCode(request, response)) return;
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = service.regist(user);
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }
        writeValue(info, response);
    }
}
