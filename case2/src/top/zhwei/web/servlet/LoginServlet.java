package top.zhwei.web.servlet;

import org.apache.commons.beanutils.BeanUtils;
import top.zhwei.domain.User;
import top.zhwei.service.UserService;
import top.zhwei.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Ticket: ${file_name}
 *
 * @author zhwei
 * @email zhaowei@boranet.com.cn
 * @Date: 2020/2/14 20:45
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("utf-8");
        //2.获取数据
        String verifycode = request.getParameter("verifycode");
        //3.验证码校验
        //3.验证码校验
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//确保验证码一次性
        if(!checkcode_server.equalsIgnoreCase(verifycode)) {
            //验证码不正确
            //提示信息
            request.setAttribute("login_msg","验证码错误！");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

            return;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        //4.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //5.调用Service查询
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);
        //6.判断是否登录成功
        if(loginUser != null){
            //登录成功
            //将用户存入session
            session.setAttribute("user",loginUser);
            //跳转页面
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        }else{
            //登录失败
            //提示信息
            request.setAttribute("login_msg","用户名或密码错误！");
            //跳转登录页面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
