package com.itheima.control.servlet;

import com.itheima.domain.Books;
import com.itheima.service.PageService;
import com.itheima.service.impl.PageServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Ticket: ${file_name}
 *
 * @author zhwei
 * @email zhaowei@boranet.com.cn
 * @Date: 2020/3/15 15:59
 */
@WebServlet("/findBooksServlet")
public class FindBooksServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1、获取id
        String id = request.getParameter("id");
        //2、调用Service方法查询该记录
        PageService service = new PageServiceImpl();
        Books books = service.findBookById(id);
        //3、将User存入request
        request.setAttribute("books",books);
        //4、转发到update.jsp
        request.getRequestDispatcher("/update.jsp").forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
